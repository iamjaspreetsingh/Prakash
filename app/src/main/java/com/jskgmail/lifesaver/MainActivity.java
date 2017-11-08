package com.jskgmail.lifesaver;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{
    private StorageReference mStorageRef;
    private static final int RESULT_PICK_CONTACT = 85;
    private ArrayList<String> stringArrayList, stringArrayList1;
    ListViewAdfrlist adapter;
    static String phon="99999999";static String myLocation;
    static double mylocationa;
    static double myLocationb;int ch=0;


    String TAG = "tag";
    static double lat=39.7,longi=-104;

    String latlong = "";//the realtime latitude longitude parameter
    private final static String API_KEY = "AIzaSyClHbZ-x92EYceOWKDSgT0NPZEBBEa_wnU";
    private final static String API_KEY1 = "AIzaSyCGZpTkUUlIYjYuJNOZMJKA6Ar4d7fE7Dc";
    double eleva = 0;
    double diffelevation = 0;


    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;


    FirebaseDatabase database = FirebaseDatabase.getInstance();















    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView l=(ListView)findViewById(R.id.lv);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


gogo();

          }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        stringArrayList=new ArrayList<>();
        stringArrayList1=new ArrayList<>();
        go11();









        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);









    }

















    @Override
    public void onConnected(Bundle bundle) {

        startLocationUpdates();
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
            startLocationUpdates();
        }
        if (mLocation != null) {
            double latitude = mLocation.getLatitude();
            double longitude = mLocation.getLongitude();
            latlong=latitude+","+longitude;
            lat=latitude;
            longi=longitude;
            go();
            goapii();

        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(100000)
                .setFastestInterval(10000);
        // Request location updates

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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
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
                        if(diffelevation>2.5)
                            Toast.makeText(getApplicationContext(),"Alert You are unsafe .You need to go to the nearest safe place..!!",Toast.LENGTH_LONG).show();
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




        DatabaseReference myRef99 = database.getReference(MainsettingActivity.myno);
        myRef99.setValue(latlong+"");


        myRef99.child("gps").setValue(latlong+"");


        myRef99.child("user").setValue(MainsettingActivity.username);

        myRef99.child("myno").setValue(MainsettingActivity.myno);

        myRef99.child("friendno").setValue(MainActivity.phon);
        myRef99.child("flood").setValue("0");

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




























































































// MainActivity


  void gogo()
  {


      LayoutInflater inflater = getLayoutInflater();
      View alertLayout = inflater.inflate(R.layout.layoutemergency, null);

      AlertDialog.Builder alert = new AlertDialog.Builder(this);

      // this is set the view from XML inside AlertDialog
      alert.setView(alertLayout);
      // disallow cancel of AlertDialog on click of back button and outside touch
      alert.setTitle("Emergency Call For");
         Button earth=(Button)alertLayout.findViewById(R.id.button2);
      earth.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Uri call=Uri.parse("+911123093054");
              Intent surf=new Intent(Intent.ACTION_DIAL,call);
              startActivity(surf);



          }
      });
      Button flood=(Button)alertLayout.findViewById(R.id.button2);
      flood.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              Uri call=Uri.parse("tel:+911123093054");
              Intent surf1=new Intent(Intent.ACTION_DIAL,call);
              startActivity(surf1);

          }
      });

      AlertDialog dialog = alert.create();
      dialog.show();












  }
















































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



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

phon=phoneNo;

            stringArrayList.add(name);
            stringArrayList1.add(phoneNo);
            adapter=new ListViewAdfrlist(MainActivity.this,stringArrayList,stringArrayList1);
            l.setAdapter(adapter);
// Set the value to the textviews
 } catch (Exception e) {
            e.printStackTrace();
        }

    }






}
