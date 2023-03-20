package com.skytask.domain.usecase

import com.skytask.domain.models.News
import com.skytask.domain.models.NewsTypeEnum
import com.skytask.domain.repository.GetNewsListRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onSubscription
import kotlin.coroutines.CoroutineContext

class GetNewsListUseCase(private val repository: GetNewsListRepository) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default
    private val _flow = MutableStateFlow<Result<List<News>>>(
        Result.failure(RuntimeException())
    )
    private val _isLoadingFlow = MutableStateFlow(false)
    private var refreshingJob: Job? = null

    val isLoadingFlow: StateFlow<Boolean>
        get() = _isLoadingFlow
    val flow: Flow<Result<List<News>>>
        get() = _flow.onSubscription { lazyLoad() }

    fun refresh() {
        if (refreshingJob?.isActive == true) return
        refreshingJob = launch {
            refreshSuspend()
        }
    }

    private fun isEmptyState(): Boolean {
        val value = _flow.value
        return value.isFailure && value.exceptionOrNull() is RuntimeException
    }

    private suspend fun refreshSuspend() =
        runCatching {
            withContext(Dispatchers.Default) {
                _isLoadingFlow.emit(true)
                loadData()
                _isLoadingFlow.emit(false)
            }
        }.onFailure {
            _isLoadingFlow.emit(false)
        }

    private fun lazyLoad() {
        if (isEmptyState()) {
            launch {
                refresh()
            }
        }
    }

    private suspend fun loadData() {
        _flow.value = Result.success(repository.getNewsList()).map { it.toFilteredList() }
    }

    private fun List<News>.toFilteredList(): List<News> {
        return this.map { news -> news.copy() }
            .filter { it.type != NewsTypeEnum.ADVERT && it.type != NewsTypeEnum.UNKNOWN }
    }
}