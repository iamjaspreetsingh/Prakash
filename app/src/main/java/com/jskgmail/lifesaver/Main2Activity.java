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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class Main2Activity extends AppCompatActivity {
static String myLocation;int ch=0;
String TAG = "tag";

    private final static String API_KEY = "AIzaSyClHbZ-x92EYceOWKDSgT0NPZEBBEa_wnU";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_LONG).show();
            return;
        } Log.e(TAG,"whyy");

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
//TODO
        Call call = apiService.getall(API_KEY);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG,"success");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG,"failureee");
            }


        });




















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





    public class MyCurrentLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            location.getLatitude();
            location.getLongitude();

             myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

            //I make a log to see the results
            Log.e("my", myLocation);

go();

        }

        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        public void onProviderEnabled(String s) {

        }

        public void onProviderDisabled(String s) {
Log.d("disable","d");
        }
    }















    public static class ApiClient {

        public static final String BASE_URL ="https://maps.googleapis.com/maps/api/elevation/";
        private static Retrofit retrofit = null;


        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }


    public interface ApiInterface {
        @GET("json?locations=39.7391536,-104.9847034&key=")
        Call<ResponseBody> getall(@Query("api_key") String apiKey);

        @GET("movie/{id}")
        Call getElevation(@Path("id") int id, @Query("api_key") String apiKey);
    }



















}
