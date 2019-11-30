package com.wispapp.themovie.ui.moviedetails

import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.wispapp.themovie.R
import com.wispapp.themovie.core.common.ApiConfigLinkProvider
import com.wispapp.themovie.core.common.ConfigsHolder
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.GenresItemModel
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val FIRST_LINE_TEXT_LENGTH = 15

const val MOVIE_ID = "movie_id"

class MovieDetailsFragment : BaseFragment(R.layout.fragment_movie_details) {

    private val moviesViewModel: MoviesViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()

    override fun initViewModel() {
        var movieId = 0

        if (arguments == null) showError("Error movie loading", null)
        arguments?.let { movieId = it.getInt(MOVIE_ID) }

        dataLoadingObserve()
        movieDetailsObserve(movieId)
    }

    override fun initView(view: View) {}

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

    private fun movieDetailsObserve(movieId: Int) {
        moviesViewModel.getMovieDetails(movieId)
        moviesViewModel.movieDetailsLiveData.observe(this, Observer {
            updateUi(it[0])
        })
    }

    private fun updateUi(detailsModel: MovieDetailsModel) {
        loadPoster(detailsModel)

        detailsModel.apply {
            score_text.text = voteAverage.toString()
            title_text.text = getWrappedTitleText(title)
            year_text.text = releaseDate
            genres_text.text = getGenresText(genres)
            overview_title_text.text
            overview_text.text = overview
        }

        play_trailer_button.setOnClickListener { showMessage("Play Trailer Clicked") }
        watched_list_button.setOnClickListener { showMessage("Watched button Clicked") }
        overview_button.setOnClickListener { showMessage("Overview button Clicked") }
        photos_button.setOnClickListener { showMessage("Photos button Clicked") }
    }

    private fun loadPoster(detailsModel: MovieDetailsModel) {
        val configs = ConfigsHolder.getConfig()
        val linkProvider = ApiConfigLinkProvider(detailsModel.posterPath, configs)
        imageLoader.loadPoster(linkProvider, poster_image)
    }

    private fun getWrappedTitleText(title: String): String {
        return if (title.length <= FIRST_LINE_TEXT_LENGTH)
            title
        else {
            val firstLine = title.substring(0, FIRST_LINE_TEXT_LENGTH)
            val enterIndex = firstLine.lastIndexOf(" ")
            title.substring(0, enterIndex) + "\n" + title.substring(enterIndex + 1, title.length)
        }
    }

    private fun getGenresText(genres: List<GenresItemModel>): String {
        var genresText = ""
        for (i in genres.indices) {
            genresText += genres[i].name
            if (i != genres.size - 1) genresText += (", ")

        }
        return genresText
    }

    private fun showMessage(message: String) {
        Snackbar.make(poster_image, message, Snackbar.LENGTH_SHORT).show()
    }
}
