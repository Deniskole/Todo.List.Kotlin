package com.example.todolist.screens.tasks

import com.example.todolist.R
import com.example.todolist.model.Task

interface TasksContract {
    interface View {
        fun showData(tasksList: List<Task>)
    }

    interface Presenter {
        fun start(filter: Storage.Filter)
        // TODO: Unused method
        fun buttonDidPress(filter: Storage.Filter)
        // TODO: Naming. Should bot be the same as in the storage.
        //  It is different operation and should have business logic naming.
        fun insert(title: String?, description: String)
        fun update(id: Int, title: String?, description: String, favorite: Boolean)
        // TODO: Why do we need pass all properties to change only on property of the item in DB?
        fun favorite(id: Int, title: String?, description: String, favorite: Boolean)
        // TODO: Naming. The same. `Remove` will be better instead of delete in this case (but delete is also OK).
        fun delete(task: Task)
    }

    interface Storage {
        suspend fun getTasks(filter: Filter): List<Task>
        suspend fun insertTask(task: Task)
        suspend fun updateTask(task: Task)
        suspend fun deleteTask(task: Task)

        enum class Filter { ALL, FAVORITE }
    }

    enum class Action(val titleResId: Int) {
        NEW(R.string.new_task),
        EDIT(R.string.edit),
        DELETE(R.string.delete)
    }
}