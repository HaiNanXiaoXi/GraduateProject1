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

import com.example.ios.graduateproject.R;
import com.example.ios.graduateproject.bean.Mp3Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ios on 2017/3/28.
 */
public class LocalityMusicRecyclerviewAdapter extends RecyclerView.Adapter<LocalityMusicRecyclerviewAdapter.ViewHolder> {


    private static final String TAG = LocalityMusicRecyclerviewAdapter.class.getSimpleName();
    private Context context;
    private List<Mp3Info> data;
    private LayoutInflater layoutInflater;

    public LocalityMusicRecyclerviewAdapter(List<Mp3Info> data, Context context) {
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
        View inflate = layoutInflater.inflate(R.layout.fragment_locality_music_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: " + data.get(position).getTitle());
        Log.e(TAG, "onBindViewHolder: " + formatTime(data.get(position).getDuration()));

        holder.fragmentLocalityItemDuration.setText(formatTime(data.get(position).getDuration()));

        int i = data.get(position).getTitle().indexOf("-");

        Log.e(TAG, "length: " + data.get(position).getTitle().length());
        Log.e(TAG, "i: " + i);

        if (i != -1) {
            holder.fragmentLocalityItemSongname.setText(data.get(position).getTitle().substring(i + 1, data.get(position).getTitle().length()));
            holder.fragmentLocalityItemSingname.setText(data.get(position).getTitle().substring(0, i));
        } else {
            holder.fragmentLocalityItemSingname.setText(data.get(position).getArtist());
            holder.fragmentLocalityItemSongname.setText(data.get(position).getTitle());
        }

        String albumArt = getAlbumArt((int) data.get(position).getAlbumId());
        if (albumArt != null) {
            holder.fragmentLocalityItemImage.setImageBitmap(BitmapFactory.decodeFile(albumArt));
        } else {
            holder.fragmentLocalityItemImage.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.music_album));
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
            fragmentLocalityItemImage = (ImageView) itemView.findViewById(R.id.fragment_locality_music_item_image);
            fragmentLocalityItemSongname = (TextView) itemView.findViewById(R.id.fragment_locality_music_item_songname);
            fragmentLocalityItemSingname = (TextView) itemView.findViewById(R.id.fragment_locality_music_item_singname);
            fragmentLocalityItemDuration = (TextView) itemView.findViewById(R.id.fragment_locality_music_item_duration);
        }
    }

    /**
     * 格式话时间，将毫秒转为分：秒格式
     *
     * @param time
     * @return
     */
    public String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    private String getAlbumArt(int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = context.getContentResolver().query(
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
