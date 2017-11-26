package com.jskgmail.lifesaver;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainbbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_mainbb);
        TextView t=(TextView)findViewById(R.id.textView20);
        t.setText("ZIP : "+MainActivity.ZIP);

        ImageView calll=(ImageView)findViewById(R.id.imageButton2);
        ImageView dir=(ImageView)findViewById(R.id.imageButton3);
        calll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] em=MainActivity.bloodno.split(",");
                Uri call=Uri.parse("tel:"+em[0]);
                Intent surf=new Intent(Intent.ACTION_DIAL,call);
                startActivity(surf);
            }
        });
        dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + MainActivity.bloodloc;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });

        TextView tt=(TextView)findViewById(R.id.textView23);
        tt.setText(MainActivity.blba);






    }
}
