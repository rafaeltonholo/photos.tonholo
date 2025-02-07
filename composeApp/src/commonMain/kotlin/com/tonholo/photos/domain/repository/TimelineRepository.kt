package com.tonholo.photos.domain.repository

import com.tonholo.photos.domain.model.Cursor
import com.tonholo.photos.domain.model.Hashtag
import com.tonholo.photos.domain.model.Paging
import com.tonholo.photos.domain.model.Photo
import com.tonholo.photos.domain.model.PhotoUrl
import com.tonholo.photos.domain.model.Timeline
import kotlinx.datetime.LocalDate
import kotlin.random.Random

interface TimelineRepository {
    suspend fun getTimeline(cursor: Cursor?, count: Int): Timeline
}

fun TimelineRepository(): TimelineRepository = object : TimelineRepository {
    override suspend fun getTimeline(cursor: Cursor?, count: Int): Timeline {
        val date = cursor?.let { LocalDate.parse(it.value) }
            ?: LocalDate.parse("1900-01-01")
        val photos = getPhotos()
            .filter { it.date > date }
            .take(count)
            .sortedByDescending(Photo::date)
            .toList()
        return Timeline(
            photos = photos,
            paging = Paging(
                count = 10,
                next = null,
                prev = null,
            ),
        )
    }

    private fun getPhotos(): Sequence<Photo> {
        return sequence {
            repeat(times = 100) {
                val photoId = Random.nextInt(from = 100, until = 1000)
                yield(
                    Photo(
                        url = listOf(
                            PhotoUrl.ThumbnailUrl("https://picsum.photos/id/${photoId}/720/480"),
                            PhotoUrl.OriginalUrl("https://picsum.photos/id/${photoId}/3840/2160"),
                        ),
                        description = "Photo description $it",
                        date = LocalDate(
                            year = Random.nextInt(from = 2000, until = 2025),
                            monthNumber = 1,
                            dayOfMonth = 1,
                        ),
                        hashtags = setOf(
                            Hashtag(
                                value = "landscapephotography",
                                link = "https://www.google.com"
                            ),
                            Hashtag(
                                value = "naturephotography",
                                link = "https://www.google.com"
                            ),
                            Hashtag(value = "canada", link = "https://www.google.com"),
                            Hashtag(value = "mountains", link = "https://www.google.com"),
                        ),
                    ),
                )
            }
        }
    }
}
