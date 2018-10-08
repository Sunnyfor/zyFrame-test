package com.shoumei.xhn.utils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.shoumei.xhn.base.BaseActivity

/**
 * 权限工具类
 * Created by zhangye on 2018/4/16.
 */
class PermissionUtil(private var activity: BaseActivity?) {

    private var messageMap = hashMapOf<String, String>()
    private var permissions = kotlin.arrayOf<String>()

    init {
        messageMap[Manifest.permission.CAMERA] = "相机权限"
        messageMap[Manifest.permission.READ_EXTERNAL_STORAGE] = "存储卡读取权限"
        messageMap[Manifest.permission.WRITE_EXTERNAL_STORAGE] = "存储卡写入权限"
    }

    private var missPermission = ""

    /**
     *  检查所有权限是否拥有
     */

    fun checkPermission(array: Array<String>): Boolean {
        array.forEach {
            val permission = it
            activity?.let {
                if (ContextCompat.checkSelfPermission(it, permission) != PackageManager.PERMISSION_GRANTED) {
                    missPermission = permission
                    return false
                }
            }

        }
        return true
    }


    /**
     * 判断是否为6.0以上系统
     */
    private fun isPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true
        }
        return false
    }


    /**
     * 开启相机权限
     * false时需要在Activity权限回调中处理
     */
    fun cameraPermission(): Boolean {
        permissions = arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return requestPermission(IntentCode.CAMERA)
    }


    /**
     * 内存卡读写权限
     * false时需要在Activity权限回调中处理
     */
    fun storagePermission(): Boolean {
        permissions = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return requestPermission(IntentCode.STORAGE)
    }


    /**
     * 拨打电话权限
     */
    fun callPermission(): Boolean {
        permissions = arrayOf(
                Manifest.permission.CALL_PHONE)
        return requestPermission(IntentCode.PHONE)
    }


    /**
     * 定位权限
     */
    fun locationPermission(): Boolean {
        permissions = arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)
        return requestPermission(IntentCode.LOCATION)
    }


    /**
     * 请求权限
     */
    private fun requestPermission(code: Int): Boolean {
        if (!isPermission()) {
            return true
        }
        if (checkPermission(permissions)) {
            return true
        } else {
            activity?.let {
                ActivityCompat.requestPermissions(it, permissions, code)
            }
        }
        return false
    }


    fun onDestroy() {
        activity = null
    }
}