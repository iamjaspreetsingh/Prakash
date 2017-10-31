package com.jskgmail.lifesaver;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity implements LocationListener {
static String myLocation;int ch=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyCurrentLocationListener locationListener = new MyCurrentLocationListener();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,locationListener);

    }




    void go(){


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef99 = database.getReference(MainsettingActivity.myno);

        myRef99.setValue(myLocation);


        DatabaseReference myRef = database.getReference(MainsettingActivity.myno).child("gps");

        myRef.setValue(myLocation);


        DatabaseReference myRef1 = database.getReference(MainsettingActivity.myno).child("user");

        myRef1.setValue(MainsettingActivity.username);

        DatabaseReference myRef2 = database.getReference(MainsettingActivity.myno).child("myno");

        myRef2.setValue(MainsettingActivity.myno);

        DatabaseReference myRef3 = database.getReference(MainsettingActivity.myno).child("friendno");

        myRef3.setValue(MainActivity.phon);
        DatabaseReference myRef44 = database.getReference(MainsettingActivity.myno).child("flood");

        myRef44.setValue("0");

final String TAG="qqqq";
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });




    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

        //I make a log to see the results
        Log.e("My", myLocation);

        go();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public class MyCurrentLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            location.getLatitude();
            location.getLongitude();

             myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

            //I make a log to see the results
            Log.e("My", myLocation);

go();
             ch=1;
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        public void onProviderEnabled(String s) {

        }

        public void onProviderDisabled(String s) {
Log.d("disable","d");
        }
    }







}
