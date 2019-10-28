package com.example.todolist.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

sealed class Task(val title: String?, val description: String, val favorite: Boolean) {

    class Firebase(val id: String, title: String?, description: String, favorite: Boolean) :
        com.example.todolist.model.Task(title, description, favorite)

    @Entity(tableName = "task_table")
    class Database constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Int, title: String?, description: String, favorite: Boolean
    ) : com.example.todolist.model.Task(title, description, favorite) {
        @Ignore
        constructor(
            title: String? = null,
            descriptions: String,
            favorite: Boolean = false
        ) : this(0, title, descriptions, favorite)
    }
}
















