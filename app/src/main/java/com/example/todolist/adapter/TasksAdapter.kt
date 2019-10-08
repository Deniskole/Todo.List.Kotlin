package com.example.todolist.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapter.TaskItemType.DESCRIPTION
import com.example.todolist.adapter.TaskItemType.TITLE_AND_DESCRIPTION
import com.example.todolist.adapter.holder.SingleLineViewHolder
import com.example.todolist.adapter.holder.TwoLineViewHolder
import com.example.todolist.model.Task
import kotlinx.android.synthetic.main.task_list_item_t_d.view.*
import java.lang.ref.WeakReference

class TasksAdapter<L>(
    listener: L
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() where L : OnViewHolderClickListener,
                                                          L : OnViewHolderLongClickListener {
    private var tasks = emptyList<Task>()
    private val weakListener = WeakReference(listener)
    override fun getItemCount() = tasks.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TITLE_AND_DESCRIPTION.ordinal) {
            TwoLineViewHolder(parent, weakListener)
        } else {
            SingleLineViewHolder(parent, weakListener)
        }
    }

    fun getTask(position: Int): Task = tasks[position]

    internal fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task: Task = tasks[position]

        with(holder.itemView) {
            when (holder.itemViewType) {
                TITLE_AND_DESCRIPTION.ordinal -> {
                    taskTitleTextView.text = task.title
                    taskDescriptionTextView.text = task.descriptions
                    if (task.favorite) {
                        setBackgroundColor(holder.itemView, R.color.teal)
                        setTaskColor(holder.itemView, R.color.colorPrimaryDark)
                    } else {
                        setBackgroundColor(holder.itemView, R.color.transparent)
                        setTaskColor(holder.itemView, R.color.gray)
                    }
                }
                DESCRIPTION.ordinal -> {
                    taskDescriptionTextView.text = tasks[position].descriptions
                    if (task.favorite) {
                        setBackgroundColor(holder.itemView, R.color.teal)
                        setTaskColor(holder.itemView, R.color.colorPrimaryDark)
                    } else {
                        setBackgroundColor(holder.itemView, R.color.transparent)
                        setTaskColor(holder.itemView, R.color.gray)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int) = if (!tasks[position].title.isNullOrBlank())
        TITLE_AND_DESCRIPTION.ordinal
    else
        DESCRIPTION.ordinal
}

private fun View.setTaskColor(view: View, color: Int) {
    view.taskFavoriteImageView.setColorFilter(
        ContextCompat.getColor(context, color)
    )
}

private fun View.setBackgroundColor(view: View, color: Int) {
    view.container.setBackgroundColor(
        ContextCompat.getColor(context, color)
    )
}




