package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import java.lang.ref.WeakReference

class TaskViewHolder(
    parent: ViewGroup,
    listener: WeakReference<OnViewHolderClickListener>
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.task_list_item, parent, false)
) {
    init {
        itemView.setOnClickListener {
            listener.get()?.onViewHolderClick(this, adapterPosition, it.id)
        }
    }
}