package com.example.people.presentation.elm

import android.os.Parcelable
import com.example.common.adapter.interfaces.DelegateItem
import com.example.people.domain.model.PeopleModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import javax.inject.Inject

@Parcelize
data class PeopleState(
    val people: @RawValue List<DelegateItem>? = null,
    val error: Throwable? = null,
    val isLoading: Boolean = false,
) : Parcelable {
    @Inject
    constructor() : this(null, null, false)
}

sealed interface PeopleEvent {
    sealed interface Ui : PeopleEvent {
        object LoadPeople : Ui
        class OnPeopleClicked(val people: PeopleModel) : Ui
        object Init : Ui

        class SetupQueryState(
            val searchQueryState: MutableSharedFlow<String>,
        ) : Ui
    }

    sealed interface Internal : PeopleEvent {
        class PeopleLoaded(val people: List<DelegateItem>) : Internal
        class PeopleFiltered(val people: List<DelegateItem>) : Internal

        class ErrorLoading(val error: Throwable) : Internal
        object SuccessNavigate : Internal
        object ErrorIsBotNavigate : Internal
    }
}

sealed interface PeopleEffect {
    object IsBotError : PeopleEffect
    object ActualDataNotLoadedError : PeopleEffect
    class PeopleFiltered(val people: List<DelegateItem>) : PeopleEffect
}

sealed interface PeopleCommand {
    class Init(val list: List<DelegateItem>?) : PeopleCommand
    object SubscribeOnPeople : PeopleCommand
    object GetActualPeopleData : PeopleCommand
    class OnPeopleClicked(val people: PeopleModel) : PeopleCommand
    class SetupQueryState(
        val searchQueryState: MutableSharedFlow<String>,
    ) : PeopleCommand
}
