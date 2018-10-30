package com.jskgmail.prakash;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.jskgmail.prakash.beaconreference.BeaconTransmitterActivity;
import com.jskgmail.prakash.beaconreference.RangingActivity;


public class MainBeaconActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);

        setContentView(R.layout.activity_main_beacon);

        ImageView i=findViewById(R.id.imageView);

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.imgbeacon, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(MainBeaconActivity.this);

                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                AlertDialog dialog = alert.create();
                dialog.show();


            }
        });

        CardView find=findViewById(R.id.find);
        CardView pub=findViewById(R.id.pub);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainBeaconActivity.this, RangingActivity.class));
            }
        });

        pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainBeaconActivity.this, BeaconTransmitterActivity.class));
            }
        });


    }


}
