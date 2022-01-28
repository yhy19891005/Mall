package com.yhy.user.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.yhy.base.ext.isEnable
import com.yhy.base.ext.onClick
import com.yhy.base.ui.activity.BaseMvpActivity
import com.yhy.user.R
import com.yhy.user.injection.component.DaggerUserComponent
import com.yhy.user.injection.module.UserModule
import com.yhy.user.presenter.ForgetPwdPresenter
import com.yhy.user.presenter.view.ForgetPwdView
import kotlinx.android.synthetic.main.activity_forget_pwd.*
import kotlinx.android.synthetic.main.activity_forget_pwd.mMobileEt
import kotlinx.android.synthetic.main.activity_forget_pwd.mVerifyCodeBtn
import kotlinx.android.synthetic.main.activity_forget_pwd.mVerifyCodeEt
import kotlinx.android.synthetic.main.activity_register.*

class ForgetPwdActivity : BaseMvpActivity<ForgetPwdPresenter>(), ForgetPwdView {

    private var phoneNum = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_pwd)

        mNextBtn.isEnable(mMobileEt) { hasInputContent() }
        mNextBtn.isEnable(mVerifyCodeEt) { hasInputContent() }
        mNextBtn.onClick{
            phoneNum = mMobileEt.text.toString().trim()
            val code = mVerifyCodeEt.text.toString().trim()
            mPresenter.forgetPwd(phoneNum, code)
        }
        mVerifyCodeBtn.onClick {
            mVerifyCodeBtn.requestSendVerifyNumber()
        }
    }

    override fun onForgetPwdResult(result: Boolean) {
        if(result){
            Toast.makeText(this, "验证成功",Toast.LENGTH_SHORT).show()
            val intent = Intent(this@ForgetPwdActivity, ResetPwdActivity::class.java)
            intent.putExtra("mobile", phoneNum)
            startActivity(intent)
            finish()
        }

    }

    override fun injectComponent() {
        DaggerUserComponent.builder()
            .activityComponent(activityComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    private fun hasInputContent(): Boolean{
        return  mMobileEt.text.toString().trim().isNullOrBlank().not() and
                mVerifyCodeEt.text.toString().trim().isNullOrBlank().not()
    }
}