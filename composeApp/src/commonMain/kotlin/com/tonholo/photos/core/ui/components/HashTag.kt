package com.tonholo.photos.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import com.tonholo.photos.core.ui.theme.Theme
import com.tonholo.photos.core.ui.theme.preview.ThemedPreview
import com.tonholo.photos.domain.model.Hashtag
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HashtagList(
    hashtags: Set<Hashtag>,
    onClick: (Hashtag) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Theme.colorScheme.primary,
) {
    Text(
        text = buildAnnotatedString {
                hashtags.forEach { hashtag ->
                    withLink(
                        LinkAnnotation.Clickable(
                            tag = hashtag.link,
                            styles = TextLinkStyles(style = SpanStyle(color = color))
                        ) {
                            onClick(hashtag)
                        },
                    ) {
                        if (!hashtag.value.startsWith('#')) {
                            append("#")
                        }
                        append(hashtag.value)
                    }
                    append(" ")
                }
            },
        style = Theme.typography.labelSmall,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun Preview() {
    ThemedPreview {
        HashtagList(
            hashtags = setOf(
                Hashtag("tag1", "https://www.google.com"),
                Hashtag("tag2", "https://www.google.com"),
                Hashtag("tag3", "https://www.google.com"),
            ),
            onClick = {},
        )
    }
}
