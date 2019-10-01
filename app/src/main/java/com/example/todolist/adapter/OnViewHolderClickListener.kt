package com.example.todolist.adapter

import androidx.recyclerview.widget.RecyclerView

interface OnViewHolderClickListener {
    fun onViewHolderClick(holder: RecyclerView.ViewHolder, position: Int, id: Int)
    fun onViewHolderLongClick(holder: RecyclerView.ViewHolder, position: Int, id: Int)
    fun onViewHolderFavoriteClick(holder: RecyclerView.ViewHolder, position: Int, id: Int)
}