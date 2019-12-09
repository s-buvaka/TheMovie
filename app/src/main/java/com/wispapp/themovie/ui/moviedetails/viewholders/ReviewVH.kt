package com.wispapp.themovie.ui.moviedetails.viewholders

import android.view.View
import com.wispapp.themovie.R
import com.wispapp.themovie.core.model.database.models.ReviewModel
import com.wispapp.themovie.ui.recycler.BaseViewHolder
import com.wispapp.themovie.ui.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewVH(private val rootView: View) : BaseViewHolder<ReviewModel>(rootView),
    GenericAdapter.Binder<ReviewModel> {

    override fun bind(
        data: ReviewModel,
        listener: GenericAdapter.OnItemClickListener<ReviewModel>?
    ) {
        rootView.apply {
            author_text.text = rootView.context.getString(R.string.item_review_author_placeholder, data.author)
            content_text.text = data.content
        }

        rootView.apply {
            more_button.setOnClickListener {
                content_text.maxLines = Int.MAX_VALUE
                more_button.visibility = View.GONE
            }
        }
    }
}