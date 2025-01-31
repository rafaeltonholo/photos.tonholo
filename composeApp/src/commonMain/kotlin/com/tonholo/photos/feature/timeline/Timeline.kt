package com.tonholo.photos.feature.timeline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonholo.photos.core.ui.components.PhotoContainer
import com.tonholo.photos.core.ui.theme.PhotosTheme
import com.tonholo.photos.core.ui.theme.Theme
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
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        LazyColumn {
            itemsIndexed(photos) { index, photo ->
                val showYear by remember(index) {
                    derivedStateOf {
                        index == 0 || photo.date.year != photos[index - 1].date.year
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
                ) {
                    TimelineIndicator(
                        date = photo.date,
                        showYear = showYear,
                    )
                    PhotoContainer(
                        photo = {
                            Image(
                                painterResource(Res.drawable.lighthouse),
                                contentDescription = null,
                            )
                        },
                        description = photo.description,
                        date = photo.date,
                        hashtags = remember(photo.hashtags) {
                            photo.hashtags.map { it.value }.toSet()
                        },
                        modifier = Modifier
                            .padding(
                                top = 24.dp,
                                start = 4.dp,
                            ),
                    )
                }
            }
        }
    }
}

@Composable
private fun TimelineIndicator(
    date: LocalDate,
    modifier: Modifier = Modifier,
    showYear: Boolean = true,
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .then(if (showYear) Modifier else Modifier.padding(horizontal = 24.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (showYear) {
            Surface(
                shape = RoundedCornerShape(percent = 100),
                color = Theme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            ) {
                Text(
                    text = date.year.toString(),
                    style = Theme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
        Spacer(
            modifier = Modifier
                .width(1.dp)
                .weight(1f)
                .background(Theme.colorScheme.onBackground.copy(alpha = 0.2f))
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
