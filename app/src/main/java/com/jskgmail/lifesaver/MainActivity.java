package com.jskgmail.lifesaver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, SensorEventListener {
;
    private StorageReference mStorageRef;
    private static final int RESULT_PICK_CONTACT = 85;
    private ArrayList<String> stringArrayList, stringArrayList1;
static String blba="No Blood Bank found";
    ListViewAdfrlist adapter;
    static String phon="",naam="";static String myLocation;
    static double mylocationa;
    static double myLocationb;int ch=0;
    static String bloodloc;
    static String hospname="",bbname="";
static String ZIP;
String hosp="";
    static String bloodno="";
    String TAG = "taggg";
    static double lat=39.7,longi=-104;
static String hospp="No Hospital Found";
   static String latlong = "";//the realtime latitude longitude parameter
    private final static String API_KEY = "AIzaSyClHbZ-x92EYceOWKDSgT0NPZEBBEa_wnU";
    private final static String API_KEY1 = "AIzaSyCGZpTkUUlIYjYuJNOZMJKA6Ar4d7fE7Dc";
    double eleva = 0;
    static String latitude="28.620660,77.08127";
   static double diffelevation = 0;
    File image = null;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;

    String mCurrentPhotoPath;
    private Sensor senAccelerometer;
    private SensorManager senSensorManager;
float lastx=0,lasty=0,lastz=0;
    long lastupdate = System.currentTimeMillis();
static String emergencyno="";
static String flood="0";



    private SensorManager mSensorManager;
    private Sensor TemperatureSensor;
    private String API_KEYpin="AIzaSyBCy3Ghs09Bk0YULL2SmI-F5yXTJ6KJCWg";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
         TemperatureSensor = senSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
 senSensorManager.registerListener(this,
                    TemperatureSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);





        final ListView l=(ListView)findViewById(R.id.lv);
TextView t=(TextView)findViewById(R.id.textView4);
        stringArrayList=new ArrayList<>();
        stringArrayList1=new ArrayList<>();


        SharedPreferences preferenceflood=getSharedPreferences("flood",MODE_PRIVATE);
        flood=preferenceflood.getString("flood","0");

SharedPreferences preference=getSharedPreferences("emergency",MODE_PRIVATE);
        phon=preference.getString("mob","");
         naam=preference.getString("nam","");
        final FloatingActionButton fab11 = (FloatingActionButton) findViewById(R.id.floatingActionButton);
if(naam!="") {
    fab11.setVisibility(View.GONE);
    String[] name=naam.split(",");
    String[] no=phon.split(",");
    for(int i=0;i<name.length;i++) {
        stringArrayList.add(name[i]);
        stringArrayList1.add(no[i]);
    }

    ListViewAdfrlist adapterj = new ListViewAdfrlist(MainActivity.this, stringArrayList, stringArrayList1);
    l.setAdapter(adapterj);
if(adapterj.isEmpty())
    t.setVisibility(View.GONE);
    else
    t.setVisibility(View.VISIBLE);
}
l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layoutdelete, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

String n=stringArrayList.get(position);
                String p=stringArrayList1.get(position);
stringArrayList.remove(position);
                stringArrayList1.remove(position);

                ListViewAdfrlist adapterj = new ListViewAdfrlist(MainActivity.this, stringArrayList, stringArrayList1);
                l.setAdapter(adapterj);
                SharedPreferences.Editor editor=getSharedPreferences("emergency",MODE_PRIVATE).edit();
                editor.putString("mob",phon.replace(p+",",""));
                editor.putString("nam",naam.replace(n+",",""));
                editor.apply();




            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();















        return false;
    }
});

// for adding a username and no at start only
DatabaseFriend db = new DatabaseFriend(getApplicationContext());
        List<Friends> contacts = db.getAllContacts();
