package com.skytask.catsnews.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skytask.catsnews.R
import com.skytask.catsnews.components.*
import com.skytask.catsnews.components.theme.OnSurface
import com.skytask.catsnews.components.theme.OnSurfaceVariant
import com.skytask.catsnews.components.theme.catsTypography
import com.skytask.catsnews.store.NewsListAction
import com.skytask.catsnews.store.NewsListStore
import com.skytask.catsnews.store.NewsVisualizer
import com.skytask.domain.models.News
import com.skytask.domain.models.NewsTypeEnum

object NewsListScreen : Screen {
    @Composable
    override fun Content() {
        val store: NewsListStore = getScreenModel()
        val state by store.observeState().collectAsState()
        val navigator = LocalNavigator.currentOrThrow
        val listState = rememberLazyListState()
        CatsScreen {
            val context = LocalContext.current
            Column {
                Header(
                    start = { },
                    center = { HeaderTitle(stringResource(id = R.string.title)) }
                )
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing && state.newsList.isNotEmpty()),
                    onRefresh = { store.dispatch(NewsListAction.Refresh) },
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight(),
                        state = listState
                    ) {
                        if (state.isRefreshing && state.newsList.isEmpty()) {
                            items(10) {
                                Column {
                                    NewsShimmer()
                                    Divider(
                                        modifier = Modifier.padding(
                                            start = 112.dp,
                                            end = 16.dp
                                        )
                                    )
                                }
                            }
                        } else if (state.newsList.isEmpty()) {
                            item {
                                EmptyList()
                            }
                        } else {
                            item {
                                val currentNews = state.newsList[0]
                                NewsFirstItem(
                                    news = currentNews,
                                    select = {
                                        when (currentNews.type) {
                                            NewsTypeEnum.STORY -> navigator.push(
                                                StoryScreen(
                                                    currentNews.id
                                                )
                                            )
                                            NewsTypeEnum.WEBLINK -> context.openUrl(currentNews.weblinkUrl)
                                            else -> {}
                                        }
                                    }
                                )
                            }
                            items(state.newsList.size - 1) {
                                val currentNews = state.newsList[it + 1]
                                NewsItem(
                                    news = currentNews,
                                    select = {
                                        when (currentNews.type) {
                                            NewsTypeEnum.STORY -> navigator.push(
                                                StoryScreen(
                                                    currentNews.id
                                                )
                                            )
                                            NewsTypeEnum.WEBLINK -> context.openUrl(currentNews.weblinkUrl)
                                            else -> {}
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun NewsShimmer() {
        Box(
            Modifier.fillMaxWidth()
        ) {
            Column {
                Row {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(80.dp, 80.dp)
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.shimmer(),
                            )
                    )
                    Column(
                        Modifier.padding(end = 16.dp, bottom = 16.dp, top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(150.dp, 12.dp)
                                .placeholder(
                                    visible = true,
                                    highlight = PlaceholderHighlight.shimmer(),
                                )
                        )
                        Box(
                            modifier = Modifier
                                .size(150.dp, 12.dp)
                                .placeholder(
                                    visible = true,
                                    highlight = PlaceholderHighlight.shimmer(),
                                )
                        )
                    }
                }
            }
            Box(
                Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .align(Alignment.TopEnd)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp, 12.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                        )
                )
            }
        }
    }

    @Composable
    private fun NewsFirstItem(news: News, select: (News) -> Unit) {
        val visualizer = remember(news) {
            NewsVisualizer(news)
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .clickable { select(news) },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NewsImage(
                modifier = Modifier.padding(bottom = 8.dp),
                contentDescription = news.accessibilityText,
                imageUrl = news.teaserImageURL
            )
            Text(
                text = news.headline,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = catsTypography.subtitle1,
                color = OnSurface,
            )
            Text(
                text = news.teaserText,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = catsTypography.body1,
                color = OnSurface,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = visualizer.stampOfChangingLabel.toString(),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = catsTypography.body2,
                color = OnSurfaceVariant,
            )
        }
    }

    @Composable
    private fun NewsItem(news: News, select: (News) -> Unit) {
        val visualizer = remember(news) {
            NewsVisualizer(news)
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { select(news) }) {
                    NewsImage(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(80.dp),
                        contentDescription = news.accessibilityText,
                        imageUrl = news.teaserImageURL
                    )
                    Column(
                        Modifier.padding(end = 16.dp, bottom = 16.dp, top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = news.headline,
                            style = catsTypography.subtitle1,
                            color = OnSurface
                        )
                        Text(
                            text = news.teaserText,
                            style = catsTypography.body1,
                            color = OnSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Divider(modifier = Modifier.padding(start = 112.dp, end = 16.dp))
            }
            Box(
                Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = visualizer.stampOfChangingLabel.toString(),
                    style = catsTypography.body2,
                    color = OnSurfaceVariant,
                )
            }
        }
    }
}