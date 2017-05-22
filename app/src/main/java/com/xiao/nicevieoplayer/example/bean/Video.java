package com.xiao.nicevieoplayer.example.bean;

/**
 * Created by XiaoJianjun on 2017/5/21.
 * 视频
 */
public class Video {
    private String title;
    private String imageUrl;
    private String videoUrl;

    public Video(String title, String imageUrl, String videoUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
