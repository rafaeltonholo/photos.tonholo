package com.tonholo.photos.domain.model

import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmInline

data class Photo(
    val url: List<PhotoUrl>,
    val description: String,
    val date: LocalDate,
    val hashtags: Set<Hashtag>,
)

sealed interface PhotoUrl {
    val url: String

    @JvmInline
    value class ThumbnailUrl(override val url: String) : PhotoUrl

    @JvmInline
    value class OriginalUrl(override val url: String) : PhotoUrl
}
