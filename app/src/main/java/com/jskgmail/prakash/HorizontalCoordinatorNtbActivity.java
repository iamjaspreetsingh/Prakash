package com.jskgmail.prakash;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.borjabravo.readmoretextview.ReadMoreTextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class HorizontalCoordinatorNtbActivity extends Activity {
    String bal="N.A.";
    int ch=-1;
     TextView bala,amt,totalfunds;
//reused
    static String hash="0xf79f41336f2b9468c59d7a759f534e1bb8c1e8343a9203ff41c90bf3e60ed0a4";
    String hashgen="Transaction initiated"+hash;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_coordinator_ntb);
        SharedPreferences prefs = getSharedPreferences("tf",MODE_PRIVATE);
        String restoredText = prefs.getString("tf", null);
        MainActivity.allfund=restoredText;

        totalFundRaised();

        initUI();
    }

    private void initUI() {

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {

                //   Toast.makeText(getApplicationContext(),"s"+position,Toast.LENGTH_LONG).show();
if (position==1) {
    final View view = LayoutInflater.from(
            getBaseContext()).inflate(R.layout.item_vp_list, null, false);


    ReadMoreTextView text1 = (ReadMoreTextView)view.findViewById(R.id.text_view1);
    text1.setTrimLines(3);
    text1.setText("Beginning on 15 August 2018, severe floods affected the south Indian state of Kerala, due to unusually high rainfall during the monsoon season. It was the worst flooding in Kerala in nearly a century. Over 483 people died, and 15 are missing. At least a million people were evacuated, mainly from Chengannur, Pandanad, Edanad, Aranmula, Kozhencherry, Ayiroor, Ranni, Pandalam, Kuttanad, Aluva, and Chalakudy, N.Paravur, Chendamangalam, Eloor and few places in Vypin Island. All 14 districts of the state were placed on red alert.\n\n According to the Kerala government, one-sixth of the total population of Kerala had been directly affected by the floods and related incidents. The Indian government had declared it a Level 3 Calamity, or \"calamity of a severe nature\". It is the worst flood in Kerala after the great flood of 99 that happened in 1924.");
    container.addView(view);





    return view;}
                if (position==2) {
                    final View view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.item_vp_list2, null, false);

                    TextView link=view.findViewById(R.id.textview22);
                    SpannableString content = new SpannableString("https://ropsten.etherscan.io/address/0x5bf5c9c902bb12dea9d3bcc4e858a538875552d1");
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    link.setText(content);
                    link.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(HorizontalCoordinatorNtbActivity.this,WebhashActivity.class));
                        }
                    });
                    ReadMoreTextView text1 = (ReadMoreTextView)view.findViewById(R.id.text_view1);
                    text1.setTrimLines(5);
                    text1.setText("Even when governmental and non-governmental organizations are swift to react when disaster strikes, humanitarian efforts are often harmed by a lack of transparency among different relief providers.\n\n" +
                            "Blockchain is able to quickly and easily spin up an independent system of record that could be key to providing quicker, more effective help for people affected by an emergency.\n\n" +
                            "Blockchain technology allows central systems of relief mission participants — for example, the UN, national humanitarian aid, NGOs, and others — to connect via a distributed network. For the involved parties, this means Distributed Power, Partner interoperability, Ad-hoc capabilities, privacy etc. ");

                    container.addView(view);
                    return view;}

                else  {
    final View view3 = LayoutInflater.from(
            getBaseContext()).inflate(R.layout.item_vp_list3, null, false);
totalFundRaised();

     totalfunds=view3.findViewById(R.id.bal);

                    totalfunds.setText(MainActivity.allfund);

                    TextView r=view3.findViewById(R.id.rank);
                    r.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            go();




                        }
                    });


                    TextView r1=view3.findViewById(R.id.verify);
                    r1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {







                            go1();




                        }
                    });

                    container.addView(view3);

                    return view3;
}
              }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_list_black_24dp),
                        Color.parseColor(colors[2]))
                        .title("DETAILS")
                        .build()
        );


        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_device_hub_black_24dp),
                        Color.parseColor(colors[1]))
                        .title("DONATIONS")
                        .build()
        );



        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_touch_app_black_24dp),
                        Color.parseColor(colors[0]))
                        .title("ABOUT")
                        .build()
        );


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 1);

        //IMPORTANT: ENABLE SCROLL BEHAVIOUR IN COORDINATOR LAYOUT
        navigationTabBar.setBehaviorEnabled(true);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                model.hideBadge();
            }
        });
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.parent);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.reliefproblem, null);
           //     final EditText pubkey=(EditText)alertLayout.findViewById(R.id.editText6);
             //   final EditText prikey=(EditText)alertLayout.findViewById(R.id.editText7);
                final TextView bal=alertLayout.findViewById(R.id.textView46);
                AlertDialog.Builder alert = new AlertDialog.Builder(HorizontalCoordinatorNtbActivity.this);

                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setTitle("My Account ");
                alert.setIcon(R.drawable.ic_account_circle_black_24dp);

                final int[] cityno = {0};
                Spinner cities=alertLayout.findViewById(R.id.cities);
                final EditText amt=alertLayout.findViewById(R.id.amt);
                List<String> category1 = new ArrayList<String>();
                category1.add(" --- All Cities --- ");

                category1.add("Delhi");
                category1.add("Bihar");
                category1.add("Chennai");
                category1.add("Ahmedabad");
                category1.add("Gujarat");
                category1.add("Himachal Pradesh");
                category1.add("Jaipur");
                category1.add("Jharkhand");
                category1.add("Punjab");
                category1.add("Karnataka");
                category1.add("Kerala");
                category1.add("Maharashtra");
                category1.add("Rajasthan");
                category1.add("Tamil Nadu");
                category1.add("Uttar Pradesh");
                category1.add("Uttarakhand");
                category1.add("West Bengal");


                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(HorizontalCoordinatorNtbActivity.this, android.R.layout.simple_spinner_item, category1);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cities.setAdapter(dataAdapter1);
                cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                        cityno[0] =position;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });














                SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);

                final String pub = prefs.getString("public", null);

                String bal1 = prefs.getString("balance", "N.A.");

                bal.setText("BALANCE: "+bal1);
                if (pub!=null)
                {

              //      pubkey.setText(pub);
                }
                final String pri = prefs.getString("private", null);
                if (pri!=null)
              //      prikey.setText(pri);

                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                alert.setPositiveButton("DONATE", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences.Editor editor= getSharedPreferences("acckeys",MODE_PRIVATE).edit();
                  //      editor.putString("public",pubkey.getText().toString());
                    //    editor.putString("private",prikey.getText().toString());

                      //  editor.apply();

                    String am=    String.valueOf(Float.parseFloat(amt.getText().toString())*10000)+"0000000000000";
                    am=am.replace(".","");
                      //  T=nuloast.makeText(getApplicationContext(),cityno[0]+amt.getText().toString()+pubkey.getText().toString()+prikey.getText().toString(),Toast.LENGTH_LONG).show();
                   if (pub!=null&&pri!=null)
                        idonate(cityno[0],am,pub,pri);
                   else
                       Toast.makeText(getApplicationContext(),"You have no account registered yet. Got to My Accounts",Toast.LENGTH_LONG).show();
                       // registertransaction(desc.getText().toString(),name.getText().toString(),mob.getText().toString());

Log.e("aaaawwwww",am);
                        Log.e("aaaawwwww", String.valueOf(cityno[0]));

                       Snackbar mySnackbar = Snackbar.make(coordinatorLayout, hashgen, Snackbar.LENGTH_LONG);
                        mySnackbar.setAction("View", new ViewHash());
                        mySnackbar.show();




                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();



    }
        });

        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#fbfbfb"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#fbfbfb"));
    }





    private void go1() {

        ch=1;

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.item_vp_list35, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(HorizontalCoordinatorNtbActivity.this);

        SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);

        final String pub = prefs.getString("public", null);

        EditText pb=alertLayout.findViewById(R.id.pk);
        if (pub!=null)
        pb.setText(pub);

         bala=alertLayout.findViewById(R.id.don);

        Button find=alertLayout.findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allDonation(pub);


            }
        });



        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        AlertDialog dialog = alert.create();
        dialog.show();



    }













    private void go() {
ch=2;
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.item_vp_list34, null);
        final int[] cityno = {0};
        Spinner cities=alertLayout.findViewById(R.id.cities);
         amt=alertLayout.findViewById(R.id.amt);
         amt.setText(MainActivity.allfund + " ethers");

        List<String> category1 = new ArrayList<String>();
        category1.add("        - All Cities -");

        category1.add("Delhi");
        category1.add("Bihar");
        category1.add("Chennai");
        category1.add("Ahmedabad");
        category1.add("Gujarat");
        category1.add("Himachal Pradesh");
        category1.add("Jaipur");
        category1.add("Jharkhand");
        category1.add("Punjab");
        category1.add("Karnataka");
        category1.add("Kerala");
        category1.add("Maharashtra");
        category1.add("Rajasthan");
        category1.add("Tamil Nadu");
        category1.add("Uttar Pradesh");
        category1.add("Uttarakhand");
        category1.add("West Bengal");


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities.setAdapter(dataAdapter1);
        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
