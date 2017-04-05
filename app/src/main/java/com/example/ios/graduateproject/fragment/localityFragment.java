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
import com.example.ios.graduateproject.adapter.LocalityViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ios on 2017/3/27.
 */
public class LocalityFragment extends BaseFragment {

    public static String TAG = LocalityFragment.class.getSimpleName();

    @BindView(R.id.locality_tablayout)
    TabLayout localityTablayout;
    @BindView(R.id.locality_ViewPager)
    ViewPager localityViewPager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_locality, container, false);
        ButterKnife.bind(this, layout);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

    }

    private void initView() {
        LocalityViewPagerAdapter adapter = new LocalityViewPagerAdapter(getFragmentManager(), getData());
        localityViewPager.setAdapter(adapter);
        localityTablayout.setupWithViewPager(localityViewPager);
    }


    public List<Fragment> getData() {
        List<Fragment> data = new ArrayList<>();
        data.add(new LocalityMusicFragment());
        data.add(new LocalityVideoFragment());
        return data;
    }
}
