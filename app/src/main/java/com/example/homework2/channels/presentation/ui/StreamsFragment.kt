package com.example.homework2.channels.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework2.R
import com.example.homework2.channels.domain.model.StreamModel
import com.example.homework2.channels.domain.model.TopicModel
import com.example.homework2.channels.presentation.elm.ChannelEffect
import com.example.homework2.channels.presentation.elm.ChannelEvent
import com.example.homework2.channels.presentation.elm.ChannelState
import com.example.homework2.channels.presentation.elm.ChannelStateManager
import com.example.homework2.channels.presentation.ui.delegates.stream.StreamDelegate
import com.example.homework2.channels.presentation.ui.delegates.topic.TopicDelegate
import com.example.homework2.common.adapter.MainAdapter
import com.example.homework2.common.lazyUnsafe
import com.example.homework2.common.showToastError
import com.example.homework2.databinding.FragmentStreamsBinding
import dagger.Lazy
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.StoreHolder
import javax.inject.Inject


class StreamsFragment : ElmFragment<ChannelEvent, ChannelEffect, ChannelState>() {
    private var _binding: FragmentStreamsBinding? = null
    private val binding get() = _binding!!

    private var isSubscribedFragment: Boolean = false

    private val adapter by lazyUnsafe {
        (MainAdapter(
            StreamDelegate {
                showTopics(
                    it,
                    isSubscribedFragment
                )
            },
            TopicDelegate { onTopicPressed(it) }
        ))
    }
    private val dividerItemDecoration by lazyUnsafe {
        DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        ).apply {
            AppCompatResources.getDrawable(requireContext(), R.drawable.divider_line)?.let {
                setDrawable(it)
            }
        }
    }

    @Inject
    lateinit var globalStoreHolder: Lazy<StoreHolder<ChannelEvent, ChannelEffect, ChannelState>>

    @Inject
    lateinit var stateManager: Lazy<ChannelStateManager>

    override val initEvent: ChannelEvent
        get() = ChannelEvent.Ui.InitFromStream

    override val storeHolder: StoreHolder<ChannelEvent, ChannelEffect, ChannelState>
        get() = globalStoreHolder.get()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ChannelFragment.INSTANCE.channelComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setFragmentFlags()
        _binding = FragmentStreamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupActions()
    }

    override fun render(state: ChannelState) {
        stateManager.get().state = state
        binding.apply {
            loadingStateContainer.root.isVisible = state.isLoading
            rvStreams.isVisible = !state.isLoading && state.error == null
            if (isSubscribedFragment) {
                state.subscribedStreamList?.let { adapter.submitList(it) }
            } else {
                state.allStreamList?.let { adapter.submitList(it) }
            }
            errorStateContainer.root.isVisible = state.error != null
        }
    }

    override fun handleEffect(effect: ChannelEffect) {
        when (effect) {
            ChannelEffect.TopicsOfStreamDidNotLoaded -> {
                val msg = getString(R.string.error_no_connection)
                showError(msg)
            }
            ChannelEffect.ActualDataLoadingError -> {
                val msg = getString(R.string.error_no_connection_failed_update_streams)
                showError(msg)
            }
            is ChannelEffect.SubscribedStreamListFiltered -> {
                if (isSubscribedFragment) {
                    adapter.submitList(effect.list)
                }
            }
            is ChannelEffect.AllStreamListFiltered -> {
                if (!isSubscribedFragment) {
                    adapter.submitList(effect.list)
                }
            }
        }
    }

    private fun onTopicPressed(topic: TopicModel) {
        store.accept(ChannelEvent.Ui.TopicPressed(topic))
    }

    private fun setupActions() {
        binding.apply {
            errorStateContainer.retryButton.setOnClickListener {
                store.accept(ChannelEvent.Ui.LoadStreamList(isSubscribedFragment))
            }
        }
    }

    private fun showTopics(streamModel: StreamModel, isSubscribedFragment: Boolean) {
        store.accept(ChannelEvent.Ui.LoadTopicsOfStream(streamModel, isSubscribedFragment))
    }

    private fun showError(msg: String) {
        requireContext().showToastError(msg)
    }

    private fun setupAdapter() {
        binding.apply {
            rvStreams.addItemDecoration(dividerItemDecoration)
            rvStreams.adapter = adapter
            rvStreams.layoutManager = LinearLayoutManager(context)
            rvStreams.itemAnimator?.moveDuration = 0
            removeDefaultChangedAnimation()
        }
    }

    private fun removeDefaultChangedAnimation() {
        val itemAnimator = binding.rvStreams.itemAnimator
        if (itemAnimator is DefaultItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
        }
    }

    private fun setFragmentFlags() {
        arguments?.apply {
            isSubscribedFragment = getBoolean(IS_SUBSCRIBED_FRAGMENT, false)
        }
    }

    companion object {
        private const val IS_SUBSCRIBED_FRAGMENT = "IS_SUBSCRIBED"
        fun newInstance(isSubscribedFragment: Boolean): StreamsFragment =
            StreamsFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(IS_SUBSCRIBED_FRAGMENT, isSubscribedFragment)
                }
            }
    }
}
