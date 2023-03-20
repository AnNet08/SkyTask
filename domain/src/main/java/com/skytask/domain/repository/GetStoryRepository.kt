package com.skytask.domain.repository

import com.skytask.domain.models.Story

interface GetStoryRepository {
    suspend fun getStory(storyId: String): Story
}