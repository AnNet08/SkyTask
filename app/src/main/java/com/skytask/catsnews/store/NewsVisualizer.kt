package com.skytask.catsnews.store

import com.skytask.domain.models.News
import kotlinx.datetime.Clock
import kotlin.time.Duration

class NewsVisualizer(news: News) {

    private val duration = news.creationDate?.let { Clock.System.now() - it } ?: Duration.ZERO
    val stampOfChangingLabel: Any
        get() = when {
            duration.inWholeMinutes < 1 -> "now"
            duration.inWholeHours < 1 -> "${duration.inWholeMinutes}m ago"
            duration.inWholeDays < 1 -> "${duration.inWholeHours}h ago"
            duration.inWholeDays in 1..1 -> "yesterday"
            duration.inWholeDays >= 2 -> "${duration.inWholeDays}d ago"
            else -> {
                "something wrong"
            }
        }
}