package com.skytask.domain.models

data class Story(
    val id: String,
    val headline: String,
    val heroImageURL: String,
    val heroImageAccessibilityText: String,
    val creationDate: String,
    val modifiedDate: String,
    val contents: List<StoryContentItem>,
)

data class StoryContentItem(
    val type: ContentItemType,
    val text: String?,
    val url: String?,
    val accessibilityText: String?,
)

enum class ContentItemType(val contentItemType: String) {
    PARAGRAPH("paragraph"),
    IMAGE("image"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun String?.toContentItemType(): ContentItemType {
            for (contentItemType in values()) {
                if (contentItemType.contentItemType == this) {
                    return contentItemType
                }
            }
            return UNKNOWN
        }
    }
}
