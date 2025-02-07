package com.tonholo.photos.feature.timeline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import coil3.compose.AsyncImage
import com.tonholo.photos.core.ui.components.PhotoContainer
import com.tonholo.photos.core.ui.theme.PhotosTheme
import com.tonholo.photos.domain.model.Hashtag
import com.tonholo.photos.domain.model.Photo
import com.tonholo.photos.domain.model.PhotoUrl
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random

@Composable
fun Timeline(
    photos: List<Photo>,
    modifier: Modifier = Modifier,
    onPhotoClick: (Photo) -> Unit = {},
    onHashtagClick: (Hashtag) -> Unit = {},
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val showSideBySide =
        windowSizeClass.containsWidthDp(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(24.dp),
    ) {
        itemsIndexed(photos) { index, photo ->
            val showYear by remember(index) {
                derivedStateOf {
                    index == 0 || photo.date.year != photos[index - 1].date.year
                }
            }
            when {
                showSideBySide && index % 2 == 0 -> Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                ) {
                    TimelinePhotoContainer(
                        photo = photo,
                        onPhotoClick = onPhotoClick,
                        showYear = showYear,
                        onHashtagClick = onHashtagClick,
                        modifier = Modifier.weight(1f),
                    )
                    TimelineIndicator(
                        date = photo.date,
                        showYear = showYear,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                showSideBySide && index % 2 != 0 -> Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TimelineIndicator(
                        date = photo.date,
                        showYear = showYear,
                    )

                    TimelinePhotoContainer(
                        photo = photo,
                        onPhotoClick = onPhotoClick,
                        showYear = showYear,
                        onHashtagClick = onHashtagClick,
                        modifier = Modifier.weight(1f),
                    )
                }

                else -> TimelineItemSingleColumn(photo, showYear, onPhotoClick, onHashtagClick)
            }
        }
    }
}

@Composable
private fun TimelinePhotoContainer(
    photo: Photo,
    onPhotoClick: (Photo) -> Unit,
    showYear: Boolean,
    onHashtagClick: (Hashtag) -> Unit,
    modifier: Modifier = Modifier,
) {
    PhotoContainer(
        photo = {
            TimelinePhoto(
                photo = photo,
                onPhotoClick = onPhotoClick,
            )
        },
        description = photo.description,
        date = photo.date,
        hashtags = photo.hashtags,
        modifier = modifier
            .padding(
                top = if (showYear) 32.dp else 16.dp,
                start = 4.dp,
                bottom = 16.dp,
            ),
        onHashtagClick = onHashtagClick,
    )
}

@Composable
fun TimelinePhoto(
    photo: Photo,
    modifier: Modifier = Modifier,
    onPhotoClick: (Photo) -> Unit = {},
) {
    var isLoading by remember { mutableStateOf(true) }
    Box(
        modifier = modifier.wrapContentSize(),
    ) {
        AsyncImage(
            model = remember(photo) {
                photo.url.filterIsInstance<PhotoUrl.ThumbnailUrl>().first().url
            },
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(ratio = 4 / 3f)
                .clickable(enabled = isLoading.not()) { onPhotoClick(photo) },
            contentScale = ContentScale.Crop,
            onSuccess = {
                isLoading = false
            },
            onError = { error ->
                println("Error loading image: ${error.result.request.data}")
                error.result.throwable.printStackTrace()
            },
            clipToBounds = true
        )

        androidx.compose.animation.AnimatedVisibility(
            visible = isLoading,
            modifier = Modifier.align(Alignment.Center),
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun TimelineItemSingleColumn(
    photo: Photo,
    showYear: Boolean,
    onPhotoClick: (Photo) -> Unit,
    onHashtagClick: (Hashtag) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
    ) {
        TimelineIndicator(
            date = photo.date,
            showYear = showYear,
        )
        TimelinePhotoContainer(
            photo = photo,
            onPhotoClick = onPhotoClick,
            showYear = showYear,
            onHashtagClick = onHashtagClick,
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PhotosTheme {
        Timeline(
            photos = remember {
                List(10) {
                    Photo(
                        url = listOf(),
                        description = "Photo description $it",
                        date = LocalDate(Random.nextInt(2000, 2025), 1, 1),
                        hashtags = setOf(),
                    )
                }
            }
        )
    }
}
