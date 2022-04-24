package com.example.taskmanager.repositories

import androidx.lifecycle.LiveData
import com.example.taskmanager.data.TaskDao
import com.example.taskmanager.model.Task

class TaskRepository(private val taskDao: TaskDao) {
    val getAll: LiveData<List<Task>> = taskDao.getAll()

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

      suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}