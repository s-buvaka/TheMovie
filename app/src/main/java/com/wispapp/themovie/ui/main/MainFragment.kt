package com.wispapp.themovie.ui.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wispapp.themovie.R
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
        popularMoviesObserve()
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

    private fun popularMoviesObserve() {
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
    }

    private fun initRecycler() {
        now_playing_recycler.adapter = nowPlayingMoviesAdapter
        popular_recycler.adapter = popularMoviesAdapter
        top_rated_recycler.adapter = topRatedMoviesAdapter
        upcoming_recycler.adapter = upcomingMoviesAdapter
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

    private fun showSearch() {
        search_field.visibility = View.VISIBLE
        search_field.requestFocus()
        search_field.showKeyboard(true)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

    }

    fun View.showKeyboard(isShow: Boolean) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (isShow) imm.showSoftInput(this, 0)
        else imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
