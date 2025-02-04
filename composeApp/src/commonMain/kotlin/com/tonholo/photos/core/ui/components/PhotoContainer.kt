package com.tonholo.photos.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.tonholo.photos.core.ui.theme.Theme
import com.tonholo.photos.core.ui.theme.preview.ThemedPreview
import com.tonholo.photos.domain.model.Hashtag
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import photosbytonholo.composeapp.generated.resources.Res
import photosbytonholo.composeapp.generated.resources.lighthouse

@Composable
fun PhotoContainer(
    photo: @Composable () -> Unit,
    description: String,
    date: LocalDate,
    modifier: Modifier = Modifier,
    hashtags: Set<Hashtag> = emptySet(),
    onHashtagClick: (Hashtag) -> Unit = {},
) {
    Card(
        modifier = modifier,
        shape = RectangleShape,
        border = CardDefaults.outlinedCardBorder(),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp, bottom = 8.dp),
        ) {
            photo()
            Column(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(text = description, style = Theme.typography.handwritten.titleLarge)
                Text(
                    text = remember(date) {
                        date.format(LocalDate.Format {
                            monthName(MonthNames.ENGLISH_ABBREVIATED)
                            char(value = ' ')
                            dayOfMonth()
                            chars(value = ", ")
                            year()
                        })
                    },
                    style = Theme.typography.bodySmall,
                )
                if (hashtags.isNotEmpty()) {
                    HashtagList(
                        hashtags = hashtags,
                        onClick = onHashtagClick,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ThemedPreview {
        PhotoContainer(
            photo = {
                Image(painterResource(Res.drawable.lighthouse), contentDescription = null)
            },
            description = "Lighthose -- Principe Edward Island, NS",
            date = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
            hashtags = setOf(
                Hashtag("landscapephotography", "https://www.google.com"),
                Hashtag("naturephotography", "https://www.google.com"),
                Hashtag("canada", "https://www.google.com")
            ),
        )
    }
}
