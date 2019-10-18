package com.kotlindemo.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.kotlindemo.MyApplication
import com.kotlindemo.R
import com.kotlindemo.base.BaseActivity
import com.kotlindemo.util.AppUtil
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.EasyPermissions
import java.util.logging.Handler


/************************************************************
 *
 *
 *                   .::::.
 *                  .::::::::.
 *                 :::::::::::  COME ON BABY
 *             ..:::::::::::'
 *           '::::::::::::'
 *             .::::::::::
 *        '::::::::::::::..
 *             ..::::::::::::.
 *           ``::::::::::::::::
 *            ::::``:::::::::'        .:::.
 *           ::::'   ':::::'       .::::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.:::::::::'      ':::::.
 *      .::'         ::::::::::::::'         ``::::.
 *  ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 *                    '.:::::'                    ':'````..
 *
 *************************************************************
 * @author: 李刘欢
 * @date：2019/10/18 9:59
 * @version:1.0.0
 * @description: SplashActivity
 */
class SplashActivity : BaseActivity() {

    private var textTypeface: Typeface? = null

    private var descTypeFace: Typeface? = null

    private var alphaAnimation: AlphaAnimation? = null

    init {
        textTypeface = Typeface.createFromAsset(MyApplication.mContext.assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(MyApplication.mContext.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun layoutId(): Int = R.layout.activity_splash

    @SuppressLint("SetTextI18n")
    override fun initView() {
        tv_app_name.typeface = textTypeface
        tv_splash_desc.typeface = descTypeFace
        tv_version_name.text = "v${AppUtil.getVerName(MyApplication.mContext)}"
        //渐变展示启动屏
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                redirectTo()
            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })
        checkPermission()
    }

    private fun redirectTo() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission() {
        val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        EasyPermissions.requestPermissions(this, "KotlinMvp应用需要以下权限，请允许", 0, *perms)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == 0) {
            if (perms.isNotEmpty()) {
                if (perms.contains(Manifest.permission.READ_PHONE_STATE) && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (alphaAnimation != null) {
                        iv_web_icon.startAnimation(alphaAnimation)
                    }
                }
            }
        }
    }
}