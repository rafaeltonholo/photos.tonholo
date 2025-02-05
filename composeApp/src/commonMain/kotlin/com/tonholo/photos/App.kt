package com.tonholo.photos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonholo.photos.core.ui.theme.PhotosTheme
import com.tonholo.photos.domain.model.Hashtag
import com.tonholo.photos.domain.model.Photo
import com.tonholo.photos.feature.timeline.Timeline
import kotlinx.datetime.LocalDate
import kotlin.random.Random

@Composable
fun App(modifier: Modifier = Modifier) {
    PhotosTheme {
        Surface(modifier = modifier) {
            Timeline(
                photos = remember {
                    List(size = 10) {
                        Photo(
                            url = listOf(),
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
                        )
                    }.sortedByDescending { photo -> photo.date }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                onPhotoClick = { println("Photo clicked: $it") },
                onHashtagClick = { println("Hashtag clicked: $it") },
            )
        }
    }
}
