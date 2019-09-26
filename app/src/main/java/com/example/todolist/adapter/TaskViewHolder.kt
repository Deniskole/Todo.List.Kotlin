package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import java.lang.ref.WeakReference

class TaskViewHolder(
    parent: ViewGroup,
    private val listener: WeakReference<TasksAdapter.Listener>
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.task_list_item, parent, false)
) {
    init {
        if (listener != null) {
            itemView.setOnClickListener(createListener(listener))
        }
    }

    private fun createListener(l: WeakReference<TasksAdapter.Listener>) = View.OnClickListener {
        l.get()?.onViewHolderClick(this, adapterPosition, it.id)
    }

}