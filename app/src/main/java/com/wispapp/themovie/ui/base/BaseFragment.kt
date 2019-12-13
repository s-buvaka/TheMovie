package com.wispapp.themovie.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment(@LayoutRes private val contentLayoutId: Int = 0) : Fragment(),
    BaseView, OnBackPressed {

    protected abstract fun initViewModel()

    protected abstract fun initView(view: View, savedInstanceState: Bundle?)

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
        initView(view, savedInstanceState)
    }

    override fun showLoading() =
        (activity as BaseActivity).showLoading()

    override fun hideLoading() =
        (activity as BaseActivity).hideLoading()

    override fun showError(errorMessage: String, repeatFunc: (() -> Unit)?) =
        (activity as BaseActivity).showError(errorMessage, repeatFunc)

    override fun hideError() =
        (activity as BaseActivity).hideError()

    override fun onBackPressed() {
        parentFragmentManager.popBackStack()
    }

    protected fun navigateTo(@IdRes resId: Int, args: Bundle? = null) {
        findNavController().navigate(resId, args)
    }
}