package com.coco_sh.shmstore.dialog

import android.Manifest
import android.app.Activity
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.Window
import com.coco_sh.shmstore.R
import com.coco_sh.shmstore.http.Constant
import com.coco_sh.shmstore.login.view.LoginActivity
import kotlinx.android.synthetic.main.dialog_default.*

/**
 * 首媒默认样式的对话框
 * Created by zhangye on 2018/1/27.
 */
class SmediaDialog(private var activity: Activity) : Dialog(activity), View.OnClickListener {
    var OnClickListener: View.OnClickListener? = null
    var cancelOnClickListener: View.OnClickListener? = null
    private var cancleIsFinish = false

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_default)
        tvCancel.setOnClickListener(this)
        tvSure.setOnClickListener(this)
        setCanceledOnTouchOutside(false)
        window.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onClick(v: View) {
        when (v.id) {
            tvCancel.id -> {
                cancelOnClickListener?.onClick(v)
                dismiss()
            }
            tvSure.id -> {
                OnClickListener?.onClick(v)
                dismiss()
            }
        }
    }

    override fun setTitle(title: CharSequence?) {
        tvTitle.text = title
    }

    fun setDesc(desc: String) {
        tvDesc.visibility = View.VISIBLE
        tvDesc.text = desc
    }

    fun setDescColor(color: Int) {
        tvDesc.setTextColor(color)
    }


    fun setPositiveText(posiText: String) {
        tvSure.text = posiText
    }

    fun setCancelText(posiText: String) {
        tvCancel.text = posiText
    }

    fun setPostion(position: Int) {
        tvSure.tag = position
    }

    fun singleButton() {
        tvCancel.visibility = View.GONE
        ivLine.visibility = View.GONE
    }


    /**
     * 短信修改密码确认弹窗
     */
    fun showSmsMotifyPassword(password: String, OnClickListener: View.OnClickListener) {
        setTitle(activity.resources.getString(R.string.surePass))
        setDesc(password)
        this.OnClickListener = OnClickListener
        show()
    }

    /**
     * 短信修改密码确认弹窗
     */
    fun showCallPhone(OnClickListener: View.OnClickListener) {
        setTitle("首媒客服：${Constant.SMEDIA_PHONE}")
        setPositiveText("拨打")
        this.OnClickListener = OnClickListener
        show()
    }



    //跳转登录弹窗
    fun showLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
        } else {
            activity.startActivity(intent)
        }
//        setTitle("前往登录")
//        setPositiveText("去登录")
//        OnClickListener = View.OnClickListener {
//            val intent = Intent(baseActivity, LoginActivity::class.java)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                baseActivity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(baseActivity).toBundle())
//            } else {
//                baseActivity.startActivity(intent)
//            }
//        }
//        show()
        dismiss()
    }


    //取消按钮销毁页面
    fun cancleFinish() {
        cancleIsFinish = true
    }


    //跳转完善档案
    fun showArchive() {
        setTitle("请完善档案")
        setPositiveText("现在前往")
        setCancelText("稍后")
//        OnClickListener = View.OnClickListener {
//            val intent = Intent(activity, ArchiveActivity::class.java)
//            activity.startActivity(intent)
//        }
        show()
    }

    //显示定位失败弹窗
    fun showLocationError() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            setTitle("未获取到定位信息，等待定位成功后刷新")
        } else {
            setTitle("请检查定位权限")
        }
        show()
    }

}
