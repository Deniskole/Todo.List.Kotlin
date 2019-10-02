package com.example.todolist.adapter.holder

import android.view.ViewGroup
import com.example.todolist.R
import com.example.todolist.adapter.OnViewHolderClickListener
import com.example.todolist.adapter.OnViewHolderLongClickListener
import kotlinx.android.synthetic.main.task_list_item_t_d.view.*
import java.lang.ref.WeakReference

class TaskDescriptionViewHolder<L>(
    parent: ViewGroup,
    listener: WeakReference<L>
) : ViewHolder<L>(parent, listener, R.layout.task_list_item_d)
        where L : OnViewHolderClickListener,
              L : OnViewHolderLongClickListener {
    init {
        itemView.taskFavoriteImageView.setOnClickListener(createClickListener())
    }
}
