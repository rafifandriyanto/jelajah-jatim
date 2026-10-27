package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.ui.JelajahJatimAppRoot
import com.example.ui.theme.JelajahJatimTheme
import com.example.ui.theme.JelajahThemePreset

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var selectedThemePreset by rememberSaveable {
                mutableStateOf(JelajahThemePreset.MONO_EDITORIAL)
            }
            var lastLightPreset by rememberSaveable {
                mutableStateOf(JelajahThemePreset.MONO_EDITORIAL)
            }

            val isDarkTheme = selectedThemePreset == JelajahThemePreset.MALAM_JATIM

            JelajahJatimTheme(
                darkTheme = isDarkTheme,
                themePreset = selectedThemePreset
            ) {
                JelajahJatimAppRoot(
                    isDarkTheme = isDarkTheme,
                    onToggleDarkTheme = {
                        selectedThemePreset = if (isDarkTheme) {
                            lastLightPreset
                        } else {
                            JelajahThemePreset.MALAM_JATIM
                        }
                    },
                    selectedThemePreset = selectedThemePreset,
                    onSelectThemePreset = { preset ->
                        selectedThemePreset = preset
                        if (preset != JelajahThemePreset.MALAM_JATIM) {
                            lastLightPreset = preset
                        }
                    }
                )
            }
        }
    }
}
