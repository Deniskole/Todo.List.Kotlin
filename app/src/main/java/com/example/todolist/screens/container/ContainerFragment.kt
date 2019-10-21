package com.example.todolist.screens.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.R
import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.TasksFragment
import com.example.todolist.util.Constants.FILTER_ALL
import com.example.todolist.util.Constants.FILTER_FAVORITE
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_container.*


class ContainerFragment : Fragment(), ContainerContract.View,
    BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationView.setOnNavigationItemSelectedListener(this)

        if (childFragmentManager.findFragmentByTag(FILTER_ALL) == null) {
            childFragmentManager.beginTransaction()
                .add(
                    R.id.container,
                    TasksFragment.newInstance(TasksContract.Storage.Filter.ALL),
                    FILTER_ALL
                )
                .commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        showScreen(item.itemId)
        return true
    }

    override fun showScreen(id: Int) {
        val fragmentA = childFragmentManager.findFragmentByTag(FILTER_ALL)
        val fragmentB = childFragmentManager.findFragmentByTag(FILTER_FAVORITE)
        val transaction = childFragmentManager.beginTransaction()

        with(transaction) {
            when (id) {
                ContainerContract.NavigationItem.ALL -> {
                    fragmentA?.let(::show)
                    fragmentB?.let(::hide)
                }
                ContainerContract.NavigationItem.FAVORITE -> {
                    fragmentB?.let(::show) ?: add(
                        R.id.container,
                        TasksFragment.newInstance(TasksContract.Storage.Filter.FAVORITE),
                        FILTER_FAVORITE
                    )
                    fragmentA?.let(::hide)
                }
            }
            commit()
        }
    }
}
