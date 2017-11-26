
package com.jskgmail.lifesaver;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_main3);
        go();
    }
    private void go() {
        final ProgressBar p=(ProgressBar)findViewById(R.id.progressBar2);
        VideoView v=(VideoView)findViewById(R.id.videoView2) ;
v.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
    @Override
    public void onPrepared(MediaPlayer mp) {
        p.setVisibility(View.GONE);
    }
});
String s="https://firebasestorage.googleapis.com/v0/b/lifesaver-18f28.appspot.com/o/earthquake.mp4?alt=media&token=98993371-823d-4b81-adc7-d9d87ec841a9";
        Uri uri=Uri.parse(s);
        v.setVideoURI(uri);
         v.setVisibility(View.VISIBLE);
         v.setVideoPath(s);
        v.requestFocus();
        v.start();





    }


}
