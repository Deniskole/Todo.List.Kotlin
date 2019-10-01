package com.example.todolist.adapter

import androidx.recyclerview.widget.RecyclerView

interface OnViewHolderClickListener {
    fun onViewHolderClick(holder: RecyclerView.ViewHolder, position: Int, id: Int)
    /* TODO: This method below should not be placed in this interface*/
    fun onViewHolderLongClick(holder: RecyclerView.ViewHolder, position: Int, id: Int)
    /* TODO: This is unused. Replace it.*/
    fun onViewHolderFavoriteClick(holder: RecyclerView.ViewHolder, position: Int, id: Int)
}