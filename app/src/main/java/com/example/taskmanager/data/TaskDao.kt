package com.example.taskmanager.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.taskmanager.model.Task


@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY deadline")
    fun getAll(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE FROM task")
    fun deleteAllTasks()
}