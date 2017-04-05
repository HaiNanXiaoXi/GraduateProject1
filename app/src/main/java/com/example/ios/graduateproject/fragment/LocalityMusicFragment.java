package com.example.ios.graduateproject.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.ios.graduateproject.R;
import com.example.ios.graduateproject.adapter.LocalityMusicRecyclerviewAdapter;
import com.example.ios.graduateproject.bean.Mp3Info;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ios on 2017/3/29.
 */
public class LocalityMusicFragment extends BaseFragment {

    @BindView(R.id.locality_music_RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.locality_music_SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Handler handler = new Handler();
    boolean isLoading;
    private List<Mp3Info> data = new ArrayList<>();
    private LocalityMusicRecyclerviewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_locality_music, container, false);
        ButterKnife.bind(this, layout);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {

        data = getAlldata();

        adapter = new LocalityMusicRecyclerviewAdapter(data, getContext());

        swipeRefreshLayout.setColorSchemeResources(R.color.blueStatus);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        getData();
                    }
                }, 1500);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });

        //添加点击事件

    }


    public void initData() {
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        getData();
                    }
                }, 1500);

    }

    /**
     * 获取数据
     */
    private void getData() {
        data = getAlldata();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyItemRemoved(adapter.getItemCount());
    }

    public List<Mp3Info> getAlldata() {
        ContentResolver cr = getActivity().getApplication().getContentResolver();
        if (cr == null) {
            return null;
        }
        // 获取所有歌曲
        Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (null == cursor) {
            return null;
        }
        Mp3Info music;
        List<Mp3Info> list = new ArrayList<Mp3Info>();
        if (cursor.moveToFirst()) {
            do {

                //歌曲名
                String title = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.TITLE));

                //歌手
                String singer = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.ARTIST));

                //专辑
                String album = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.ALBUM));

                //长度
                long size = cursor.getLong(cursor
                        .getColumnIndex(MediaStore.Audio.Media.SIZE));

                //时长
                long duration = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DURATION));

                //路径
                String url = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DATA));

                //显示的文件名
                String _display_name = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));

                //类型
                String mime_type = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
                music = new Mp3Info();
                music.setAlbum(album);
                music.setDuration(duration);
                music.setArtist(singer);
                music.setSize(size);
                music.setTitle(title);
                music.setUrl(url);
                list.add(music);
            } while (cursor.moveToNext());
        }
        return list;
    }


    private String getAlbumArt(int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = this.getActivity().getContentResolver().query(
                Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)),
                projection, null, null, null);
        String album_art = null;
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        cur = null;
        return album_art;
    }


}
