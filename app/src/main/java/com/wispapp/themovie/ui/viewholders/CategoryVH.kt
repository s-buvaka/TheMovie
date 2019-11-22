package com.wispapp.themovie.ui.viewholders

import android.view.View
import com.wispapp.themovie.core.model.database.models.CATEGORY
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.ui.base.recycler.BaseViewHolder
import com.wispapp.themovie.ui.base.recycler.GenericAdapter

class CategoryVH(private val rootView: View) : BaseViewHolder<CategoryList>(rootView),
    GenericAdapter.Binder<CategoryList> {
}

data class CategoryList(val category: CATEGORY, val movie: MovieModel)

