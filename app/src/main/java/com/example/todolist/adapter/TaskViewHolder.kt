package com.example.todolist.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_list_item_d.view.*
import java.lang.ref.WeakReference

class TaskViewHolder(
    listener: WeakReference<OnViewHolderClickListener>, view: View
) : RecyclerView.ViewHolder(view) {
    init {
        itemView.setOnClickListener {
            listener.get()?.onViewHolderClick(this, adapterPosition, it.id)
        }

        itemView.taskFavoriteImageView.setOnClickListener {
            listener.get()?.onViewHolderFavoriteClick(this, adapterPosition, it.id)
        }

        itemView.setOnLongClickListener {
            listener.get()?.onViewHolderLongClick(this, adapterPosition, it.id)
            true
        }
    }
}
