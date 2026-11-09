package com.example.odlixnote.data.repository

import com.example.odlixnote.data.local.dao.TaskDao
import com.example.odlixnote.data.local.entity.SubTaskEntity
import com.example.odlixnote.data.local.entity.TaskEntity
import com.example.odlixnote.data.local.entity.TaskWithSubTasks
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<TaskEntity>> = taskDao.observeAllTasks()
    val allTasksWithSubTasks: Flow<List<TaskWithSubTasks>> = taskDao.observeTasksWithSubTasks()

    fun observeTaskById(id: Long): Flow<TaskEntity?> = taskDao.observeTaskById(id)

    fun observeTaskWithSubTasksById(id: Long): Flow<TaskWithSubTasks?> = taskDao.observeTaskWithSubTasksById(id)

    suspend fun getTaskById(id: Long): TaskEntity? = taskDao.getTaskById(id)

    suspend fun insertTask(task: TaskEntity): Long = taskDao.insertTask(task)

    suspend fun updateTask(task: TaskEntity) = taskDao.updateTask(task)

    suspend fun deleteTask(task: TaskEntity) = taskDao.deleteTask(task)

    suspend fun deleteTaskById(id: Long) = taskDao.deleteTaskById(id)

    fun observeSubTasksForTask(taskId: Long): Flow<List<SubTaskEntity>> = taskDao.observeSubTasksForTask(taskId)

    suspend fun insertSubTask(subTask: SubTaskEntity): Long = taskDao.insertSubTask(subTask)

    suspend fun updateSubTask(subTask: SubTaskEntity) = taskDao.updateSubTask(subTask)

    suspend fun deleteSubTask(subTask: SubTaskEntity) = taskDao.deleteSubTask(subTask)

    suspend fun deleteSubTaskById(id: Long) = taskDao.deleteSubTaskById(id)
}
