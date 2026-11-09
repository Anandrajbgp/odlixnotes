package com.example.odlixnote.ui.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Pin
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.odlixnote.ui.theme.getNoteColor
import com.example.odlixnote.viewmodel.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    noteId: Long,
    viewModel: NotesViewModel,
    onBackClick: () -> Unit
) {
    val notes by viewModel.notes.collectAsState()
    val existingNote = remember(noteId, notes) { notes.find { it.id == noteId } }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var backgroundColor by remember { mutableIntStateOf(0) }
    var isPinned by remember { mutableStateOf(false) }

    LaunchedEffect(existingNote) {
        if (existingNote != null) {
            title = existingNote.title
            content = existingNote.content
            backgroundColor = existingNote.backgroundColor
            isPinned = existingNote.isPinned
        }
    }

    val isDark = isSystemInDarkTheme()
    val currentCardColor = getNoteColor(backgroundColor, isDark)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == -1L) "New Note" else "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { isPinned = !isPinned }) {
                        Icon(
                            imageVector = Icons.Rounded.Pin,
                            contentDescription = "Pin",
                            tint = if (isPinned) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (noteId != -1L) {
                        IconButton(onClick = {
                            if (existingNote != null) {
                                viewModel.deleteNote(existingNote)
                                onBackClick()
                            }
                        }) {
                            Icon(Icons.Rounded.Delete, contentDescription = "Delete Note")
                        }
                    }
                    IconButton(onClick = {
                        if (title.isNotBlank() || content.isNotBlank()) {
                            if (noteId == -1L) {
                                viewModel.addNote(title, content, backgroundColor, isPinned)
                            } else if (existingNote != null) {
                                viewModel.updateNote(
                                    existingNote.copy(
                                        title = title,
                                        content = content,
                                        backgroundColor = backgroundColor,
                                        isPinned = isPinned
                                    )
                                )
                            }
                        }
                        onBackClick()
                    }) {
                        Icon(Icons.Rounded.Check, contentDescription = "Save Note")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = currentCardColor)
            )
        },
        containerColor = currentCardColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Color Palette Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (colorIndex in 0..5) {
                    val color = getNoteColor(colorIndex, isDark)
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (backgroundColor == colorIndex) 2.dp else 1.dp,
                                color = if (backgroundColor == colorIndex) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                            .clickable { backgroundColor = colorIndex }
                    )
                }
            }

            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Title") },
                textStyle = MaterialTheme.typography.titleLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = { Text("Note content...") },
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}
