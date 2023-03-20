package com.skytask.catsnews.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skytask.catsnews.R
import com.skytask.catsnews.components.*
import com.skytask.catsnews.components.theme.*
import com.skytask.catsnews.store.StoryAction
import com.skytask.catsnews.store.StorySideEffect
import com.skytask.catsnews.store.StoryStore
import com.skytask.domain.models.ContentItemType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class StoryScreen(val newsId: String) : Screen {
    @Composable
    override fun Content() {
        val store: StoryStore = getScreenModel()
        val state by store.observeState().collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val listState = rememberLazyListState()
        CatsScreen {
            val story = state.story ?: return@CatsScreen
            Column {
                Header(
                    start = { HeaderButtonBack { navigator.pop() } },
                    center = { HeaderTitle(stringResource(id = R.string.title)) }
                )
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    state = listState
                ) {
                    if (story.contents.isEmpty() && story.headline.isEmpty()) {
                        item {
                            EmptyList()
                        }
                    } else {
                        item {
                            NewsImage(
                                modifier = Modifier,
                                contentDescription = story.heroImageAccessibilityText,
                                imageUrl = story.heroImageURL
                            )
                        }
                        item {
                            Text(
                                text = story.headline,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                SurfaceSelected,
                                                OnSurfaceVariant
                                            )
                                        )
                                    )
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 24.dp,
                                        bottom = 8.dp
                                    ),
                                style = catsTypography.subtitle2,
                                color = Background,
                            )
                        }
                        items(story.contents.size) {
                            when (story.contents[it].type) {
                                ContentItemType.PARAGRAPH -> {
                                    Text(
                                        text = story.contents[it].text.orEmpty(),
                                        style = catsTypography.body1,
                                        color = OnSurface,
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            bottom = 16.dp
                                        )
                                    )
                                }
                                ContentItemType.IMAGE -> {
                                    NewsImage(
                                        modifier = Modifier.padding(bottom = 16.dp),
                                        contentDescription = story.contents[it].accessibilityText.orEmpty(),
                                        imageUrl = story.contents[it].url.orEmpty()
                                    )
                                }
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            store.dispatch(StoryAction.OnStart(newsId))
            store.observeSideEffect().onEach { effect ->
                when (effect) {
                    StorySideEffect.Exit -> {
                        navigator.pop()
                    }
                }
            }.launchIn(this)
        }
    }
}