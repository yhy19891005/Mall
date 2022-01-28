package com.yhy.base.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*
import kotlin.system.exitProcess

class AppManager private constructor(){

    private val activityStack = Stack<Activity>()

    companion object{
        val INSTANCE by lazy { AppManager() }
    }

    /**
     * 入栈
     * */
    fun addActivity(activity: Activity){
        activityStack.add(activity)
    }

    /**
     * 出栈
     * */
    fun finishActivity(activity: Activity){
        activityStack.remove(activity)
    }

    /**
     * 获取栈顶Activity
     * */
    fun currentActivity(): Activity = activityStack.lastElement()

    /**
     * 清空栈
     * */
    fun finishAllActivity(){
        for (act in activityStack){
            act.finish()
        }
        activityStack.clear()
    }

    /**
     * 退出应用
     * */
    fun exitApp(context: Context){
        finishAllActivity()
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        manager.killBackgroundProcesses(context.packageName)
        exitProcess(0)
    }
}