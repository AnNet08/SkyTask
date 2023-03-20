package com.skytask.domain.models

import kotlinx.datetime.Instant

data class News(
    val id: String,
    val type: NewsTypeEnum,
    val headline: String,
    val teaserText: String,
    val weblinkUrl: String,
    val creationDate: Instant?,
    val modifiedDate: Instant?,
    val adverbURL: String,
    val teaserImageURL: String,
    val accessibilityText: String,
)

enum class NewsTypeEnum(val newsType: String) {
    STORY("story"),
    WEBLINK("weblink"),
    ADVERT("advert"),
    UNKNOWN("");

    companion object {
        fun String?.toNewsTypeEnum(): NewsTypeEnum {
            for (newsType in values()) {
                if (newsType.newsType == this) {
                    return newsType
                }
            }
            return UNKNOWN
        }
    }
}