package com.example.todolist.screens.tasks

import com.example.todolist.extension.unit
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract.Storage.Filter.ALL
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class TasksPresenter @Inject constructor(
    private val view: TasksContract.View,
    private val storage: TasksContract.Storage
) : CoroutineScope, TasksContract.Presenter {

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    override fun start() = select(ALL)

    private fun select(filter: TasksContract.Storage.Filter) {
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
            select(ALL)
        }.unit()

    override fun update(id: Int, title: String?, description: String, favorite: Boolean) {
        launch {
            storage.updateTask(Task(id, title, description, favorite))
            select(ALL)
        }
    }

    override fun favorite(id: Int, title: String?, description: String, favorite: Boolean) {
        update(id, title, description, !favorite)
    }

    override fun delete(task: Task) {
        launch {
            storage.deleteTask(task)
            select(ALL)
        }
    }
}



