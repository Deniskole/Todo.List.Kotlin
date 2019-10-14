package com.example.todolist.data

import androidx.room.*
import com.example.todolist.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * from task_table")
    fun getAllTasks(): List<Task>

    @Query("SELECT * from task_table WHERE favorite = 1")
    fun getDoneTasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Update
    fun updateTask(task:Task)

    @Delete
    fun deleteTask(task: Task)
}
