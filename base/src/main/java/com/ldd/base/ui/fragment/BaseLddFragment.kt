package com.ldd.base.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ldd.base.R

/**
 * 基本的Fragment
 * 1、封装页面跳转
 * 2、Toast提示
 */
abstract class BaseLddFragment : Fragment() {
    var TAG: String = ""
    lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        TAG = this::class.java.simpleName
        mContext= activity!!

        val resource:Int = if (0 == getLayoutId()){
            R.layout.l_default_fg_content
        }else{
            getLayoutId()
        }
        return inflater.inflate(resource,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG,"=============onActivityCreated============")
        initSetting()
        onCreateViewFinish()
    }


    /**是否初始化**/
    private var isInit = false
    override fun onResume() {
        super.onResume()
        Log.i(TAG,"=============onResume============")
        //没有初始化，开始初始化数据
        if (!isInit) {
            initData()
            isInit = true
        }
        refreshData()
    }


    //========================================子类实现的方法=============================================================================
    /**设置布局文件的ID */
    protected abstract fun getLayoutId(): Int
    /**onCreateView完成 */
    protected open fun onCreateViewFinish() {}
    /**初始化数据 */
    protected abstract fun initData()
    /**刷新数据 */
    protected open fun refreshData() {}
    /**初始化自定义的设置 */
    protected open fun initSetting() {}


    //========================================跳转界面=============================================================================

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
            mIntent= Intent()
        }
        mIntent?.setClass(mContext,mClass)
        //使用replaceExtras，而不使用putExtras，
        //防止bundle存旧数据，场景：列表界面，新增、修改，传id
        bundle?.let { mIntent?.replaceExtras(it) }
        startActivity(mIntent)
    }
    /**
     * 携带数据跳转到新的页面，并返回结果
     */
    fun startActivityCustom(mClass: Class<*>,bundle:Bundle?,requestCode:Int){
        if(null==mIntent){
            mIntent= Intent()
        }
        mIntent?.setClass(mContext,mClass)
        bundle?.let { mIntent?.replaceExtras(it) }
        startActivityForResult(mIntent,requestCode)
    }

    //========================================toast弹框=============================================================================
    private var toast: Toast?=null

    /**
     * Toast 弹框，可以传任意类型
     */
    @SuppressLint("ShowToast")
    fun showToast(any:Any){
        activity?.runOnUiThread {
            if (toast == null) {
                toast = Toast.makeText(mContext, null, Toast.LENGTH_SHORT)
            }
            toast?.let {
                it.setText(any.toString())
                it.show()
            }
        }
    }


}