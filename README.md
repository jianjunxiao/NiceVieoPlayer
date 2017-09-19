# NiceVieoPlayer

[![](https://jitpack.io/v/xiaoyanger0825/NiceVieoPlayer.svg)](https://jitpack.io/#xiaoyanger0825/NiceVieoPlayer) [![Build Status](https://travis-ci.org/xiaoyanger0825/NiceVieoPlayer.svg?branch=master)](https://travis-ci.org/xiaoyanger0825/NiceVieoPlayer) [![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16) [![作者](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-xiaoyanger0825-orange.svg)](https://github.com/xiaoyanger0825)
### Features

 * 用IjkPlayer/MediaPlayer + TextureView封装，可切换IjkPlayer、MediaPlayer.
 * 支持本地和网络视频播放.
 * 完美切换小窗口、全屏，可在RecyclerView中无缝全屏.
 * 手势滑动调节播放进度、亮度、声音.
 * 支持清晰度切换.
 * 可自定义控制界面.

### Usage
下载niceviewoplayer库，在AndroidStudio中作为Mudule添加依赖。

或者在Gradle中添加依赖：

```
allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
    }
}

dependencies {
    compile 'com.github.xiaoyanger0825:NiceVieoPlayer:v2.2'
}
```
**在对应视频界面所在的Activity的Manifest.xml中需要添加如下配置：**
```
android:configChanges="orientation|keyboardHidden|screenSize"
```

#### 1.在Activity中使用
在Activity中使用时，该Activity需要继承自`AppCompatActivity`，在onStop中需要释放播放器，并且要在onBackPress中处理按下返回键的逻辑：

```
private void init() {
    mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
    mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
    mNiceVideoPlayer.setUp(mVideoUrl, null);
  
    TxVideoPlayerController controller = new TxVideoPlayerController(this);
    controller.setTitle(mTitle);
    controller.setImage(mImageUrl);
    mNiceVideoPlayer.setController(controller);
}

@Override
protected void onStop() {
    super.onStop();
    // 在onStop时释放掉播放器
    NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
}
@Override
public void onBackPressed() {
    // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
    // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
    if (NiceVideoPlayerManager.instance().onBackPressd()) return;
    super.onBackPressed();
}
```
详细可参考demo中的`TinyWindowPlayActivity`、`ChangeClarityActivity`、`RecyclerViewActivity`.
#### 2.在Fragment中使用
在Fragment中使用时，该Fragment外层的Activity需要继承自`AppCompatActivity`，并且也要处理返回键按下逻辑：
```
public class XXXActivity extends AppCompatActivity {
    ...
    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
    ...
}
```
同时在Fragment中的onStop方法中释放播放器：
```
public class XXXFragenment extends Fragment {
    ...
    @Override
    public void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }
    ...
}
```
详细可参考demo中的`UseInFragActivity`和`DemoFragment`.

#### 3.切换清晰度
如果需要切换清晰度，需要在`controller`中配置清晰度相关的等级和视频链接地址：
```
private void init() {
    mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
    mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
    TxVideoPlayerController controller = new TxVideoPlayerController(this);
    controller.setTitle(mTitle);
    controller.setClarity(getClarites(), 0);    // 设置清晰度以及默认播放的清晰度
    Glide.with(this)
            .load(mImageUrl)
            .placeholder(R.drawable.img_default)
            .crossFade()
            .into(controller.imageView());
    mNiceVideoPlayer.setController(controller);
}

public List<Clarity> getClarites() {
    List<Clarity> clarities = new ArrayList<>();
    clarities.add(new Clarity("标清", "270P", "http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
    clarities.add(new Clarity("高清", "480P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
    clarities.add(new Clarity("超清", "720P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
    clarities.add(new Clarity("蓝光", "1080P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
    return clarities;
}
```
详细参考demo中的`ChangeClarityActivity`

#### 4.在RecyclerView列表中使用
在ReclerView列表中使用时需要监听itemView回收，以此释放掉对应的播放器
```
mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        NiceVideoPlayer niceVideoPlayer = ((VideoViewHolder) holder).mVideoPlayer;
        if (niceVideoPlayer == NiceVideoPlayerManager.instance().getCurrentNiceVideoPlayer()) {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        }
    }
});
```
详细参考demo中的`RecyclerViewActivity`.
#### 5.播放时Home键按下以及回到播放界面的处理
按照上面的做法，在onStop直接释放掉播放器，那么在播放时按下Home键播放器也会被释放掉，如果在此回到播放界面，播放器回到最初始的状态。如果需要在播放的时候按下Home键只是暂停播放器，重新回到播放界面时又继续播放，那么可以参考demo中的`CompatHomeKeyActiivty`，或者对应的Activity集成自`CompatHomeKeyActiivty`，详细参考demo中的`ProcessHome1Activity`。当然，如果是在Fragment中，参考`CompatKeyFragment`，或者继承自`CompatKeyFragment`(外层的Activity还是继承自AppCompat，并处理onBackPress)，详细参考demo中的`ProcessHome2Activity`.

#### 5.自定义控制界面
```
public class CustomController extends NiceVideoPlayerController {
    // 实现自己的控制界面
    ...
}
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