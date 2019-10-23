package com.example.todolist.screens.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todolist.R
import com.example.todolist.extension.unit
import com.example.todolist.screens.tasks.TasksContract
import com.example.todolist.screens.tasks.TasksFragment
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

        showScreen(ContainerContract.NavigationItem.ALL, true)
    }

    override fun onNavigationItemSelected(item: MenuItem) = showScreen(item.itemId).let { true }

    private fun showScreen(id: Int, justCreate: Boolean = false) =
        with(childFragmentManager.beginTransaction()) {
            val previousTag = TasksFragment.tag(navigationView.selectedItemId)
            val previous = childFragmentManager.findFragmentByTag(previousTag)

            val nextTag = TasksFragment.tag(id)
            val next = childFragmentManager.findFragmentByTag(nextTag)
            val nextFilter = when (id) {
                ContainerContract.NavigationItem.ALL -> TasksContract.Storage.Filter.ALL
                ContainerContract.NavigationItem.FAVORITE -> TasksContract.Storage.Filter.FAVORITE
                else -> throw IllegalArgumentException()
            }

            if (previous != null && !justCreate) hide(previous)

            if (next == null) {
                add(R.id.container, TasksFragment.newInstance(nextFilter), nextTag)
            } else if (!justCreate) {
                show(next)
            }

            commit()
        }.unit()
}
