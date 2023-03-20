package com.skytask.data.storage

import com.skytask.data.storage.models.NewsBean

interface GetNewsListStorage {
    fun getNewsList(): List<NewsBean>
}