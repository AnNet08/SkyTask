package com.skytask.data.storage.json

import android.content.Context
import com.google.gson.Gson
import com.skytask.data.storage.GetStoryStorage
import com.skytask.data.storage.models.StoryBean
import com.skytask.data.utils.getJsonDataFromAsset

class GetStoryJsonStorage(private val context: Context) : GetStoryStorage {
    override fun getStory(storyId: String): StoryBean {
        val jsonFileString = getJsonDataFromAsset(context, "sample_story1.json")
        val gson = Gson()
        return gson.fromJson(jsonFileString, StoryBean::class.java)
    }
}