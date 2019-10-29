package com.example.todolist.screens.tasks

import com.example.todolist.extension.unit
import com.example.todolist.model.Task
import com.example.todolist.screens.tasks.storage.TasksFireBaseStorage
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class TasksPresenter @Inject constructor(
    private val view: TasksContract.View,
    private val storage: TasksContract.Storage,
    private val filter: TasksContract.Storage.Filter,
    private val fireBaseStorage: TasksFireBaseStorage,
    private val reff: DatabaseReference
) : CoroutineScope, TasksContract.Presenter {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun start() {
        select()
//        fireBaseStorage.clickTest()
//
//        GlobalScope.launch {
//            val items = fireBaseStorage.getTasks(TasksContract.Storage.Filter.ALL)
//            for (item in items) {
//                Log.d("TAG", "${item.id} ${item.title} ${item.descriptions} ${item.favorite}")
//            }
//        }
    }

    private fun select() {

        launch {
            view.showData(withContext(Dispatchers.IO) {
                storage.getTasks(filter).toMutableList()
            })

            fireBaseStorage.getTasks(filter)
        }
    }

    override fun insert(title: String?, description: String) = launch {
        val id = reff.push().key.toString()
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
                task.favorite = !task.favorite
                storage.favoriteTask(task)
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



