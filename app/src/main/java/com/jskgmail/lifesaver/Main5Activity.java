package com.jskgmail.lifesaver;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class Main5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        VideoView v=(VideoView)findViewById(R.id.videoView) ;
        final ProgressBar p=(ProgressBar)findViewById(R.id.progressBar3);
        v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                p.setVisibility(View.GONE);
            }
        });
        String s="https://firebasestorage.googleapis.com/v0/b/lifesaver-18f28.appspot.com/o/firevid.mp4?alt=media&token=2941b6bc-9293-4d72-9b60-3601800909d8";        Uri uri=Uri.parse(s);
        v.setVideoURI(uri);
        v.setVideoPath(s);
        v.requestFocus();
        v.start();
    }
}
