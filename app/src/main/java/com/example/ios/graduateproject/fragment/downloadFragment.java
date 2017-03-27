package com.example.ios.graduateproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ios.graduateproject.R;

/**
 * Created by ios on 2017/3/27.
 */
public class DownloadFragment extends BaseFragment {

    public static String TAG = DownloadFragment.class.getSimpleName();
    ;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_download, container, false);

        return layout;
    }
}
