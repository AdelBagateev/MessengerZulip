package com.example.people.presentation.elm

import vivid.money.elmslie.core.store.dsl_reducer.DslReducer
import javax.inject.Inject

class PeopleReducer @Inject constructor() :
    DslReducer<PeopleEvent, PeopleState, PeopleEffect, PeopleCommand>() {
    override fun Result.reduce(event: PeopleEvent): Any {
        return when (event) {
            is PeopleEvent.Ui.LoadPeople -> {
                state { copy(isLoading = true, error = null) }
                commands { +PeopleCommand.SubscribeOnPeople }
                commands { +PeopleCommand.GetActualPeopleData }
            }
            is PeopleEvent.Ui.OnPeopleClicked -> {
                commands { +PeopleCommand.OnPeopleClicked(event.people) }
            }
            PeopleEvent.Ui.Init -> {
                commands { +PeopleCommand.Init(state.people) }
            }
            is PeopleEvent.Ui.SetupQueryState -> {
                commands { +PeopleCommand.SetupQueryState(event.searchQueryState) }
            }

            //INTERNAL
            is PeopleEvent.Internal.PeopleLoaded -> {
                state { copy(isLoading = false, error = null, people = event.people) }
            }
            is PeopleEvent.Internal.PeopleFiltered -> {
                effects { +PeopleEffect.PeopleFiltered(event.people) }
            }
            is PeopleEvent.Internal.ErrorLoading -> {
                if (state.people == null) {
                    state { copy(isLoading = false, error = event.error) }
                } else {
                    effects { PeopleEffect.ActualDataNotLoadedError }
                }
            }
            PeopleEvent.Internal.SuccessNavigate -> {
                state { copy() }
            }
            PeopleEvent.Internal.ErrorIsBotNavigate -> {
                effects { +PeopleEffect.IsBotError }
            }
        }
    }

}
