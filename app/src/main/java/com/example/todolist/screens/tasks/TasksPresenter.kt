package com.example.todolist.screens.tasks

import com.example.todolist.model.Task
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class TasksPresenter(
    private val view: TasksContract.View,
    private val storage: TasksContract.TasksStorage
) : CoroutineScope, TasksContract.Presenter {
    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    override fun start() {
        storage.setNightMode(storage.getNightMode())
        launch(Dispatchers.Main) {
            view.showData(withContext(Dispatchers.IO) {
                storage.getTasks(storage.getFilterMode()).toMutableList()
            })
        }
    }

    override fun filterMode(filter: Boolean) =
        if (filter)
            storage.setFilterMode(TasksContract.TasksStorage.Filter.ALL)
        else
            storage.setFilterMode(TasksContract.TasksStorage.Filter.FINISHED)

    override fun setNightMode(mode: Boolean) = storage.setNightMode(mode)
    override fun getNightMode(): Boolean = storage.getNightMode()

    override fun allButtonDidPress() {
        filterMode(true)
        start()
    }

    override fun finishedButtonDidPress() {
        filterMode(false)
        start()
    }

    override fun insert(task: Task) {
        launch {
            storage.insertTask(task)
            start()
        }
    }

    override fun delete(task: Task) {
        launch {
            storage.deleteTask(task)
            start()
        }
    }
}



