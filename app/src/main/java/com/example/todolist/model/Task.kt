package com.example.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude

@Entity(tableName = "task_table")
data class Task constructor(
    @PrimaryKey
    @get:Exclude
    var id: String = "",
    val title: String? = null,
    val descriptions: String,
    var favorite: Boolean = false
)