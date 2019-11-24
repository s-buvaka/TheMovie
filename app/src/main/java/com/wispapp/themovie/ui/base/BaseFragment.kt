package com.wispapp.themovie.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes private val contentLayoutId: Int = 0) : Fragment(),
    BaseView {

    protected abstract fun initViewModel()

    protected abstract fun initView()

    protected abstract fun dataLoadingObserve()

    protected abstract fun exceptionObserve()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        dataLoadingObserve()
        exceptionObserve()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        if (contentLayoutId != 0) inflater.inflate(contentLayoutId, container, false)
        else null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun showLoading() =
        (activity as BaseActivity).showLoading()

    override fun hideLoading() =
        (activity as BaseActivity).hideLoading()

    override fun showError(errorMessage: String, func: (() -> Unit)?) =
        (activity as BaseActivity).showError(errorMessage, func)

    override fun hideError() =
        (activity as BaseActivity).hideError()
}