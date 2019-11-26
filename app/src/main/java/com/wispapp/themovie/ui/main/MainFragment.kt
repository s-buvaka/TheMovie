package com.wispapp.themovie.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wispapp.themovie.R
import com.wispapp.themovie.core.application.showKeyboard
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.moviedetails.MOVIE_ID
import com.wispapp.themovie.ui.recycler.GenericAdapter
import kotlinx.android.synthetic.main.bottom_sheet_main_fragment.*
import kotlinx.android.synthetic.main.content_main_fragment.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


private const val DATE_FORMAT = "EEEE, MMMM d"

class MainFragment : BaseFragment(R.layout.fragment_main),
    GenericAdapter.OnItemClickListener<MovieModel> {

    private val moviesViewModel: MoviesViewModel by viewModel()

    private val nowPlayingMoviesAdapter by lazy { getMovieAdapter() }
    private val popularMoviesAdapter by lazy { getMovieAdapter() }
    private val topRatedMoviesAdapter by lazy { getMovieAdapter() }
    private val upcomingMoviesAdapter by lazy { getMovieAdapter() }
    private val searchMovieAdapter by lazy { getSearchAdapter() }

    private val bottomSheetBehavior by lazy { BottomSheetBehavior.from(bottom_sheet) }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_activity_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> showSearch()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initViewModel() {
        moviesObserveLiveData()
    }

    override fun initView(view: View) {
        current_date_text.text = getCurrentDate()
        initRecycler()
        initToolbar(view)
        initBottomSheet()
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

    override fun onClickItem(data: MovieModel) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, data.id)
        findNavController().navigate(R.id.movieDetailsFragment, bundle)
    }

    private fun moviesObserveLiveData() {
        moviesViewModel.getMovies()

        moviesViewModel.nowPlayingMoviesLiveData.observe(this, Observer {
            nowPlayingMoviesAdapter.update(it)
        })
        moviesViewModel.popularMoviesLiveData.observe(this, Observer {
            popularMoviesAdapter.update(it)
        })
        moviesViewModel.topRatedMovieLiveData.observe(this, Observer {
            topRatedMoviesAdapter.update(it)
        })
        moviesViewModel.upcomingMoviesLiveData.observe(this, Observer {
            upcomingMoviesAdapter.update(it)
        })
        moviesViewModel.searchMovieLiveData.observe(this, Observer {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            searchMovieAdapter.update(it)
        })
    }

    private fun initRecycler() {
        now_playing_recycler.adapter = nowPlayingMoviesAdapter
        popular_recycler.adapter = popularMoviesAdapter
        top_rated_recycler.adapter = topRatedMoviesAdapter
        upcoming_recycler.adapter = upcomingMoviesAdapter

        searchMovieAdapter.setDiffUtil(getMovieDiffUtilCallback())
        search_movie_recycler.adapter = searchMovieAdapter
    }

    private fun initToolbar(view: View) {
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun initBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        return dateFormat.format(Date())
    }

    private fun getMovieAdapter() = object : GenericAdapter<MovieModel>(this) {
        override fun getLayoutId(position: Int, obj: MovieModel): Int = R.layout.item_movie
    }

    private fun getSearchAdapter() = object : GenericAdapter<MovieModel>(this) {
        override fun getLayoutId(position: Int, obj: MovieModel): Int = R.layout.item_search_movie
    }

    private fun showSearch() {
        search_field.visibility = View.VISIBLE
        search_field.requestFocus()
        search_field.showKeyboard(true)
        search_field.addTextChangedListener(movieSearchTextWatcher)
    }

    private val movieSearchTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (s.length >= 4 && s.length % 2 == 0)
                moviesViewModel.searchMovie(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun getMovieDiffUtilCallback() = object : GenericAdapter.GenericItemDiff<MovieModel> {
        override fun isSame(
            oldItems: List<MovieModel>,
            newItems: List<MovieModel>,
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean {
            val old = oldItems[oldItemPosition]
            val new = newItems[newItemPosition]
            return old.id == new.id
        }

        override fun isSameContent(
            oldItems: List<MovieModel>,
            newItems: List<MovieModel>,
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean {
            val old = oldItems[oldItemPosition]
            val new = newItems[newItemPosition]
            return old.id == new.id &&
                    old.title == new.title &&
                    old.originalTitle == new.originalTitle &&
                    old.overview == new.overview &&
                    old.popularity == new.popularity &&
                    old.originalLanguage == new.originalLanguage &&
                    old.hasVideo == new.hasVideo &&
                    old.genreIds == new.genreIds &&
                    old.posterPath == new.posterPath &&
                    old.backdropPath == new.backdropPath &&
                    old.releaseDate == new.releaseDate &&
                    old.isAdult == new.isAdult &&
                    old.voteAverage == new.voteAverage &&
                    old.voteCount == new.voteCount &&
                    old.category == new.category
        }
    }
}
