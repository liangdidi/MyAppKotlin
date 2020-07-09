package com.ldd.mak.ui.activity

import com.ldd.mak.R
import kotlinx.android.synthetic.main.ac_get_screen_param.*


/**
 * 获取屏幕参数
 */
class GetScreenParamActivity :BaseActivity() {
    override fun isWantTitleBar()=true

    override fun getLayoutId()=R.layout.ac_get_screen_param

    override fun initData() {
        val displayMetrics = resources.displayMetrics
        val width=displayMetrics.widthPixels
        val height=displayMetrics.heightPixels
        val density=displayMetrics.density
        val densityDpi=displayMetrics.densityDpi
        val scaledDensity=displayMetrics.scaledDensity

        tvScreenParam.text="宽：$width \n高：$height \n密度：$density \n密度DPI：$densityDpi \n缩放密度：$scaledDensity"
    }
}