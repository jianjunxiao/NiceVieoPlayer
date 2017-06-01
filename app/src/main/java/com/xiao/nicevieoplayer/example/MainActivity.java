package com.xiao.nicevieoplayer.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerController;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevieoplayer.R;

public class MainActivity extends AppCompatActivity {

    private NiceVideoPlayer mNiceVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
        mNiceVideoPlayer.setUp("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4", null);
        NiceVideoPlayerController controller = new NiceVideoPlayerController(this);
        controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
        controller.setImage("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg");
        mNiceVideoPlayer.setController(controller);
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        super.onStop();
    }

    public void enterTinyWindow(View view) {
        if (mNiceVideoPlayer.isPlaying()
                || mNiceVideoPlayer.isBufferingPlaying()
                || mNiceVideoPlayer.isPaused()
                || mNiceVideoPlayer.isBufferingPaused()) {
            mNiceVideoPlayer.enterTinyWindow();
        } else {
            Toast.makeText(this, "要播放后才能进入小窗口", Toast.LENGTH_SHORT).show();
        }
    }

    public void showVideoList(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }
}
