package com.example.ios.graduateproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ios.graduateproject.R;
import com.example.ios.graduateproject.adapter.MusicViewPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ios on 2017/3/27.
 */
public class MusicFragment extends BaseFragment {

    public static String TAG = MusicFragment.class.getSimpleName();
    @BindView(R.id.music_tablayout)
    TabLayout musicTablayout;
    @BindView(R.id.music_ViewPager)
    ViewPager musicViewPager;
    private MusicViewPagerAdapter pagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_music, container, false);
        ButterKnife.bind(this, layout);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

    }

    private void initView() {
        /*pagerAdapter = new MusicViewPagerAdapter(getFragmentManager(), getData());
        musicViewPager.setAdapter(pagerAdapter);
        musicTablayout.setupWithViewPager(musicViewPager);*/
    }

    public List<Fragment> getData() {


        return null;
    }
}
