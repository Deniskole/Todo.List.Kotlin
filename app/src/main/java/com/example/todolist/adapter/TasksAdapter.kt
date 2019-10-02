package com.example.todolist.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapter.TaskItemType.DESCRIPTION_TASK
import com.example.todolist.adapter.TaskItemType.TITLE_DESCRIPTION_TASK
import com.example.todolist.adapter.holder.TaskDescriptionViewHolder
import com.example.todolist.adapter.holder.TaskTitleDescriptionViewHolder
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
            TaskTitleDescriptionViewHolder(parent, weakListener)
        } else {
            TaskDescriptionViewHolder(parent, weakListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TITLE_DESCRIPTION_TASK.ordinal -> {
                with(holder.itemView) {
                    taskTitleTextView.text = items[position].title
                    taskDescriptionTextView.text = items[position].descriptions
                    if (items[position].favorite) {
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
            DESCRIPTION_TASK.ordinal -> {
                with(holder.itemView) {
                    taskDescriptionTextView.text = items[position].descriptions
                    if (items[position].favorite) {
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


