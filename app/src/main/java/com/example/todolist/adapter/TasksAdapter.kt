package com.example.todolist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_list_item.view.*

class TasksAdapter(private val items: List<String>) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun getItemCount() = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TaskViewHolder(parent)

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.taskTextView.text = items[position]
    }
}