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

    override fun start() = updateView()

    private fun updateView() = launch {
        view.showData(storage.getTasks(filter))
    }.unit()

    override fun insert(title: String?, description: String) = launch {
        storage.insertTask(title, description)
        updateView()
    }.unit()

    override fun update(id: String, title: String?, description: String, favorite: Boolean) =
        launch {
            storage.updateTask(Task(id, title, description, favorite))
            updateView()
        }.unit()

    override fun favorite(task: Task) = launch {
        withContext(Dispatchers.IO) {
            val taskTemp = Task(task.id, task.title, task.description, !task.favorite)
            storage.favoriteTask(taskTemp)
        }
        updateView()
    }.unit()

    override fun remove(task: Task) = launch {
        withContext(Dispatchers.IO) {
            storage.deleteTask(task)
        }
        updateView()
    }.unit()
}



