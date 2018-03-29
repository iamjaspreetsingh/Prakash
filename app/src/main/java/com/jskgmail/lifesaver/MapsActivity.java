package com.jskgmail.lifesaver;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
  private GoogleMap mMap;
  String TAG="MAPSREPORT";
    private ArrayList<String> GPS;
    private ArrayList<String> PROB;
    private ArrayList<String> DESC;
    private RotateLoading rotateLoading;
  int pos=0;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       rotateLoading=findViewById(R.id.rotateloading);
       rotateLoading.setLoadingColor(R.color.colorPrimary);

      rotateLoading.start();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("PROBLEMS");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GPS=new ArrayList<>();
                PROB=new ArrayList<>();
                DESC=new ArrayList<>();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {  for (DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                {if (dataSnapshot2.getKey().equals("GPS")) {
                    Log.e("GPSSSSSSS", String.valueOf(dataSnapshot2.getValue()));
                    GPS.add(String.valueOf(dataSnapshot2.getValue()));


                }
                    if (dataSnapshot2.getKey().equals("Prob")) {
                        Log.e("GPSSSSSSS", String.valueOf(dataSnapshot2.getValue()));
                        PROB.add(String.valueOf(dataSnapshot2.getValue()));


                    }
                    if (dataSnapshot2.getKey().equals("Description")) {
                        Log.e("GPSSSSSSS", String.valueOf(dataSnapshot2.getValue()));
                        DESC.add(String.valueOf(dataSnapshot2.getValue()));


                    }
                }

                }
                for (int i=0;i<GPS.size();i++)
                {  String latlong[]=GPS.get(i).split(",");
                    double lat= Double.parseDouble(latlong[0]);
                    double longi= Double.parseDouble(latlong[1]);
                    LatLng sydney = new LatLng(lat,longi);

                    mMap.addMarker(new MarkerOptions().position(sydney).title(PROB.get(i)).snippet(DESC.get(i)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
                }
                rotateLoading.stop();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });







        // Add a marker in Sydney and move the camera
Log.e("zzzz"+MainActivity.mylocationa, ""+MainActivity.myLocationb);
        LatLng sydney1 = new LatLng(MainActivity.lat,MainActivity.longi);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1,12));


          }

}
