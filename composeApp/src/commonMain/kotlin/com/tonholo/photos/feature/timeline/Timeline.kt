package com.tonholo.photos.feature.timeline

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.tonholo.photos.core.ui.components.PhotoContainer
import com.tonholo.photos.core.ui.theme.PhotosTheme
import com.tonholo.photos.domain.model.Hashtag
import com.tonholo.photos.domain.model.Photo
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import photosbytonholo.composeapp.generated.resources.Res
import photosbytonholo.composeapp.generated.resources.lighthouse
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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.widthIn(max = 700.dp),
    ) {
        itemsIndexed(photos) { index, photo ->
            val showYear by remember(index) {
                derivedStateOf {
                    index == 0 || photo.date.year != photos[index - 1].date.year
                }
            }
            TimelineItem(
                index = index,
                photo = photo,
                showSideBySide = showSideBySide,
                showYear = showYear,
                onPhotoClick = onPhotoClick,
                onHashtagClick = onHashtagClick,
            )
        }
    }
}

@Composable
private fun TimelineItem(
    index: Int,
    photo: Photo,
    showSideBySide: Boolean,
    showYear: Boolean,
    onPhotoClick: (Photo) -> Unit,
    onHashtagClick: (Hashtag) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.aligned(Alignment.CenterHorizontally),
        modifier = modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .heightIn(max = 520.dp)
            .widthIn(max = 1024.dp),
    ) {
        val space = remember {
            movableContentOf {
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        val photoContainer = rememberMovablePhotoContainer(
            photo,
            showYear,
            onPhotoClick,
            onHashtagClick,
        )

        if (showSideBySide) {
            if (index % 2 == 0) {
                photoContainer()
            } else {
                space()
            }
        }
        TimelineIndicator(
            date = photo.date,
            showYear = showYear,
        )
        if (!showSideBySide || index % 2 != 0) {
            photoContainer()
        } else {
            space()
        }
    }
}

@Composable
private fun RowScope.rememberMovablePhotoContainer(
    photo: Photo,
    showYear: Boolean,
    onPhotoClick: (Photo) -> Unit,
    onHashtagClick: (Hashtag) -> Unit,
) = remember(photo) {
    movableContentOf {
        PhotoContainer(
            photo = {
                Image(
                    painterResource(Res.drawable.lighthouse),
                    contentDescription = null,
                    modifier = Modifier.clickable { onPhotoClick(photo) },
                )
            },
            description = photo.description,
            date = photo.date,
            hashtags = photo.hashtags,
            modifier = Modifier
                .weight(1f)
                .padding(
                    top = if (showYear) 32.dp else 16.dp,
                    start = 4.dp,
                    bottom = 16.dp,
                ),
            onHashtagClick = onHashtagClick,
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
