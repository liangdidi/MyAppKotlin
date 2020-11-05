package com.ldd.base.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
        initSetting()
        initData()
    }

    //========================================子类实现的方法=============================================================================
    /**设置布局文件的ID */
    protected abstract fun getLayoutId(): Int
    /**初始化数据 */
    protected abstract fun initData()
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
        bundle?.let { mIntent?.putExtras(it) }
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
        bundle?.let { mIntent?.putExtras(it) }
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