package com.skytask.catsnews.di

import com.skytask.domain.repository.GetNewsListRepository
import com.skytask.domain.repository.GetStoryRepository
import com.skytask.domain.usecase.GetNewsListUseCase
import com.skytask.domain.usecase.GetStoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class DomainModule {
    @Provides
    fun provideGetNewsListUseCase(repository: GetNewsListRepository): GetNewsListUseCase {
        return GetNewsListUseCase(repository = repository)
    }

    @Provides
    fun provideGetStoryUseCase(repository: GetStoryRepository): GetStoryUseCase {
        return GetStoryUseCase(repository = repository)
    }
}