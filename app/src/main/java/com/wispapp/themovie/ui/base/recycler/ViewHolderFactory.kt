package com.wispapp.themovie.ui.base.recycler

import android.view.View

object ViewHolderFactory {

    fun create(view: View, viewType: Int): BaseViewHolder<*> =
         when (viewType) {

            else -> throw Exception("Wrong view type")
        }
}