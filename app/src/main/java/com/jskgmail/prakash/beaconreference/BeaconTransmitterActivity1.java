package com.jskgmail.prakash.beaconreference;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jskgmail.prakash.BluetoothHelper.BluetoothHelper;
import com.jskgmail.prakash.BluetoothHelper.BluetoothListener;
import com.jskgmail.prakash.MainActivity;
import com.jskgmail.prakash.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.ArrayList;
import java.util.Arrays;

public class BeaconTransmitterActivity1 extends AppCompatActivity implements
        BluetoothListener.OnBluetoothSupportedCheckListener, BluetoothListener.OnBluetoothEnabledCheckListener,
        BluetoothListener.BluetoothTrigger,BeaconConsumer {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    public static final String SENT_SMS_ACTION_NAME = "SMS_SENT";
    public static final String DELIVERED_SMS_ACTION_NAME = "SMS_DELIVERED";

    protected static final String TAG = "Beacon Transmitter";
    private static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private static final int CAMERA_REQUEST = 50;
    private boolean flashLightStatus = false;
    MediaPlayer alert;
    String[] parserLayout = { "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25",
            "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"};

    BluetoothHelper bluetoothHelper;



    boolean isBluetoothEnabled;

    Beacon beacon;
    BeaconParser beaconParser;
    BeaconTransmitter beaconTransmitter;

    private BeaconManager beaconManager;

    int beaconLayout = 0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_transmitter1);

        bluetoothHelper = new BluetoothHelper();
        bluetoothHelper.initializeBluetooth(this);

        BeaconReferenceApplication app = (BeaconReferenceApplication) this.getApplication();
        beaconManager = app.getBeaconManager();
        Button yes = (Button) findViewById(R.id.button2);
        Button no = (Button) findViewById(R.id.button3);
        final boolean hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean isEnabled = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;


        ActivityCompat.requestPermissions(BeaconTransmitterActivity1.this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_REQUEST);


        final ImageButton alarm = (ImageButton) findViewById(R.id.imageButton);
        alert = MediaPlayer.create(BeaconTransmitterActivity1.this, R.raw.siren);
        final int[] i = {120};
        alert.start();
        final TextView time = (TextView) findViewById(R.id.textView12);
        final CountDownTimer countDownTimer=new CountDownTimer(120000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {


                time.setText(i[0] + "s");
                i[0]--;
                if (i[0] % 2 == 0)
                    flashLightOn();
                else if (i[0] > 10) flashLightOff();
                else flashLightOff();
            }

            @Override
            public void onFinish() {

                if (MainActivity.flood.equals("1"))
                    go();
            }
        }.start();

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               go();
                ambulance();

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.flood = "0";
                SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
                editor.putString("flood", "0");

                editor.apply();
                countDownTimer.cancel();
                alert.stop();
                flashLightOff();
  {
                    beaconTransmitter.startAdvertising();
                    beaconTransmitter = null;

                }

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


        beaconManager.setBackgroundBetweenScanPeriod(5000l);
        beaconManager.setForegroundBetweenScanPeriod(5000l);

        beaconManager.bind(this);

       trasmitClick();
            }


    public void trasmitClick() {

        if (beaconTransmitter == null) {

            String major, minor, uuid;


                uuid = "94339309-bfe2-4807-b747-9aee23508620";

                major = "8";

                minor = "2";


            beacon = new Beacon.Builder()
                    .setId1(uuid)
                    .setId2(major)
                    .setId3(minor)
                    .setManufacturer(0x0118) // It is for AltBeacon.  Change this for other beacon layouts
                    .setTxPower(-59)
                    .setDataFields(Arrays.asList(new Long[]{6l, 7l})) // Remove this for beacon layouts without d: fields
                    .build();

            // Change the layout below for other beacon types

            beaconParser = new BeaconParser()
                    .setBeaconLayout(parserLayout[beaconLayout]);

            beaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);
            beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {
                @Override
                public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                    super.onStartSuccess(settingsInEffect);
                }

                @Override
                public void onStartFailure(int errorCode) {
                    super.onStartFailure(errorCode);
                }
            });


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBLENotSupported() {
        Toast.makeText(BeaconTransmitterActivity1.this, "BLE not supported", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBluetoothNotSupported() {
        Toast.makeText(BeaconTransmitterActivity1.this, "Blutooth not supported", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBluetoothEnabled(boolean enable) {
        if (enable) {
            isBluetoothEnabled = true;
        } else {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BLUETOOTH);
        }
    }

    @Override
    public void initBluetooth() {
        if (bluetoothHelper != null)
            bluetoothHelper.initializeBluetooth(this);
    }

    @Override
    public void enableBluetooth() {
        if (bluetoothHelper != null)
            bluetoothHelper.enableBluetooth(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BLUETOOTH && resultCode == AppCompatActivity.RESULT_CANCELED) {
            Toast.makeText(BeaconTransmitterActivity1.this, "Bluetooth permission denied", Toast.LENGTH_LONG).show();
            bluetoothHelper = null;
            return;
        } else {
            isBluetoothEnabled = true;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bluetoothHelper != null)
            bluetoothHelper.enableBluetooth(this);
        BeaconReferenceApplication.isActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        BeaconReferenceApplication.isActive = false;
    }


    @Override
    public void onBeaconServiceConnect() {

    }

    void go() {
       String sms=
     "Your friend may be in danger. His location is : https://www.google.com/maps/search/" +
                MainActivity.latlong + "/  & at the height of " + MainActivity.diffelevation +
                " metres from road level. The emergency no. of nearest hospital ( " + MainActivity.hospname + " ) are " + MainActivity.emergencyno + ". The contact no. of nearest blood bank ( " + MainActivity.bbname + " ) " + MainActivity.bloodno;
        MainActivity.flood = "0";
        SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
        editor.putString("flood", "0");
        editor.apply();

        SharedPreferences preference = getSharedPreferences("emergency", MODE_PRIVATE);
        String phno = preference.getString("mob", null);
        String numbers = "";

        if (phno != null) {

            String[] no = phno.split(",");
       //     intentt.putExtra("address", numbers);
            for (int i = 0; i < no.length; i++) {

                //numbers = numbers + no[i] + ";";
                sendSMSMessage(no[i],sms);

            }

            Log.e("numbersemer",numbers);
        }


      //  intentt.putExtra("address", numbers);
        //BeaconTransmitterActivity1.this.startActivity(intentt);

    //   sendSMSMessage(sms);

        finish();


    }

    protected void sendSMSMessage(String no,String sms) {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        SmsManager smsManager = SmsManager.getDefault();
    //    smsManager.sendTextMessage(no, null, sms, null, null);

        PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(SENT_SMS_ACTION_NAME), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(DELIVERED_SMS_ACTION_NAME), 0);

        ArrayList<String> parts = smsManager.divideMessage(sms);

        ArrayList<PendingIntent> sendList = new ArrayList<>();
        sendList.add(sentPI);

        ArrayList<PendingIntent> deliverList = new ArrayList<>();
        deliverList.add(deliveredPI);

        smsManager.sendMultipartTextMessage(no, null, parts, sendList, deliverList);







    }


















    void ambulance() {
        String[] em = MainActivity.emergencyno.split(",");
        Uri call = Uri.parse("tel:" + em[0]);
        Intent surf = new Intent(Intent.ACTION_DIAL, call);
        startActivity(surf);
    }


    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true);
            }
            flashLightStatus = true;

        } catch (CameraAccessException e) {
        }
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, false);
            }
            flashLightStatus = false;

        } catch (CameraAccessException e) {
        }
    }
}