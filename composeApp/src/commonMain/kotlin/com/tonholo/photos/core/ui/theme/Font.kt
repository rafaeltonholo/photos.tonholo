package com.tonholo.photos.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import photosbytonholo.composeapp.generated.resources.Res
import photosbytonholo.composeapp.generated.resources.caveat_variablefont_wght

val Caveat: FontFamily
    @Composable
    get() = FontFamily(
        Font(
            resource = Res.font.caveat_variablefont_wght,
        ),
    )
