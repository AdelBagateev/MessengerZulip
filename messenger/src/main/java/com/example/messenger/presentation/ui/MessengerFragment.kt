package com.example.messenger.presentation.ui

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
import com.example.common.adapter.MainAdapter
import com.example.common.adapter.decorators.SpaceItemDecorator
import com.example.common.hideKeyboard
import com.example.common.lazyUnsafe
import com.example.common.showSnackbarError
import com.example.messenger.R
import com.example.messenger.data.datasource.remote.MessageApiClient
import com.example.messenger.databinding.FragmentMessengerBinding
import com.example.messenger.di.MessengerComponentHolder
import com.example.messenger.di.store.MessengerStoreHolderWrapper
import com.example.messenger.domain.model.MessageModel
import com.example.messenger.presentation.elm.MessengerEffect
import com.example.messenger.presentation.elm.MessengerEvent
import com.example.messenger.presentation.elm.MessengerState
import com.example.messenger.presentation.ui.adapters.main.delegates.date.DateDelegate
import com.example.messenger.presentation.ui.adapters.main.delegates.message.MessageDelegate
import com.example.messenger.presentation.ui.adapters.main.delegates.message.MessageDelegateItem
import com.example.messenger.presentation.ui.decorators.StickyHeaderItemDecoration
import com.example.messenger.presentation.ui.dialogs.ActionChooserBottomSheetDialogFragment
import com.example.messenger.presentation.ui.dialogs.EmojiBottomSheetDialogFragment
import com.example.messenger.presentation.ui.views.EmojiView
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.StoreHolder
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import com.example.common.R as CommonR

class MessengerFragment : ElmFragment<MessengerEvent, MessengerEffect, MessengerState>() {

    private lateinit var streamName: String

    //тип нулабельный тк реализована возможность
    //просмотра соощений из стрима без конкретного топика
    private var topicName: String? = null
    private val isTopicSelected by lazyUnsafe {
        topicName != null
    }

    private var _binding: FragmentMessengerBinding? = null
    private val binding get() = _binding!!

    private var previousItemCount = 0
    private var activityResultPickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null
    private val messageRvScrollListener by lazyUnsafe { MessageRvScrollListener() }

    private val dialogStoreHolderWrapper by lazyUnsafe { factory.create(lifecycle) }

    @Inject
    lateinit var factory: MessengerStoreHolderWrapper.Factory

    private val adapter by lazyUnsafe {
        (MainAdapter(
            MessageDelegate(
                onMessageLongClick = { showBottomSheetActionChooser(it) },
                onReactionClick = { msg, emoji -> updateEmojiCounter(msg, emoji) },
                onPlusBtnClick = { showBottomSheetEmojiChooser(it) },
                onTopicClick = { navigateToDialogWithTopic(it) },
                isTopicSelected = isTopicSelected
            ),
            DateDelegate()
        ))
    }

    private val spaceItemDecorator: SpaceItemDecorator by lazyUnsafe {
        SpaceItemDecorator(
            requireContext(),
            12f
        )
    }

    private val stickyHeaderItemDecoration: StickyHeaderItemDecoration by lazyUnsafe {
        StickyHeaderItemDecoration()
    }

    override val initEvent: MessengerEvent
        get() = MessengerEvent.Ui.Init

