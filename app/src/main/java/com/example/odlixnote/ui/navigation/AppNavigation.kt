package com.example.odlixnote.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.example.odlixnote.ui.notes.AddEditNoteScreen
import com.example.odlixnote.ui.notes.NotesListScreen
import com.example.odlixnote.ui.tasks.TasksListScreen
import com.example.odlixnote.viewmodel.NotesViewModel
import com.example.odlixnote.viewmodel.TasksViewModel
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen : NavKey {
    @Serializable
    data object NotesList : Screen

    @Serializable
    data object TasksList : Screen

    @Serializable
    data class AddEditNote(val noteId: Long = -1L) : Screen
}

@Composable
fun AppNavigation() {
    val notesBackStack = remember { mutableStateListOf<NavKey>(Screen.NotesList) }
    val tasksBackStack = remember { mutableStateListOf<NavKey>(Screen.TasksList) }

    var currentTab by remember { mutableStateOf<NavKey>(Screen.NotesList) }

    val activeBackStack = when (currentTab) {
        is Screen.TasksList -> tasksBackStack
        else -> notesBackStack
    }

    val notesViewModel: NotesViewModel = viewModel(factory = NotesViewModel.Factory)
    val tasksViewModel: TasksViewModel = viewModel(factory = TasksViewModel.Factory)

    val entryProvider: (NavKey) -> NavEntry<NavKey> = { key ->
        when (key) {
            is Screen.NotesList -> NavEntry(key) {
                NotesListScreen(
                    viewModel = notesViewModel,
                    onNoteClick = { noteId -> notesBackStack.add(Screen.AddEditNote(noteId)) },
                    onAddNoteClick = { notesBackStack.add(Screen.AddEditNote(-1L)) }
                )
            }
            is Screen.TasksList -> NavEntry(key) {
                TasksListScreen(viewModel = tasksViewModel)
            }
            is Screen.AddEditNote -> NavEntry(key) {
                AddEditNoteScreen(
                    noteId = key.noteId,
                    viewModel = notesViewModel,
                    onBackClick = {
                        if (notesBackStack.size > 1) {
                            notesBackStack.removeLastOrNull()
                        }
                    }
                )
            }
            else -> error("Unknown route: $key")
        }
    }

    val isTopLevel = activeBackStack.size <= 1

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            AnimatedVisibility(
                visible = isTopLevel,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentTab is Screen.NotesList,
                        onClick = { currentTab = Screen.NotesList },
                        icon = { Icon(Icons.Rounded.Description, contentDescription = "Notes") },
                        label = { Text("Notes") }
                    )
                    NavigationBarItem(
                        selected = currentTab is Screen.TasksList,
                        onClick = { currentTab = Screen.TasksList },
                        icon = { Icon(Icons.Rounded.CheckCircle, contentDescription = "Tasks") },
                        label = { Text("Tasks") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = activeBackStack,
            onBack = {
                if (activeBackStack.size > 1) {
                    activeBackStack.removeLastOrNull()
                }
            },
            entryProvider = entryProvider,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
