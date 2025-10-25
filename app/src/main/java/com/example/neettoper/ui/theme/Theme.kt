package com.example.neettoper.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Define the light color scheme using your new palette
private val AppLightColorScheme = lightColorScheme(
    primary = NeetPrimary,
    onPrimary = NeetOnPrimary,
    background = NeetBackground,
    onBackground = NeetText,
    surface = NeetSurface,
    onSurface = NeetText,
    outline = NeetBorder, // Used for OutlinedTextField border
    onSurfaceVariant = NeetTextSecondary // Used for OutlinedTextField label
)

@Composable
fun NeetToperTheme(
    // We are forcing the light theme to use your custom palette
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false, // Disable dynamic color
    content: @Composable () -> Unit
) {
    val colorScheme = AppLightColorScheme // Always use our custom light scheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb() // Use background for status bar
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}