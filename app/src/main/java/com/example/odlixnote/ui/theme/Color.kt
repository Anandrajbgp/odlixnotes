package com.example.odlixnote.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Realme & Google Tasks Note Palette colors
val NoteColorDefault = Color(0xFFFFFFFF)
val NoteColorYellow = Color(0xFFFFF8D6)
val NoteColorGreen = Color(0xFFE2F6D3)
val NoteColorBlue = Color(0xFFD3F0F6)
val NoteColorPink = Color(0xFFFADED9)
val NoteColorPurple = Color(0xFFEEDFF6)

val NoteColorDefaultDark = Color(0xFF1E1E1E)
val NoteColorYellowDark = Color(0xFF4A4523)
val NoteColorGreenDark = Color(0xFF2A4222)
val NoteColorBlueDark = Color(0xFF223E45)
val NoteColorPinkDark = Color(0xFF482B28)
val NoteColorPurpleDark = Color(0xFF3A2845)

fun getNoteColor(colorInt: Int, isDark: Boolean): Color {
    return when (colorInt) {
        1 -> if (isDark) NoteColorYellowDark else NoteColorYellow
        2 -> if (isDark) NoteColorGreenDark else NoteColorGreen
        3 -> if (isDark) NoteColorBlueDark else NoteColorBlue
        4 -> if (isDark) NoteColorPinkDark else NoteColorPink
        5 -> if (isDark) NoteColorPurpleDark else NoteColorPurple
        else -> if (isDark) NoteColorDefaultDark else NoteColorDefault
    }
}
