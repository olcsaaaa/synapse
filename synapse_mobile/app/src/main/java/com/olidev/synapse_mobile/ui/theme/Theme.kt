package com.olidev.synapse_mobile.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// --- Core Material 3 Schemes ---

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

@Immutable
data class ExtraColors(
    val electricViolet: ColorFamily,
    val springGreen: ColorFamily,
    val vividMagenta: ColorFamily
)

val LocalExtraColors = staticCompositionLocalOf {
    ExtraColors(
        electricViolet = ColorFamily(Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified),
        springGreen = ColorFamily(Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified),
        vividMagenta = ColorFamily(Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified)
    )
}

object SynapseTheme {
    val extraColors: ExtraColors
        @Composable
        @ReadOnlyComposable
        get() = LocalExtraColors.current
}

@Composable
fun SynapseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkScheme
        else -> lightScheme
    }

    val extraColors = if (darkTheme) {
        ExtraColors(
            electricViolet = ColorFamily(quaternaryDark, onQuaternaryDark, quaternaryContainerDark, onQuaternaryContainerDark),
            springGreen = ColorFamily(quinaryDark, onQuinaryDark, quinaryContainerDark, onQuinaryContainerDark),
            vividMagenta = ColorFamily(senaryDark, onSenaryDark, senaryContainerDark, onSenaryContainerDark)
        )
    } else {
        ExtraColors(
            electricViolet = ColorFamily(quaternaryLight, onQuaternaryLight, quaternaryContainerLight, onQuaternaryLight),
            springGreen = ColorFamily(quinaryLight, onQuinaryLight, quinaryContainerLight, onQuinaryLight),
            vividMagenta = ColorFamily(senaryLight, onSenaryLight, senaryContainerLight, onSenaryLight)
        )
    }

    CompositionLocalProvider(
        LocalExtraColors provides extraColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}