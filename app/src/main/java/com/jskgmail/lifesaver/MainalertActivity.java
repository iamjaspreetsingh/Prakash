package com.jskgmail.lifesaver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainalertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainalert);
        Button yes = (Button) findViewById(R.id.button2);
        Button no = (Button) findViewById(R.id.button3);
        final ImageButton alarm = (ImageButton) findViewById(R.id.imageButton);
        final MediaPlayer alert = MediaPlayer.create(MainalertActivity.this, R.raw.siren);
        final int[] i = {120};
        alert.start();
        final TextView time = (TextView) findViewById(R.id.textView12);
        new CountDownTimer(120000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {


                time.setText(i[0] + "s");
                i[0]--;
            }

            @Override
            public void onFinish() {
if(MainActivity.flood.equals("1"))
                go();
            }
        }.start();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambulance();

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.flood = "0";
                SharedPreferences.Editor editor=getSharedPreferences("flood",MODE_PRIVATE).edit();
                editor.putString("flood","0");

                editor.apply();
                alert.stop();

                finish();
            }

        });
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alert.isPlaying()) {
                    alert.stop();
                    alarm.setImageResource(R.drawable.ic_volume_up_black_24dp);
                } else {
                    alert.start();
                    alarm.setImageResource(R.drawable.ic_volume_off_black_24dp);
                }
            }
        });

    }

    void go() {
        Intent intentt = new Intent(Intent.ACTION_VIEW);
        intentt.setData(Uri.parse("sms:"));
        intentt.setType("vnd.android-dir/mms-sms");
        intentt.putExtra("sms_body", "Your friend may be in danger as a sudden change in accelerometer reading was noted. His location is : https://www.google.com/maps/search/" +
                MainActivity.latlong + "/  & at the height of " + MainActivity.diffelevation +
                " metres from road level.The emergency no. of nearest hospital ( "+ MainActivity.hospname+" ) are " + MainActivity.emergencyno+". The contact no. of nearest blood bank ( "+MainActivity.bbname+" ) "+MainActivity.bloodno);

        MainActivity.flood = "0";
        SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
        editor.putString("flood", "0");
        editor.apply();

        SharedPreferences preference = getSharedPreferences("emergency", MODE_PRIVATE);
        String phno = preference.getString("mob", null);
        String numbers = "";

        if (phno != null) {

            String[] no = phno.split(",");
            intentt.putExtra("address", numbers);
            for (int i = 0; i < no.length; i++) {
                numbers = numbers + no[i] + ";";

            }
        }


        intentt.putExtra("address", numbers);
        MainalertActivity.this.startActivity(intentt);

        finish();


    }

    void ambulance()
    {String[] em=MainActivity.emergencyno.split(",");
        Uri call=Uri.parse("tel:"+em[0]);
        Intent surf=new Intent(Intent.ACTION_DIAL,call);
        startActivity(surf);
    }
}
