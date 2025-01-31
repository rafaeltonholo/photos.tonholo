package com.tonholo.photos.core.ui.theme.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tonholo.photos.core.ui.theme.PhotosTheme
import com.tonholo.photos.core.ui.theme.Theme

@Composable
fun ThemedPreview(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
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
    content: @Composable () -> Unit
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
