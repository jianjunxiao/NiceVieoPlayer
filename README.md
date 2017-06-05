# NiceVieoPlayer
## 用IjkPlayer/MediaPlayer + TextureView封装，完美切换全屏、小窗口播放。
### Usage
下载niceviewoplayer库，在AndroidSutio中作为Mudule添加依赖。

1.在Activity中使用：
```
 private void init() {
      mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
      mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.PLAYER_TYPE_IJK); // or NiceVideoPlayer.PLAYER_NATIVE
      mNiceVideoPlayer.setUp(mVideoUrl, null);
      NiceVideoPlayerController controller = new NiceVideoPlayerController(this);
      controller.setTitle(mTitle);
      controller.setImage(mImageUrl);
      mNiceVideoPlayer.setController(controller);
  }
  
  // 按返回键
  // 当前是全屏或小窗口，需要先退出全屏或小窗口。
  @Override
  public void onBackPressed() {
      if (NiceVideoPlayerManager.instance().onBackPressd()) {
          return;
      }
      super.onBackPressed();
  }
  ```
2.在RecyclerView列表中使用需要监听itemView detach：
```
mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
    @Override
    public void onChildViewAttachedToWindow(View view) {

    }

    @Override
    public void onChildViewDetachedFromWindow(View view) {
        NiceVideoPlayer niceVideoPlayer = (NiceVideoPlayer) view.findViewById(R.id.nice_video_player);
        if (niceVideoPlayer != null) {
            niceVideoPlayer.release();
        }
    }
});
```
### Proguard
```
-keep class tv.danmaku.ijk.media.player.**{*;}
```
### Demo
![](https://github.com/xiaoyanger0825/NiceVieoPlayer/raw/master/images/aa.jpg)
![](https://github.com/xiaoyanger0825/NiceVieoPlayer/raw/master/images/bb.jpg)
![](https://github.com/xiaoyanger0825/NiceVieoPlayer/raw/master/images/cc.jpg)
![](https://github.com/xiaoyanger0825/NiceVieoPlayer/raw/master/images/dd.jpg)