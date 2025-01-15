package com.om.diucampusschedule.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = P_80_dark,
    secondary = S_80_dark,
    tertiary = T_80_dark,
    error = E_80_dark,
    onPrimary = P_20_dark,
    onSecondary = S_20_dark,
    onTertiary = T_20_dark,
    onError = E_20_dark,
    primaryContainer = P_30_dark,
    secondaryContainer = S_30_dark,
    tertiaryContainer = T_30_dark,
    errorContainer = E_30_dark,
    onPrimaryContainer = P_90_dark,
    onSecondaryContainer = S_90_dark,
    onTertiaryContainer = T_90_dark,
    onErrorContainer = E_90_dark,
    surfaceDim = N_6_dark,
    surface = N_6_dark,
    surfaceBright = N_24_dark,
    inverseSurface = N_90_dark,
    surfaceContainerLowest = N_4_dark,
    surfaceContainerLow = N_10_dark,
    surfaceContainer = N_12_dark,
    surfaceContainerHigh = N_17_dark,
    surfaceContainerHighest = N_24_dark,
    inverseOnSurface = N_20_dark,
    inversePrimary = P_40_dark,
    onSurface = N_90_dark,
    onSurfaceVariant = NV_90_dark,
    outline = NV_60_dark,
    outlineVariant = NV_30_dark,
    scrim = N_0_dark
)

private val LightColorScheme = lightColorScheme(
    primary = P_40,
    secondary = S_40,
    tertiary = T_40,
    error = E_40,
    onPrimary = P_100,
    onSecondary = S_100,
    onTertiary = T_100,
    onError = E_100,
    primaryContainer = P_90,
    secondaryContainer = S_90,
    tertiaryContainer = T_90,
    errorContainer = E_90,
    onPrimaryContainer = P_10,
    onSecondaryContainer = S_10,
    onTertiaryContainer = T_10,
    onErrorContainer = E_10,
    surfaceDim = N_87,
    surface = N_98,
    surfaceBright = N_98,
    inverseSurface = N_20,
    surfaceContainerLowest = N_100,
    surfaceContainerLow = N_96,
    surfaceContainer = N_94,
    surfaceContainerHigh = N_92,
    surfaceContainerHighest = N_90,
    inverseOnSurface = N_95,
    inversePrimary = P_80,
    onSurface = N_10,
    onSurfaceVariant = NV_30,
    outline = NV_50,
    outlineVariant = NV_80,
    scrim = N_0


)

@Composable
fun DIUCampusScheduleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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