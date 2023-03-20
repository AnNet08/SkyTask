package com.skytask.catsnews.store

import cafe.adriel.voyager.core.model.ScreenModel
import com.skytask.domain.models.Story
import com.skytask.domain.nanoredux.Action
import com.skytask.domain.nanoredux.Effect
import com.skytask.domain.nanoredux.State
import com.skytask.domain.nanoredux.Store
import com.skytask.domain.usecase.GetStoryUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StoryState(
    val isProgress: Boolean = false,
    val story: Story? = null,
) : State

sealed class StoryAction : Action {
    internal class StoryLoaded(val story: Story?) : StoryAction()
    internal class OnStart(val storyId: String) : StoryAction()
}

sealed class StorySideEffect : Effect {
    object Exit : StorySideEffect()
}

class StoryStore @Inject constructor(
    private val getStoryUseCase: GetStoryUseCase,
) : Store<StoryState, StoryAction, StorySideEffect>,
    ScreenModel,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private val state: MutableStateFlow<StoryState> = MutableStateFlow(StoryState())

    private val sideEffect = MutableSharedFlow<StorySideEffect>()

    override fun observeState(): StateFlow<StoryState> = state

    override fun observeSideEffect(): Flow<StorySideEffect> = sideEffect

    override fun dispatch(action: StoryAction) {
        val oldState = state.value
        val newState = when (action) {
            is StoryAction.OnStart -> {
                launch {
                    getStoryUseCase(action.storyId).onSuccess { story ->
                        dispatch(StoryAction.StoryLoaded(story))
                    }.onFailure {
                        sideEffect.emit(StorySideEffect.Exit)
                    }
                }
                oldState
            }
            is StoryAction.StoryLoaded -> {
                oldState.copy(story = action.story)
            }
        }
        if (newState != oldState) {
            state.value = newState
        }
    }
}