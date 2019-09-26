package com.example.todolist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_list_item.view.*
import java.lang.ref.WeakReference

class TasksAdapter(private val items: List<String>) : RecyclerView.Adapter<TaskViewHolder>() {

    lateinit var listener: Listener

    interface Listener {
        fun onViewHolderClick(holder: RecyclerView.ViewHolder, position: Int, id: Int)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        var weakReference: WeakReference<Listener> = WeakReference(listener)
        return TaskViewHolder(parent, weakReference)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.taskTextView.text = items[position]
    }
}


