package com.wispapp.themovie.ui.moviedetails

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.wispapp.themovie.R
import com.wispapp.themovie.core.application.Constants.MIME_TYPE_TEXT_HTML
import com.wispapp.themovie.core.common.ApiConfigLinkProvider
import com.wispapp.themovie.core.common.ConfigsHolder
import com.wispapp.themovie.core.model.database.models.ImageModel
import com.wispapp.themovie.ui.base.BaseFragment
import com.wispapp.themovie.ui.recycler.GenericAdapter
import com.wispapp.themovie.ui.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movie_image.*
import kotlinx.android.synthetic.main.toolbar_movie_image.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoviePhotoFragment : BaseFragment(R.layout.fragment_movie_image) {

    private val moviesViewModel: MoviesViewModel by sharedViewModel()

    private val pagerAdapter by lazy { getViewPagerAdapter() }
    private var actionShare: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_movie_image, menu)
        actionShare = menu.findItem(R.id.action_share)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> shareImage()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initViewModel() {
        moviesViewModel.movieImagesLiveData.observe(this, Observer {
            showImage(it)
        })
    }

    override fun initView(view: View) {
        initImageViewPager()
        initToolbar(view)
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

    private fun shareImage() {
        val shortUrl = moviesViewModel.selectedImageLiveData.value?.get(0)?.filePath
        val movieTitle = moviesViewModel.movieDetailsLiveData.value?.get(0)?.title

        shortUrl?.let {
            val imageUrl = ApiConfigLinkProvider(it, ConfigsHolder.getConfig()).getUrl()
            val share = Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, imageUrl)
                putExtra(
                    Intent.EXTRA_TITLE,
                    getString(R.string.movie_share_text_placeholder, movieTitle)
                )
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                type = MIME_TYPE_TEXT_HTML
            }, null)
            startActivity(share)
        }
    }

    private fun initImageViewPager() {
        image_pager.adapter = pagerAdapter
        image_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val image = pagerAdapter.getItem(position)
                moviesViewModel.selectedImageLiveData.postValue(mutableListOf(image))

                setToolbarPagePosition(position)
            }

            private fun setToolbarPagePosition(position: Int) {
                val photoCount = moviesViewModel.movieImagesLiveData.value?.size ?: 0
                number_image_text.text = getString(
                    R.string.movie_image_position_placeholder,
                    position + 1,
                    photoCount
                )
            }
        })
    }

    private fun initToolbar(view: View) {
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        movie_title_text.text = moviesViewModel.movieDetailsLiveData.value?.get(0)?.title
    }

    private fun getViewPagerAdapter() = object : GenericAdapter<ImageModel>() {
        override fun getLayoutId(position: Int, obj: ImageModel): Int =
            R.layout.item_fullscreen_image
    }

    private fun showImage(imageList: MutableList<ImageModel>) {
        val image = moviesViewModel.selectedImageLiveData.value?.get(0)
        val position = imageList.indexOf(image)
        pagerAdapter.update(imageList)
        image_pager.setCurrentItem(position, false)
    }
}
