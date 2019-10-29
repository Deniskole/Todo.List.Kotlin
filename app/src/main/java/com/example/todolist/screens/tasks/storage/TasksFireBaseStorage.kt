package com.example.todolist.screens.tasks.storage

import android.util.Log
import com.example.todolist.extension.await
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class TasksFireBaseStorage @Inject constructor(private val refference: DatabaseReference) :
    TasksContract.Storage {

    private companion object {
        val taskList = mutableListOf<Task>()
    }

    override suspend fun getTasks(filter: TasksContract.Storage.Filter): List<Task> {

        taskList.clear()
        GlobalScope.launch {
            val result = refference.await()
            if (result.exists()) {
                for (dataSnapsShot in result.children) {
                    val task = dataSnapsShot.getValue(Task::class.java)
                    if (task != null) {
                        task.id = dataSnapsShot.key ?: ""
                        taskList.add(task)
                    }
//                    if (task != null) {
//                        Log.d(
//                            "TAG",
//                            "ID:${task.id} TITILE:${task.title}  DESCRIPTION:${task.descriptions} FAVORITE:${task.favorite}"
//                        )
//                    }
                }
            }
        }
        Log.d("TAG", taskList.size.toString() + " ----")
        return when (filter) {
            TasksContract.Storage.Filter.ALL -> {
                taskList
            }
            TasksContract.Storage.Filter.FAVORITE -> {
                taskList.filter { it.favorite }
            }
        }
    }

    override suspend fun insertTask(task: Task) {
        val id = refference.push().key.toString()
        val task3 = Task(id, task.title, task.descriptions, task.favorite)

        GlobalScope.launch {
            refference.child(id).setValue(task3).await()
        }
    }

    override suspend fun updateTask(task: Task) {
        GlobalScope.launch {
            refference.child(task.id).setValue(task).await()
        }
    }

    override suspend fun deleteTask(task: Task) {
        GlobalScope.launch {
            refference.child(task.id).removeValue().await()
        }
    }

    override suspend fun favoriteTask(task: Task) {
        GlobalScope.launch {
            refference.child(task.id).child("favorite").setValue(true).await()
        }
    }
}