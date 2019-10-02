package com.example.todolist.adapter

import androidx.recyclerview.widget.RecyclerView

interface OnViewHolderLongClickListener {
    fun onViewHolderLongClick(holder: RecyclerView.ViewHolder, position: Int, id: Int) : Boolean
}