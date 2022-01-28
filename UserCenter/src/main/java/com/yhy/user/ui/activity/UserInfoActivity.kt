package com.yhy.user.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.kotlin.provider.common.ProviderConstant
import com.qiniu.android.http.ResponseInfo
import com.qiniu.android.storage.UpCompletionHandler
import com.qiniu.android.storage.UploadManager
import com.yhy.base.common.BaseConstant
import com.yhy.base.ext.onClick
import com.yhy.base.ui.activity.BaseMvpActivity
import com.yhy.base.utils.AppPrefsUtils
import com.yhy.base.utils.AppUtil
import com.yhy.base.utils.GlideUtils
import com.yhy.base.widget.ChooseIvPopupWindow
import com.yhy.user.R
import com.yhy.user.data.protocol.UserInfo
import com.yhy.user.injection.component.DaggerUserComponent
import com.yhy.user.injection.module.UserModule
import com.yhy.user.presenter.UserInfoPresenter
import com.yhy.user.presenter.view.UserInfoView
import com.yhy.user.utils.UserPrefsUtils
import kotlinx.android.synthetic.main.activity_user_info.*
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.lang.Exception

class UserInfoActivity : BaseMvpActivity<UserInfoPresenter>(), UserInfoView, ChooseIvPopupWindow.OnChooseIvCallBack {

