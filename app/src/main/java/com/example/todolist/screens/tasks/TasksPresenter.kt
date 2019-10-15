package com.example.todolist.screens.tasks

import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract.Storage.Filter.ALL
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class TasksPresenter(
    private val view: TasksContract.View,
    private val storage: TasksContract.Storage
) : CoroutineScope, TasksContract.Presenter {

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    override fun show(filter: TasksContract.Storage.Filter) {
        launch(Dispatchers.Main) {
            view.showData(withContext(Dispatchers.IO) {
                storage.getTasks(filter).toMutableList()
            })
        }
    }

    override fun buttonDidPress(filter: TasksContract.Storage.Filter) {
        show(filter)
    }

    override fun insert(title: String?, description: String) {
        launch {
            storage.insertTask(Task(title, description))
            show(ALL)
        }
    }

    override fun update(id: Int, title: String?, description: String, favorite: Boolean) {
        launch {
            storage.updateTask(Task(id, title, description, favorite))
            show(ALL)
        }
    }

    override fun favorite(id: Int, title: String?, description: String, favorite: Boolean) {
        val favoriteTask = !favorite
        update(id, title, description, favoriteTask)
    }

    override fun delete(task: Task) {
        launch {
            storage.deleteTask(task)
            show(ALL)
        }
    }
}



