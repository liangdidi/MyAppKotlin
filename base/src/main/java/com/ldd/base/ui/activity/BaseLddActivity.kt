package com.ldd.base.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.ldd.base.util.ActivityManageUtil
import com.ldd.base.util.PermissionUtil

/**
 * 基类Activity
 * 1、简单的标题栏
 * 2、沉浸式状态栏效果，默认白色背景
 * 3、界面管理工具类ActivityManageUtil
 * 4、封装页面跳转
 * 5、Toast提示
 * 自定义：更改沉浸式状态栏颜色，自定义抽象基类，重写initSetting（）方法，调用mImmersionBar设置
 */
abstract class BaseLddActivity : AppCompatActivity() {
    var TAG: String = ""
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = this::class.java.simpleName
        mContext = this

        //设置沉浸式状态栏效果
        setImmersionBar()
        //添加Activity管理工具中
        ActivityManageUtil.addActivity(this)
    }


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



    //========================================关闭界面=============================================================================
    /**
     * 关闭界面
     */
    fun finishActivityCustom(){
        ActivityManageUtil.finishCurrentActivity()
    }

    /**
     * 返回键
     */
    override fun onBackPressed() {
        Log.i(TAG, "=====onBackPressed=====")
        finishActivityCustom()
    }

    //========================================跳转界面=============================================================================

    /**
     * 打开新页面 并关闭当前页面
     */
    fun startActivityAndFinish(mClass: Class<*>) {
        startActivityCustom(mClass)
        finishActivityCustom()
    }

    /**
     * 跳转到新的页面
     */
    fun startActivityCustom(mClass: Class<*>){
        startActivityCustom(mClass,null)
    }

    var mIntent:Intent?=null
    /**
     * 携带数据跳转到新的页面
     */
    fun startActivityCustom(mClass: Class<*>,bundle:Bundle?){
        if(null==mIntent){
            mIntent=Intent()
        }
        mIntent?.setClass(mContext,mClass)
        bundle?.let { mIntent?.putExtras(it) }
        startActivity(mIntent)
    }
    /**
     * 携带数据跳转到新的页面，并返回结果
     */
    fun startActivityCustom(mClass: Class<*>,bundle:Bundle?,requestCode:Int){
        if(null==mIntent){
            mIntent=Intent()
        }
        mIntent?.setClass(mContext,mClass)
        bundle?.let { mIntent?.putExtras(it) }
        startActivityForResult(mIntent,requestCode)
    }

    //========================================toast弹框=============================================================================
    private var toast:Toast?=null

    /**
     * Toast 弹框，可以传任意类型
     */
    @SuppressLint("ShowToast")
    fun showToast(any:Any){
        runOnUiThread {
            if (null==toast) {
                toast = Toast.makeText(mContext, null, Toast.LENGTH_SHORT)
            }
            toast?.let {
                it.setText(any.toString())
                it.show()
            }
        }
    }

    //========================================权限请求=============================================================================

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtil.onRequestPermissionsResult(requestCode,
            permissions,grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        PermissionUtil.onActivityResult(requestCode)
    }

}