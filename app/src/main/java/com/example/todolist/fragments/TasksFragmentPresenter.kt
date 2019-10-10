package com.example.todolist.fragments

import androidx.lifecycle.Observer
import com.example.todolist.data.AppDatabase
import com.example.todolist.model.Task
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

private lateinit var db: AppDatabase
private var parentJob = Job()
private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Main
private val scope = CoroutineScope(coroutineContext)
private lateinit var tasksList: List<Task>
private lateinit var tasksListDone: List<Task>

class TasksFragmentPresenter(private val fragment: TasksFragment) {

    init {
        db = AppDatabase.getDatabase(fragment.requireContext())
    }

    fun all() {
        db.taskDao().getAllTasks().observe(fragment, Observer { tasks ->
            tasks?.let { it ->
                tasksList = it
                tasksList.let {
                    tasksList = it
                    fragment.showData(tasksList)
                }
            }
        })
    }

    fun insert(task: Task) = scope.launch(Dispatchers.IO) { db.taskDao().insertTask(task) }
    fun delete(task: Task) = scope.launch(Dispatchers.IO) { db.taskDao().deleteTask(task) }

    fun done() {
        scope.launch(Dispatchers.Main) {
            tasksListDone = withContext(Dispatchers.IO) { db.taskDao().getDoneTasks() }
            fragment.showData(tasksListDone)
        }
    }
}