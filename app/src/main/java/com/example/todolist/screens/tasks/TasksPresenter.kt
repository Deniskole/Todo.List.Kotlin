package com.example.todolist.screens.tasks

import com.example.todolist.extension.unit
import com.example.todolist.model.Task
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class TasksPresenter @Inject constructor(
    private val view: TasksContract.View,
    private val storage: TasksContract.Storage
) : CoroutineScope, TasksContract.Presenter {

    // TODO: Usually default coroutine context is Main.
    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    lateinit var filter: TasksContract.Storage.Filter

    // TODO: method `start()` should not have any parameters. Inject filter with DI.
    override fun start(filter: TasksContract.Storage.Filter) {
        select(filter)
        this.filter = filter
    }

    private fun select(filter: TasksContract.Storage.Filter /* TODO: Do you need this parameter???? */) {
        launch(Dispatchers.Main) {
            view.showData(withContext(Dispatchers.IO) {
                storage.getTasks(filter).toMutableList()
            })
        }
    }

    override fun buttonDidPress(filter: TasksContract.Storage.Filter) = select(filter)

    override fun insert(title: String?, description: String) =
        launch {
            storage.insertTask(Task(title, description))
            select(filter)
        }.unit()

    override fun update(id: Int, title: String?, description: String, favorite: Boolean) {
        launch {
            storage.updateTask(Task(id, title, description, favorite))
            select(filter)
        }
    }

    override fun favorite(id: Int, title: String?, description: String, favorite: Boolean) {
        /* TODO: WHY DELETE????? */
        if (filter == TasksContract.Storage.Filter.FAVORITE) {
            delete(Task(id, title, description, favorite))
        } else {
            update(id, title, description, !favorite)
        }
    }

    override fun delete(task: Task) {
        launch {
            storage.deleteTask(task)
            select(filter)
        }
    }
}



