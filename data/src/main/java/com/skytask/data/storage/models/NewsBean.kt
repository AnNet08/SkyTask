package com.skytask.data.storage.models

import kotlinx.serialization.Serializable

@Serializable
data class DataBean(
    val title: String?,
    val data: List<NewsBean>,
)

@Serializable
data class NewsBean(
    val id: String?,
    val type: String?,
    val headline: String?,
    val teaserText: String?,
    val weblinkUrl: String?,
    val creationDate: String?,
    val modifiedDate: String?,
    val url: String?,
    val teaserImage: TeaserImage?,
)

@Serializable
data class TeaserImage(
    val _links: ImageLinks?,
    val accessibilityText: String?,
)

@Serializable
data class ImageLinks(
    val url: ImageUrl?,
)

@Serializable
data class ImageUrl(
    val href: String?,
    val templated: Boolean?,
    val type: String?,
)