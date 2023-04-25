package com.example.homework2.dialog.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2.R
import com.example.homework2.channels.domain.model.TopicModel
import com.example.homework2.common.adapter.MainAdapter
import com.example.homework2.common.adapter.decorators.SpaceItemDecorator
import com.example.homework2.common.adapter.decorators.StickyHeaderItemDecoration
import com.example.homework2.common.hideKeyboard
import com.example.homework2.common.lazyUnsafe
import com.example.homework2.common.showToastError
import com.example.homework2.databinding.FragmentDialogBinding
import com.example.homework2.dialog.di.DaggerDialogComponent
import com.example.homework2.dialog.domain.model.MessageModel
import com.example.homework2.dialog.presentation.elm.DialogEffect
import com.example.homework2.dialog.presentation.elm.DialogEvent
import com.example.homework2.dialog.presentation.elm.DialogState
import com.example.homework2.dialog.presentation.elm.DialogStateManager
import com.example.homework2.dialog.presentation.ui.adapters.main.delegates.date.DateDelegate
import com.example.homework2.dialog.presentation.ui.adapters.main.delegates.message.MessageDelegate
import com.example.homework2.dialog.presentation.ui.adapters.main.delegates.message.MessageDelegateItem
import com.example.homework2.getAppComponent
import com.example.homework2.views.EmojiView
import dagger.Lazy
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.StoreHolder
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class MessengerFragment : ElmFragment<DialogEvent, DialogEffect, DialogState>() {

    private lateinit var streamName: String
    private lateinit var topicName: String
    private var _binding: FragmentDialogBinding? = null
    private val binding get() = _binding!!

    private var previousItemCount = 0
    private var activityResultPickMedia : ActivityResultLauncher<PickVisualMediaRequest>? = null
    private val adapter by lazyUnsafe {
        (MainAdapter(
            MessageDelegate(
                { showBottomSheet(it) },
                { msg, emoji -> updateEmojiCounter(msg, emoji) }
            ),
            DateDelegate()))
    }

    private val spaceItemDecorator: SpaceItemDecorator by lazyUnsafe {
        SpaceItemDecorator(
            requireContext(),
            12f
        )
    }

    private val stickyHeaderItemDecoration: StickyHeaderItemDecoration by lazyUnsafe { StickyHeaderItemDecoration() }

    override val initEvent: DialogEvent
        get() = DialogEvent.Ui.Init

    override val storeHolder: StoreHolder<DialogEvent, DialogEffect, DialogState>
        get() = globalStoreHolder.get()

    @Inject
    lateinit var globalStoreHolder: Lazy<StoreHolder<DialogEvent, DialogEffect, DialogState>>

    @Inject
    lateinit var stateManager: Lazy<DialogStateManager>
    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupPhotoPicker()
        setupAppComponent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initFieldFromBundle()
        initList()
        _binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setStreamAndTopic()
        setButtonSender()
        configureMessageSender()
        setActionBtnBack()
        setActionsBtnRetry()
        setPreloadMessages()
        removeDefaultChangedAnimation()
    }

    override fun onStop() {
        binding.messageSender.etWrite.clearFocus()
        store.accept(DialogEvent.Ui.DeleteOldMessages(streamName, topicName))
        super.onStop()
    }

    override fun render(state: DialogState) {
        stateManager.get().state = state
        binding.apply {
            loadingStateContainer.root.isVisible = state.isLoading
            rvMessages.isVisible = !state.isLoading && state.error == null
            state.dialogList?.let { adapter.submitList(it) }
            errorStateContainer.root.isVisible = state.error != null
        }
    }

    override fun handleEffect(effect: DialogEffect) {
        when (effect) {
            DialogEffect.MessageDidNotSend -> {
                val msg = getString(R.string.error_no_connection_message)
                showError(msg)
            }
            DialogEffect.CounterEmojiDidNotChanged -> {
                val msg = getString(R.string.error_no_connection_reaction)
                showError(msg)
            }
            DialogEffect.NewEmojiDidNotAdded -> {
                val msg = getString(R.string.error_no_connection_reaction)
                showError(msg)
            }
            DialogEffect.ListNotLoaded -> {
                val msg = getString(R.string.error_no_connection)
                showError(msg)
            }
            DialogEffect.ScrollToLastPosition -> scrollAdapterToLastPosition()
            DialogEffect.NewMessageAdded -> {
                updateMessageSender()
                scrollAdapterToLastPosition()
            }
        }
    }

    private fun setupAppComponent() {
        val appComponent = requireContext().getAppComponent()

        val dialogComponent = DaggerDialogComponent.builder()
            .lifecycle(lifecycle)
            .appComponent(appComponent)
            .build()

        dialogComponent.inject(this)
    }
    private fun setPreloadMessages() {
        binding.rvMessages.addOnScrollListener (
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
                    val itemCount = layoutManager.itemCount
                    if (firstVisiblePosition <= 5 && previousItemCount != itemCount) {
                        val mainAdapter = recyclerView.adapter as MainAdapter
                        val msg = mainAdapter.currentList[1] as MessageDelegateItem
                        store.accept(DialogEvent.Ui.PreLoadDialogList(streamName, topicName, msg.id()))

                        previousItemCount = itemCount
                    }
                }
            }
        )
    }
    private fun setActionsBtnRetry() {
        binding.errorStateContainer.retryButton.setOnClickListener {
            store.accept(DialogEvent.Ui.LoadDialogList(streamName, topicName, "newest"))
        }
    }

    private fun updateEmojiCounter(message: MessageModel, emoji: EmojiView) {
        store.accept(DialogEvent.Ui.ChangeEmojiCounter(message, emoji))
    }

    private fun updateMessageSender() {
        binding.apply {
            messageSender.etWrite.setText("")
            messageSender.etWrite.hideKeyboard()
        }
    }

    private fun scrollAdapterToLastPosition() {
        adapter.let {
            binding.rvMessages.scrollToPosition(it.itemCount - 1)
        }
    }

    private fun configureMessageSender() {
        binding.messageSender.btnSend.setOnClickListener {
            val text = binding.messageSender.etWrite.text.toString()
            if (text.isBlank()) {
                activityResultPickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else{
                store.accept(DialogEvent.Ui.SendMessage(text, streamName, topicName))
            }
        }
    }
    private fun setupPhotoPicker() {
        activityResultPickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                val inputStream = requireContext().contentResolver.openInputStream(it)
                val file = File(requireContext().cacheDir, "image.jpg")
                val outputStream = FileOutputStream(file)

                inputStream?.let { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                outputStream.close()
                inputStream?.close()

                store.accept(DialogEvent.Ui.SendMediaMessage(file, streamName, topicName))
            }
        }
    }
    private fun initFieldFromBundle() {
        arguments?.let {
            streamName = it.getString(STREAM_NAME_KEY, DEFAULT_NAME)
            topicName = it.getString(TOPIC_NAME_KEY, DEFAULT_NAME)
        }
    }

    private fun showBottomSheet(message: MessageModel) {
        EmojiBottomSheetDialog.newInstance(message.id).apply {
            this.dialogStore = store
        }
            .show(parentFragmentManager, null)
    }

    private fun setActionBtnBack() {
        binding.toolbarRoot.btnBack.setOnClickListener {
            store.accept(DialogEvent.Ui.BtnBackPressed)
        }
    }

    private fun initList() {
        store.accept(DialogEvent.Ui.LoadDialogList(streamName, topicName, "newest"))
    }

    private fun setAdapter() {
        binding.apply {
            rvMessages.adapter = adapter
            rvMessages.addItemDecoration(stickyHeaderItemDecoration)
            rvMessages.addItemDecoration(spaceItemDecorator)
            rvMessages.layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true
            }
            rvMessages.setHasFixedSize(true)
        }
    }

    private fun showError(msg: String) {
        requireContext().showToastError(msg)
    }

    private fun setStreamAndTopic() {
        binding.apply {
            toolbarRoot.tvStream.text = streamName
            tvTopic.text = getString(R.string.topic, topicName)
        }
    }

    private fun setButtonSender() {
        binding.apply {
            messageSender.etWrite.addTextChangedListener(
                afterTextChanged = {
                    if (it?.isBlank() == true) {
                        setSendMessageButton()
                    } else {
                        setChooseMediaButton()
                    }
                },
            )
        }
    }

    private fun removeDefaultChangedAnimation() {
        val itemAnimator = binding.rvMessages.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }
    }

    private fun setSendMessageButton() {
        binding.apply {
            messageSender.btnSend.setImageResource(R.drawable.ic_add_media)
            messageSender.btnSend.setBackgroundResource(R.drawable.bg_ib_media_sender)
        }
    }

    private fun setChooseMediaButton() {
        binding.apply {
            messageSender.btnSend.setImageResource(R.drawable.ic_send)
            messageSender.btnSend.setBackgroundResource(R.drawable.bg_ib_message_sender)
        }
    }

    companion object {
        private const val STREAM_NAME_KEY = "stream"
        private const val TOPIC_NAME_KEY = "topic"
        const val DEFAULT_NAME = "UNKNOWN"

        fun newInstance(model: TopicModel): MessengerFragment =
            MessengerFragment().apply {
                arguments = Bundle().apply {
                    putString(STREAM_NAME_KEY, model.stream.name)
                    putString(TOPIC_NAME_KEY, model.name)
                }
            }
    }
}
