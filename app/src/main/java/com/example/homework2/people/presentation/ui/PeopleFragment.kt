package com.example.homework2.people.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework2.R
import com.example.homework2.common.adapter.MainAdapter
import com.example.homework2.common.adapter.decorators.SpaceItemDecorator
import com.example.homework2.common.hideKeyboard
import com.example.homework2.common.lazyUnsafe
import com.example.homework2.common.showToastError
import com.example.homework2.databinding.FragmentPeopleBinding
import com.example.homework2.getAppComponent
import com.example.homework2.people.di.DaggerPeopleComponent
import com.example.homework2.people.domain.model.PeopleModel
import com.example.homework2.people.presentation.elm.PeopleEffect
import com.example.homework2.people.presentation.elm.PeopleEvent
import com.example.homework2.people.presentation.elm.PeopleState
import com.example.homework2.people.presentation.elm.PeopleStateManager
import com.example.homework2.people.presentation.ui.delegate.PeopleDelegate
import dagger.Lazy
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.StoreHolder
import javax.inject.Inject

class PeopleFragment : ElmFragment<PeopleEvent, PeopleEffect, PeopleState>() {
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!

    private val queryState: MutableSharedFlow<String> = MutableSharedFlow()

    private val adapter by lazyUnsafe {
        (MainAdapter(PeopleDelegate { navigateToProfile(it) }))
    }

    private val spaceItemDecorator: SpaceItemDecorator by lazyUnsafe {
        SpaceItemDecorator(requireContext(), 16f, true, 8f)
    }

    @Inject
    lateinit var globalStoreHolder:  Lazy<StoreHolder<PeopleEvent, PeopleEffect, PeopleState>>

    @Inject
    lateinit var stateManager:  Lazy<PeopleStateManager>

    override val initEvent: PeopleEvent
        get() = PeopleEvent.Ui.Init

    override val storeHolder: StoreHolder<PeopleEvent, PeopleEffect, PeopleState>
        get() = globalStoreHolder.get()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = requireContext().getAppComponent()

        val peopleComponent = DaggerPeopleComponent.builder()
            .lifecycle(lifecycle)
            .appComponent(appComponent)
            .build()

        peopleComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPeopleList()
        sendQueryState()
        setupSearcher()
        setupAdapter()
        setupActions()
    }

    override fun render(state: PeopleState) {
        stateManager.get().state = state
        binding.apply {
            loadingStateContainer.root.isVisible = state.isLoading
            rvPeople.isVisible = !state.isLoading && state.error == null
            state.people?.let { adapter.submitList(it) }
            errorStateContainer.root.isVisible = state.error != null
        }
    }

    override fun onStop() {
        super.onStop()
        clearSearcher()
        binding.searchRoot.etSearcher.clearFocus()
        binding.searchRoot.etSearcher.setText("")
        binding.searchRoot.etSearcher.hideKeyboard()
        clearQueryState()
    }

    private fun clearSearcher() {
        binding.apply {
            searchRoot.etSearcher.clearFocus()
            searchRoot.etSearcher.setText("")
            searchRoot.etSearcher.hideKeyboard()
        }
    }

    private fun initPeopleList() {
        if (store.currentState.people == null) {
            store.accept(PeopleEvent.Ui.LoadPeople)
        }
    }

    private fun sendQueryState() {
        store.accept(PeopleEvent.Ui.SetupQueryState(queryState))
    }
    override fun handleEffect(effect: PeopleEffect) {
        when (effect) {
            PeopleEffect.IsBotError -> {
                val msg = getString(R.string.error_bots_have_not_profile)
                showError(msg)
            }
            is PeopleEffect.PeopleFiltered -> {
                adapter.submitList(effect.people)
            }
            PeopleEffect.ActualDataNotLoadedError -> {
                val msg = getString(R.string.error_no_connection_failed_update_people)
                showError(msg)
            }
        }
    }

    private fun setupSearcher() {
        binding.searchRoot.etSearcher.apply {
            addTextChangedListener {
                lifecycleScope.launch {
                    queryState.emit(it.toString())
                }
                setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        hideKeyboard()
                    }
                    true
                }
            }
        }
    }

    private fun setupActions() {
        binding.errorStateContainer.retryButton.setOnClickListener {
            store.accept(PeopleEvent.Ui.LoadPeople)
        }
    }

    private fun navigateToProfile(people: PeopleModel) {
        store.accept(PeopleEvent.Ui.OnPeopleClicked(people))
    }

    private fun setupAdapter() {
        binding.apply {
            rvPeople.adapter = adapter
            rvPeople.layoutManager = LinearLayoutManager(context)
            rvPeople.addItemDecoration(spaceItemDecorator)
        }
    }
    private fun clearQueryState() {
        lifecycleScope.launch {
            queryState.emit("")
        }
    }
    private fun showError(msg: String) {
        requireContext().showToastError(msg)
    }
}
