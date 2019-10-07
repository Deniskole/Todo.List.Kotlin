package com.example.todolist.adapter.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapter.OnViewHolderClickListener
import com.example.todolist.adapter.OnViewHolderLongClickListener
import java.lang.ref.WeakReference

abstract class ViewHolder<L>(
    parent: ViewGroup,
    private val listener: WeakReference<L>,
    layoutRes: Int
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
) {
    init {
        itemView.setOnClickListener(createClickListener())
        itemView.setOnLongClickListener { v ->
            return@setOnLongClickListener listener.get()?.let {
                if (it is OnViewHolderLongClickListener) {
                    it.onViewHolderLongClick(this, adapterPosition, v.id)
                } else false
            } ?: false
        } // TODO: Create listener in the same way as for the clicks.
    }

    protected fun createClickListener() = View.OnClickListener { v ->
        listener.get()?.let {
            if (it is OnViewHolderClickListener) {
                it.onViewHolderClick(this, adapterPosition, v.id)
            }
        }
    }
}

