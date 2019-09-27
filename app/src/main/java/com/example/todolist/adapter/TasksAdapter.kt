package com.example.todolist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_list_item.view.*
import java.lang.ref.WeakReference

class TasksAdapter(
    private val items: List<String>,
    listener: OnViewHolderClickListener
) : RecyclerView.Adapter<TaskViewHolder>() {

    private val weakListener = WeakReference(listener)

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TaskViewHolder(parent, weakListener)

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.taskTextView.text = items[position]
    }
}


