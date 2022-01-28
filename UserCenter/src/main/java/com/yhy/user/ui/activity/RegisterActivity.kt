package com.yhy.user.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.yhy.base.common.AppManager
import com.yhy.base.ext.isEnable
import com.yhy.base.ext.onClick
import com.yhy.base.ui.activity.BaseMvpActivity
import com.yhy.user.R
import com.yhy.user.injection.component.DaggerUserComponent
import com.yhy.user.injection.module.UserModule

import com.yhy.user.presenter.RegisterPresenter
import com.yhy.user.presenter.view.RegisterView
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseMvpActivity<RegisterPresenter>(),RegisterView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        mPresenter = RegisterPresenter();

        mRegisterBtn.isEnable(mMobileEt) { hasInputContent() }
        mRegisterBtn.isEnable(mVerifyCodeEt) { hasInputContent() }
        mRegisterBtn.isEnable(mPwdEt) { hasInputContent() }
        mRegisterBtn.isEnable(mPwdConfirmEt) { hasInputContent() }

        mRegisterBtn.onClick{
//            val phoneNum = mMobileEt.text.toString().trim()
//            if(TextUtils.isEmpty(phoneNum)){
//                Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show()
//                return@onClick
//            }
//            val code = mVerifyCodeEt.text.toString().trim()
//            if(TextUtils.isEmpty(code)){
//                Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show()
//                return@onClick
//            }
//            val psw = mPwdEt.text.toString().trim()
//            if(TextUtils.isEmpty(psw)){
//                Toast.makeText(this,"请设置密码",Toast.LENGTH_SHORT).show()
//                return@onClick
//            }
//            val confirmPsw = mPwdConfirmEt.text.toString().trim()
//            if(TextUtils.isEmpty(confirmPsw)){
//                Toast.makeText(this,"请确认密码",Toast.LENGTH_SHORT).show()
//                return@onClick
//            }

            val phoneNum = mMobileEt.text.toString().trim()
            val code = mVerifyCodeEt.text.toString().trim()
            val psw = mPwdEt.text.toString().trim()
            val confirmPsw = mPwdConfirmEt.text.toString().trim()
            if(confirmPsw != psw){
                Toast.makeText(this,"密码不一致",Toast.LENGTH_SHORT).show()
                return@onClick
            }
            mPresenter.register(phoneNum,code,psw)
        }

        mVerifyCodeBtn.onClick {
//            mPresenter.register2("13912345678","111111","159789")
            mVerifyCodeBtn.requestSendVerifyNumber()
        }
    }

    override fun onRegisterResult(result: String) {
        Toast.makeText(this, result,Toast.LENGTH_SHORT).show()
        finish()
//        if(result){
//            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show()
//        }else{
//            Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show()
//        }
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
                mVerifyCodeEt.text.toString().trim().isNullOrBlank().not() and
                mPwdEt.text.toString().trim().isNullOrBlank().not() and
                mPwdConfirmEt.text.toString().trim().isNullOrBlank().not()
    }
}