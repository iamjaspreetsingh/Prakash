package com.jskgmail.lifesaver.beaconreference;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

import com.jetradar.desertplaceholder.DesertPlaceholder;
import com.jskgmail.lifesaver.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class RangingActivity extends Activity implements BeaconConsumer {
    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    MediaPlayer alert;
    float last=0;
    DesertPlaceholder desertPlaceholder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranging);

         desertPlaceholder= (DesertPlaceholder) findViewById(R.id.placeholder);
        desertPlaceholder.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do stuff

            }
        });
desertPlaceholder.setVisibility(View.VISIBLE);
        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(turnOn, 0);

        alert = MediaPlayer.create(RangingActivity.this, R.raw.beep);


        beaconManager.bind(this);


    }

    @Override 
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    @Override 
    protected void onPause() {
        super.onPause();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);
    }

    @Override 
    protected void onResume() {
        super.onResume();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
           @Override
           public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
              if (beacons.size() > 0) {
                 //EditText editText = (EditText)RangingActivity.this.findViewById(R.id.rangingText);
                 Beacon firstBeacon = beacons.iterator().next();



                 logToDisplay("The beacon " + firstBeacon.toString() + " is about " + firstBeacon.getDistance() + " meters away.");
logToDisplayd(firstBeacon.getDistance());





              }
           }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    private void logToDisplay(final String line) {
        runOnUiThread(new Runnable() {
            public void run() {
                TextView editText = (TextView) RangingActivity.this.findViewById(R.id.rangingText);
                editText.setText(line+"\n");

            }
        });
    }
    private void logToDisplayd(final double line) {
        runOnUiThread(new Runnable() {
            public void run() {

                TextView cm=(TextView)RangingActivity.this.findViewById(R.id.cm);
                cm.setText(" "+(float)line +" m ");
              if ((float)line<=last)
              { alert.reset();
                alert.release();
                alert = MediaPlayer.create(RangingActivity.this, R.raw.beep);
                alert.start();}
                else {
                  alert.reset();
                  alert.release();
                  alert = MediaPlayer.create(RangingActivity.this, R.raw.beepe);
                  alert.start();

              }


                last=(float)line;
                desertPlaceholder.setVisibility(View.GONE);

            }
        });



    }

}