int c=0;
        for (Friends cn : contacts) {
            c++;


        }
        if(c==0)
        {
            Intent i=new Intent(MainActivity.this,MainsettingActivity.class);
            startActivity(i);
        }
















        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri call=Uri.parse("tel:+911123093054");
                Intent surf=new Intent(Intent.ACTION_DIAL,call);
                startActivity(surf);

          }
        });




        fab11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, RESULT_PICK_CONTACT);



            }
        });










        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        go11();








        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

        int RC_LOCATION = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);

        String[] perms = { android.Manifest.permission.ACCESS_FINE_LOCATION,  };
        if(RC_LOCATION!=0)
        if (EasyPermissions.somePermissionPermanentlyDenied(this, Arrays.asList(perms))) {
            new AppSettingsDialog.Builder(this).build().show();
        }





    }







    @Override
    public void onConnected(Bundle bundle) {


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
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation == null) {

            startLocationUpdates1();



        }
        if (mLocation != null) {
            double latitude = mLocation.getLatitude();
            double longitude = mLocation.getLongitude();

            latlong=latitude+","+longitude;
            lat=latitude;
            longi=longitude;

            goapii();
            goapipin();
            go();
            startService();
            startLocationUpdates();

            } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }









    public void startService() {
        startService(new Intent(getBaseContext(), MyService.class));



    }
















    protected void startLocationUpdates1() {

                        mLocationRequest = LocationRequest.create()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(100000)
                                .setFastestInterval(10000);
                        // Request location updates

                        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.










                            return;
                        }

                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MainActivity.this);
                        Log.d("reque", "--->>>>");


                        finish();






    }











































































    protected void startLocationUpdates() {
        DatabaseFriend db = new DatabaseFriend(getApplicationContext());
        List<Friends> contacts = db.getAllContacts();
        String myno="98";
        for (Friends cn : contacts) {
            if(!(cn.getName().equals("")))
            {


                myno=cn.getNameDD();
            }

        }






 {


     mLocationRequest = LocationRequest.create()
             .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
             .setInterval(1000)
             .setFastestInterval(1000);
     // Request location updates

     if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
         // TODO: Consider calling
         //    ActivityCompat#requestPermissions
         // here to request the missing permissions, and then overriding
         //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
         //                                          int[] grantResults)
         // to handle the case where the user grants the permission. See the documentation
         // for ActivityCompat#requestPermissions for more details.
         return;
     }
     LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MainActivity.this);
     Log.d("reque", "--->>>>");





















     FirebaseDatabase database = FirebaseDatabase.getInstance();



     DatabaseReference myRef99 = database.getReference(myno);

    DatabaseReference myref = myRef99.child("flood");

    final String TAG = "qqqq";
    myref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            String value = dataSnapshot.getValue(String.class);
            Log.e(TAG, "Value is: " + value);
            if(value.equals("0"))
            {

                flood = "0";
                SharedPreferences.Editor editor=getSharedPreferences("flood",MODE_PRIVATE).edit();
                editor.putString("flood","0");

                editor.apply();

            }
            if (value.equals("1"))//earthquake in the area so update
            {

                flood = "1";
                SharedPreferences.Editor editor=getSharedPreferences("flood",MODE_PRIVATE).edit();
                editor.putString("flood","1");

                editor.apply();
                alertmyfriend();
                finish();}





        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    });




}




        // Create the location request if person's last location is of disaster

    }
// sends sms to emergency contacts automaticaaly with all the details of where the phone was last spotted
    private void alertmyfriend() {
        Intent ij=new Intent(this,MainalertActivity.class);
        startActivity(ij);





    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        Log.e("lococococ", String.valueOf(location.getLatitude()));
        latlong=location.getLatitude()+","+location.getLongitude();
        lat=location.getLatitude();
        longi=location.getLongitude();
        go();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            long diffTime = (curTime - lastupdate);

            if ((curTime - lastupdate) > 100) {



                float speed = Math.abs(x + y + z - lastx - lasty - lastz)/ diffTime * 10000;
                Log.v("speeed",speed+"" );
                if (speed > 2000) {
//TODO alert
MainActivity.flood = "1";
                    SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
                    editor.putString("flood", "1");
                    editor.apply();
                }

                lastx = x;
                lasty = y;
                lastz = z;




                lastupdate = curTime;
            }
        }

        if(sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            Log.e("temp",sensorEvent.values[0]+"");
            if (sensorEvent.values[0]>50){ MainActivity.flood = "1";
            SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
            editor.putString("flood", "1");
            editor.apply();}
        }










    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
Log.e("accuracyyy",""+accuracy);
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

                   String latlongnew=elv+","+elv1;
                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);
//TODO
                    Call call = apiService.getall(latlongnew,API_KEY);
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













    void goapii()
    {



        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_LONG).show();
            return;
        }
        Log.e(TAG, "whyy");

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//TODO
        Call call = apiService.getall(latlong, API_KEY);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                FriendsProcessor mytask = new FriendsProcessor();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });


        if (API_KEY1.isEmpty()) {
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_LONG).show();
            return;
        }
        Log.e(TAG, "whyy");
ApiInterface1 apiService1 =
                ApiClient.getClient1().create(ApiInterface1.class);
//TODO
        Call call1 = apiService1.getall(latlong, API_KEY1);
        call1.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                FriendsProcessor1 mytask = new FriendsProcessor1();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });





    }


