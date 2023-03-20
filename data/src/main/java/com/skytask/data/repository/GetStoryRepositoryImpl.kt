package com.skytask.data.repository

import com.skytask.data.storage.GetStoryStorage
import com.skytask.data.storage.models.StoryBean
import com.skytask.data.storage.models.StoryContentItemBean
import com.skytask.domain.models.ContentItemType.Companion.toContentItemType
import com.skytask.domain.models.Story
import com.skytask.domain.models.StoryContentItem
import com.skytask.domain.repository.GetStoryRepository

class GetStoryRepositoryImpl(private val storageJson: GetStoryStorage) : GetStoryRepository {
    override suspend fun getStory(storyId: String): Story {
        return storageJson.getStory(storyId).toStory()
    }

    private fun StoryBean.toStory(): Story {
        return Story(
            id = id.orEmpty(),
            headline = headline.orEmpty(),
            heroImageURL = heroImage?.imageUrl.orEmpty(),
            heroImageAccessibilityText = heroImage?.accessibilityText.orEmpty(),
            creationDate = creationDate.orEmpty(),
            modifiedDate = modifiedDate.orEmpty(),
            contents = contents.map { it.toStoryContentItem() }
        )
    }

    private fun StoryContentItemBean.toStoryContentItem(): StoryContentItem {
        return StoryContentItem(
            type = type.toContentItemType(),
            text = text.orEmpty(),
            url = url.orEmpty(),
            accessibilityText = accessibilityText.orEmpty(),
        )
    }
}