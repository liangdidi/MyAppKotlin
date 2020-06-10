package com.ldd.base.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.ldd.base.R
import kotlinx.android.synthetic.main.ac_base.*
import kotlinx.android.synthetic.main.l_default_title_bar.*

/**
 * 基类Activity
 * 1、简单的标题栏
 * 2、沉浸式状态栏效果，默认白色背景
 * 3、
 *
 * 自定义：使用自定义的标题栏，自定义抽象基类，重写getTitleBarId（）方法
 * 自定义：更改沉浸式状态栏颜色，自定义抽象基类，重写initSetting（）方法，调用mImmersionBar设置
 */
abstract class BaseLddActivity : AppCompatActivity() {
    var TAG: String = ""
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_base)
        TAG = this.localClassName
        mContext = this

        //是否想要标题
        if (isWantTitleBar()) {
            //如果标题未设置，使用默认的
            if (0 == getTitleBarId())
                View.inflate(this, R.layout.l_default_title_bar, llBaseTitleBar)
            else
                View.inflate(this, getTitleBarId(), llBaseTitleBar)
        }

        //如果内容未设置，使用默认的
        if (0 == getLayoutId())
            View.inflate(this, R.layout.l_default_content, llBaseContent)
        else
            View.inflate(this, getLayoutId(), llBaseContent)

        //设置沉浸式状态栏效果
        setImmersionBar()
        //初始化设置
        initSetting()
        //初始化数据
        initData()
    }

    //========================================子类实现的方法=============================================================================
    /**是否想要公共标题栏 */
    protected abstract fun isWantTitleBar(): Boolean
    /**自定义的标题栏 */
    protected abstract fun getTitleBarId(): Int
    /**设置布局文件的ID */
    protected abstract fun getLayoutId(): Int
    /**初始化数据 */
    protected abstract fun initData()

    /**
     * 初始化设置
     * 重写方法，自定义设置 如：锁定屏幕、更改沉浸式状态栏
     * */
    protected open fun initSetting() {}


    //========================================状态栏沉浸式效果=============================================================================
    /**沉浸式状态栏 */
    lateinit var mImmersionBar: ImmersionBar

    /**
     * 设置沉浸式状态栏效果
     */
    private fun setImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
            .fitsSystemWindows(true) //解决布局与状态栏重叠问题
            .statusBarColor(android.R.color.white) //状态栏为白色
            .statusBarDarkFont(true) //状态栏字体深色
            .keyboardEnable(true)
        mImmersionBar.init()
    }


    /**
     * 设置标题名称
     */
    fun setTitleBarName(name:String){
        tvDefaultTitleBarTitle.text=name
    }



}