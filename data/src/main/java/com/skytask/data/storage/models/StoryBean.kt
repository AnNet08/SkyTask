package com.skytask.data.storage.models

import kotlinx.serialization.Serializable

@Serializable
data class StoryBean(
    val id: String?,
    val headline: String?,
    val heroImage: HeroImage?,
    val creationDate: String?,
    val modifiedDate: String?,
    val contents: List<StoryContentItemBean>,
)

@Serializable
data class StoryContentItemBean(
    val type: String?,
    val text: String?,
    val url: String?,
    val accessibilityText: String?,
)

@Serializable
data class HeroImage(
    val imageUrl: String?,
    val accessibilityText: String?,
)