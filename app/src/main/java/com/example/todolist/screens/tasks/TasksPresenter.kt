package com.example.todolist.screens.tasks

import com.example.todolist.model.Task
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/* TODO: Injectable constructor but this functionality is not used. */
class TasksPresenter @Inject constructor(
    private val view: TasksContract.View,
    private val storage: TasksContract.TasksStorage
) : CoroutineScope, TasksContract.Presenter {

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    /*
        TODO: Start method should not be used for reloading data. It is used for the initial state only.
    */
    override fun start() {
        launch(Dispatchers.Main) {
            view.showData(withContext(Dispatchers.IO) {
                storage.getTasks(storage.getFilterMode()).toMutableList()
            })
        }
    }

    override fun filterMode(filter: Boolean) =
        if (filter) {
            storage.setFilterMode(TasksContract.TasksStorage.Filter.ALL)
        } else {
            storage.setFilterMode(TasksContract.TasksStorage.Filter.FINISHED)
        }

    override fun allButtonDidPress() {
        filterMode(true)
        start() // TODO:
    }

    override fun finishedButtonDidPress() {
        filterMode(false)
        start() // TODO:
    }

    override fun insert(title: String?, description: String) {
        launch {
            storage.insertTask(Task(title, description))
            start()
        }
    }

    override fun update(id: Int?, title: String?, description: String, favorite: Boolean) {
        launch {
            if (id == null) {
                /* TODO: WHY UPDATE???? */
                storage.updateTask(Task(title, description, favorite))
            } else {
                storage.updateTask(Task(id, title, description, favorite))
            }
            start() // TODO:
        }
    }

    override fun delete(task: Task) {
        launch {
            storage.deleteTask(task)
            start() // TODO:
        }
    }
}



