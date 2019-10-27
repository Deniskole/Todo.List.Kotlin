package com.example.todolist.screens.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R
import com.example.todolist.model.Task
import com.example.todolist.model.TaskFireBase
import com.example.todolist.screens.container.ContainerFragment
import com.example.todolist.screens.tasks.TasksFireBaseStorage
import toothpick.Toothpick

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appScope = Toothpick.openScope(this)
        Toothpick.inject(this, appScope)

        if (supportFragmentManager.findFragmentById(R.id.container) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ContainerFragment(), ContainerFragment::class.java.name)
                .commit()
        }





    }


    fun testClick(view: View){
        val fireBase = TasksFireBaseStorage()
        fireBase.insertTest()
    }

}




