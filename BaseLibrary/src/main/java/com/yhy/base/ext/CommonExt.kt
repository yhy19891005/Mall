package com.yhy.base.ext

import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.alibaba.android.arouter.launcher.ARouter
import com.kennyc.view.MultiStateView
import com.trello.rxlifecycle.LifecycleProvider
import com.yhy.base.R
import com.yhy.base.common.BaseConstant
import com.yhy.base.common.RouterPath
import com.yhy.base.data.protocol.BaseResp
import com.yhy.base.rx.BaseFunc
import com.yhy.base.rx.BaseFuncBoolean
import com.yhy.base.ui.adapter.DefaultTextWatcher
import com.yhy.base.utils.AppPrefsUtils
import com.yhy.base.utils.GlideUtils
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/*
    扩展Observable执行
 */
fun <T> Observable<T>.excute(subscriber: Subscriber<T>, provider: LifecycleProvider<*>){
    this.observeOn(AndroidSchedulers.mainThread())
        .compose(provider.bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .subscribe(subscriber)
}

/*
    扩展数据转换
 */
fun <T> Observable<BaseResp<T>>.covert(): Observable<T> = this.flatMap(BaseFunc())

/*
    扩展数据转换成Boolean型
 */
fun <T> Observable<BaseResp<T>>.covertBoolean(): Observable<Boolean> = this.flatMap(BaseFuncBoolean())

/*
    扩展点击事件
 */
fun View.onClick(listener:View.OnClickListener):View{
    setOnClickListener(listener)
    return this
}

/*
    扩展点击事件，参数为方法
 */
fun View.onClick(method:() -> Unit):View{
    setOnClickListener { method() }
    return this
}

/*
    扩展点击事件，参数为方法
 */
fun Button.isEnable(et: EditText, method:() -> Boolean){
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher(){
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
           btn.isEnabled = method()
        }
    })
}

/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}

/*
    判断是否为登录状态
 */
fun isLogin(): Boolean = AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN)!!.isNotEmpty()

/*
    需要登录才能进行的操作
 */
fun afterLogin(method: ()-> Unit){
    if(isLogin()){
        method()
    }else{
        ARouter.getInstance().build(RouterPath.UserCenter.PATH_LOGIN).navigation()
    }
}

/*
    多状态视图开始加载
 */
fun MultiStateView.startLoading(){
    viewState = MultiStateView.VIEW_STATE_LOADING
    val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
    val animBackground = loadingView!!.findViewById<View>(R.id.loading_anim_view).background
    (animBackground as AnimationDrawable).start()
}

/*
    扩展视图可见性
 */
fun View.setVisible(visible:Boolean){
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
