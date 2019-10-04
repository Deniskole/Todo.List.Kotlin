package com.example.android.roomwordssample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.todolist.data.AppDatabase
import com.example.todolist.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: TaskRepository
    val allWords: LiveData<List<Task>>

    init {
        val tasksDao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(tasksDao)
        allWords = repository.allTasks
    }

    fun insert(task: Task) = scope.launch(Dispatchers.IO) {
        repository.insert(task)
    }

    fun delete(task: Task) = scope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
