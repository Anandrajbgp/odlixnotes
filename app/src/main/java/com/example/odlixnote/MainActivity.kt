package com.example.odlixnote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.odlixnote.ui.navigation.AppNavigation
import com.example.odlixnote.ui.theme.OdlixnoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OdlixnoteTheme {
                AppNavigation()
            }
        }
    }
}
