package com.example.todolist.screens.tasks

import com.example.todolist.extension.unit
import com.example.todolist.model.Task
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class TasksPresenter @Inject constructor(
    private val view: TasksContract.View,
    private val storage: TasksContract.Storage,
    private val filter: TasksContract.Storage.Filter
) : CoroutineScope, TasksContract.Presenter {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun start() {
        select()
    }

    private fun select() {
//        DataBase
        launch {
            view.showData(withContext(Dispatchers.IO) {
                storage.getTasks(filter).toMutableList()
            })
        }

//        //FireBase
//        launch {
//            view.showData(storage.getTasks(filter))
//        }
    }

    override fun insert(title: String?, description: String) = launch {
        val id = UUID.randomUUID().toString()
        withContext(Dispatchers.IO) { storage.insertTask(Task(id, title, description)) }
        select()
    }.unit()

    override fun update(id: String, title: String?, description: String, favorite: Boolean) {
        launch {
            withContext(Dispatchers.IO) {
                storage.updateTask(Task(id, title, description, favorite))
            }
            select()
        }
    }

    override fun favorite(task: Task) {
        launch {
            withContext(Dispatchers.IO) {
                val taskTemp = Task(task.id, task.title, task.description, !task.favorite)
                storage.favoriteTask(taskTemp)
            }
            select()
        }
    }

    override fun remove(task: Task) {
        launch {
            withContext(Dispatchers.IO) {
                storage.deleteTask(task)
            }
            select()
        }
    }
}



