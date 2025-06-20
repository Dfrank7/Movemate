package com.test.movemate.ui.theme

import android.app.Activity
import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = PrimaryTextColor,
    secondary = SecondaryColor,
    onSecondary = SecondaryTextColor,
    tertiary = TertiaryColor,
    onTertiary = PrimaryTextColor,
    background = BackgroundLightColor,
    onBackground = Color.Black,
    surface = SurfaceLight,
    onSurface = Color.Black,
    surfaceVariant = SurfaceLight,
    onSurfaceVariant = Color.Black,
    secondaryContainer = SecondaryColor,
    onSecondaryContainer = Color.White,
    outlineVariant = IconDefaultColor,
    error = ErrorColor,
    onError = OnErrorLightColor,
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = PrimaryTextColor,
    secondary = SecondaryColor,
    onSecondary = PrimaryTextColor,
    tertiary = TertiaryColor,
    onTertiary = PrimaryTextColor,
    background = BackgroundDarkColor,
    onBackground = Color.White,
    surface = SurfaceDark,
    onSurface = Color.White,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = Color.White,
    secondaryContainer = SecondaryColor,
    onSecondaryContainer = Color.White,
    outlineVariant = IconDefaultColor,
    error = ErrorColor,
    onError = OnErrorColor,
)

@Composable
fun MoveMateChallengeTheme(
    theme: Int,
    content: @Composable () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    val autoColors = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme

    val dynamicColors = if (supportsDynamicTheming()) {
        val context = LocalContext.current
        if (isSystemInDarkTheme()) {
            dynamicDarkColorScheme(context)
        } else {
            dynamicLightColorScheme(context)
        }
    } else {
        autoColors
    }

    val colors = when (theme) {
        Theme.LIGHT_THEME.themeValue -> LightColorScheme
        Theme.DARK_THEME.themeValue -> DarkColorScheme
        Theme.MATERIAL_YOU.themeValue -> dynamicColors
        else -> autoColors
    }

    val systemUiController = rememberSystemUiController()

    val statusBarColor = LightColorScheme.primary

    val darkIcons = false

//    SideEffect {
//        systemUiController.setStatusBarColor(
//            color     = statusBarColor,
//            darkIcons = darkIcons
//        )
//        // If you also want to tint the navigation bar:
//        // sysUi.setNavigationBarColor(statusBarColor, darkIcons)
//    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = !isDarkTheme
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

// To be used to set the preferred theme inside settings
enum class Theme(
    val themeValue: Int
) {
    MATERIAL_YOU(
        themeValue = 12
    ),
    FOLLOW_SYSTEM(
        themeValue = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    ),
    LIGHT_THEME(
        themeValue = AppCompatDelegate.MODE_NIGHT_NO
    ),
    DARK_THEME(
        themeValue = AppCompatDelegate.MODE_NIGHT_YES
    );
}

//@Composable
//fun MovemateTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
//    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
//    content: @Composable () -> Unit
//) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
//}