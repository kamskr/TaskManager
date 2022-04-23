package com.example.taskmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.example.taskmanager.data.AppDatabase
import com.example.taskmanager.data.Task

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        val taskDao = db.taskDao()
        val tasks: List<Task> = taskDao.getAll()

        Log.v("test", tasks.toString());
    }
}