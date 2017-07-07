package com.xiao.nicevieoplayer.example.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

/**
 * Created by XiaoJianjun on 2017/7/7.
 * 在此Fragment中，如果视频正在播放或缓冲，按下Home键，暂停视频播放，回到此Fragment后继续播放视频；
 * 如果离开次Fragment（跳转到其他Activity或按下Back键），则释放视频播放器
 */
public class CompatHomeKeyFragment extends Fragment {

    private boolean pressedHome;
    private HomeKeyWatcher mHomeKeyWatcher;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHomeKeyWatcher = new HomeKeyWatcher(getActivity());
        mHomeKeyWatcher.setOnHomePressedListener(new HomeKeyWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                pressedHome = true;
            }
        });
        pressedHome = false;
        mHomeKeyWatcher.startWatch();
    }

    @Override
    public void onStart() {
        mHomeKeyWatcher.startWatch();
        pressedHome = false;
        super.onStart();
        NiceVideoPlayerManager.instance().resumeNiceVideoPlayer();
    }

    @Override
    public void onStop() {
        // 在OnStop中是release还是suspend播放器，需要看是不是因为按了Home键
        if (pressedHome) {
            NiceVideoPlayerManager.instance().suspendNiceVideoPlayer();
        } else {
            NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        }
        super.onStop();
        mHomeKeyWatcher.stopWatch();
    }


}
