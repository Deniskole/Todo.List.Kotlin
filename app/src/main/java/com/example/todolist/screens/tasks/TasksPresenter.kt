package com.example.todolist.screens.tasks

import com.example.todolist.extension.unit
import com.example.todolist.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class TasksPresenter @Inject constructor(
    private val view: TasksContract.View,
    private val storage: TasksContract.Storage,
    private val filter: TasksContract.Storage.Filter
) : CoroutineScope, TasksContract.Presenter {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun start() = updateView()

    override fun insert(title: String?, description: String) = updateView {
        storage.insertTask(title, description)
    }

    override fun update(
        id: String, title: String?, description: String, favorite: Boolean
    ) = updateView {
        storage.updateTask(Task(id, title, description, favorite))
    }

    override fun toggleFavorite(task: Task) = updateView {
        storage.favoriteTask(task, !task.favorite)
    }

    override fun remove(task: Task) = updateView { storage.deleteTask(task) }

    private fun updateView(block: suspend CoroutineScope.() -> Unit = {}) =
        launch {
            block()
            view.showData(storage.getTasks(filter))
        }.unit()
}



