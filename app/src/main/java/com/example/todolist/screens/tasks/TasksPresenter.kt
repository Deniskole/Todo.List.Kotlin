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

    /*
    * TODO: Why start method was removed?
    *  - start method is used to determine when the screen is started.
    *  - this method should be used to configure the screen for the first usage.
    *  - show method should be private and not been called from the `View` presenter can't "show" anything, so the naming is incorrect aswell.
    * */

    override fun show(filter: TasksContract.Storage.Filter) {
        launch(Dispatchers.Main) {
            view.showData(withContext(Dispatchers.IO) {
                storage.getTasks(filter).toMutableList()
            })
        }
    }

//    TODO: Can be simplified.
//    override fun buttonDidPress(filter: TasksContract.Storage.Filter) {
//        show(filter)
//    }

    override fun buttonDidPress(filter: TasksContract.Storage.Filter) = show(filter)

    /*
        TODO: This is an example of how such methods can be simplified (It is no an issue or warning).
        I've created extension with method `unit()` that can be used when you want to omit function result.

        override fun insert(title: String?, description: String) {
            launch {
                storage.insertTask(Task(title, description))
                show(ALL)
            }
        }
    */
    override fun insert(title: String?, description: String) = launch {
        storage.insertTask(Task(title, description))
        show(ALL)
    }.unit()

    override fun update(id: Int, title: String?, description: String, favorite: Boolean) {
        launch {
            storage.updateTask(Task(id, title, description, favorite))
            show(ALL)
        }
    }

    override fun favorite(id: Int, title: String?, description: String, favorite: Boolean) {
        /* TODO: Why do we need local variable?*/
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



