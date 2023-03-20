package com.skytask.catsnews.store

import cafe.adriel.voyager.core.model.ScreenModel
import com.skytask.domain.models.News
import com.skytask.domain.nanoredux.Action
import com.skytask.domain.nanoredux.Effect
import com.skytask.domain.nanoredux.State
import com.skytask.domain.nanoredux.Store
import com.skytask.domain.usecase.GetNewsListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class NewsListState(
    val newsList: List<News> = emptyList(),
    val isRefreshing: Boolean = false,
) : State

sealed class NewsListAction : Action {
    object Refresh : NewsListAction()
    internal class LoadedNewsList(val newsList: List<News>) : NewsListAction()
    internal class ChangeRefreshing(val isRefreshing: Boolean) : NewsListAction()
}

sealed class NewsListSideEffect : Effect {}

class NewsListStore @Inject constructor(
    private val getNewsListUseCase: GetNewsListUseCase,
) : Store<NewsListState, NewsListAction, NewsListSideEffect>,
    ScreenModel,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state: MutableStateFlow<NewsListState> = MutableStateFlow(NewsListState())

    private val sideEffect = MutableSharedFlow<NewsListSideEffect>()

    init {
        getNewsListUseCase.flow.onEach {
            it.onSuccess { newsList ->
                dispatch(NewsListAction.LoadedNewsList(newsList))
            }
        }.launchIn(this)

        getNewsListUseCase.isLoadingFlow.onEach {
            dispatch(NewsListAction.ChangeRefreshing(it))
        }.launchIn(this)
    }

    override fun observeState(): StateFlow<NewsListState> = state

    override fun observeSideEffect(): Flow<NewsListSideEffect> = sideEffect

    override fun dispatch(action: NewsListAction) {
        val oldState = state.value
        val newState = when (action) {
            NewsListAction.Refresh -> {
                getNewsListUseCase.refresh()
                oldState
            }
            is NewsListAction.LoadedNewsList -> {
                oldState.copy(newsList = action.newsList)
            }
            is NewsListAction.ChangeRefreshing -> {
                oldState.copy(isRefreshing = action.isRefreshing)
            }
        }
        if (newState != oldState) {
            state.value = newState
        }
    }
}