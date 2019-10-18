package com.example.todolist.screens.container

import androidx.annotation.IntDef
import com.example.todolist.R

interface ContainerContract {
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(NavigationItem.ALL, NavigationItem.FAVORITE)
    annotation class NavigationItem {
        companion object {
            const val ALL = R.id.itemAll
            const val FAVORITE = R.id.itemFavorite
        }
    }

    interface Presenter {
        fun select(@NavigationItem what: Int)
    }
}