void goapibloodbank()
{
    ApiInterfaceblood apiService = ApiClientblood.getClient().create(ApiInterfaceblood.class);
//TODO
    Call call = apiService.getall();
    call.enqueue(new Callback() {
        @Override
        public void onResponse(Call call, Response response) {
            Log.e(TAG, "success");
            Log.e(TAG, response.raw().request().url().toString());
            String url = response.raw().request().url().toString();
            Log.e("blll", url);
            BloodB mytask = new BloodB();
            mytask.execute(url);


        }

        @Override
        public void onFailure(Call call, Throwable t) {
            Log.e(TAG, "failureee");
        }


    });

}

    private class BloodB extends AsyncTask<String, Void, Integer> {



        public BloodB() {

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

Log.e("ZIPzz",ZIP);

                    // now process the string using the method that we implemented in the previous exercise
               String[] res=response.split("],");
                    for(int i=1;i<res.length;i++)
                    {String[] r=res[i].split(",\"");
                       Log.e("bllll",r[6].replace("\"",""));
                        if(r[6].replace("\"","").equals(ZIP)) {
                            Log.e("bllloodbank", res[i]);
                            bloodno=r[7].replace("\"","");
                            bloodloc=r[r.length-2].replace("\"","")+","+r[r.length-1].replace("\"","");
                            Log.e("bblloocc",bloodloc);
                            bbname=r[1].replace("\"","");
blba="Blood Bank Name : "+r[1].replace("\"","")+"\n\nAddress Details : "+r[5].replace("\"","")+"\n\nEmergency no. : "+r[7].replace("\"","");
                        }
                    }

/*
                    String ele = (obj.getJSONArray("results")).toString();
                    JSONObject obj1=new JSONObject(ele.replaceFirst("\\[",""));
                    String eee=obj1.getJSONArray("address_components").toString();
                    Log.e("resppp",eee);
                    JSONArray arr=obj1.getJSONArray("address_components");
                    for(int i=0;i<arr.length();i++){
                        if(arr.getString(i).contains("postal_code"))
                            Log.e("respppp", arr.getString(i));
                        JSONObject obj11=new JSONObject(arr.getString(i));
                        Log.e("resppppp", obj11.getString("long_name"));
                        ZIP=obj11.getString("long_name");
                        goapiihospital();


                    }*/


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













































    void goapipin()
    {

        if (API_KEYpin.isEmpty()) {
            Toast.makeText(getApplicationContext(), "empty", Toast.LENGTH_LONG).show();
            return;
        }


        ApiInterfacepin apiService = ApiClientpin.getClient().create(ApiInterfacepin.class);
//TODO
        Call call = apiService.getall(latlong,API_KEYpin);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                Log.e(TAG, url);
                Pincode mytask = new Pincode();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });




    }

    private class Pincode extends AsyncTask<String, Void, Integer> {



        public Pincode() {

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
                    JSONObject obj=new JSONObject(response.replace(" ",""));

                    String ele = (obj.getJSONArray("results")).toString();
                    JSONObject obj1=new JSONObject(ele.replaceFirst("\\[",""));
String eee=obj1.getJSONArray("address_components").toString();
                    Log.e("resppp",eee);
                    JSONArray arr=obj1.getJSONArray("address_components");
                  for(int i=0;i<arr.length();i++){
                      if(arr.getString(i).contains("postal_code"))
                     Log.e("respppp", arr.getString(i));
                      JSONObject obj11=new JSONObject(arr.getString(i));
                      Log.e("resppppp", obj11.getString("long_name"));
                      ZIP=obj11.getString("long_name");
                      goapiihospital();
                      goapibloodbank();


                  }


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


























































//hospital



    void goapiihospital()
    {




        ApiInterfacehosp apiService = ApiClienthospital.getClient().create(ApiInterfacehosp.class);
//TODO
        Call call = apiService.getall();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
               Hospitalprocessor mytask = new Hospitalprocessor();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });




    }

    private class Hospitalprocessor extends AsyncTask<String, Void, Integer> {



        public Hospitalprocessor() {

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
    JSONObject obj=new JSONObject(response);
//may be said illogical but looking for faster implementation so not made jsonarrays and objects
                   String data=obj.getString("data");
                    String[] dataa=data.split("\\[");
                 for(int i=3;i<1050;i++) {
                     Log.e("respon", dataa[i]);
                     String[] pin = dataa[i].split("\"");
                     String[] pinloc = dataa[i].split(",\"");
                     Log.e("responzzzz", pin[16]);
                     if(pin[16].replace(",","").equals(ZIP)) {
                         Log.e("emergencyno", dataa[i]);
                         Log.e("emergencynooo",pin[17]);
                         emergencyno=pin[17];
                         hosp=pin[1];
                         Log.e("emergencyname",pin[1]);
                         hospname=pin[1].replace("\"","");
                         hospp="Hospital Name : "+pin[1].replace("\"","")+"\n\nAddress details : "+pin[9].replace("\"","")+"\n\n Emergency no. : "+emergencyno+"\n";
                     }
                 }                        result = 1; // Successful

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
                        if(!(eleva==0)){if(Double.valueOf(elv)>0)
                            diffelevation=eleva-Double.valueOf(elv);
                        else  diffelevation=eleva+Double.valueOf(elv);
                        }
                        else {if(Double.valueOf(elv)>0)
                            eleva =  Double.valueOf(elv)-eleva;
                        else  eleva = eleva + Double.valueOf(elv);
                        }                      Log.e("elelelele", String.valueOf(eleva));
                        Log.e("differelelelele", String.valueOf(diffelevation));

                        //the condition to check if the person is inside the house during earthquake

              if(diffelevation>2)
                  Log.e("Alert","you are at risk");

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





        DatabaseFriend db = new DatabaseFriend(getApplicationContext());
        List<Friends> contacts = db.getAllContacts();
String username="",name="",myno="98";
        for (Friends cn : contacts) {
            if(!(cn.getName().equals("")))
            {

                username=cn.getName();
                name=cn.getNameD();
                myno=cn.getNameDD();
                Log.e("numbermy",myno);
            }

        }








        FirebaseDatabase database = FirebaseDatabase.getInstance();



        DatabaseReference myRef99 = database.getReference(myno);


        myRef99.child("gps").setValue(latlong+"");


        myRef99.child("user").setValue(username);

        myRef99.child("myno").setValue(myno);

        myRef99.child("friendno").setValue(MainActivity.phon);
        SharedPreferences preferenceflood=getSharedPreferences("flood",MODE_PRIVATE);
        flood=preferenceflood.getString("flood","0");
        myRef99.child("flood").setValue(flood);
        myRef99.child("elevation").setValue(diffelevation);

        final String TAG="qqqq";
        myRef99.child("gps").addListenerForSingleValueEvent(new ValueEventListener() {
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






    public static class ApiClientdead {

        public static final String BASE_URL ="https://api-us.faceplusplus.com/facepp/v3/";

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
    public interface ApiInterfacedead {

        @POST("search")
        retrofit2.Call<ResponseBody> getall(@Query("api_key") String code,
                                            @Query("api_secret") String monthact,
                                            // @Query("image_file") File file,
                                            @Query("image_url") String url,

                                            @Query("faceset_token") String faceset
        );


    }
















    public static class ApiClienthospital {

        public static final String BASE_URL ="https://data.gov.in/node/356921/datastore/export/";

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
    public interface ApiInterfacehosp {

        @GET("json")
        Call<ResponseBody> getall();

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

        @GET("json?")
        Call<ResponseBody> getall(@Query("locations") String loc, @Query("key") String apiKey);

    }

    public interface ApiInterface1 {

        @GET("nearestRoads?"+"")
        Call<ResponseBody> getall(@Query("points") String point,@Query("key") String apiKey1);

    }
    public static class ApiClientpin {

        public static final String BASE_URL ="https://maps.googleapis.com/maps/api/geocode/";

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
    public interface ApiInterfacepin {

        @GET("json?")
        Call<ResponseBody> getall(@Query("latlng") String point,@Query("key") String apiKey11);

    }

    public static class ApiClientblood{

        public static final String BASE_URL ="https://data.gov.in/node/3287321/datastore/export/";

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
    public interface ApiInterfaceblood{

        @GET("json")
        Call<ResponseBody> getall();

    }





























































































































// MainActivity



















































    private void go11() {
        ImageView img2=(ImageView)findViewById(R.id.imageView) ;

        ImageView img1=(ImageView)findViewById(R.id.imageView3) ;
        FirebaseApp.initializeApp(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference  riversRef = mStorageRef.child("earthquake.jpg");


        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference();
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(riversRef)
                .into(img1);

        StorageReference  riversRef1 = mStorageRef.child("flood.jpg");
        riversRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
  }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        mStorageRef = FirebaseStorage.getInstance().getReference();
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(riversRef1)
                .into(img2);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Main3Activity.class);
                startActivity(i);


            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Main4Activity.class);
                startActivity(i);


            }
        });



    }


        public void pickContact(View v)
{
Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
}
@Override
protected void onActivityResult(int requestCode,int resultCode, Intent data) {
// check whether the result is ok
if (resultCode == RESULT_OK) {
// Check for the request code, we might be usign multiple startActivityForReslut
switch (requestCode) {
case RESULT_PICK_CONTACT:
contactPicked(data);
break;
}
} else {
Log.e("MainActivity", "Failed to pick contact");
}

/*
              * Query the Uri and read contact details. Handle the picked contact data.
              * @param data
*/
















    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id=item.getItemId();
        if(id==R.id.setting) {

            Intent i=new Intent(MainActivity.this,MainsettingActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            Intent i = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(i);

/* Uri gmmIntentUri = Uri.parse("google.streetview:cbll=28.4590822,77.498286,0a,75y");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
          Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
          mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
          startActivity(mapIntent);
          */


        } else if (id == R.id.Emergencycontacts) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, RESULT_PICK_CONTACT);
        }
        else if (id == R.id.settingg) {
            Intent i = new Intent(MainActivity.this, MainsettingActivity.class);
            startActivity(i);
        }
        else if (id==R.id.hosp)
        { Intent i = new Intent(MainActivity.this, MainhospActivity.class);
            startActivity(i);

        }
        else if (id==R.id.bloodb)
        { Intent i = new Intent(MainActivity.this, MainbbActivity.class);
            startActivity(i);

        }
        else if (id==R.id.blue)
        {

        }
        else if (id==R.id.testmy)
        { MainActivity.flood="1";
            SharedPreferences.Editor editor=getSharedPreferences("flood",MODE_PRIVATE).edit();
            editor.putString("flood","1");

            editor.apply();
Toast.makeText(getApplicationContext(),"It is a fake test of how app works during any mishap",Toast.LENGTH_LONG).show();

        }
        else if (id==R.id.dead)
        {
            dispatchTakePictureIntent();

            recogniseface();
        }
        else if (id==R.id.about)
        {

            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.layoutabout, null);

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setTitle("About ");
            alert.setIcon(R.drawable.ic_question_answer_black_24dp);


            alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {


                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();











        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private File createImageFile(){
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            goapii();
        }
        return image;

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = createImageFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.jskgmail.lifesaver.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);

            }

        }
    }




    private void contactPicked(Intent data) {
        ListView l=(ListView)findViewById(R.id.lv);
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
// getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
//Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
// column index of the phone number
            int phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
// column index of the contact name
            int nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            Log.d("name",name);
            Log.d("phno",phoneNo);
naam=naam+name+",";
phon=phon+phoneNo+",";
            SharedPreferences.Editor editor=getSharedPreferences("emergency",MODE_PRIVATE).edit();
            editor.putString("mob",phon);
            editor.putString("nam",naam);
            editor.apply();
            stringArrayList.add(name);
            stringArrayList1.add(phoneNo);
            FloatingActionButton fab11=(FloatingActionButton)findViewById(R.id.floatingActionButton);
            fab11.setVisibility(View.GONE);
            TextView t=(TextView)findViewById(R.id.textView4);

                t.setVisibility(View.VISIBLE);

            adapter=new ListViewAdfrlist(MainActivity.this,stringArrayList,stringArrayList1);
            l.setAdapter(adapter);
// Set the value to the textviews
 } catch (Exception e) {
            e.printStackTrace();
        }

    }




    void goapidead()
    {




        ApiInterfacedead apiService1 = ApiClientdead.getClient().create(ApiInterfacedead.class);
       //TODO
        retrofit2.Call call = apiService1.getall("8eXIfwPbVhLUXV4xt9eW2xRSxWt74Fki","9xyBX7iWUUWu4msZbaAm6_XTRN9OiT5b","https://firebasestorage.googleapis.com/v0/b/attendance-4e350.appspot.com/o/06112011292-001.jpg?alt=media&token=a087ec2b-891c-4182-8df7-1917685aeb11","537c2b49a9a160655b9a3c707555af4b");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                Log.e(TAG, url);
                Dead mytask = new Dead();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });




    }

    private class Dead extends AsyncTask<String, Void, Integer> {



        public Dead() {

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

                urlConnection.setRequestMethod("POST");

                int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    String response = convertInputStreamToString(inputStream);


                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.layoutdead, null);
                  AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    TextView t=(TextView)alertLayout.findViewById(R.id.textView82);
                    t.setText(response);

                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setTitle("Identify Dead person ");
                    alert.setIcon(R.drawable.ic_cast_grey);
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


                    alert.setPositiveButton("Set", new DialogInterface.OnClickListener() {


                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();

















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


















































    private void recogniseface() {




goapidead();


    }



}
