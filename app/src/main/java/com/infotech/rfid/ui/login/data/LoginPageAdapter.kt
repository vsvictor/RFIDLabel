package com.infotech.rfid.ui.login.data

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.infotech.rfid.base.BaseFragment

class LoginPageAdapter(val pages: List<BaseFragment<*, *>>, owner: Fragment):FragmentStateAdapter(owner) {
    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages.get(position)
    }
}