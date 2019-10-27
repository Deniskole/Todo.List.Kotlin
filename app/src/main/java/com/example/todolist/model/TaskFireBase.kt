package com.example.todolist.model

import com.google.firebase.database.Exclude

data class TaskFireBase(
    @get:Exclude
    var id: String = "",
    val title: String? = "",
    val description: String = "",
    val favorite: Boolean = false
)