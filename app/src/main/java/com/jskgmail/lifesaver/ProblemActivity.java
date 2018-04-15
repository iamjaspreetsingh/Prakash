package com.jskgmail.lifesaver;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

public class ProblemActivity extends AppCompatActivity {
int problem=0;
RelativeLayout rl;
static String hash="0xf79f41336f2b9468c59d7a759f534e1bb8c1e8343a9203ff41c90bf3e60ed0a4";
    String hashgen="Transaction initiated"+hash;
    TextView cno;

    final String[] problems = {"Click here and select "};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);

        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Read from the database


rl=findViewById(R.id.r);

cno=findViewById(R.id.textView17);








getlatestcompno();







        final CheckedTextView spinner=findViewById(R.id.spinner2);
        final EditText desc=findViewById(R.id.editText2);
        final EditText name=findViewById(R.id.editText3);
        SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);
        String username = prefs.getString("username", "");

        final String uid= prefs.getString("uid", "");
        name.setText(username);
        final EditText mob=findViewById(R.id.editText4);
        final EditText addr=findViewById(R.id.editText5);

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.problems, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(ProblemActivity.this);
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setTitle("Problem ");

                final CheckedTextView checkedTextView=alertLayout.findViewById(R.id.checkedTextView);
                final CheckedTextView checkedTextView1=alertLayout.findViewById(R.id.checkedTextView1);
                final CheckedTextView checkedTextView2=alertLayout.findViewById(R.id.checkedTextView2);
                final CheckedTextView checkedTextView3=alertLayout.findViewById(R.id.checkedTextView3);
                final CheckedTextView checkedTextView4=alertLayout.findViewById(R.id.checkedTextView4);
                final CheckedTextView checkedTextView5=alertLayout.findViewById(R.id.checkedTextView5);
                if (checkedTextView.isChecked()) {
                    checkedTextView.setChecked(false);
                    checkedTextView.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                }else{checkedTextView.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                    checkedTextView.setChecked(true);
                }
                if (checkedTextView1.isChecked()) {
                    checkedTextView1.setChecked(false);
                    checkedTextView1.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                }else{checkedTextView1.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                    checkedTextView1.setChecked(true);
                }
                if (checkedTextView2.isChecked()) {
                    checkedTextView2.setChecked(false);
                    checkedTextView2.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                }else{checkedTextView2.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                    checkedTextView2.setChecked(true);
                }
                if (checkedTextView3.isChecked()) {
                    checkedTextView3.setChecked(false);
                    checkedTextView3.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                }else{checkedTextView3.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                    checkedTextView3.setChecked(true);
                }
                if (checkedTextView4.isChecked()) {
                    checkedTextView4.setChecked(false);
                    checkedTextView4.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                }else{checkedTextView4.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                    checkedTextView4.setChecked(true);
                }
                if (checkedTextView5.isChecked()) {
                    checkedTextView5.setChecked(false);
                    checkedTextView5.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                }else{checkedTextView5.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                    checkedTextView5.setChecked(true);
                }
                checkedTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkedTextView.isChecked()) {
                            checkedTextView.setChecked(false);
                            checkedTextView.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                        }else{checkedTextView.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                            checkedTextView.setChecked(true);
                        }}
                });
                checkedTextView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkedTextView1.isChecked()) {
                            checkedTextView1.setChecked(false);
                            checkedTextView1.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                        }else{checkedTextView1.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                            checkedTextView1.setChecked(true);
                        }}
                });
                checkedTextView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkedTextView2.isChecked()) {
                            checkedTextView2.setChecked(false);
                            checkedTextView2.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                        }else{checkedTextView2.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                            checkedTextView2.setChecked(true);
                        }}
                });  checkedTextView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkedTextView3.isChecked()) {
                            checkedTextView3.setChecked(false);
                            checkedTextView3.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                        }else{checkedTextView3.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                            checkedTextView3.setChecked(true);
                        }}
                });  checkedTextView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkedTextView4.isChecked()) {
                            checkedTextView4.setChecked(false);
                            checkedTextView4.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                        }else{checkedTextView4.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                            checkedTextView4.setChecked(true);
                        }}
                });  checkedTextView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkedTextView5.isChecked()) {
                            checkedTextView5.setChecked(false);
                            checkedTextView5.setCheckMarkDrawable(R.drawable.ic_done_black_24dp);
                        }else{checkedTextView5.setCheckMarkDrawable(R.drawable.ic_arrow_back_black_24dp);
                            checkedTextView5.setChecked(true);
                        }}
                });
                alert.setIcon(R.drawable.ic_report_problem_black_24dp);
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                alert.setPositiveButton("Set", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        problems[0] =("");
                        if (!checkedTextView.isChecked()) problems[0] =problems[0] +("Food Shortage");
                         if (!checkedTextView1.isChecked()) problems[0] =problems[0] +("Water Shortage");
                          if (!checkedTextView2.isChecked()) problems[0] =problems[0] +("Electricity Supply");
                          if (!checkedTextView3.isChecked()) problems[0] =problems[0] +("Sewage Problem");
                         if (!checkedTextView4.isChecked()) problems[0] =problems[0] +("Shelter Problem");
                         if (!checkedTextView5.isChecked()) problems[0] =problems[0] +("Any other");
                        if (problems[0].equals(""))problems[0]="Click here and select";
spinner.setText(problems[0]);














                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();






            }
        });

