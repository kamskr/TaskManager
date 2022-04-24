package com.example.taskmanager.repositories

import androidx.lifecycle.LiveData
import com.example.taskmanager.data.TaskDao
import com.example.taskmanager.model.Task
import java.util.*

class TaskRepository(private val taskDao: TaskDao) {
    val getAll: LiveData<List<Task>> = taskDao.getAll()

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

    suspend fun addTestData() {

        val task1 = Task(0, "Do the groceries", 4, Date(2022, 5, 1),23, 1 )
        val task2 = Task(0, "Finish university project", 1, Date(2022, 5, 1),74, 43 )
        val task3 = Task(0, "Talk to the client", 2, Date(2022, 5, 2),0, 1 )
        val task4 = Task(0, "Finish setting up database", 3, Date(2022, 5, 22),65, 4 )
        val task5 = Task(0, "Plan the kitchen", 1, Date(2022, 7, 20),25, 20 )
        val task6 = Task(0, "Do the workshops with interns", 2, Date(2022, 6, 1),0, 2 )
        val task7 = Task(0, "Go to the gym", 5, Date(2022, 5, 1),89, 1 )
        val task8 = Task(0, "Return project", 1, Date(2022, 4, 26),99, 1 )

        val tasks: List<Task> = listOf(task1, task2, task3, task4, task5, task6, task7, task8)

        tasks.forEach{
            task -> taskDao.addTask(task)
        }

    }
}