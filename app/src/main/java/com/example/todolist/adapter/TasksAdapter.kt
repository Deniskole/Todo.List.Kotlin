package com.example.todolist.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapter.TaskItemType.DESCRIPTION_TASK
import com.example.todolist.adapter.TaskItemType.TITLE_DESCRIPTION_TASK
import com.example.todolist.adapter.holder.SingleLineViewHolder
import com.example.todolist.adapter.holder.TwoLineViewHolder
import com.example.todolist.model.Task
import kotlinx.android.synthetic.main.task_list_item_t_d.view.*
import java.lang.ref.WeakReference

class TasksAdapter<L>(
    private var items: List<Task>,
    listener: L
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() where L : OnViewHolderClickListener,
                                                          L : OnViewHolderLongClickListener {
    private val weakListener = WeakReference(listener)
    override fun getItemCount() = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TITLE_DESCRIPTION_TASK.ordinal) {
            TwoLineViewHolder(parent, weakListener)
        } else {
            SingleLineViewHolder(parent, weakListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: Task = items[position]

        with(holder.itemView) {
            when (holder.itemViewType) {
                TITLE_DESCRIPTION_TASK.ordinal -> {
                    taskTitleTextView.text = item.title
                    taskDescriptionTextView.text = item.descriptions
                    if (item.favorite) {
                        taskFavoriteImageView.setColorFilter(
                            ContextCompat.getColor(context, R.color.colorRed)
                        )
                    } else {
                        taskFavoriteImageView.setColorFilter(
                            ContextCompat.getColor(context, R.color.colorGray)
                        )
                    }
                }
                DESCRIPTION_TASK.ordinal -> {
                    taskDescriptionTextView.text = items[position].descriptions
                    if (item.favorite) {
                        taskFavoriteImageView.setColorFilter(
                            ContextCompat.getColor(context, R.color.colorRed)
                        )
                    } else {
                        taskFavoriteImageView.setColorFilter(
                            ContextCompat.getColor(context, R.color.colorGray)
                        )
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!items[position].title.isNullOrBlank())
            TITLE_DESCRIPTION_TASK.ordinal
        else
            DESCRIPTION_TASK.ordinal
    }
}


