package com.example.todolist.screens.tasks.storage

import com.example.todolist.extension.await
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.TasksContract.Storage.Filter
import com.google.firebase.database.DatabaseReference
import toothpick.InjectConstructor


@InjectConstructor
class TasksFireBaseStorage(private val ref: DatabaseReference) : TasksContract.Storage {
    companion object {
        private const val PATH_FAVORITE = "favorite"
    }

    override suspend fun getTasks(filter: Filter) = mutableListOf<Task>().apply {
        val query = when (filter) {
            Filter.ALL -> ref
            Filter.FAVORITE -> ref.orderByChild(PATH_FAVORITE).equalTo(true)
        }
        val result = query.await()
        if (result.exists()) {
            for (snapshot in result.children) {
                val task = snapshot.getValue(Task::class.java)
                if (task != null) {
                    task.id = snapshot.key ?: ""

                    add(task)
                }
            }
        }
    }

    override suspend fun insertTask(title: String?, description: String): Task? = with(ref) {
        push().key?.let { Task(it, title, description) }?.also { ref.child(it.id).setValue(it) }
    }

    override suspend fun updateTask(task: Task) {
        ref.child(task.id).setValue(task).await()
    }

    override suspend fun deleteTask(task: Task) {
        ref.child(task.id).removeValue().await()
    }

    override suspend fun favoriteTask(task: Task) {
        ref.child(task.id).child(PATH_FAVORITE).setValue(task.favorite).await()
    }
}