    private lateinit var view: View
    private val REQUEST_CODE_APPLY_CAMERA_PERMISSION = 1
    private val REQUEST_CODE_APPLY_ALBUM_PERMISSION = 2
    private val PERMISSION_PHOTO_ALBUM = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.READ_EXTERNAL_STORAGE)
    private val PERMISSION_TAKE_PHOTO = arrayOf(Manifest.permission.CAMERA,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.READ_EXTERNAL_STORAGE)

    private var localFilePath = ""
    private var remoteImgUrl = ""

    private val upLoadManager by lazy { UploadManager() }
    private val popupWindow by lazy { ChooseIvPopupWindow(this, this) }

    private var mUserIcon: String? = ""
    private var mUserName: String? = ""
    private var mUserMobile: String? = ""
    private var mUserGender: String? = ""
    private var mUserSign: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = View.inflate(this, R.layout.activity_user_info, null)
        setContentView(view)

        initData()

        mUserIconView.onClick {
            chooseIv()
        }
        mHeaderBar.getRightView().onClick {
            if(mGenderMaleRb.isChecked){
                mUserGender = "0"
            }else {
                mUserGender = "1"
            }
            mPresenter.editUser(remoteImgUrl, mUserNameEt.text.toString(),mUserGender!!,mUserSignEt.text.toString())
        }
    }

    private fun initData(){
        mUserIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
        mUserName = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        mUserMobile = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_MOBILE)
        mUserGender = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_GENDER)
        mUserSign = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_SIGN)

        if(!TextUtils.isEmpty(mUserIcon)){
            GlideUtils.loadImage(this@UserInfoActivity, mUserIcon!!, mUserIconIv)
        }
        mUserName?.let { mUserNameEt.setText(mUserName) }
        mUserMobile?.let { mUserMobileTv.text = it }
        if("0" == mUserGender){
            mGenderMaleRb.isChecked = true
            mGenderFemaleRb.isChecked = false
        }else{
            mGenderMaleRb.isChecked = false
            mGenderFemaleRb.isChecked = true
        }
        mUserSign?.let { mUserSignEt.setText(mUserSign) }
    }

    private fun chooseIv(){
        popupWindow.show(view)
    }

    override fun injectComponent() {
        DaggerUserComponent.builder()
            .activityComponent(activityComponent)
            .userModule(UserModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    /**
     * 拍照
     * */
    override fun onTakePhotoCallBack() {
        val hasPermission = checkPermission(PERMISSION_TAKE_PHOTO)
        if(hasPermission){
            //有权限 直接打开相机拍照
            takePhoto()
        }else{
            //无权限 动态申请相机相关权限
            ActivityCompat.requestPermissions(this@UserInfoActivity, PERMISSION_TAKE_PHOTO, REQUEST_CODE_APPLY_CAMERA_PERMISSION)
        }
    }

    private fun takePhoto() {
        popupWindow.dismiss()

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val cameraFilePath = externalCacheDir
            .toString() + "/" + System.currentTimeMillis() + ".jpg"
        val outputImage = File(cameraFilePath)
        if (!outputImage.exists()) {
            try {
                outputImage.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        var imageUri: Uri? = null
        imageUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                this,
                this.packageName.toString() + ".fileprovider",
                outputImage
            )
        } else {
            Uri.fromFile(outputImage)
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(
            intent,
            REQUEST_CODE_APPLY_CAMERA_PERMISSION
        )
    }

    /**
     * 从相册中选取
     * */
    override fun onAlbumCallBack() {
        val hasPermission = checkPermission(PERMISSION_PHOTO_ALBUM)
        if(hasPermission){
            //有权限 直接打开相册
            openAlbum()
        }else{
            //无权限 动态申请存储卡权限
            ActivityCompat.requestPermissions(this@UserInfoActivity, PERMISSION_PHOTO_ALBUM, REQUEST_CODE_APPLY_ALBUM_PERMISSION)
        }
    }

    private fun openAlbum(){

        popupWindow.dismiss()

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_APPLY_ALBUM_PERMISSION)
    }

    private fun checkPermission(permissions: Array<String>): Boolean{
        for (permission in permissions){
            if (ActivityCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE_APPLY_ALBUM_PERMISSION->{
                var result = data?.data
                val path: String = AppUtil.getRealPathFromUriAboveApi19(this@UserInfoActivity, result!!)
                val file = File(path)
                if (file.length() > 2 * 1024 * 1024) {
                    var bitmap: Bitmap? = null
                    try {
                        val options = BitmapFactory.Options()
                        options.inSampleSize = 2
                        bitmap = BitmapFactory.decodeStream(
                            contentResolver.openInputStream(result!!),
                            null,
                            options
                        )
                        val compressFile: File = AppUtil.getFile(this@UserInfoActivity, bitmap!!)
                        result = Uri.fromFile(compressFile)
                        localFilePath = result.path.toString()
                        Log.e("DDDDDDD","camera-----result = $result")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }else{
                    localFilePath = path
                }
                mPresenter.getUpLoadToken()
            }
            REQUEST_CODE_APPLY_CAMERA_PERMISSION->{
                val result = data?.data
                Log.e("DDDDDDD","camera-----result = ${data==null}")
                Log.e("DDDDDDD","camera-----result = $result")
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_APPLY_ALBUM_PERMISSION ->{
                var b = true
                for(i in grantResults){
                    if(i != PackageManager.PERMISSION_GRANTED){
                        b = false
                        break
                    }
                }
                if(b){
                    openAlbum()
                }else{
                    Toast.makeText(this, "权限被拒绝",Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_CODE_APPLY_CAMERA_PERMISSION ->{
                var b = true
                for(i in grantResults){
                    if(i != PackageManager.PERMISSION_GRANTED){
                        b = false
                        break
                    }
                }
                if(b){
                    takePhoto()
                }else{
                    Toast.makeText(this, "权限被拒绝",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onGetUpLoadTokenResult(result: String) {
        Log.e("DDDDDD", "七牛云TOKEN = $result");
        upLoadManager.put(localFilePath, null, result,
            { key, info, response ->

                val refix = response?.opt("hash")
                if(refix == null){
                    remoteImgUrl = "https://img12.360buyimg.com/n7/jfs/t5878/352/2479795637/201591/d23a1872/5931182fN31cfa389.jpg"
                }else{
                    remoteImgUrl =  BaseConstant.IMAGE_SERVER_ADDRESS + response?.opt("hash")
                }
                Log.e("DDDDDD","-----remoteImgUrl = $remoteImgUrl")
                runOnUiThread {
                    GlideUtils.loadImage(
                        this@UserInfoActivity,
                        remoteImgUrl,
                        mUserIconIv
                    )
                }

            },null)
    }

    override fun onEditUserResult(result: UserInfo) {
        //保存用户信息
        UserPrefsUtils.putUserInfo(result)
        Toast.makeText(this, "保存成功",Toast.LENGTH_SHORT).show()
    }
}