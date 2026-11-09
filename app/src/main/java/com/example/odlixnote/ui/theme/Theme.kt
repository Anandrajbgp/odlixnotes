package com.example.odlixnote.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF98CCF8),
    onPrimary = Color(0xFF003356),
    primaryContainer = Color(0xFF004A77),
    onPrimaryContainer = Color(0xFFCBE6FF),
    secondary = Color(0xFFB5C7D9),
    onSecondary = Color(0xFF20323F),
    secondaryContainer = Color(0xFF364857),
    onSecondaryContainer = Color(0xFFD1E3F5),
    tertiary = Color(0xFFD3B5E5),
    onTertiary = Color(0xFF382348),
    background = Color(0xFF111315),
    onBackground = Color(0xFFE2E2E5),
    surface = Color(0xFF111315),
    onSurface = Color(0xFFE2E2E5),
    surfaceVariant = Color(0xFF41474D),
    onSurfaceVariant = Color(0xFFC1C7CE),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006399),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFCBE6FF),
    onPrimaryContainer = Color(0xFF001E32),
    secondary = Color(0xFF4E616F),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD1E3F5),
    onSecondaryContainer = Color(0xFF091E2A),
    tertiary = Color(0xFF67587A),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFF8F9FA),
    onBackground = Color(0xFF1A1C1E),
    surface = Color(0xFFF8F9FA),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFDFE3EB),
    onSurfaceVariant = Color(0xFF43474E),
)

@Composable
fun OdlixnoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
