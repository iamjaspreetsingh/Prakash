package com.jskgmail.lifesaver;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private StorageReference mStorageRef;
    private static final int RESULT_PICK_CONTACT = 85;
    private ArrayList<String> stringArrayList, stringArrayList1;
    ListViewAdfrlist adapter;
    static String phon;static String myLocation;int ch=0;
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
        go();





ogogog();



  }







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














void ogogog()
{


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










    void go1(){


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
            Log.e("My", myLocation);
            if(!(ch==1))
                go1();
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

























    private void go() {
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
