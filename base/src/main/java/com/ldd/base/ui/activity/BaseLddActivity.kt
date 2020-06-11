package com.ldd.base.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.ldd.base.R
import com.ldd.base.util.ActivityManageUtil
import com.ldd.base.util.PermissionUtil
import kotlinx.android.synthetic.main.ac_base.*
import kotlinx.android.synthetic.main.l_default_title_bar.*

/**
 * 基类Activity
 * 1、简单的标题栏
 * 2、沉浸式状态栏效果，默认白色背景
 * 3、界面管理工具类ActivityManageUtil
 * 4、封装页面跳转
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
        TAG = this::class.java.simpleName
        mContext = this
        ActivityManageUtil.addActivity(this)

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
        tvDefaultTitleBarTitle?.text=name
    }


    //========================================关闭界面=============================================================================
    private var firstTime: Long = 0
    /**
     * 标题返回键 点击事件
     */
    fun onFinishActivity(view: View) {
        Log.i(TAG, "=====onFinishActivity=====")
        //=====================防止快速点击左上角返回，关闭上个页面=======================
        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime < 800) {
            return
        }
        firstTime = secondTime
        //=====================防止快速点击左上角返回，关闭上个页面=======================
        finishActivityCustom()
    }

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
    /**
     * 携带数据跳转到新的页面
     */
    fun startActivityCustom(mClass: Class<*>,bundle:Bundle?){
        val mIntent=Intent(mContext,mClass)
        bundle?.let { mIntent.putExtras(it) }
        startActivity(mIntent)
    }
    /**
     * 携带数据跳转到新的页面，并返回结果
     */
    fun startActivityCustom(mClass: Class<*>,bundle:Bundle?,requestCode:Int){
        val mIntent=Intent(mContext,mClass)
        bundle?.let { mIntent.putExtras(it) }
        startActivityForResult(mIntent,requestCode)
    }

    //========================================toast弹框=============================================================================
    private var toast:Toast?=null

    /**
     * Toast 弹框，可以传任意类型
     */
    fun showToast(any:Any){
        runOnUiThread {
            if (toast == null) {
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