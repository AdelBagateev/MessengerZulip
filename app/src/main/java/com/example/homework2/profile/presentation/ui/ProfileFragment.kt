package com.example.homework2.profile.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import coil.load
import com.example.homework2.R
import com.example.homework2.common.showToastError
import com.example.homework2.databinding.FragmentProfileBinding
import com.example.homework2.getAppComponent
import com.example.homework2.profile.di.DaggerProfileComponent
import com.example.homework2.profile.domain.model.PresenceModel
import com.example.homework2.profile.domain.model.UserModel
import com.example.homework2.profile.presentation.elm.ProfileEffect
import com.example.homework2.profile.presentation.elm.ProfileEvent
import com.example.homework2.profile.presentation.elm.ProfileState
import com.example.homework2.profile.presentation.elm.ProfileStateManager
import dagger.Lazy
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.StoreHolder
import javax.inject.Inject
import kotlin.properties.Delegates

class ProfileFragment : ElmFragment<ProfileEvent, ProfileEffect, ProfileState>() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var userId by Delegates.notNull<Int>()
    private var fromPeopleFragment by Delegates.notNull<Boolean>()

    @Inject
    lateinit var globalStoreHolder: Lazy<StoreHolder<ProfileEvent, ProfileEffect, ProfileState>>

    @Inject
    lateinit var stateManager: Lazy<ProfileStateManager>

    override val initEvent: ProfileEvent
        get() = ProfileEvent.Ui.Init

    override val storeHolder: StoreHolder<ProfileEvent, ProfileEffect, ProfileState>
        get() = globalStoreHolder.get()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = requireContext().getAppComponent()

        val profileComponent = DaggerProfileComponent.builder()
            .lifecycle(lifecycle)
            .appComponent(appComponent)
            .build()

        profileComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initFieldFromBundle()
        initUser()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar()
        setupActions()
    }

    override fun render(state: ProfileState) {
        stateManager.get().state = state
        binding.apply {
            loadingStateContainer.root.isVisible = state.isLoading
            loadedStateContainer.isVisible = !state.isLoading && state.error == null
            errorStateContainer.root.isVisible = state.error != null

            state.user?.let {
                setupUser(it)
            }
        }
    }

    override fun handleEffect(effect: ProfileEffect) {
        when(effect) {
            ProfileEffect.ErrorGetActualDataFromNetwork -> {
                val msg = getString(R.string.error_no_connection_failed_update_user)
                showError(msg)
            }
        }
    }
    private fun showError(msg: String) {
        requireContext().showToastError(msg)
    }
    private fun initUser() {
        store.accept(ProfileEvent.Ui.LoadUser(userId))
    }

    private fun setupToolBar() {
        if (fromPeopleFragment) {
            binding.tbRoot.root.isVisible = true
        }
    }

    private fun setupActions() {
        binding.tbRoot.btnBack.setOnClickListener {
            store.accept(ProfileEvent.Ui.BtnBackPressed)
        }
        binding.errorStateContainer.retryButton.setOnClickListener {
            store.accept(ProfileEvent.Ui.LoadUser(userId))
        }
    }

    private fun initFieldFromBundle() {
        arguments?.let {
            userId = it.getInt(ID_KEY)
            fromPeopleFragment = it.getBoolean(FROM_PEOPLE_FRAGMENT_KEY)
        }
    }

    private fun FragmentProfileBinding.setupUser(user: UserModel) {
        ivAvatar.load(user.avatar) {
            placeholder(R.drawable.ic_profile)
            error(R.drawable.ic_no_avatar)
        }
        tvName.text = user.name
        tvStatus.setTextColor(calculateTextColor(user.status, tvStatus.context))
        tvStatus.text = user.status
    }

    private fun calculateTextColor(status: String, context: Context): Int {
        return when (status) {
            PresenceModel.ONLINE_STATUS -> ContextCompat.getColor(context, R.color.online_profile)
            PresenceModel.OFFLINE_STATUS -> ContextCompat.getColor(context, R.color.offline_profile)
            PresenceModel.IDLE_STATUS -> ContextCompat.getColor(context, R.color.idle_profile)
            else -> {
                ContextCompat.getColor(context, R.color.offline_profile)
            }
        }
    }

    companion object {
        private const val ID_KEY = "id"
        private const val FROM_PEOPLE_FRAGMENT_KEY = "fromPeopleFragment"
        fun newInstance(id: Int, fromPeopleFragment: Boolean): ProfileFragment =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_KEY, id)
                    putBoolean(FROM_PEOPLE_FRAGMENT_KEY, fromPeopleFragment)
                }
            }
    }

}

