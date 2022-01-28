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
import com.yhy.user.presenter.ResetPwdPresenter
import com.yhy.user.presenter.view.ResetPwdView
import kotlinx.android.synthetic.main.activity_reset_pwd.*

class ResetPwdActivity : BaseMvpActivity<ResetPwdPresenter>(), ResetPwdView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pwd)

        val mobile = intent.getStringExtra("mobile")

        mConfirmBtn.isEnable(mPwdEt) { hasInputContent() }
        mConfirmBtn.isEnable(mPwdConfirmEt) { hasInputContent() }
        mConfirmBtn.onClick{
            val psw = mPwdEt.text.toString().trim()
            val confirmPsw = mPwdConfirmEt.text.toString().trim()
            if(psw != confirmPsw){
                Toast.makeText(this, "密码不一致",Toast.LENGTH_SHORT).show()
                return@onClick
            }
            mobile?.let {
                mPresenter.resetPwd(it, psw)
            }
        }
        mHeaderBar.getRightView().onClick {
            startActivity(Intent(this@ResetPwdActivity, RegisterActivity::class.java))
        }
    }

    override fun onResetPwdResult(result: Boolean) {
        if(result){
            Toast.makeText(this, "密码修改成功",Toast.LENGTH_SHORT).show()
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
        return  mPwdEt.text.toString().trim().isNullOrBlank().not() and
                mPwdConfirmEt.text.toString().trim().isNullOrBlank().not()
    }
}