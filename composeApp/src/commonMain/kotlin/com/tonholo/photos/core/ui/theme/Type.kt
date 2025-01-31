package com.tonholo.photos.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
internal val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

internal val HandwrittenTypography
    @Composable
    get() = Typography(
        titleLarge = Typography.titleLarge.copy(
            fontFamily = Caveat,
        ),
        bodyLarge = Typography.bodyLarge.copy(
            fontFamily = Caveat,
        ),
        bodyMedium = Typography.bodyMedium.copy(
            fontFamily = Caveat,
        )
    )

interface AppTypography {
    val displayLarge: TextStyle
        @Composable get
    val displayMedium: TextStyle
        @Composable get
    val displaySmall: TextStyle
        @Composable get
    val headlineLarge: TextStyle
        @Composable get
    val headlineMedium: TextStyle
        @Composable get
    val headlineSmall: TextStyle
        @Composable get
    val titleLarge: TextStyle
        @Composable get
    val titleMedium: TextStyle
        @Composable get
    val titleSmall: TextStyle
        @Composable get
    val bodyLarge: TextStyle
        @Composable get
    val bodyMedium: TextStyle
        @Composable get
    val bodySmall: TextStyle
        @Composable get
    val labelLarge: TextStyle
        @Composable get
    val labelMedium: TextStyle
        @Composable get
    val labelSmall: TextStyle
        @Composable get
    val handwritten: Typography
        @Composable get
}

internal data class ExtendedTypograhy(
    val baseTypography: Typography,
    val handwrittenTypography: Typography,
) : AppTypography {
    override val displayLarge: TextStyle
        @Composable get() = baseTypography.displayLarge
    override val displayMedium: TextStyle
        @Composable get() = baseTypography.displayMedium
    override val displaySmall: TextStyle
        @Composable get() = baseTypography.displaySmall
    override val headlineLarge: TextStyle
        @Composable get() = baseTypography.headlineLarge
    override val headlineMedium: TextStyle
        @Composable get() = baseTypography.headlineMedium
    override val headlineSmall: TextStyle
        @Composable get() = baseTypography.headlineSmall
    override val titleLarge: TextStyle
        @Composable get() = baseTypography.titleLarge
    override val titleMedium: TextStyle
        @Composable get() = baseTypography.titleMedium
    override val titleSmall: TextStyle
        @Composable get() = baseTypography.titleSmall
    override val bodyLarge: TextStyle
        @Composable get() = baseTypography.bodyLarge
    override val bodyMedium: TextStyle
        @Composable get() = baseTypography.bodyMedium
    override val bodySmall: TextStyle
        @Composable get() = baseTypography.bodySmall
    override val labelLarge: TextStyle
        @Composable get() = baseTypography.labelLarge
    override val labelMedium: TextStyle
        @Composable get() = baseTypography.labelMedium
    override val labelSmall: TextStyle
        @Composable get() = baseTypography.labelSmall
    override val handwritten: Typography
        @Composable
        get() = handwrittenTypography
}

val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("LocalAppTypograhy is not provided in the composition local")
}
