package com.skytask.catsnews.di

import android.content.Context
import com.skytask.data.repository.GetNewsListRepositoryImpl
import com.skytask.data.repository.GetStoryRepositoryImpl
import com.skytask.data.storage.GetNewsListStorage
import com.skytask.data.storage.GetStoryStorage
import com.skytask.data.storage.json.GetNewsListJsonStorage
import com.skytask.data.storage.json.GetStoryJsonStorage
import com.skytask.domain.repository.GetNewsListRepository
import com.skytask.domain.repository.GetStoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideGetNewsListStorage(@ApplicationContext context: Context): GetNewsListStorage {
        return GetNewsListJsonStorage(context = context)
    }

    @Provides
    @Singleton
    fun provideGetNewsListRepository(storage: GetNewsListStorage): GetNewsListRepository {
        return GetNewsListRepositoryImpl(storageJson = storage)
    }

    @Provides
    @Singleton
    fun provideGetStoryStorage(@ApplicationContext context: Context): GetStoryStorage {
        return GetStoryJsonStorage(context = context)
    }

    @Provides
    @Singleton
    fun provideGetStoryRepository(storage: GetStoryStorage): GetStoryRepository {
        return GetStoryRepositoryImpl(storageJson = storage)
    }
}