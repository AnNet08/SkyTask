package com.skytask.domain.repository

import com.skytask.domain.models.News

interface GetNewsListRepository {
    suspend fun getNewsList(): List<News>
}