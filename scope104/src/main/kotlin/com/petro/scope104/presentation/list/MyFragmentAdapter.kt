package com.petro.scope104.presentation.list

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyFragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(
    fm!!
) {
    override fun getItem(i: Int): Fragment {
        return WorkerListFragment.Companion.newInstance(ListType.values()[i])
    }

    override fun getCount(): Int {
        return ListType.values().size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return ListType.values()[position].name
    }
}