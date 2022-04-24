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

    @Delete
    fun deleteTask(task: Task)

//    @Query("SELECT * FROM task WHERE uid IN (:userIds)")
//    fun loadAllByIds(taskIds: IntArray): List<Task>
//
//    @Query("SELECT * FROM task WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): User
}