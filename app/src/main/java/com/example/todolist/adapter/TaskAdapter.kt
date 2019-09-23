package com.example.todolist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import kotlinx.android.synthetic.main.task_list_item.view.*

class TaskAdapter(private var items: ArrayList<String>, private val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.task_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTask.text = items[position]
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTask: TextView = view.taskTextView
}