cityno[0] =position;
if (position==0)
{         amt.setText(MainActivity.allfund + " ethers");


}else
stateContributer(cityno[0]);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        AlertDialog.Builder alert = new AlertDialog.Builder(HorizontalCoordinatorNtbActivity.this);

        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        AlertDialog dialog = alert.create();
        dialog.show();



    }


    private class ViewHash implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Intent i=new Intent(HorizontalCoordinatorNtbActivity.this,WebhashActivity.class);
            startActivity(i);

        }
    }



    void idonate(int city,String amount,String pub,String pri)
    {
        ApiInterfaceidonate apiService = ApiClientidonate.getClient().create(ApiInterfaceidonate.class);
//TODO
        Call call = apiService.getall(city,amount,pub,pri);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                String url = response.raw().request().url().toString();
                Log.e("qqqq", url+"g");
                Complaint mytask = new Complaint();
                mytask.execute(url);
              //  Toast.makeText(getApplicationContext(),""+url,Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }


        });

    }









    void totalFundRaised()
    {
       // SharedPreferences prefs = getSharedPreferences("acckeys",MODE_PRIVATE);
       // String publ = prefs.getString("public", null);

        ApiInterfacecomp apiService = ApiClientcomp.getClient().create(ApiInterfacecomp.class);
//TODO
        Call call = apiService.getall();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                String url = response.raw().request().url().toString();
                Log.e("totslfund", url);
                TotFunds mytask = new TotFunds();
                mytask.execute(url);

          //      Toast.makeText(getApplicationContext(),""+url,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }


        });

    }

    void stateContributer(int city)
    {
        Log.e("cccccccccccity", String.valueOf(city));
        ApiInterfacecity apiService = ApiClientcity.getClient().create(ApiInterfacecity.class);
//TODO
        Call call = apiService.getall(city);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                String url = response.raw().request().url().toString();
                Log.e("cccccccccccity", url);
                Complaint mytask = new Complaint();
                mytask.execute(url);
      //          Toast.makeText(getApplicationContext(),""+url,Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }


        });

    }

    void allDonation(String pub)
    {
        ApiInterfacedonation apiService = ApiClientdonation.getClient().create(ApiInterfacedonation.class);
//TODO
        Log.e("doddssdsn", pub);

        Call call = apiService.getall(pub);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                String url = response.raw().request().url().toString();
                Log.e("doddssdsn", url);
                Complaint mytask = new Complaint();
                mytask.execute(url);
             //   Toast.makeText(getApplicationContext(),""+url,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }


        });

    }








    public static class ApiClientcomp {

        public static final String BASE_URL ="http://18.224.251.147:8080/";

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
        @GET("amountRaised")
        retrofit2.Call<ResponseBody> getall(

        );


    }


    public static class ApiClientcity {

        public static final String BASE_URL ="http://18.224.251.147:8080/";

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

    public interface ApiInterfacecity {
        @GET("cityContribution/{p1}")
        retrofit2.Call<ResponseBody> getall(
                @Path("p1") int city

        );


    }



    public static class ApiClientdonation {

        public static final String BASE_URL ="http://18.224.251.147:8080/";

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

    public interface ApiInterfacedonation {
        @GET("balanceOf/{p1}")
        retrofit2.Call<ResponseBody> getall(
                @Path("p1") String pk

        );


    }






    public static class ApiClientidonate {

        public static final String BASE_URL ="http://18.224.251.147:8080/";

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

    public interface ApiInterfaceidonate {
        @GET("addDonation/{p1}/{p2}/{p3}/{p4}")
        retrofit2.Call<ResponseBody> getall(
                @Path("p1") int city,
                @Path("p2") String amt,
                @Path("p3") String pub,
                @Path("p4") String pri


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

                final URL url = new URL(urls[0]);
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


                    Log.e("compbalanceee",response.replace("\"",""));

                    bal=response.replace("\"","");
                  //  else bal= String.valueOf(a);


                    Log.e("thiii",bal);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.e("utllll", String.valueOf(url));
                    //        Toast.makeText(getApplicationContext(), String.valueOf(url), Toast.LENGTH_LONG).show();
                            if(ch==1) {


    Log.e("compbalanceee",response.replace("\"",""));
    final String[] a=response.replace("\"","").replace(" ","").split("");
    Log.e("compbalanceee", String.valueOf(a.length));
String myd="0 eth";
    if (a.length==21)
        myd=a[1]+""+a[2]+"."+a[3]+""+a[4]+""+a[5]+" ethers";
    else if (a.length==20)
        myd=a[1]+"."+a[2]+""+a[3]+""+a[4]+""+a[5]+" ethers";

    else  if (a.length==22)
        myd=a[1]+""+a[2]+""+a[3]+"."+a[4]+""+a[5]+" ethers";

    else myd= response.replace("\"","")+"wei";


    bala.setText(myd
    );

}
else if (ch==2)
{

String aa = "0";
    String[] a=response.replace("\"","").replace(" ","").split("");
    Log.e("compbalanceee", String.valueOf(a.length));

    if (a.length==21)
        aa=a[1]+""+a[2]+"."+a[3]+""+a[4]+""+a[5]+"";
    else if (a.length==20)
        aa=a[1]+"."+a[2]+""+a[3]+""+a[4]+""+a[5]+"";

    else  if (a.length==22)
        aa=a[1]+""+a[2]+""+a[3]+"."+a[4]+""+a[5]+"";

    amt.setText(aa+" ethers");

}



                    }
                            });



                    // this is set the view from XML inside AlertDialog














                    result = 1; // Successful

                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {



            }

            return result; //"Failed to fetch data!";

        }

    }




    private class TotFunds extends AsyncTask<String, Void, Integer> {



        public TotFunds() {

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




                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          Log.e("compbalanceee",response.replace("\"",""));
                                          final String[] a=response.replace("\"","").replace(" ","").split("");
                                          Log.e("compbalanceee", String.valueOf(a.length));

                                          if (a.length==21)
                                          MainActivity.    allfund=a[1]+""+a[2]+"."+a[3]+""+a[4]+""+a[5]+"";
                                          else if (a.length==20)
                                              MainActivity.    allfund=a[1]+"."+a[2]+""+a[3]+""+a[4]+""+a[5]+"";

                                          else  if (a.length==22)
                                              MainActivity.allfund=a[1]+""+a[2]+""+a[3]+"."+a[4]+""+a[5]+"";

                                          else MainActivity.allfund=a[1]+""+a[2]+""+a[3]+""+a[4]+""+a[5]+" wei";

                                          Log.e("compbalaneth", String.valueOf(MainActivity.allfund));

                                     totalfunds.setText(MainActivity.allfund);

                                          SharedPreferences.Editor editor= getSharedPreferences("tf",MODE_PRIVATE).edit();
                                          editor.putString("tf",MainActivity.allfund);


                                          editor.apply();


                                      }
                                  });


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












}
