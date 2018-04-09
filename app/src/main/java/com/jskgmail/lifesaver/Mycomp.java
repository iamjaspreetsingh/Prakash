package com.jskgmail.lifesaver;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.victor.loading.rotate.RotateLoading;

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
import retrofit2.http.Path;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class Mycomp extends AppCompatActivity {
private RotateLoading rotateLoading;
    TextView des;
    TextView pro;
    TextView mob;
    TextView add;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_mycomp);
        rotateLoading=findViewById(R.id.rotateloading1);
        rotateLoading.start();
        SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);

         des=findViewById(R.id.des);
         pro=findViewById(R.id.pro);
        mob=findViewById(R.id.no);
         add=findViewById(R.id.add);
         status=findViewById(R.id.textView23111);
        final String uid= prefs.getString("uid", "");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("PROBLEMS");

        SharedPreferences pref= getSharedPreferences("acckeys",MODE_PRIVATE);
        final String getBcomp = pref.getString("my_compno", null);
        if(getBcomp!="fire")
        {

            status.setText("Status of complaint");
            getfind(getBcomp);

            FloatingTextButton floatingTextButton = findViewById(R.id.fab);
            floatingTextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    changestatus(getBcomp);
                    finish();

                    Toast.makeText(getApplicationContext(),"Complaint Deletetion initiated",Toast.LENGTH_LONG).show();
                }
            });


        }
        else {

            status.setText("Address Details");

            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (uid.equals(String.valueOf(dataSnapshot1.getKey())))

                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {


                                if (dataSnapshot2.getKey().equals("Prob")) {
                                    pro.setText("" + dataSnapshot2.getValue());

                                }
                                if (dataSnapshot2.getKey().equals("Description")) {
                                    des.setText("" + dataSnapshot2.getValue());

                                }
                                if (dataSnapshot2.getKey().equals("Address")) {
                                    add.setText("" + dataSnapshot2.getValue());

                                }
                                if (dataSnapshot2.getKey().equals("Mobile")) {
                                    mob.setText("" + dataSnapshot2.getValue());
                                }

                            }

                    }

                    rotateLoading.stop();

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value

                }
            });



            FloatingTextButton floatingTextButton = findViewById(R.id.fab);
            floatingTextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                if (uid.equals(String.valueOf(dataSnapshot1.getKey()))) {
                                    DatabaseReference myRef1 = myRef.child(uid);
                                    myRef1.removeValue();
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Your complaint is removed", Toast.LENGTH_LONG).show();
                                }

                            }

                            rotateLoading.stop();

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value

                        }
                    });


                }
            });

        }





    }



    void getfind(String compno)
    {


ApiInterfacelatestcomp apiService = ApiClientcomp.getClient().create(ApiInterfacelatestcomp.class);
//TODO
        Call call = apiService.getall(compno);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                String url = response.raw().request().url().toString();
                Log.e("compno", url);
                BloodB mytask = new BloodB();
                mytask.execute(url);
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }


        });

    }



    public static class ApiClientcomp {

        public static final String BASE_URL ="http://54.169.130.29:3000/";

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
    public interface ApiInterfacelatestcomp {
        @GET("getComplaint/{p1}")
        retrofit2.Call<ResponseBody> getall(

                @Path("p1") String compno
                        );


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

                final int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    final String response = convertInputStreamToString(inputStream);

                    Log.e("complaintwhattt", response.replace("\"",""));
                   final String response1= response.replace("\"","");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            rotateLoading.stop();


//may be said illogical but looking for faster implementation so not made jsonarrays and objects
                            String[] re = response1.replace("\"","").replace("[", "").replace("]", "")
                                    .split(",");
                            Log.e("compew0",re[0]);
                            des.setText(re[2]);
                            pro.setText(re[5]);
                            mob.setText(re[4]);
                            if (re[0].equals("9")) {
                                add.setText("Complaint Deleted");
                                add.setTextColor(Color.RED);

                            }
                            else {add.setText("Your complaint is received and is under consideration");
                                add.setTextColor(Color.BLACK);

                            }
                        }
                    });



                    //  showui(response);

                    // now process the string using the method that we implemented in the previous exercise




                    result = 1; // Successful

                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {
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



void changestatus(String getBcomp)
{
    SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);
    String pub = prefs.getString("public", null);
    String pri = prefs.getString("private", null);
    ApiInterfacecomp apiService = ApiClientcomp.getClient().create(ApiInterfacecomp.class);
//TODO
    Call call = apiService.getall(getBcomp,"9",pub,pri);
    call.enqueue(new Callback() {
        @Override
        public void onResponse(Call call, Response response) {

            String url = response.raw().request().url().toString();
            Log.e("compchangestatus", url);
            Complaint mytask = new Complaint();
            mytask.execute(url);


        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }


    });

}
    public interface ApiInterfacecomp {
        @GET("changeStatus/{p1}/{p2}/{p3}/{p4}")
        retrofit2.Call<ResponseBody> getall(

                @Path("p1") String id,
                @Path("p2") String status,
                @Path("p3") String pub,
                @Path("p4") String priv

        );


    }


    private class Complaint extends AsyncTask<String, Void, Integer> {



        public Complaint() {

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




                    result = 1; // Successful

                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {



            }

            return result; //"Failed to fetch data!";

        }

    }


}
