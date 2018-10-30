package com.jskgmail.prakash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
static ListView list;
static TextView f1,f2,f3,f4,f5,f6,f7,f8,f9;
static RelativeLayout det;
static String no;
static String latitud="28.620660,77.08127";
    ListViewAdaptersea adapter;
String hospp7;
    ArrayList<String> arrayList2=new ArrayList<>();
    ArrayList<String> arrayList22=new ArrayList<>();
    ArrayList<String> arrayList23=new ArrayList<>();
    ArrayList<String> arrayList24=new ArrayList<>();
    ArrayList<String> arrayList25=new ArrayList<>();
    ArrayList<String> arrayList26=new ArrayList<>();
    ArrayList<String> arrayList27=new ArrayList<>();
    ArrayList<String> arrayList28=new ArrayList<>();
    ArrayList<String> arrayList29 = new ArrayList<>();
    ArrayList<String> arrayList299 = new ArrayList<>();
SearchView search;
    String[] names;
     int i;
    ArrayList<String> arrayList=new ArrayList<>();
    ArrayList<String> arrayList1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);


        FloatingTextButton call = findViewById(R.id.fab);
        FloatingTextButton dirl = findViewById(R.id.fab1);


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] em = no.split(",");
                Uri call = Uri.parse("tel:" + em[0]);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);
            }
        });

        dirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + MainActivity.latitude;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });


        list=(ListView)findViewById(R.id.listsearch);
         f1=(TextView) findViewById(R.id.friendlist1);
        f2=(TextView) findViewById(R.id.friendlist3);
        f3=(TextView) findViewById(R.id.friendlist5);
        f4=(TextView) findViewById(R.id.friendlist7);
        f5=(TextView) findViewById(R.id.friendlist9);
        f6=(TextView) findViewById(R.id.friendlist11);
        f7=(TextView) findViewById(R.id.friendlist13);
        f8=(TextView) findViewById(R.id.friendlist15);
        f9=(TextView) findViewById(R.id.friendlist17);
        det=findViewById(R.id.dett);
    //    String percent=  MainActivity.percentagesending;
        TextView t=(TextView)findViewById(R.id.textView55);
   //     t.setText("My overall % : "+percent+" %");
//tt.setText(" Logged in as \n "+ConnectActivity.mynaam+"\n ("+ConnectActivity.usernamee+")");
det.setVisibility(View.GONE);
        search=  findViewById(R.id.searchView);
        search.setOnQueryTextListener( this);




    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }
    int kk=0;
    @Override
    public boolean onQueryTextChange(String newText) {

        String text = newText;
        Log.v("sosos", text);
        newText = newText.toLowerCase();
        list.setVisibility(View.VISIBLE);
        if (text.equals(""))
            list.setVisibility(View.GONE);
        arrayList2 = new ArrayList<>();
        arrayList22 = new ArrayList<>();
        arrayList23 = new ArrayList<>();
        arrayList24 = new ArrayList<>();
        arrayList25 = new ArrayList<>();
        arrayList26 = new ArrayList<>();
        arrayList27 = new ArrayList<>();
        arrayList28 = new ArrayList<>();
        arrayList29 = new ArrayList<>();
        arrayList299 = new ArrayList<>();


        adapter = new ListViewAdaptersea(SearchActivity.this, arrayList2, arrayList22, arrayList23, arrayList24
                , arrayList25, arrayList26, arrayList27, arrayList28,arrayList29,arrayList299);


        list.setAdapter(adapter);

        arrayList2 = new ArrayList<>();
        arrayList22 = new ArrayList<>();
        arrayList23 = new ArrayList<>();
        arrayList24 = new ArrayList<>();
        arrayList25 = new ArrayList<>();
        arrayList26 = new ArrayList<>();
        arrayList27 = new ArrayList<>();
        arrayList28 = new ArrayList<>();
        arrayList29 = new ArrayList<>();
        arrayList299 = new ArrayList<>();


        final String finalNewText = newText;


                arrayList2 = new ArrayList<>();
                arrayList22 = new ArrayList<>();
        arrayList23 = new ArrayList<>();
        arrayList24 = new ArrayList<>();
        arrayList25 = new ArrayList<>();
        arrayList26 = new ArrayList<>();
        arrayList27 = new ArrayList<>();
        arrayList28 = new ArrayList<>();
        arrayList29 = new ArrayList<>();
        arrayList299 = new ArrayList<>();


                adapter = new ListViewAdaptersea(SearchActivity.this, arrayList2, arrayList22,arrayList23,arrayList24
                        ,arrayList25,arrayList26,arrayList27,arrayList28,arrayList29,arrayList299);


                list.setAdapter(adapter);

                arrayList2 = new ArrayList<>();
                arrayList22 = new ArrayList<>();
        arrayList23 = new ArrayList<>();
        arrayList24 = new ArrayList<>();
        arrayList25 = new ArrayList<>();
        arrayList26 = new ArrayList<>();
        arrayList27 = new ArrayList<>();
        arrayList28 = new ArrayList<>();
        arrayList29 = new ArrayList<>();
        arrayList299 = new ArrayList<>();

                // This method is called once with the initial value and again
                // whenever data at this location is updated.



                // now process the string using the method that we implemented in the previous exercise
                JSONObject obj= null;
                try {
                    obj = new JSONObject(loadJSONFromAsset());
                    String data=obj.getString("data");
                    Log.e("comppdata",data);
                    String[] dataa=data.split("\\[");
                    for(int i=3;i<1050;i++) {
                        String[] pin = dataa[i].split("\"");
                        {
                            if (pin.length>39) {
                                String hospp = "" + pin[1].replace("\"", "");
                                String hospp1 = "" + pin[3].replace("\"", "");
                                String hospp3 = "" + pin[7].replace("\"", "");
                                String hospp4 = "" + pin[9].replace("\"", "");
                                String hospp5 = "" + pin[13].replace("\"", "");
                                String hospp6 = "" + pin[37].replace("\"", "");
                                 hospp7 = "" + pin[17].replace("\"", "");
                                String hospp8 = "" + pin[31].replace("\"", "");
                                String hospp9 = "" + pin[35].replace("\"", "");

                                 latitud=String.valueOf( pin[38].replace("\"", "").replaceFirst(",",""));

                                Log.e("Searchlatlong",MainActivity.latitude);
                                if ((hospp.toLowerCase().contains(finalNewText)) || (hospp4.toLowerCase().contains(finalNewText)))
                                    if ((!arrayList2.contains(hospp)))
                                        if ((!arrayList22.contains(hospp1)))
                                        {
                                        arrayList2.add(hospp);
                                        arrayList22.add(hospp1);
                                            arrayList23.add(hospp3);
                                            arrayList24.add(hospp4);
                                            arrayList25.add(hospp5);
                                            arrayList26.add(hospp6);
                                            arrayList27.add(hospp7);
                                            arrayList28.add(hospp8);
                                            arrayList29.add(hospp9);
                                            arrayList299.add(latitud);


                                        }

                            }





                        }

                    }} catch (JSONException e) {
                    e.printStackTrace();
                }




























        // }
        adapter = new ListViewAdaptersea(SearchActivity.this, arrayList2, arrayList22,arrayList23,arrayList24
                ,arrayList25,arrayList26,arrayList27,arrayList28,arrayList29,arrayList299);


                list.setAdapter(adapter);





return false;

    }
























    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplication().getAssets().open("hospitalapi.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }














}










