package com.example.todolist.adapter

import android.content.Context
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == TITLE_AND_DESCRIPTION.ordinal) {
            TwoLineViewHolder(parent, weakListener)
        } else {
            SingleLineViewHolder(parent, weakListener)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val task: Task = tasks[position]

        with(holder.itemView) {
            when (holder.itemViewType) {
                TITLE_AND_DESCRIPTION.ordinal -> {
                    taskTitleTextView.text = task.title
                    taskDescriptionTextView.text = task.descriptions
                    setItemColor(context, holder, task.favorite)
                }
                DESCRIPTION.ordinal -> {
                    taskDescriptionTextView.text = tasks[position].descriptions
                    setItemColor(context, holder, task.favorite)
                }
            }
        }
    }

    override fun getItemCount() = tasks.size

    override fun getItemViewType(position: Int) = if (!tasks[position].title.isNullOrBlank())
        TITLE_AND_DESCRIPTION.ordinal else DESCRIPTION.ordinal

    fun getTask(position: Int): Task = tasks[position]

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    private fun setItemColor(context: Context, holder: RecyclerView.ViewHolder, flag: Boolean) =
        if (flag) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.teal))
            holder.itemView.taskFavoriteImageView.setColorFilter(
                ContextCompat.getColor(context, R.color.colorPrimaryDark)
            )
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
            holder.itemView.taskFavoriteImageView.setColorFilter(
                ContextCompat.getColor(context, R.color.gray)
            )
        }
}









