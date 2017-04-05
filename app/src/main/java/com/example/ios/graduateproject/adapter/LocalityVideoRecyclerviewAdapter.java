package com.example.ios.graduateproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ios.graduateproject.R;
import com.example.ios.graduateproject.bean.Mp3Info;
import com.example.ios.graduateproject.bean.VideoInfo;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ios on 2017/3/28.
 */
public class LocalityVideoRecyclerviewAdapter extends RecyclerView.Adapter<LocalityVideoRecyclerviewAdapter.ViewHolder> {


    private static final String TAG = LocalityVideoRecyclerviewAdapter.class.getSimpleName();
    private Context context;
    private List<VideoInfo> data;
    private LayoutInflater layoutInflater;

    public LocalityVideoRecyclerviewAdapter(List<VideoInfo> data, Context context) {
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }

        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = layoutInflater.inflate(R.layout.fragment_locality_video_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: " + data.get(position).getTitle());
        Log.e(TAG, "onBindViewHolder: " + data.get(position).getDuration());

        holder.fragmentLocalityItemDuration.setText(setTime(data.get(position).getDuration()));
        holder.fragmentLocalityItemSingname.setText(bytes2kb(data.get(position).getSize()));

        holder.fragmentLocalityItemSongname.setText(data.get(position).getTitle());

        File file = new File(data.get(position).getUri_thumb());
        if (file.exists() && file.isFile()) {
            holder.fragmentLocalityItemImage.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
        } else {
            holder.fragmentLocalityItemImage.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.video));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fragmentLocalityItemImage;
        TextView fragmentLocalityItemSongname;
        TextView fragmentLocalityItemSingname;
        TextView fragmentLocalityItemDuration;


        public ViewHolder(View itemView) {
            super(itemView);
            fragmentLocalityItemImage = (ImageView) itemView.findViewById(R.id.fragment_locality_video_item_image);
            fragmentLocalityItemSongname = (TextView) itemView.findViewById(R.id.fragment_locality_video_item_songname);
            fragmentLocalityItemSingname = (TextView) itemView.findViewById(R.id.fragment_locality_video_item_singname);
            fragmentLocalityItemDuration = (TextView) itemView.findViewById(R.id.fragment_locality_video_item_duration);
        }
    }

    //设置时间
    public static String setTime(long total) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String format = simpleDateFormat.format(total);
        return format;
    }

    /**
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节)
     *
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        if (returnValue > 1)
            return (returnValue + "MB");
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();
        return (returnValue + "KB");
    }


}
