package com.tonholo.photos.feature.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
            .fillMaxHeight()
            .then(if (showYear) Modifier else Modifier.padding(horizontal = 25.5.dp)),
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
