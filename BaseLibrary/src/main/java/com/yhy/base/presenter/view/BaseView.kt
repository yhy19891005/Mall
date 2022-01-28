package com.yhy.base.presenter.view

interface BaseView {

    /**
     * 显示加载进度
     * */
    fun showLoading()

    /**
     * 隐藏加载进度
     * */
    fun hideLoading()

    /**
     * 发生错误时的回调
     * */
    fun onError(text: String)

}