package com.skytask.data.storage.json

import android.content.Context
import com.google.gson.Gson
import com.skytask.data.storage.GetNewsListStorage
import com.skytask.data.storage.models.DataBean
import com.skytask.data.storage.models.NewsBean
import com.skytask.data.utils.getJsonDataFromAsset

class GetNewsListJsonStorage(private val context: Context) : GetNewsListStorage {
    override fun getNewsList(): List<NewsBean> {
        val jsonFileString = getJsonDataFromAsset(context, "sample_list.json")
        val gson = Gson()
        return gson.fromJson(jsonFileString, DataBean::class.java).data
    }
}