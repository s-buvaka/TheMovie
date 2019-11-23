package com.wispapp.themovie.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.wispapp.themovie.R
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.base.recycler.BaseViewHolder
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import com.wispapp.themovie.ui.base.recycler.ViewHolderFactory
import com.wispapp.themovie.ui.moviedetails.MOVIE_ID
import com.wispapp.themovie.ui.viewholders.Categories
import com.wispapp.themovie.ui.viewholders.MovieCategory
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main),
    GenericAdapter.OnItemClickListener<Categories> {

    private val moviesViewModel: MoviesViewModel by viewModel()
    private val adapter: GenericAdapter<Categories> by lazy { categoriesAdapter }

    override fun initViewModel() {
        popularMoviesObserve()
    }

    override fun initView() {
        initRecycler()
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
        moviesViewModel.exception.observe(this, Observer { errorMessage ->
            if (errorMessage != null && errorMessage.isNotEmpty())
                showError(errorMessage)
        })
    }

    override fun onClickItem(data: Categories) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, data.movieId)
        findNavController().navigate(R.id.movieDetailsFragment, bundle)
    }

    private fun popularMoviesObserve() {
        moviesViewModel.getMovies()
        moviesViewModel.popularMovieLiveData.observe(this, Observer {
            adapter.update(it)
        })
    }

    private fun initRecycler() {
        movie_recycler.adapter = adapter
    }

    private val categoriesAdapter = object : GenericAdapter<Categories>(this) {
        override fun getItemId(position: Int): Long = getItem(position).item.hashCode().toLong()

        override fun getLayoutId(position: Int, obj: Categories): Int {
            return when (obj) {
                is MovieCategory -> R.layout.item_category_title
                else -> R.layout.item_category_movies
            }
        }

        override fun getViewHolder(view: View, viewType: Int): BaseViewHolder<*> {
            return when (viewType) {
                R.layout.item_category_title ->
                    ViewHolderFactory.create(view, R.layout.item_category_title)
                else -> ViewHolderFactory.create(view, R.layout.item_category_movies)
            }
        }
    }
}
