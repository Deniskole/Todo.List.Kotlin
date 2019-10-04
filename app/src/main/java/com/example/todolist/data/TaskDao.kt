package com.example.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * from task_table")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Query("DELETE FROM task_table")
    fun deleteAll()
}
