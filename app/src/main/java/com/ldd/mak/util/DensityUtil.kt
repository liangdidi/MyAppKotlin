package com.ldd.mak.util

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.util.DisplayMetrics

/**
 * 修改系统的屏幕像素比来实现屏幕的适配效果
 */
object DensityUtil {
    //屏幕的参考宽度值，单位是dp
    private const val STAND_WIDTH = 960f

    //系统原来的像素缩放比
    private var appDensity = 0f
    //字体缩放比
    private var appScaleDensity = 0f

    fun setDensity(application: Application, activity: Activity) {
        val appMetrics: DisplayMetrics = application.resources.displayMetrics
        if (appDensity == 0f) {
            //获取系统本来的像素缩放比和字体缩放比
            appDensity = appMetrics.density
            appScaleDensity = appMetrics.scaledDensity
        }
        application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                //代表字体大小进行了更改，需要对字体缩放比进行重新赋值
                if (newConfig.fontScale > 0) {
                    appScaleDensity =application.resources.displayMetrics.scaledDensity
                }
            }

            override fun onLowMemory() {}
        })

        //通过实际的屏幕大小来计算新的屏幕像素缩放比
        val targetDensity = appMetrics.widthPixels / STAND_WIDTH
        val targetScaleDensity = targetDensity * (appScaleDensity / appDensity)
        val targetDensityDpi = targetDensity * 160

        //将获取到的新的缩放比设置回去
        val actMetrics = activity.resources.displayMetrics
        actMetrics.density = targetDensity
        actMetrics.densityDpi = targetDensityDpi.toInt()
        actMetrics.scaledDensity = targetScaleDensity
    }

}
