# Implementation Plan - OdlixNote Room Database and Repositories

## Proposed Changes

### Data Layer (`com.example.odlixnote.data`)

#### [NEW] [NoteEntity.kt](file:///C:/Users/Anand raj/AndroidStudioProjects/odlixnote/app/src/main/java/com/example/odlixnote/data/local/entity/NoteEntity.kt)
- Room entity representing a Note (`id`, `title`, `content`, `backgroundColor`, `updatedAt`, `isPinned`).

#### [NEW] [TaskEntity.kt](file:///C:/Users/Anand raj/AndroidStudioProjects/odlixnote/app/src/main/java/com/example/odlixnote/data/local/entity/TaskEntity.kt)
- Room entity representing a Task (`id`, `title`, `isCompleted`, `dueDate`, `reminderTime`, `priority`, `updatedAt`).

#### [NEW] [SubTaskEntity.kt](file:///C:/Users/Anand raj/AndroidStudioProjects/odlixnote/app/src/main/java/com/example/odlixnote/data/local/entity/SubTaskEntity.kt)
- Room entity representing a SubTask (`id`, `taskId`, `title`, `isCompleted`).

#### [NEW] [TaskWithSubTasks.kt](file:///C:/Users/Anand raj/AndroidStudioProjects/odlixnote/app/src/main/java/com/example/odlixnote/data/local/entity/TaskWithSubTasks.kt)
- Room relation data class combining `TaskEntity` and its list of `SubTaskEntity`.

#### [NEW] [NoteDao.kt](file:///C:/Users/Anand raj/AndroidStudioProjects/odlixnote/app/src/main/java/com/example/odlixnote/data/local/dao/NoteDao.kt)
- DAO for notes (`observeAllNotes`, `getNoteById`, `insertNote`, `updateNote`, `deleteNote`, `deleteNoteById`, `searchNotes`).

#### [NEW] [TaskDao.kt](file:///C:/Users/Anand raj/AndroidStudioProjects/odlixnote/app/src/main/java/com/example/odlixnote/data/local/dao/TaskDao.kt)
- DAO for tasks and subtasks (`observeAllTasks`, `observeTasksWithSubTasks`, `getTaskById`, `insertTask`, `updateTask`, `deleteTask`, `insertSubTask`, `updateSubTask`, `deleteSubTask`, `deleteSubTaskById`, etc.).

#### [NEW] [AppDatabase.kt](file:///C:/Users/Anand raj/AndroidStudioProjects/odlixnote/app/src/main/java/com/example/odlixnote/data/local/database/AppDatabase.kt)
- Room database instance abstract class.

#### [NEW] [NoteRepository.kt](file:///C:/Users/Anand raj/AndroidStudioProjects/odlixnote/app/src/main/java/com/example/odlixnote/data/repository/NoteRepository.kt)
- Repository wrapping `NoteDao`.

#### [NEW] [TaskRepository.kt](file:///C:/Users/Anand raj/AndroidStudioProjects/odlixnote/app/src/main/java/com/example/odlixnote/data/repository/TaskRepository.kt)
- Repository wrapping `TaskDao`.

## Verification Plan

### Automated Tests
- Gradle build compilation check (`./gradlew :app:assembleDebug`).
