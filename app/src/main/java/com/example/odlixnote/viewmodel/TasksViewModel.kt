package com.example.odlixnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.odlixnote.OdlixnoteApplication
import com.example.odlixnote.data.local.entity.SubTaskEntity
import com.example.odlixnote.data.local.entity.TaskEntity
import com.example.odlixnote.data.local.entity.TaskWithSubTasks
import com.example.odlixnote.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TasksViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    val tasksWithSubTasks: StateFlow<List<TaskWithSubTasks>> = taskRepository.allTasksWithSubTasks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val uncompletedTasks: StateFlow<List<TaskWithSubTasks>> = tasksWithSubTasks
        .map { list -> list.filter { !it.task.isCompleted } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val completedTasks: StateFlow<List<TaskWithSubTasks>> = tasksWithSubTasks
        .map { list -> list.filter { it.task.isCompleted } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTask(
        title: String,
        dueDate: Long? = null,
        reminderTime: Long? = null,
        priority: Int = 0,
        subTaskTitles: List<String> = emptyList()
    ) {
        viewModelScope.launch {
            val task = TaskEntity(
                title = title,
                dueDate = dueDate,
                reminderTime = reminderTime,
                priority = priority,
                updatedAt = System.currentTimeMillis()
            )
            val taskId = taskRepository.insertTask(task)
            for (subTitle in subTaskTitles) {
                if (subTitle.isNotBlank()) {
                    val subTask = SubTaskEntity(
                        taskId = taskId,
                        title = subTitle.trim(),
                        isCompleted = false
                    )
                    taskRepository.insertSubTask(subTask)
                }
            }
        }
    }

    fun toggleTaskCompletion(taskId: Long) {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(taskId)
            if (task != null) {
                val updated = task.copy(
                    isCompleted = !task.isCompleted,
                    updatedAt = System.currentTimeMillis()
                )
                taskRepository.updateTask(updated)
            }
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            val updated = task.copy(updatedAt = System.currentTimeMillis())
            taskRepository.updateTask(updated)
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun deleteTaskById(taskId: Long) {
        viewModelScope.launch {
            taskRepository.deleteTaskById(taskId)
        }
    }

    fun addSubTask(taskId: Long, title: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            val subTask = SubTaskEntity(
                taskId = taskId,
                title = title.trim(),
                isCompleted = false
            )
            taskRepository.insertSubTask(subTask)
        }
    }

    fun toggleSubTask(subTask: SubTaskEntity) {
        viewModelScope.launch {
            val updated = subTask.copy(isCompleted = !subTask.isCompleted)
            taskRepository.updateSubTask(updated)
        }
    }

    fun deleteSubTask(subTask: SubTaskEntity) {
        viewModelScope.launch {
            taskRepository.deleteSubTask(subTask)
        }
    }

    fun deleteSubTaskById(subTaskId: Long) {
        viewModelScope.launch {
            taskRepository.deleteSubTaskById(subTaskId)
        }
    }

    fun updateDueDate(taskId: Long, dueDate: Long?) {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(taskId)
            if (task != null) {
                val updated = task.copy(dueDate = dueDate, updatedAt = System.currentTimeMillis())
                taskRepository.updateTask(updated)
            }
        }
    }

    fun updateReminderSettings(taskId: Long, reminderTime: Long?) {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(taskId)
            if (task != null) {
                val updated = task.copy(reminderTime = reminderTime, updatedAt = System.currentTimeMillis())
                taskRepository.updateTask(updated)
            }
        }
    }

    fun updatePriority(taskId: Long, priority: Int) {
        viewModelScope.launch {
            val task = taskRepository.getTaskById(taskId)
            if (task != null) {
                val updated = task.copy(priority = priority, updatedAt = System.currentTimeMillis())
                taskRepository.updateTask(updated)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as OdlixnoteApplication)
                TasksViewModel(application.taskRepository)
            }
        }
    }
}
