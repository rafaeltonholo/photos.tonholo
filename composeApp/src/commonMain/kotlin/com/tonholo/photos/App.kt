package com.tonholo.photos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonholo.photos.core.ui.theme.PhotosTheme
import com.tonholo.photos.domain.model.Photo
import com.tonholo.photos.domain.repository.TimelineRepository
import com.tonholo.photos.feature.timeline.Timeline

@Composable
fun App(modifier: Modifier = Modifier) {
    // temp
    val repo = remember { TimelineRepository() }
    val photos = remember { mutableStateListOf<Photo>() }
    LaunchedEffect(Unit) {
        photos.addAll(repo.getTimeline(cursor = null, count = 10).photos)
    }

    PhotosTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
        ) {
            Timeline(
                photos = photos,
                modifier = Modifier
                    .widthIn(max = 1024.dp),
                onPhotoClick = { println("Photo clicked: $it") },
                onHashtagClick = { println("Hashtag clicked: $it") },
            )
        }
    }
}
