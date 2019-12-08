package com.wispapp.themovie.ui.moviedetails.fragments

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.wispapp.themovie.R
import com.wispapp.themovie.core.application.Constants.REMOTE_DATE_FORMATE
import com.wispapp.themovie.core.common.ApiConfigLinkProvider
import com.wispapp.themovie.core.common.ConfigsHolder
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.GenresItemModel
import com.wispapp.themovie.core.model.database.models.ImageModel
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.recycler.GenericAdapter
import com.wispapp.themovie.ui.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val FIRST_LINE_TEXT_LENGTH = 15
private const val OUTPUT_DATE_FORMAT = "MMMM d, yyyy"

const val MOVIE_ID = "movie_id"

class MovieDetailsFragment : BaseFragment(R.layout.fragment_movie_details),
    GenericAdapter.OnItemClickListener<ImageModel> {

    private val moviesViewModel: MoviesViewModel by sharedViewModel()
    private val imageLoader: ImageLoader by inject()

    private val photoAdapter by lazy { getImageAdapter() }

    override fun initViewModel() {
        val movieId = getMovieId()

        movieDetailsObserve(movieId)
        movieImagesObserve(movieId)
        moviesViewModel.getTrailers(movieId)
    }

    override fun initView(view: View) {
        initRecycler()

        overview_button.setOnClickListener { showOverview() }
        photos_button.setOnClickListener { showPhotos() }
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

    override fun onClickItem(data: ImageModel) {
        moviesViewModel.selectedImageLiveData.postValue(mutableListOf(data))
        navigateTo(R.id.action_details_to_image)
    }

    private fun getMovieId(): Int {
        var movieId = 0

        if (arguments == null) showError("Error movie loading", null)

        arguments?.let { movieId = it.getInt(MOVIE_ID) }
        return movieId
    }

    private fun movieDetailsObserve(movieId: Int) {
        moviesViewModel.getMovieDetails(movieId)
        moviesViewModel.movieDetailsLiveData.observe(this, Observer {
            updateUi(it[0])
        })
    }

    private fun movieImagesObserve(movieId: Int) {
        moviesViewModel.getImages(movieId)
        moviesViewModel.imagesLiveData.observe(this, Observer {
            photoAdapter.update(it)
            scrollToSelectedImage(it)
        })
    }

    private fun scrollToSelectedImage(imageList: MutableList<ImageModel>) {
        val image = moviesViewModel.selectedImageLiveData.value?.get(0)
        val position = imageList.indexOf(image)
        images_recycler.layoutManager?.scrollToPosition(position)
    }

    private fun initRecycler() {
        val layoutManager = GridLayoutManager(requireContext(), 3)
        images_recycler.layoutManager = layoutManager
        images_recycler.adapter = photoAdapter
    }

    private fun updateUi(detailsModel: MovieDetailsModel) {
        loadPoster(detailsModel)

        detailsModel.apply {
            score_text.text = voteAverage.toString()
            title_text.text = getWrappedTitleText(title)
            year_text.text = getDate(releaseDate)
            genres_text.text = getGenresText(genres)
            overview_text.text = overview
        }

        play_trailer_button.setOnClickListener { openTrailerFragment() }
        watched_list_button.setOnClickListener { showMessage("Watched button Clicked") }
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
            val firstLine = title.substring(0,
                FIRST_LINE_TEXT_LENGTH
            )
            val enterIndex = firstLine.lastIndexOf(" ")
            title.substring(0, enterIndex) + "\n" + title.substring(enterIndex + 1, title.length)
        }
    }

    private fun getDate(releaseDate: String): String {
        val remoteDateFormat = SimpleDateFormat(REMOTE_DATE_FORMATE, Locale.ENGLISH)
        val outPutDateFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.ENGLISH)
        val date = remoteDateFormat.parse(releaseDate)

        return date?.let {
            outPutDateFormat.format(date)
        } ?: run { "" }
    }

    private fun getGenresText(genres: List<GenresItemModel>): String {
        var genresText = ""
        for (i in genres.indices) {
            genresText += genres[i].name
            if (i != genres.size - 1) genresText += (", ")

        }
        return genresText
    }

    private fun openTrailerFragment() {

        navigateTo(R.id.action_details_to_video)
    }

    private fun showMessage(message: String) {
        Snackbar.make(poster_image, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun getImageAdapter() = object : GenericAdapter<ImageModel>(this) {
        override fun getLayoutId(position: Int, obj: ImageModel): Int = R.layout.item_movie_image
    }

    private fun showOverview() {
        overview_button.isEnabled = false
        photos_button.isEnabled = true

        overview_layout.visibility = View.VISIBLE
        images_recycler.visibility = View.GONE
    }

    private fun showPhotos() {
        overview_button.isEnabled = true
        photos_button.isEnabled = false

        overview_layout.visibility = View.GONE
        images_recycler.visibility = View.VISIBLE
    }
}
