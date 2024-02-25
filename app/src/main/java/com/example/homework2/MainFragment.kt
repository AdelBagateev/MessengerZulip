package com.example.homework2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.common.lazyUnsafe
import com.example.homework2.databinding.FragmentMainBinding
import com.example.homework2.di.AppComponentHolder
import com.example.homework2.di.qualifiers.MainFragmentNavigatorHolder
import com.example.homework2.di.qualifiers.MainFragmentRouter
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.Lazy
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var currentScreenIndex: Int? = null
    private var currentScreen: FragmentScreen = Screens.Chanel()

    @Inject
    @MainFragmentRouter
    lateinit var localRouter: Lazy<Router>

    @Inject
    @MainFragmentNavigatorHolder
    lateinit var navigatorHolder: Lazy<NavigatorHolder>

    private val navigator by lazyUnsafe {
        AppNavigator(requireActivity(), R.id.top_cont)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AppComponentHolder.get().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        currentScreenIndex = savedInstanceState?.getInt(SCREEN_KEY)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null || currentScreenIndex == CHANNEL_SCREEN) {
            localRouter.get().replaceScreen(currentScreen)
        }
        binding.apply {
            bnvMain.setOnItemSelectedListener { menu ->
                when (menu.itemId) {
                    R.id.main_channels -> {
                        currentScreenIndex = CHANNEL_SCREEN
                        currentScreen = Screens.Chanel()
                        localRouter.get().replaceScreen(currentScreen)
                    }
                    R.id.main_people -> {
                        currentScreenIndex = PEOPLE_SCREEN
                        currentScreen = Screens.People()
                        localRouter.get().replaceScreen(currentScreen)
                    }
                    R.id.main_profile -> {
                        currentScreenIndex = PROFILE_SCREEN
                        currentScreen = Screens.Profile(BuildConfig.ID)
                        localRouter.get().replaceScreen(currentScreen)
                    }
                }
                return@setOnItemSelectedListener true
            }
            bnvMain.setOnItemReselectedListener {
                return@setOnItemReselectedListener
            }

        }
    }

    override fun onResume() {
        navigatorHolder.get().setNavigator(navigator)
        super.onResume()
    }

    override fun onPause() {
        navigatorHolder.get().removeNavigator()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentScreenIndex?.let { outState.putInt(SCREEN_KEY, it) }
    }

    companion object {
        private const val SCREEN_KEY = "screen"
        private const val CHANNEL_SCREEN = 0
        private const val PEOPLE_SCREEN = 1
        private const val PROFILE_SCREEN = 2
    }
}
