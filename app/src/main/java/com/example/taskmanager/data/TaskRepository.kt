package com.example.taskmanager.data

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {
    val getAll: LiveData<List<Task>> = taskDao.getAll()

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

      suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}