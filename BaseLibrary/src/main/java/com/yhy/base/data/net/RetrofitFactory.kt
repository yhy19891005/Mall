package com.yhy.base.data.net

import com.yhy.base.common.BaseConstant
import com.yhy.base.utils.AppPrefsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory private constructor(){

    companion object{
        val INSTANCE: RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val retrofit: Retrofit

    //添加请求头
    private val interceptor: Interceptor = Interceptor{
        chain ->
      val request = chain.request()
            .newBuilder()
            .addHeader("Content-type","application/json")
            .addHeader("Charset","utf-8")
            .addHeader("token",AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN)?:"")
            .build()
        chain.proceed(request)
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BaseConstant.SERVER_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(initClient())
            .build()
    }

    //初始化OkHttpClient
    private fun initClient(): OkHttpClient {
       return OkHttpClient.Builder()
           .addInterceptor(interceptor)
           .addInterceptor(initLogInterceptor())
           .connectTimeout(BaseConstant.TIME_OUT,TimeUnit.SECONDS)
           .readTimeout(BaseConstant.TIME_OUT,TimeUnit.SECONDS)
           .build()
    }

    //初始化日志拦截器
    private fun initLogInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun <T> create(service: Class<T>): T = retrofit.create(service)
}