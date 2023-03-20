package com.skytask.data.repository

import com.skytask.data.storage.GetNewsListStorage
import com.skytask.data.storage.models.NewsBean
import com.skytask.domain.models.News
import com.skytask.domain.models.NewsTypeEnum.Companion.toNewsTypeEnum
import com.skytask.domain.repository.GetNewsListRepository
import kotlinx.datetime.toInstant

class GetNewsListRepositoryImpl(private val storageJson: GetNewsListStorage) :
    GetNewsListRepository {

    override suspend fun getNewsList(): List<News> {
        return storageJson.getNewsList().map { it.toNews() }
    }

    private fun NewsBean.toNews(): News {
        return News(
            id = id.orEmpty(),
            type = type.toNewsTypeEnum(),
            headline = headline.orEmpty(),
            teaserText = teaserText.orEmpty(),
            weblinkUrl = weblinkUrl.orEmpty(),
            creationDate = creationDate?.toInstant(),
            modifiedDate = modifiedDate?.toInstant(),
            adverbURL = url.orEmpty(),
            teaserImageURL = teaserImage?._links?.url?.href.orEmpty(),
            accessibilityText = teaserImage?.accessibilityText.orEmpty(),
        )
    }
}