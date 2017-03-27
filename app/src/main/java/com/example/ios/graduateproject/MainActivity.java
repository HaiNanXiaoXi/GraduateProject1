package com.example.ios.graduateproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;

import com.example.ios.graduateproject.fragment.DownloadFragment;
import com.example.ios.graduateproject.fragment.LocalityFragment;
import com.example.ios.graduateproject.fragment.MusicFragment;
import com.example.ios.graduateproject.fragment.VideoFragment;

import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.main_controller)
    RadioGroup mcontrpller;

    private FragmentManager fm;
    private FragmentTransaction transaction;
    private Fragment showfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        mcontrpller.setOnCheckedChangeListener(this);

        fm = getSupportFragmentManager();

        transaction = fm.beginTransaction();

        showfragment = new MusicFragment();

        Log.i("Main", "-------->1");

        transaction.add(R.id.main_container, showfragment, MusicFragment.TAG);

        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb1:
                FragmentTransaction(MusicFragment.TAG, MusicFragment.class);
                break;
            case R.id.rb2:
                FragmentTransaction(VideoFragment.TAG, VideoFragment.class);
                break;
            case R.id.rb3:
                FragmentTransaction(DownloadFragment.TAG, DownloadFragment.class);
                break;
            case R.id.rb4:
                FragmentTransaction(LocalityFragment.TAG, LocalityFragment.class);
                break;
        }
    }

    private void FragmentTransaction(String TAG, Class<? extends Fragment> cls) {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();

        transaction.hide(showfragment);

        showfragment = this.fm.findFragmentByTag(TAG);

        if (showfragment != null) {
            transaction.show(showfragment);
        } else {
            try {
                showfragment = cls.getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            transaction.add(R.id.main_container, showfragment, TAG);

        }
        transaction.commit();
    }

}
