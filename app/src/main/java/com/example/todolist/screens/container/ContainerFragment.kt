package com.example.todolist.screens.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.R
import com.example.todolist.screens.tasks.TasksFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class ContainerFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var presenter: ContainerContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //                .add(R.id.container, TasksFragment(), TasksFragment::class.java.name)


//        activity?.supportFragmentManager?.beginTransaction()
//                ?.add(R.id.container, TasksFragment(), TasksFragment::class.java.name)
//            ?.commit()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }


    // region BottomNavigationView

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        presenter.select(item.itemId)
        return true
    }

    // endregion

}
