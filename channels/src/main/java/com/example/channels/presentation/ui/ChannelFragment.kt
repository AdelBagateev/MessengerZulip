package com.example.channels.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.channels.databinding.FragmentChanelsBinding
import com.example.channels.di.ChannelComponentHolder
import com.example.channels.di.store.ChannelStoreHolderWrapper
import com.example.channels.presentation.elm.ChannelEffect
import com.example.channels.presentation.elm.ChannelEvent
import com.example.channels.presentation.elm.ChannelState
import com.example.channels.presentation.ui.viewpager.ChanelPagerAdapter
import com.example.common.hideKeyboard
import com.example.common.lazyUnsafe
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.StoreHolder
import javax.inject.Inject

class ChannelFragment : ElmFragment<ChannelEvent, ChannelEffect, ChannelState>() {
    private var _binding: FragmentChanelsBinding? = null
    private val binding get() = _binding!!

    private val queryState: MutableSharedFlow<String> = MutableSharedFlow()
    override val initEvent: ChannelEvent
        get() = ChannelEvent.Ui.InitFromChannel

    override val storeHolder: StoreHolder<ChannelEvent, ChannelEffect, ChannelState>
        get() = channelStoreHolderWrapper.storeHolder

    val channelStoreHolderWrapper by lazyUnsafe { factory.create(lifecycle) }

    @Inject
    lateinit var factory: ChannelStoreHolderWrapper.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupComponent()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChanelsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewPager()
        setupActionItemChangedViewPager()
        setupSearcher()
        setupCreateNewStreamAction()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun render(state: ChannelState) {}

    override fun onStop() {
        super.onStop()
        clearFocusEditText()
        clearQueryState()
    }

    private fun setupCreateNewStreamAction() {
        binding.btnCreateNewStream.setOnClickListener {
            CreateNewStreamDialogFragment().apply {
                this.channelStore = store
            }
                .show(parentFragmentManager, null)
        }
    }

    private fun setupActionItemChangedViewPager() {
        binding.vpChannels.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val isSubscribedFragment = (SUBSCRIBED_FRAGMENT_INDEX == position)

                    sendLoadListEvent(isSubscribedFragment)
                    sendNewQueryState(isSubscribedFragment)
                    clearEditText()
                    binding.searchRoot.etSearcher.hideKeyboard()
                }
            }
        )
    }

    private fun setupComponent() {
        ChannelComponentHolder.get().inject(this)
    }

    private fun clearEditText() {
        binding.searchRoot.etSearcher.setText("")
    }

    private fun sendNewQueryState(isSubscribedFragment: Boolean) {
        store.accept(ChannelEvent.Ui.SetupQueryState(queryState, isSubscribedFragment))
    }

    private fun sendLoadListEvent(isSubscribedFragment: Boolean) {
        if (isSubscribedFragment && store.currentState.subscribedStreamList == null
            || !isSubscribedFragment && store.currentState.allStreamList == null
        ) {
            store.accept(ChannelEvent.Ui.LoadStreamList(isSubscribedFragment))
        }
    }

    private fun clearFocusEditText() {
        binding.searchRoot.etSearcher.clearFocus()
    }

    private fun setupViewPager() {
        val tabs: List<String> = listOf(FIRST_TAB, SECOND_TAB)
        val pagerAdapter = ChanelPagerAdapter(childFragmentManager, lifecycle)
        binding.vpChannels.adapter = pagerAdapter
        pagerAdapter.update(
            listOf(
                StreamsFragment.newInstance(true),
                StreamsFragment.newInstance(false)
            )
        )
        TabLayoutMediator(binding.tabLayout, binding.vpChannels) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    private fun setupSearcher() {
        binding.searchRoot.etSearcher.apply {
            addTextChangedListener {
                lifecycleScope.launch {
                    queryState.emit(it.toString())
                }
            }
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    hideKeyboard()
                }
                true
            }
        }
    }

    private fun clearQueryState() {
        lifecycleScope.launch {
            queryState.emit("")
        }
    }

    companion object {
        private const val SUBSCRIBED_FRAGMENT_INDEX = 0
        private const val FIRST_TAB = "Subscribed"
        private const val SECOND_TAB = "All streams"
    }
}
