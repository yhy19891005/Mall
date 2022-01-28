package com.yhy.base.common

class BaseConstant {

    companion object{
        //服务器地址
        const val SERVER_HOST = "http://192.168.232.249:8080/"
        //连接超时时间
        const val TIME_OUT = 20L
        //sp文件名
        const val TABLE_PREFS = "mall_data"
        //请求成功时的错误码
        const val RESP_SUC = 0
        //七牛服务地址
        const val IMAGE_SERVER_ADDRESS = "http://osea2fxp7.bkt.clouddn.com/"
        //用户token
        const val KEY_SP_TOKEN = "token"
    }
}