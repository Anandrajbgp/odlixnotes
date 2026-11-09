package com.example.odlixnote.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val content: String,
    val backgroundColor: Int = 0, // Color integer representation or token
    val updatedAt: Long = System.currentTimeMillis(),
    val isPinned: Boolean = false
)
