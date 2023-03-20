package com.skytask.catsnews.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.hilt.ScreenModelKey
import com.skytask.catsnews.store.NewsListStore
import com.skytask.catsnews.store.StoryStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class NewsStoreModule {
    @Binds
    @IntoMap
    @ScreenModelKey(NewsListStore::class)
    abstract fun bindHiltNewsListScreenModel(store: NewsListStore): ScreenModel

    @Binds
    @IntoMap
    @ScreenModelKey(StoryStore::class)
    abstract fun bindHiltStoryScreenModel(store: StoryStore): ScreenModel
}