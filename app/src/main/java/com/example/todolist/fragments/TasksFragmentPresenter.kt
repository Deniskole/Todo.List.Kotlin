package com.example.todolist.fragments

import androidx.lifecycle.Observer
import com.example.todolist.data.AppDatabase
import com.example.todolist.data.TaskRepository
import com.example.todolist.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private lateinit var db: AppDatabase
private lateinit var repository: TaskRepository
private var parentJob = Job()
private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Main
private val scope = CoroutineScope(coroutineContext)
private lateinit var tasksList: List<Task>

class TasksFragmentPresenter(private val fragment: TasksFragment) {

    fun all() {
        db = AppDatabase.getDatabase(fragment.requireContext())
        repository = TaskRepository(db.taskDao())
        repository.allTasks.observe(fragment, Observer { tasks ->
            tasks?.let { it ->
                tasksList = it
                tasksList.let {
                    tasksList = it
                    fragment.showData(tasksList)
                }
            }
        })

    }

    fun insert(task: Task) = scope.launch(Dispatchers.IO) { repository.insert(task) }
    fun delete(task: Task) = scope.launch(Dispatchers.IO) { repository.delete(task) }

    fun done() {
        var tasksListDone: List<Task> = tasksList
        tasksListDone = tasksListDone.filter { it.favorite }
        fragment.showData(tasksListDone)
    }
}