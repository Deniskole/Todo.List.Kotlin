package com.example.todolist.screens.tasks

import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.TasksContract.TasksStorage.Filter.ALL
import com.example.todolist.screens.tasks.TasksContract.TasksStorage.Filter.FINISHED
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class TasksPresenter(
    private val view: TasksContract.TasksView,
    private val storage: TasksContract.TasksStorage
) : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    private fun start(filter: TasksContract.TasksStorage.Filter) {
        launch(Dispatchers.Main) {
            view.showData(withContext(Dispatchers.IO) { storage.getTasks(filter).toMutableList() })
        }
    }

    fun all() = start(ALL)
    fun done() = start(FINISHED)
    fun insert(task: Task) = launch { storage.insertTask(task) }
    fun delete(task: Task) = launch { storage.deleteTask(task) }
}



