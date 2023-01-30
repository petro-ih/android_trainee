package com.petro.scope104;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentAdapter extends FragmentPagerAdapter {
    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return WorkerListFragment.newInstance(ListType.values()[i]);
    }

    @Override
    public int getCount() {
        return ListType.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ListType.values()[position].name();
    }
}