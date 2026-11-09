package com.example.odlixnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.odlixnote.OdlixnoteApplication
import com.example.odlixnote.data.local.entity.NoteEntity
import com.example.odlixnote.data.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    @OptIn(ExperimentalCoroutinesApi::class)
    val notes: StateFlow<List<NoteEntity>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                noteRepository.allNotes
            } else {
                noteRepository.searchNotes(query.trim())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun searchNotes(query: String) {
        _searchQuery.value = query
    }

    fun addNote(title: String, content: String, backgroundColor: Int = 0, isPinned: Boolean = false) {
        viewModelScope.launch {
            val note = NoteEntity(
                title = title,
                content = content,
                backgroundColor = backgroundColor,
                isPinned = isPinned,
                updatedAt = System.currentTimeMillis()
            )
            noteRepository.insertNote(note)
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            val updated = note.copy(updatedAt = System.currentTimeMillis())
            noteRepository.updateNote(updated)
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }

    fun deleteNotes(noteIds: List<Long>) {
        viewModelScope.launch {
            for (id in noteIds) {
                noteRepository.deleteNoteById(id)
            }
        }
    }

    fun updateNoteColor(noteId: Long, color: Int) {
        viewModelScope.launch {
            val note = noteRepository.getNoteById(noteId)
            if (note != null) {
                val updated = note.copy(backgroundColor = color, updatedAt = System.currentTimeMillis())
                noteRepository.updateNote(updated)
            }
        }
    }

    fun togglePin(noteId: Long) {
        viewModelScope.launch {
            val note = noteRepository.getNoteById(noteId)
            if (note != null) {
                val updated = note.copy(isPinned = !note.isPinned, updatedAt = System.currentTimeMillis())
                noteRepository.updateNote(updated)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OdlixnoteApplication)
                NotesViewModel(application.noteRepository)
            }
        }
    }
}
