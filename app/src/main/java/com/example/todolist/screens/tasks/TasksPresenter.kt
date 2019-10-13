package com.example.todolist.screens.tasks

import com.example.todolist.model.Task
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class TasksPresenter(
    private val view: TasksContract.TasksView,
    private val storage: TasksContract.TasksStorage
) : CoroutineScope {
    private var list = mutableListOf<Task>()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    private fun start(filter: TasksContract.TasksStorage.Filter): List<Task> {
        launch(Dispatchers.Main) {
            list = withContext(Dispatchers.IO) { storage.getTasks(filter).toMutableList() }
            view.showData(list)
        }
        return list
    }

    fun all() = view.showData(start(TasksContract.TasksStorage.Filter.ALL))
    fun done() = view.showData(start(TasksContract.TasksStorage.Filter.FINISHED))
    fun insert(task: Task) = launch { storage.insertTask(task) }
    fun delete(task: Task) = launch { storage.deleteTask(task) }
}



