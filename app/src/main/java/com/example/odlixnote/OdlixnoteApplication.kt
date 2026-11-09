package com.example.odlixnote

import android.app.Application
import com.example.odlixnote.data.local.database.AppDatabase
import com.example.odlixnote.data.repository.NoteRepository
import com.example.odlixnote.data.repository.TaskRepository

class OdlixnoteApplication : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val noteRepository by lazy { NoteRepository(database.noteDao()) }
    val taskRepository by lazy { TaskRepository(database.taskDao()) }
}
