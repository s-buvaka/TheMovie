package com.wispapp.themovie.ui.moviedetails.fragments

import android.view.View
import androidx.lifecycle.Observer
import com.wispapp.themovie.R
import com.wispapp.themovie.core.model.database.models.ReviewModel
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.recycler.GenericAdapter
import com.wispapp.themovie.ui.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_reviews.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ReviewsFragment : BaseFragment(R.layout.fragment_reviews) {

    private val moviesViewModel: MoviesViewModel by sharedViewModel()

    private val reviewAdapter by lazy { getAdapter() }

    override fun initViewModel() {
        moviesViewModel.reviewsLiveData.observe(this, Observer {
            reviewAdapter.update(it)
        })
    }

    override fun initView(view: View) {
        reviews_recycler.adapter = reviewAdapter
    }

    override fun dataLoadingObserve() {
        moviesViewModel.isDataLoading.observe(this, Observer { isDataLoaded ->
            if (isDataLoaded)
                showLoading()
            else
                hideLoading()
        })
    }

    override fun exceptionObserve() {
        moviesViewModel.exception.observe(this, Observer { error ->
            if (error != null && error.errorMessage.isNotEmpty())
                showError(error.errorMessage, error.func)
        })
    }

    private fun getAdapter() = object : GenericAdapter<ReviewModel>() {
        override fun getLayoutId(position: Int, obj: ReviewModel): Int = R.layout.item_review
    }
}
