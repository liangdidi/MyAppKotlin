package com.ldd.base.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import java.util.*

object PermissionUtil {

    private lateinit var mActivity: Activity
    /**权限集合 */
    private val listRequestPermission: MutableList<String> =ArrayList()

    /**请求权限码 */
    private const val REQUEST_PERMISSION = 111
    /**请求打开系统设置码 */
    private const val REQUEST_OPEN_SETTING = 222

    /**是否拒绝后也可以进入 */
    private var isDeniedEnter = false
    /**是否显示取消按钮 */
    private var isShowCancel = true
    /**权限检测回调 */
    private var permissionCheckCallBack: PermissionCheckCallBack? = null
    /**权限数组 */
    private var permissions: Array<out String>? = null


    /**
     * 重置参数
     */
    fun init(): PermissionUtil {
        isDeniedEnter = false
        isShowCancel = true
        permissionCheckCallBack = null
        permissions = null
        return this
    }

    /**
     * 拒绝后也可以进入
     * 默认 false
     */
    fun isDeniedEnter(isDeniedEnter: Boolean): PermissionUtil {
        this.isDeniedEnter = isDeniedEnter
        return this
    }

    /**
     * 是否显示取消按钮
     * 默认 true
     */
    fun isShowCancel(isShowCancel: Boolean): PermissionUtil {
        this.isShowCancel = isShowCancel
        return this
    }

    /**
     * 权限检测回调
     */
    fun setCallBack(callBack: PermissionCheckCallBack): PermissionUtil {
        permissionCheckCallBack = callBack
        return this
    }

    /**
     * 权限数组
     */
    fun setPermissions(vararg permissions: String): PermissionUtil {
        this.permissions = permissions
        return this
    }


    /**拒绝的权限，并勾选禁止后不再提示 列表 */
    private val listDeniedNoHintPermissions: MutableList<String> =ArrayList()

    /**
     * 权限请求
     */
    fun requestPermissions(activity: Activity) {
        mActivity = activity
        if (permissionCheckCallBack == null) {
            Toast.makeText(mActivity, "权限回调为空", Toast.LENGTH_SHORT).show()
            return
        }
        //6.0以下不检测，直接进入
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            permissionCheckCallBack!!.onEnterNextStep()
            return
        }
        if (permissions == null || permissions!!.isEmpty()) {
            Toast.makeText(mActivity, "请求权限为空", Toast.LENGTH_SHORT).show()
            return
        }
        listRequestPermission.clear()
        listDeniedNoHintPermissions.clear()
        for (i in permissions!!.indices) {
            //未通过的权限，记录，去请求
            if (mActivity.checkSelfPermission(permissions!![i]) == PackageManager.PERMISSION_DENIED) {
                listRequestPermission.add(permissions!![i])

                //拒绝的权限，并勾选禁止后不再提示
                if (!mActivity.shouldShowRequestPermissionRationale(permissions!![i])) {
                    listDeniedNoHintPermissions.add(permissions!![i])
                }
            }
        }
        if (listRequestPermission.size == 0) {
            permissionCheckCallBack!!.onEnterNextStep()
            return
        }
        val newPermissions =
            listRequestPermission.toTypedArray()
        mActivity.requestPermissions(newPermissions, REQUEST_PERMISSION)
    }


    /**
     * 权限申请回调，必须在Activity,onRequestPermissionsResult中，调用此方法
     */
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSION) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return
            }
            var grantCount = 0
            for (i in permissions.indices) {
                Log.i(
                    "ldd", "权限：" + getPermissionHint(permissions[i])
                            + "，是否允许：" + (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                            + "，是否勾选禁止后不再提示：" + mActivity.shouldShowRequestPermissionRationale(permissions[i])
                )
                //检查权限
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    grantCount++
                } else {
                }
            }

            //全部允许，返回成功
            if (grantCount == listRequestPermission.size) {
                permissionCheckCallBack!!.onEnterNextStep()
            } else {
                if (isDeniedEnter) {
                    permissionCheckCallBack!!.onEnterNextStep()
                } else {
                    for (i in listDeniedNoHintPermissions.indices) {
                        showToSettingDialog(listDeniedNoHintPermissions[i])
                        break
                    }
                }
            }
        }
    }

    /**
     * 去设置界面，操作权限回调，必须在Activity,onActivityResult，调用此方法
     */
    fun onActivityResult(requestCode: Int) {
        if (requestCode == REQUEST_OPEN_SETTING) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return
            }
            requestPermissions(mActivity)
        }
    }


    /**
     * 当用户选择了拒绝并不再提示，显示自定义提示对话框
     * @param permission 权限名称
     */
    private fun showToSettingDialog(permission: String) {

        AlertDialog.Builder(mActivity).apply {
            setTitle("权限申请")
            setMessage("需要 \"${getPermissionHint(permission)}\" 权限，" + "请在权限设置中打开 \"${getPermissionHint(permission)}\" 权限")
            setPositiveButton("立即开启") { dialog1: DialogInterface?, which: Int ->
                // 跳转到应用设置界面
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri =Uri.fromParts("package", mActivity.packageName, null)
                intent.data = uri
                mActivity.startActivityForResult(intent, REQUEST_OPEN_SETTING)
            }
            setCancelable(false)
            //根据设置是否显示取消按钮
            if (isShowCancel) {
                setNegativeButton("取消") { dialog12: DialogInterface, which: Int ->
                    if (isDeniedEnter) {
                        permissionCheckCallBack!!.onEnterNextStep()
                    }
                    dialog12.dismiss()
                }
            }
            show()
        }
    }


    /**
     * 根据权限，获取对应的提示
     */
    private fun getPermissionHint(permission: String?): String {
        var hint = ""
        when (permission) {
            Manifest.permission.CAMERA -> hint = "相机"
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> hint = "存储"
            Manifest.permission.WRITE_CONTACTS -> hint = "通讯录"
            Manifest.permission.RECORD_AUDIO -> hint = "麦克风"
            Manifest.permission.ACCESS_FINE_LOCATION -> hint = "位置信息"
        }
        return hint
    }


    interface PermissionCheckCallBack {
        /**
         * 进入
         * 1、权限申请成功进入
         * 2、设置拒绝也可以进入。
         */
        fun onEnterNextStep()
    }


}