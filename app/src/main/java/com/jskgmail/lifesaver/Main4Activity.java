package com.jskgmail.lifesaver;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        go();
    }



    private void go() {
VideoView v=(VideoView)findViewById(R.id.videoView) ;
       String s="https://firebasestorage.googleapis.com/v0/b/lifesaver-18f28.appspot.com/o/flood.mp4?alt=media&token=179d7e4e-7171-4a87-b1f8-b1fc3d976c60";
        Uri uri=Uri.parse(s);
        v.setVideoURI(uri);
        v.setVideoPath(s);
        v.requestFocus();
        v.start();


    }









}
