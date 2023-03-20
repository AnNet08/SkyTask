package com.skytask.domain.usecase

import com.skytask.domain.models.Story
import com.skytask.domain.repository.GetStoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class GetStoryUseCase(private val repository: GetStoryRepository) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default

    suspend operator fun invoke(storyId: String): Result<Story?> = runCatching {
        withContext(Dispatchers.Default) {
            repository.getStory(storyId)
        }
    }
}
