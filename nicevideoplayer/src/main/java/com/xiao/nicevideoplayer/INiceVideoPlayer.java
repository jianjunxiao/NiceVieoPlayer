package com.xiao.nicevideoplayer;

/**
 * Created by XiaoJianjun on 2017/5/5.
 * NiceVideoPlayer抽象接口
 */
public interface INiceVideoPlayer {

    void start();
    void restart();
    void pause();
    void seekTo(long pos);
    void setVolume(int volume);
    void setSpeed(float speed);

    boolean isIdle();
    boolean isPreparing();
    boolean isPrepared();
    boolean isBufferingPlaying();
    boolean isBufferingPaused();
    boolean isPlaying();
    boolean isPaused();
    boolean isError();
    boolean isCompleted();

    boolean isFullScreen();
    boolean isTinyWindow();
    boolean isNormal();

    int getMaxVolume();
    int getVolume();
    long getDuration();
    long getCurrentPosition();
    int getBufferPercentage();
    float getSpeed(float speed);
    long getTcpSpeed();

    void enterFullScreen();
    boolean exitFullScreen();
    void enterTinyWindow();
    boolean exitTinyWindow();

    void release();
}
