package com.example.homework2.people.presentation.elm

import com.example.homework2.Screens
import com.example.homework2.common.adapter.interfaces.DelegateItem
import com.example.homework2.common.catchCancellationException
import com.example.homework2.people.domain.LoadPeopleUseCase
import com.example.homework2.people.domain.SubscribeOnPeopleUseCase
import com.example.homework2.people.domain.model.PeopleModel
import com.example.homework2.people.presentation.ui.delegate.PeopleDelegateItem
import com.example.homework2.people.utils.PeopleMapperPresentation
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import timber.log.Timber
import vivid.money.elmslie.coroutines.Actor
import javax.inject.Inject

class PeopleActor @Inject constructor(
    private val loadPeopleUseCase: LoadPeopleUseCase,
    private val subscribeOnPeopleUseCase: SubscribeOnPeopleUseCase,
    private val router: Router,
    private val mapper : PeopleMapperPresentation
) : Actor<PeopleCommand, PeopleEvent> {
    private var currentList : List<DelegateItem> = emptyList()
    override fun execute(command: PeopleCommand): Flow<PeopleEvent> {
        return when (command) {
            is PeopleCommand.SubscribeOnPeople -> subscribeOnPeople()
            is PeopleCommand.Init -> updateLocalList(command.list)
            PeopleCommand.GetActualPeopleData -> getActualData()
            is PeopleCommand.OnPeopleClicked -> navigate(command.people)
            is PeopleCommand.SetupQueryState -> filterPeopleList(command.searchQueryState)
        }
    }

    private fun getActualData(): Flow<PeopleEvent> {
        return flow {
            emit(loadPeopleUseCase())
        }.mapEvents{ error->
            catchCancellationException(error)
            Timber.e(error.message)
            PeopleEvent.Internal.ErrorLoading(error)
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun filterPeopleList(searchQueryState: MutableSharedFlow<String>): Flow<PeopleEvent> {
        return searchQueryState
            .distinctUntilChanged()
            .debounce(300L)
            .flatMapLatest { query -> flow { emit(search(query)) } }
            .mapEvents ({PeopleEvent.Internal.PeopleFiltered(it)})
            .flowOn(Dispatchers.Default)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun subscribeOnPeople(): Flow<PeopleEvent> {
        return subscribeOnPeopleUseCase()
            .distinctUntilChanged()
            .flatMapLatest {newList ->
                currentList = mapper.toPeopleDelegateItem(newList)
                flowOf(currentList)
            }
            .mapEvents(
                { newList ->
                    PeopleEvent.Internal.PeopleLoaded(newList)
                },
                { error ->
                    catchCancellationException(error)
                    Timber.e(error.message)
                    PeopleEvent.Internal.ErrorLoading(error)
                }
            ).flowOn(Dispatchers.Default)

    }

    private fun search(query: String) : List<DelegateItem> {
        if(query.isEmpty())  return currentList
        val result = currentList
            .filterIsInstance<PeopleDelegateItem>()
            .filter { peopleDelegate ->
                val name =  peopleDelegate .value.fullName
                name.lowercase().contains(query.lowercase())
        }
        return result
    }
    private fun updateLocalList(list: List<DelegateItem>?): Flow<PeopleEvent> {
        list?.let { currentList = it }
        return emptyFlow()
    }
    private fun navigate(people: PeopleModel): Flow<PeopleEvent> {
        return if (people.isBot) {
            flow {
                emit(PeopleEvent.Internal.ErrorIsBotNavigate)
            }
                .flowOn(Dispatchers.Default)
        } else {
            router.navigateTo(Screens.Profile(people.id, true))
            flow {
                emit(PeopleEvent.Internal.SuccessNavigate)
            }
                .flowOn(Dispatchers.Default)
        }
    }
}
