package com.skytask.data.storage

import com.skytask.data.storage.models.StoryBean

interface GetStoryStorage {
    fun getStory(storyId: String): StoryBean
}