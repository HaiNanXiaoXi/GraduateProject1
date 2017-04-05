package com.example.ios.graduateproject.bean;

/**
 * Created by ios on 2017/3/29.
 */
public class VideoInfo {
    public long id;
    public String displayName;//歌名
    public String album;//专辑
    public String artist;//艺术家
    public String title;//显示名称
    public String mimeType;//类型
    public String path;//路径
    public long duration;// 时长
    public long size;//大小
    public String resolution;
    public String uri_thumb;

    public String getUri_thumb() {
        return uri_thumb;
    }

    public void setUri_thumb(String uri_thumb) {
        this.uri_thumb = uri_thumb;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Override
    public String toString() {
        return "VideoInfo{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", path='" + path + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", resolution='" + resolution + '\'' +
                '}';
    }
}
