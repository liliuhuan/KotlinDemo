package com.kotlindemo.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.kotlindemo.R
import com.kotlindemo.base.BaseActivity
import com.kotlindemo.mode.TabEntity
import com.kotlindemo.showToast
import com.kotlindemo.ui.fragment.DiscoveryFragment
import com.kotlindemo.ui.fragment.HomeFragment
import com.kotlindemo.ui.fragment.HotFragment
import com.kotlindemo.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnTabSelectListener {
    companion object {
        private const val CURRENT_INDEX = "current_index"
    }

    private val mTitles = arrayOf("每日精选", "发现", "热门", "我的")

    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(
        R.mipmap.ic_home_normal,
        R.mipmap.ic_discovery_normal,
        R.mipmap.ic_hot_normal,
        R.mipmap.ic_mine_normal
    )
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(
        R.mipmap.ic_home_selected,
        R.mipmap.ic_discovery_selected,
        R.mipmap.ic_hot_selected,
        R.mipmap.ic_mine_selected
    )

    private val mTabEntities = ArrayList<CustomTabEntity>()

    private var mHomeFragment: HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mMineFragment: MineFragment? = null

    //默认为0
    private var mIndex = 0

    private var mExitTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt(CURRENT_INDEX)
        }
        super.onCreate(savedInstanceState)
    }

    override fun layoutId(): Int = R.layout.activity_main

    override fun initView() {
        initTab()
        mTabLayout.currentTab = mIndex
        switchFragmentIndex(mIndex)
    }

    private fun switchFragmentIndex(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> mHomeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance(mTitles[position]).let {
                mHomeFragment = it
                transaction.add(R.id.mFlContainer, it, "home")
            }
            1 -> mDiscoveryFragment?.let {
                transaction.show(it)
            } ?: DiscoveryFragment.getInstance(mTitles[position]).let {
                mDiscoveryFragment = it
                transaction.add(R.id.mFlContainer, it, "discovery")
            }
            2 -> mHotFragment?.let {
                transaction.show(it)
            } ?: HotFragment.getInstance(mTitles[position]).let {
                mHotFragment = it
                transaction.add(R.id.mFlContainer, it, "home")
            }
            3 -> mMineFragment?.let {
                transaction.show(it)
            } ?: MineFragment.getInstance(mTitles[position]).let {
                mMineFragment = it
                transaction.add(R.id.mFlContainer, it, "home")
            }
        }
        this.mIndex = position
        mTabLayout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    private fun initTab() {
        val mapTo = (0 until mTitles.size).mapTo(mTabEntities) {
            TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it])
        }

        mTabLayout.setTabData(mTabEntities)

        mTabLayout.setOnTabSelectListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (mTabLayout != null) {
            outState.putInt(Companion.CURRENT_INDEX, mIndex)
        }
    }


    override fun onTabSelect(position: Int) {
        switchFragmentIndex(position)
    }

    override fun onTabReselect(position: Int) {
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mDiscoveryFragment?.let { transaction.hide(it) }
        mHotFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}
