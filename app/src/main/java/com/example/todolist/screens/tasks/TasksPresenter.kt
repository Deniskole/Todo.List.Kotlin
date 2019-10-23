package com.example.todolist.screens.tasks

import com.example.todolist.extension.unit
import com.example.todolist.model.Task
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class TasksPresenter @Inject constructor(
    private val view: TasksContract.View,
    private val storage: TasksContract.Storage,
    private val filter: TasksContract.Storage.Filter
) : CoroutineScope, TasksContract.Presenter {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun start() {
        select()
    }

    private fun select() {
        launch {
            view.showData(withContext(Dispatchers.IO) {
                storage.getTasks(filter).toMutableList()
            })
        }
    }

    override fun insert(title: String?, description: String) = launch {
        withContext(Dispatchers.IO) { storage.insertTask(Task(title, description)) }
        select()

    }.unit()

    override fun update(id: Int, title: String?, description: String, favorite: Boolean) {
        launch {
            withContext(Dispatchers.IO) {
                storage.updateTask(Task(id, title, description, favorite))
            }
            select()
        }
    }

    override fun favorite(id: Int, favorite: Boolean) {
        launch {
            withContext(Dispatchers.IO) {
                storage.favoriteTask(id, !favorite)
            }
            select()
        }
    }

    override fun remove(task: Task) {
        launch {
            withContext(Dispatchers.IO) {
                storage.deleteTask(task)
            }
            select()
        }
    }
}



