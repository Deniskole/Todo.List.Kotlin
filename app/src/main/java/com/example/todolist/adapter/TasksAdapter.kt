package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.adapter.TaskItemType.DESCRIPTION
import com.example.todolist.adapter.TaskItemType.DESCRIPTION_TITLE
import com.example.todolist.model.Task
import kotlinx.android.synthetic.main.task_list_item_d.view.taskDescriptionTextView
import kotlinx.android.synthetic.main.task_list_item_t_d.view.*
import java.lang.ref.WeakReference

class TasksAdapter(
    var items: List<Task>,
    listener: OnViewHolderClickListener
) : RecyclerView.Adapter<TaskViewHolder>() {

    private val weakListener = WeakReference(listener)

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = if (viewType == DESCRIPTION_TITLE.TYPE) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_list_item_t_d, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_list_item_d, parent, false)
        }
        /* TODO: Divide view holder into 2 view holder's. */
        return TaskViewHolder(weakListener, view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        when (holder.itemViewType) {
            DESCRIPTION_TITLE.TYPE -> {
                holder.itemView.taskTitleTextView.text = items[position].title
                holder.itemView.taskDescriptionTextView.text = items[position].descriptions
                if (items[position].favorite) {
                    holder.itemView.taskFavoriteImageView.setImageResource(R.drawable.ic_favorite_red_24dp)
                } else {
                    holder.itemView.taskFavoriteImageView.setImageResource(R.drawable.ic_favorite_gray_24dp)
                }
            }
            DESCRIPTION.TYPE -> {
                holder.itemView.taskDescriptionTextView.text = items[position].descriptions
                if (items[position].favorite) {
                    holder.itemView.taskFavoriteImageView.setImageResource(R.drawable.ic_favorite_red_24dp)
                } else {
                    holder.itemView.taskFavoriteImageView.setImageResource(R.drawable.ic_favorite_gray_24dp)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        /* TODO: This block can be simplified. */
        val title: String? = items[position].title
        return if (title != null && title.isNotEmpty()) {
            DESCRIPTION_TITLE.TYPE
        } else {
            DESCRIPTION.TYPE
        }
    }
}


