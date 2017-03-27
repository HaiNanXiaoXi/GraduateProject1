package com.example.ios.graduateproject.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 音乐播放器实现的功能
 * 1.播放
 * 2.暂停
 * 3.上一首
 * 4.下一首
 * 5.获取当前的播放进度
 */

public class PlayService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private MediaPlayer mediaPlayer;

    private int currentIndex = 0;//表示当前正在播放歌曲的位置


    private boolean isPause = false;

    //播放模式
    public static final int ORDER_PLAY = 1;
    public static final int RANDOM_PLAY = 2;
    public static final int SINGLE_PLAY = 3;

    private int play_mode = ORDER_PLAY;

    public int getPlay_mode() {
        return play_mode;
    }

    public void setPlay_mode(int play_mode) {
        this.play_mode = play_mode;
    }

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private MusicUpdateLinstener musicUpdateLinstener;

    public PlayService() {
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public boolean isPause() {
        return isPause;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = new MediaPlayer();


        mediaPlayer.setOnCompletionListener(this);

        mediaPlayer.setOnErrorListener(this);


        executorService.execute(updateStatusRunnable);

        Log.i("Main", "----------->7");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
            executorService = null;
        }
    }

    Runnable updateStatusRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (musicUpdateLinstener != null && mediaPlayer != null && mediaPlayer.isPlaying()) {
                    musicUpdateLinstener.onPublish(getcurrentProgress());
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Random random = new Random();

    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (play_mode) {
            case ORDER_PLAY:
                //  next();
                break;
            case RANDOM_PLAY:

                break;
            case SINGLE_PLAY:

                break;
            default:
                break;
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }


    public class PlayBinder extends Binder {
        public PlayService getPlayService() {
            return PlayService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new PlayBinder();
    }


    public void play(String uri) {
        try {
            isPause = false;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, Uri.parse(uri));
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();

                    if (musicUpdateLinstener != null) {
                        musicUpdateLinstener.onChange(currentIndex);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 暂停
     */
    public void pose() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    /**
     * 下一首
     */
 /*   public void next() {
        if (currentIndex == infos.size() - 1) {
            currentIndex = 0;
        } else {
            currentIndex++;
        }
        play(currentIndex);
    }*/

    /**
     * 上一首
     */
/*    public void prve() {
        if (currentIndex == 0) {
            currentIndex = infos.size() - 1;
        } else {
            currentIndex--;
        }
        play(currentIndex);
    }*/

    /**
     * 开始播放
     */
    public void start() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPause = false;
        }
    }

    public boolean isplay() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    public int getcurrentProgress() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public void seekTo(int msec) {
        mediaPlayer.seekTo(msec);
    }

    /**
     * 更新状态的接口
     */
    public interface MusicUpdateLinstener {
        public void onPublish(int progress);

        public void onChange(int position);
    }

    public void setMusicUpdateLinstener(MusicUpdateLinstener musicUpdateLinstener) {
        this.musicUpdateLinstener = musicUpdateLinstener;
    }
}