    override val storeHolder: StoreHolder<MessengerEvent, MessengerEffect, MessengerState>
        get() = dialogStoreHolderWrapper.storeHolder

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupComponent()
        initPhotoPicker()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initFieldFromBundle()
        initList()
        _binding = FragmentMessengerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setupTopicNarrowing()
        setStreamAndTopic()
        setButtonSender()
        setupActionMessageSender()
        setActionBtnBack()
        setActionsBtnRetry()
        setPreloadMessages()
        removeDefaultChangedAnimation()
    }

    override fun render(state: MessengerState) {
        binding.apply {
            loadingStateContainer.root.isVisible = state.isLoading
            rvMessages.isVisible = (!state.isLoading && state.error == null)
            state.dialogList?.let {
                adapter.submitList(it)
            }
            errorStateContainer.root.isVisible = state.error != null
        }
    }

    override fun handleEffect(effect: MessengerEffect) {
        when (effect) {
            MessengerEffect.MessageDidNotSend -> {
                val msg = getString(R.string.error_no_connection_message)
                showError(msg)
            }
            MessengerEffect.CounterEmojiDidNotChanged -> {
                val msg = getString(R.string.error_no_connection_reaction)
                showError(msg)
            }
            MessengerEffect.NewEmojiDidNotAdded -> {
                val msg = getString(R.string.error_no_connection_reaction)
                showError(msg)
            }
            MessengerEffect.ListNotLoaded -> {
                val msg = getString(CommonR.string.error_no_connection)
                showError(msg)
            }
            MessengerEffect.MessageDidNotUpdated -> {
                val msg = getString(R.string.error_no_connection_update_message)
                showError(msg)
            }
            is MessengerEffect.TimeOutMessageUpdating -> {
                val msg = getString(R.string.error_timeout_updating_message)
                showError(msg)
            }
            MessengerEffect.MessageDidNotDeleted -> {
                val msg = getString(R.string.error_no_connection_delete_message)
                showError(msg)
            }
            MessengerEffect.FieldsIsEmpty -> {
                val msg = getString(R.string.fields_can_not_be_empty)
                showError(msg)
            }
            MessengerEffect.ScrollToLastPosition -> scrollAdapterToLastPosition()
            MessengerEffect.NewMessageAdded -> {
                updateMessageSender()
                scrollAdapterToLastPosition()
            }
        }
    }

    override fun onStop() {
        binding.messageSender.etWrite.clearFocus()
        store.accept(MessengerEvent.Ui.DeleteOldMessages(streamName))
        super.onStop()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        previousItemCount = savedInstanceState?.getInt(PREVIOUS_ITEM_COUNT_KEY) ?: return
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(PREVIOUS_ITEM_COUNT_KEY, previousItemCount)
    }

    private fun setupComponent() {
        MessengerComponentHolder.get().inject(this)
    }

    private fun setPreloadMessages() {
        binding.rvMessages.addOnScrollListener(messageRvScrollListener)
    }

    private fun setActionsBtnRetry() {
        binding.errorStateContainer.retryButton.setOnClickListener {
            store.accept(
                MessengerEvent.Ui.LoadMessengerList(
                    streamName,
                    topicName,
                    MessageApiClient.NEWEST
                )
            )
        }
    }

    private fun updateEmojiCounter(message: MessageModel, emoji: EmojiView) {
        store.accept(MessengerEvent.Ui.ChangeEmojiCounter(message, emoji))
    }

    private fun updateMessageSender() {
        binding.apply {
            messageSender.etWrite.setText("")
            messageSender.etWrite.hideKeyboard()
        }
    }

    private fun navigateToDialogWithTopic(topicName: String) {
        store.accept(MessengerEvent.Ui.TopicPressed(streamName, topicName))
    }

    private fun scrollAdapterToLastPosition() {
        adapter.let {
            binding.rvMessages.scrollToPosition(it.itemCount - 1)
        }
    }

    private fun setupTopicNarrowing() {
        binding.etTopicNarrowing.isVisible = !isTopicSelected
    }

    private fun setupActionMessageSender() {
        binding.messageSender.btnSend.setOnClickListener {

            val actualTopicName = topicName ?: getTopicFromNarrowing()
            if (actualTopicName.isBlank()) {
                val msg = getString(R.string.field_topic_can_not_be_empty)
                showError(msg)
                return@setOnClickListener
            }

            val content = binding.messageSender.etWrite.text.toString()
            if (content.isBlank()) {
                activityResultPickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                store.accept(MessengerEvent.Ui.SendMessage(content, streamName, actualTopicName))
            }
        }
    }

    private fun getTopicFromNarrowing(): String {
        return binding.etTopicNarrowing.text.toString()
    }

    private fun initPhotoPicker() {
        activityResultPickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
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

                    val actualTopicName = topicName ?: getTopicFromNarrowing()
                    store.accept(
                        MessengerEvent.Ui.SendMediaMessage(
                            file,
                            streamName,
                            actualTopicName
                        )
                    )
                }
            }
    }

    private fun initFieldFromBundle() {
        arguments?.let {
            streamName = it.getString(STREAM_NAME_KEY, DEFAULT_NAME)
            topicName = it.getString(TOPIC_NAME_KEY)
        }
    }

    private fun showBottomSheetActionChooser(message: MessageModel) {
        ActionChooserBottomSheetDialogFragment().apply {
            this.messengerStore = store
            this.message = message
        }
            .show(parentFragmentManager, null)
    }

    private fun showBottomSheetEmojiChooser(message: MessageModel) {
        EmojiBottomSheetDialogFragment.newInstance(message.id).apply {
            this.dialogStore = store
        }
            .show(parentFragmentManager, null)
    }

    private fun setActionBtnBack() {
        binding.toolbarRoot.btnBack.setOnClickListener {
            store.accept(MessengerEvent.Ui.BtnBackPressed)
        }
    }

    private fun initList() {
        store.accept(
            MessengerEvent.Ui.LoadMessengerList(
                streamName,
                topicName,
                MessageApiClient.NEWEST
            )
        )
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
        binding.root.showSnackbarError(msg)
    }

    private fun setStreamAndTopic() {
        binding.apply {
            toolbarRoot.tvStream.text = streamName
            if (topicName == null) {
                tvTopic.text = TOPIC_NOT_SELECTED
            } else {
                tvTopic.text = getString(R.string.topic, topicName)
            }
        }
    }

    private fun setButtonSender() {
        binding.apply {
            messageSender.etWrite.addTextChangedListener(
                afterTextChanged = {
                    if (it?.isBlank() == true) {
                        setChooseMediaButton()
                    } else {
                        setSendMessageButton()
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

    private fun setChooseMediaButton() {
        binding.apply {
            messageSender.btnSend.setImageResource(R.drawable.ic_add_media)
            messageSender.btnSend.setBackgroundResource(R.drawable.bg_ib_media_sender)
        }
    }

    private fun setSendMessageButton() {
        binding.apply {
            messageSender.btnSend.setImageResource(R.drawable.ic_send)
            messageSender.btnSend.setBackgroundResource(R.drawable.bg_ib_message_sender)
        }
    }

    private inner class MessageRvScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
            val itemCount = layoutManager.itemCount
            if (firstVisiblePosition <= 5 && previousItemCount != itemCount) {
                val mainAdapter = recyclerView.adapter as MainAdapter
                val msg = mainAdapter.currentList[1] as MessageDelegateItem
                store.accept(
                    MessengerEvent.Ui.PreLoadMessengerList(
                        streamName,
                        topicName,
                        msg.id()
                    )
                )

                previousItemCount = itemCount
            }
        }
    }

    companion object {
        private const val STREAM_NAME_KEY = "stream"
        private const val TOPIC_NAME_KEY = "topic"
        private const val PREVIOUS_ITEM_COUNT_KEY = "previousItemCount"

        private const val DEFAULT_NAME = "UNKNOWN"
        private const val TOPIC_NOT_SELECTED = "TOPIC NOT SELECTED"
        fun newInstance(streamName: String, topicName: String?): MessengerFragment =
            MessengerFragment().apply {
                arguments = Bundle().apply {
                    putString(STREAM_NAME_KEY, streamName)
                    putString(TOPIC_NAME_KEY, topicName)
                }
            }
    }
}
