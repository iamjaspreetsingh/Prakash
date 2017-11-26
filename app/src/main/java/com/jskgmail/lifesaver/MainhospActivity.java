package com.jskgmail.lifesaver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MainhospActivity extends AppCompatActivity {
String TAG="HOSPt",blba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_mainhosp);
        TextView t=(TextView)findViewById(R.id.textView20);
        t.setText("ZIP code : "+MainActivity.ZIP);

        ImageView calll=(ImageView)findViewById(R.id.imageButton2);
        ImageView dir=(ImageView)findViewById(R.id.imageButton3);
calll.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String[] em=MainActivity.emergencyno.split(",");
        Uri call=Uri.parse("tel:"+em[0]);
        Intent surf=new Intent(Intent.ACTION_DIAL,call);
        startActivity(surf);
    }
});
        dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + MainActivity.latitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });
        TextView tt=(TextView)findViewById(R.id.textView23);
        tt.setText(MainActivity.hospp);
        Log.e("bllba",MainActivity.hospp);
        String b = null;
        try {
            JSONArray obj=new JSONArray(MainActivity.blba);
            for (int i=0;i<obj.length();i++)
            {
               b=b+  (obj.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }







}
