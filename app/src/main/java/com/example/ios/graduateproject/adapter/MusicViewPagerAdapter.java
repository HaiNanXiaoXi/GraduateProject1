package com.example.ios.graduateproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ios on 2017/3/27.
 */
public class MusicViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;

    private List<String> Strings;

    public MusicViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
        Strings = new ArrayList<>();
        Strings.add("歌手");
        Strings.add("榜单");
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
