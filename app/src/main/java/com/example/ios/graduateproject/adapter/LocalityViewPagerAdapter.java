package com.example.ios.graduateproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ios on 2017/3/27.
 */
public class LocalityViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    private List<String> Strings;

    public LocalityViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }

        Strings = new ArrayList<>();
        Strings.add("音乐");
        Strings.add("视频");
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Strings.get(position);
    }
}
