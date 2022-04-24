package com.example.taskmanager.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.data.AppDatabase
import com.example.taskmanager.model.Task
import com.example.taskmanager.repositories.TaskRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class TaskViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Task>>
    private val taskRepository: TaskRepository

    init {
        val userDao = AppDatabase.getInstance(application).taskDao()
        taskRepository = TaskRepository(userDao)
        readAllData = taskRepository.getAll
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.addTask(task)
        }
    }
}