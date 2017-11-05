package com.jskgmail.lifesaver;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
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

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class Main2Activity extends AppCompatActivity {
static String myLocation;int ch=0;
String TAG = "tag";
String latlong="39.7391536,-104.9847034";
    private final static String API_KEY = "AIzaSyClHbZ-x92EYceOWKDSgT0NPZEBBEa_wnU";
    private final static String API_KEY1="AIzaSyCGZpTkUUlIYjYuJNOZMJKA6Ar4d7fE7Dc";
double eleva=0;


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
        Call call = apiService.getall(latlong,API_KEY);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG,"success");
                Log.e(TAG,  response.raw().request().url().toString());
                String url=response.raw().request().url().toString();
                FriendsProcessor mytask = new FriendsProcessor();
                mytask.execute(url);



            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG,"failureee");
            }


        });
















        if (API_KEY1.isEmpty()) {
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_LONG).show();
            return;
        } Log.e(TAG,"whyy");

        ApiInterface1 apiService1 =
                ApiClient.getClient1().create(ApiInterface1.class);
//TODO
        Call call1 = apiService1.getall(API_KEY1);
        call1.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG,"success");
                Log.e(TAG,  response.raw().request().url().toString());
                String url=response.raw().request().url().toString();
                FriendsProcessor1 mytask = new FriendsProcessor1();
                mytask.execute(url);



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





















































    private class FriendsProcessor1 extends AsyncTask<String, Void, Integer> {



        public FriendsProcessor1() {

            super();

        }


        // The onPreExecute is executed on the main UI thread before background processing is

        // started. In this method, we start the progressdialog.

        @Override

        protected void onPreExecute() {

            super.onPreExecute();


            // Show the progress dialog on the screen


        }


        // This method is executed in the background and will return a result to onPostExecute

        // method. It receives the file name as input parameter.

        @Override

        protected Integer doInBackground(String... urls) {


            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;


            // TODO connect to server, download and process the JSON string


            // Now we read the file, line by line and construct the

            // Json string from the information read in.

            try {

                /* forming th java.net.URL object */

                URL url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();



                 /* optional request header */

                urlConnection.setRequestProperty("Content-Type", "application/json");



                /* optional request header */

                urlConnection.setRequestProperty("Accept", "application/json");



                /* for Get request */

                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    String response = convertInputStreamToString(inputStream);


                    // now process the string using the method that we implemented in the previous exercise

                    Log.e("responseeee",response.replace(" ",""));
                    JSONObject obj=new JSONObject(response.replace(" ",""));

                        String ele = (obj.getString("snappedPoints"));
                        String[] elee=ele.split(",");
                        String elv=elee[0].replace("[{\"location\":{\"latitude\":","");
                    String elv1=elee[1].replace("\"longitude\":","").replace("}","");
                        Log.e("lat",elv);
                    Log.e("longi",elv1);

latlong=elv+","+elv1;
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
//TODO
                    Call call = apiService.getall(latlong,API_KEY);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Log.e(TAG,"success");
                            Log.e(TAG,  response.raw().request().url().toString());
                            String url=response.raw().request().url().toString();
                            FriendsProcessor mytask = new FriendsProcessor();
                            mytask.execute(url);



                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.e(TAG,"failureee");
                        }


                    });


































                    result = 1; // Successful

                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {

                Log.d(TAG, e.getLocalizedMessage());

            }

            return result; //"Failed to fetch data!";

        }

    }

















    private class FriendsProcessor extends AsyncTask<String, Void, Integer> {



        public FriendsProcessor() {

            super();

        }


        // The onPreExecute is executed on the main UI thread before background processing is

        // started. In this method, we start the progressdialog.

        @Override

        protected void onPreExecute() {

            super.onPreExecute();


            // Show the progress dialog on the screen


        }


        // This method is executed in the background and will return a result to onPostExecute

        // method. It receives the file name as input parameter.

        @Override

        protected Integer doInBackground(String... urls) {


            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;


            // TODO connect to server, download and process the JSON string


            // Now we read the file, line by line and construct the

            // Json string from the information read in.

            try {

                /* forming th java.net.URL object */

                URL url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();



                 /* optional request header */

                urlConnection.setRequestProperty("Content-Type", "application/json");



                /* optional request header */

                urlConnection.setRequestProperty("Accept", "application/json");



                /* for Get request */

                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    String response = convertInputStreamToString(inputStream);


                    // now process the string using the method that we implemented in the previous exercise

                    Log.e("responseeee",response.replace(" ",""));
JSONObject obj=new JSONObject(response.replace(" ",""));
                    if(obj.getString("status").equals("OK")) {
                        String ele = (obj.getString("results"));
                     String[] elee=ele.split(",");
                        String elv=elee[0].replace("[{\"elevation\":","");
                        Log.e("elevation",elv);
eleva=eleva-Double.valueOf(elv);
                        Log.e("elelelele", String.valueOf(eleva));
                        result = 1; // Successful
                    }
                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {

                Log.d(TAG, e.getLocalizedMessage());

            }

            return result; //"Failed to fetch data!";

        }

    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));

        String line = "";

        String result = "";

        while((line = bufferedReader.readLine()) != null){

            result += line;

        }



            /* Close Stream */

        if(null!=inputStream){

            inputStream.close();

        }

        return result;

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
        public static final String BASE_URL1 ="https://roads.googleapis.com/v1/";
        private static Retrofit retrofit = null;
        private static Retrofit retrofit1 = null;


        public static Retrofit getClient() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }




        public static Retrofit getClient1() {
            if (retrofit1==null) {
                retrofit1 = new Retrofit.Builder()
                        .baseUrl(BASE_URL1)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit1;
        }



    }



    public interface ApiInterface {
        String loc="39.7391536,-104.9847034";
        @GET("json?")
        Call<ResponseBody> getall(@Query("locations") String loc,@Query("key") String apiKey);

    }

    public interface ApiInterface1 {
        String loc="60.170880,24.942795";
        @GET("nearestRoads?points="+loc+"")
        Call<ResponseBody> getall(@Query("key") String apiKey1);

    }

















}
