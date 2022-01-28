package com.yhy.base.widget

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.yhy.base.R
import com.yhy.base.ext.onClick

class ChooseIvPopupWindow(activity: Activity, mCallBack: OnChooseIvCallBack): PopupWindow(activity) {

    private val mActivity = activity

   init {
       val view = View.inflate(mActivity, R.layout.layout_choose_iv_dialog,null)
       view.findViewById<TextView>(R.id.take_photo).onClick { mCallBack.onTakePhotoCallBack() }
       view.findViewById<TextView>(R.id.photo_album).onClick { mCallBack.onAlbumCallBack() }
       view.findViewById<TextView>(R.id.cancel).onClick { dismiss() }

       super.setContentView(view)
       super.setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
       super.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
       super.setOutsideTouchable(true)
       super.setBackgroundDrawable(ColorDrawable(-0x50000000))
       super.update()
   }

    fun show(parent: View?) {
        val lp = mActivity.window.attributes
        lp.alpha = 0.7f
        mActivity.window.attributes = lp
        super.update()
        super.showAtLocation(parent, Gravity.BOTTOM, 0, 0)
    }

    override fun dismiss() {
        super.dismiss()
        isFocusable = false
        val lp = mActivity.window.attributes
        lp.alpha = 1f
        mActivity.window.attributes = lp
    }

    interface OnChooseIvCallBack{

        /**
         * 拍照
         * */
        fun onTakePhotoCallBack()

        /**
         * 从相册中选择
         * */
        fun onAlbumCallBack()
    }
}