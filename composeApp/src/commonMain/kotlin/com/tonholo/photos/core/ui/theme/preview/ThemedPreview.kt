package com.tonholo.photos.core.ui.theme.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonholo.photos.core.ui.theme.PhotosTheme
import com.tonholo.photos.core.ui.theme.Theme

@Composable
fun ThemedPreview(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ThemedPreview(darkTheme = false, content)
        Spacer(modifier = Modifier.height(16.dp))
        ThemedPreview(darkTheme = true, content)
    }
}

@Composable
private fun ColumnScope.ThemedPreview(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    Text(if (darkTheme) "Dark mode:" else "Light mode:")
    PhotosTheme(darkTheme = darkTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colorScheme.background)
                .padding(16.dp),
        ) {
            content()
        }
    }
}
