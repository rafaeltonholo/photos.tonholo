package com.tonholo.photos.feature.timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonholo.photos.core.ui.theme.Theme
import kotlinx.datetime.LocalDate

@Composable
fun TimelineIndicator(
    date: LocalDate,
    modifier: Modifier = Modifier,
    showYear: Boolean = true,
) {
    Column(
        modifier = modifier
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
        }
        VerticalDivider(
            thickness = 1.dp,
            color = Theme.colorScheme.onBackground.copy(alpha = 0.2f),
            modifier = Modifier.weight(1f),
        )
    }
}
