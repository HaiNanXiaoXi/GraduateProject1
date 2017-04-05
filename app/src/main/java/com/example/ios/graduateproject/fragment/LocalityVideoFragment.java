package com.example.ios.graduateproject.fragment;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
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

import com.example.ios.graduateproject.MainActivity;
import com.example.ios.graduateproject.R;
import com.example.ios.graduateproject.adapter.LocalityMusicRecyclerviewAdapter;
import com.example.ios.graduateproject.adapter.LocalityVideoRecyclerviewAdapter;
import com.example.ios.graduateproject.bean.Mp3Info;
import com.example.ios.graduateproject.bean.VideoInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ios on 2017/3/29.
 */
public class LocalityVideoFragment extends BaseFragment {

    @BindView(R.id.locality_video_RecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.locality_video_SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Handler handler = new Handler();
    boolean isLoading;
    private List<VideoInfo> data = new ArrayList<>();
    private LocalityVideoRecyclerviewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_locality_video, container, false);
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

        adapter = new LocalityVideoRecyclerviewAdapter(data, getContext());

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

    public List<VideoInfo> getAlldata() {
        List<VideoInfo> data = new ArrayList<>();
        Cursor cursor = getActivity().getApplication().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    VideoInfo videoInfo = new VideoInfo();

                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)); // id
                    String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)); // 专辑
                    String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)); // 艺术家
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 显示名称
                    String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(path);
                    String duration1 = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    Log.e("xi", "getAlldata: " + duration1);
                    //long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)); // 时长
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
                    String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));

                    String[] selectionArgs = new String[]{id + ""};
                    String[] thumbColumns = new String[]{MediaStore.Video.Thumbnails.DATA,
                            MediaStore.Video.Thumbnails.VIDEO_ID};
                    String selection = MediaStore.Video.Thumbnails.VIDEO_ID + "=?";

                    String uri_thumb = "";
                    Cursor thumbCursor = (getActivity().getApplicationContext().getContentResolver()).query(
                            MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, selection, selectionArgs,
                            null);

                    if (thumbCursor != null && thumbCursor.moveToFirst()) {
                        uri_thumb = thumbCursor
                                .getString(thumbCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));

                    }

                    Log.e("xi", "getAlldata: " + uri_thumb);


                    videoInfo.setId(id);
                    videoInfo.setDisplayName(displayName);
                    videoInfo.setAlbum(album);
                    videoInfo.setArtist(artist);
                    videoInfo.setTitle(title);
                    videoInfo.setMimeType(mimeType);
                    videoInfo.setPath(path);
                    int i = Integer.parseInt(duration1);
                    videoInfo.setDuration(i);
                    videoInfo.setSize(size);
                    videoInfo.setResolution(resolution);
                    videoInfo.setUri_thumb(uri_thumb);

                    Log.e("xi", "getAlldata: " + videoInfo.toString());

                    data.add(videoInfo);
                } while (cursor.moveToNext());
            }
        }
        return data;

    }


}
