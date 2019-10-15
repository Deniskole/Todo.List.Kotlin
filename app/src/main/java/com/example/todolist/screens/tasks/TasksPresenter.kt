package com.example.todolist.screens.tasks

import com.example.todolist.model.Task
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.example.todolist.screens.tasks.TasksContract.TasksStorage.Filter.*


class TasksPresenter(
    private val view: TasksContract.View,
    private val storage: TasksContract.TasksStorage
) : CoroutineScope, TasksContract.Presenter {

    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()

    override fun show(filter: TasksContract.TasksStorage.Filter) {
        launch(Dispatchers.Main) {
            view.showData(withContext(Dispatchers.IO) {
                storage.getTasks(filter).toMutableList()
            })
        }
    }

    override fun buttonDidPress(filter: TasksContract.TasksStorage.Filter) {
        show(filter)
    }

    override fun insert(title: String?, description: String) {
        launch {
            storage.insertTask(Task(title, description))
            show(ALL)
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
            show(ALL)
        }
    }

    override fun delete(task: Task) {
        launch {
            storage.deleteTask(task)
            show(ALL)
        }
    }
}



