package com.ldd.base.util

import android.app.Activity
import android.util.Log
import java.util.*

/**
 * Activity管理工具
 * 增、删，查当前页面
 */
object ActivityManageUtil {

    private val activityStack: Stack<Activity> = Stack<Activity>()

    /**
     * 添加
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }
    /**
     * 移除
     */
    fun removeActivity(activity: Activity) {
        activityStack.remove(activity)
    }

    /**
     * 当前页面
     */
    fun currentActivity(): Activity = activityStack.lastElement()

    /**
     * 结束当前页面
     */
    fun finishCurrentActivity() {
        finishActivity(currentActivity())
    }

    /**
     * 结束指定的页面
     */
    fun finishActivity(activity: Activity) {
        removeActivity(activity)
        activity.finish()
    }

    /**
     * 结束指定类名的页面
     */
    fun finishActivity(mClass: Class<*>) {
        activityStack.forEach {
            if(mClass == it.javaClass){
                finishActivity(it)
            }
        }
    }

    /**
     * 结束所有的页面
     */
    fun finishAllActivity() {
        activityStack.forEach {
            it.finish()
        }
        activityStack.clear()
    }

}