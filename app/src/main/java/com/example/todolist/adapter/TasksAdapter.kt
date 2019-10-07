package com.example.todolist.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapter.TaskItemType.DESCRIPTION
import com.example.todolist.adapter.TaskItemType.TITLE_AND_DESCRIPTION
import com.example.todolist.adapter.holder.SingleLineViewHolder
import com.example.todolist.adapter.holder.TwoLineViewHolder
import com.example.todolist.model.Task
import kotlinx.android.synthetic.main.task_list_item_d.view.*
import kotlinx.android.synthetic.main.task_list_item_t_d.view.*
import kotlinx.android.synthetic.main.task_list_item_t_d.view.taskDescriptionTextView
import kotlinx.android.synthetic.main.task_list_item_t_d.view.taskFavoriteImageView
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
            /*TODO: Reuse common code. Avoid code duplication.*/
            /*TODO: container_single && container_two is???*/
            when (holder.itemViewType) {
                TITLE_AND_DESCRIPTION.ordinal -> {
                    taskTitleTextView.text = task.title
                    /*TODO: Check the import for this property. Do you see troubles with it? */
                    taskDescriptionTextView.text = task.descriptions
                    if (task.favorite) {
                        container_two.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorTeal
                            )
                        )
                        taskFavoriteImageView.setColorFilter(
                            ContextCompat.getColor(context, R.color.colorPrimaryDark)
                        )
                    } else {
                        container_two.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorWhite
                            )
                        )
                        taskFavoriteImageView.setColorFilter(
                            ContextCompat.getColor(context, R.color.colorGray)
                        )
                    }
                }
                DESCRIPTION.ordinal -> {
                    taskDescriptionTextView.text = tasks[position].descriptions
                    if (task.favorite) {
                        container_single.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorTeal
                            )
                        )
                        taskFavoriteImageView.setColorFilter(
                            ContextCompat.getColor(context, R.color.colorPrimaryDark)
                        )
                    } else {
                        container_single.setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.colorWhite
                            )
                        )
                        taskFavoriteImageView.setColorFilter(
                            ContextCompat.getColor(context, R.color.colorGray)
                        )
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


