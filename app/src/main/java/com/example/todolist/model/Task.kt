package com.example.todolist.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
class Task constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String? = null,
    val descriptions: String,
    var favorite: Boolean = false
) {
    @Ignore
    constructor(
        title: String? = null,
        descriptions: String,
        favorite: Boolean = false
    ) : this(0, title, descriptions, favorite)
}