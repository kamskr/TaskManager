package com.example.taskmanager.model

import android.os.Parcelable
import androidx.annotation.Size
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "task")
data class Task (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    // The lower the number the most urgent it is
    @ColumnInfo(name = "priority") val priority: Int,
    @ColumnInfo(name = "deadline") val deadline: Date,
    @Size(min=0, max=100)
    @ColumnInfo(name = "percentage_done") val percentageDone: Int,
    @Size(min=0)
    @ColumnInfo(name = "estimated_time_in_hours") val estimatedTimeInHours: Int,
):Parcelable