spinner.setText(problems[0]);
        final RelativeLayout r=findViewById(R.id.r);
        FloatingTextButton don=findViewById(R.id.fab);
        don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.howproblem, null);
                    final EditText pubkey=(EditText)alertLayout.findViewById(R.id.editText6);
                    final EditText prikey=(EditText)alertLayout.findViewById(R.id.editText7);
                    final TextView bal=alertLayout.findViewById(R.id.textView46);
                final TextView firebase=alertLayout.findViewById(R.id.textView47);
                    AlertDialog.Builder alert = new AlertDialog.Builder(ProblemActivity.this);

                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setTitle("My Account ");
                    alert.setIcon(R.drawable.ic_account_circle_black_24dp);

                    firebase.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {



                            Toast toast = new Toast(getApplicationContext());
                            ImageView view1 = new ImageView(getApplicationContext());
                            view1.setImageResource(R.mipmap.done);
                            toast.setView(view1);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.show();

                            SharedPreferences.Editor editor= getSharedPreferences("acckeys",MODE_PRIVATE).edit();
                            editor.putString("my_compno", "fire");
                            editor.apply();

                            finish();



                        }
                    });

                DatabaseReference myRef = database.getReference("PROBLEMS");

                DatabaseReference myRef1 = myRef.child(uid);

                myRef1.child("GPS").setValue(MainActivity.lat+","+MainActivity.longi);
                myRef1.child("Prob").setValue(problems[0]);
                myRef1.child("Description").setValue(desc.getText().toString());
                myRef1.child("Name").setValue(name.getText().toString());
                myRef1.child("Mobile").setValue(mob.getText().toString());
                myRef1.child("Address").setValue(addr.getText().toString());


                SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);
                String pub = prefs.getString("public", null);

                String bal1 = prefs.getString("balance", "N.A.");

                bal.setText("BALANCE: "+bal1);
                if (pub!=null)
                {

                    pubkey.setText(pub);
                }
                String pri = prefs.getString("private", null);
                if (pri!=null)
                    prikey.setText(pri);

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });


                    alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {


                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SharedPreferences.Editor editor= getSharedPreferences("acckeys",MODE_PRIVATE).edit();
                            editor.putString("public",pubkey.getText().toString());
                            editor.putString("private",prikey.getText().toString());

                            editor.apply();

                           registertransaction(desc.getText().toString(),name.getText().toString(),mob.getText().toString());


                            Snackbar mySnackbar = Snackbar.make(rl, hashgen, Snackbar.LENGTH_LONG);
                            mySnackbar.setAction("View", new ViewHash());
                            mySnackbar.show();




                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();














            }
        });



}
    void getlatestcompno()
    {

        ApiInterfacelatestcomp apiService = ApiClientcomp.getClient().create(ApiInterfacelatestcomp.class);
//TODO
        Call call = apiService.getall();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                String url = response.raw().request().url().toString();
                Log.e("compno", url);
                BloodB1 mytask = new BloodB1();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }


        });

    }
void registertransaction(String descr,String nam,String mob)
{
    SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);
    String pub = prefs.getString("public", null);
    String pri = prefs.getString("private", null);
        ApiInterfacecomp apiService = ApiClientcomp.getClient().create(ApiInterfacecomp.class);
//TODO
        Call call = apiService.getall(pub,descr,nam,mob,problems[0],pri);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                String url = response.raw().request().url().toString();
                Log.e("compether", url);
               // Snackbar mySnackbar = Snackbar.make(rl, hashgen, Snackbar.LENGTH_LONG);
              //  mySnackbar.setAction("View", new Complaint.ViewHash());
              //  mySnackbar.show();



                Complaint mytask = new Complaint();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }


        });

    }
    public static class ApiClientcomp {

        public static final String BASE_URL ="http://18.217.79.51:8080/";

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
    public interface ApiInterfacecomp {
        @GET("complain/{p1}/{p2}/{p3}/{p4}/{p5}/{p6}")
        retrofit2.Call<ResponseBody> getall(

                                          @Path("p1") String pubkey,
                                          @Path("p2") String desc,
                                          @Path("p3") String name,
                                          @Path("p4") String mob,
                                          @Path("p5") String prob,
                                          @Path("p6") String prikey
        );


    }

    public interface ApiInterfacelatestcomp {
        @GET("latestcomplaint")
        retrofit2.Call<ResponseBody> getall(



        );


    }
    private class BloodB1 extends AsyncTask<String, Void, Integer> {



        public BloodB1() {

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

                    final String response = convertInputStreamToString(inputStream);

                        Log.e("complaintno", response);
                        final int compno=Integer.parseInt(response.replace("\"", "")) ;
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {

                                          cno.setText("Your complaint ID is : "+compno);

                                      }
                                  });

                    SharedPreferences.Editor editor= getSharedPreferences("acckeys",MODE_PRIVATE).edit();
                    editor.putString("my_compno", String.valueOf(compno));
                    editor.apply();



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

                //if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    String response = convertInputStreamToString(inputStream)+"aa";
                Log.e("comphashhh",response);

             //   hash=""+response;

                    hashgen="Transaction initiated"+response;



                    Log.e("comphashhh",hashgen);


                          //  Snackbar mySnackbar = Snackbar.make(rl, hashgen, Snackbar.LENGTH_LONG);
                        //    mySnackbar.setAction("View", new ViewHash());
                         //   mySnackbar.show();


                    // now process the string using the method that we implemented in the previous exercise




                //    getlatestcompno();




                    result = 1; // Successful

              //  } else {

             //       result = 0; //"Failed to fetch data!";

              //  }

            } catch (Exception e) {
                Log.e("compppp", String.valueOf(e));



            }

            return result; //"Failed to fetch data!";

        }


    }

    private class ViewHash implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Intent i=new Intent(ProblemActivity.this,WebhashActivity.class);
            startActivity(i);

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





































































}
