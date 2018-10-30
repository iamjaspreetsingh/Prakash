package com.jskgmail.prakash;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        static TextView f1,f2,f3,f4,f5,f6,f7,f8,f9;
        static TextView f11,f22,f33,f44,f55,f66,f77,f88,f99;

        PieChart pieChart;
        String prob[]={"Avalanche","Cyclone","Tornado","Tsunami","Earthquake","Flood","Landslide","Lightning","Other Natural Causes"};
        String prob1[]={"Air Crash","Ship Accidents"," Accidental Fire (Total)","Collapse of Dam","Collapse of  Bridge","Drowning (Total)","Road Accidents Total","Railway Accidents","Electrocution","Domestic Gas Cylinder","Fall from height"};

        private static final String ARG_SECTION_NUMBER = "section_number";
        private String cityname;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);

            assert getArguments() != null;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {

                pieChart = rootView.findViewById(R.id.piechart_1);
                f1 = (TextView) rootView.findViewById(R.id.friendlist1);
                f2 = (TextView) rootView.findViewById(R.id.friendlist3);
                f3 = (TextView) rootView.findViewById(R.id.friendlist5);
                f4 = (TextView) rootView.findViewById(R.id.friendlist7);
                f5 = (TextView) rootView.findViewById(R.id.friendlist9);
                f6 = (TextView) rootView.findViewById(R.id.friendlist11);
                f7 = (TextView) rootView.findViewById(R.id.friendlist13);
                f8 = (TextView) rootView.findViewById(R.id.friendlist15);
                f9 = (TextView) rootView.findViewById(R.id.friendlist17);

                final int[] cityno = {0};
                Spinner cities = rootView.findViewById(R.id.cities);
                // final EditText amt=findViewById(R.id.amt);
                List<String> category1 = new ArrayList<String>();
                category1.add(" --- All Cities --- ");

                category1.add("Andhra Pradesh");
                category1.add("Arunachal Pradesh");
                category1.add("Bihar");
                category1.add("Ahmedabad");
                category1.add("Gujarat");
                category1.add("Himachal Pradesh");
                category1.add("Rajasthan");
                category1.add("Delhi UT");
                category1.add("Punjab");
                category1.add("Karnataka");
                category1.add("Kerala");
                category1.add("Maharashtra");
                category1.add("Rajasthan");
                category1.add("Tamil Nadu");
                category1.add("Uttar Pradesh");
                category1.add("Madhya Pradesh");
                category1.add("Uttarakhand");
                category1.add("Karnataka");
                category1.add("West Bengal");
                category1.add("Odisha");
                category1.add("Meghalaya");
                category1.add("Manipur");
                category1.add("A & N Islands");


                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, category1);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cities.setAdapter(dataAdapter1);
                cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        cityname = String.valueOf(parent.getItemAtPosition(position));
                        cityno[0] = position;
                        //   set();

                        JSONObject obj = null;


                        try {
                            obj = new JSONObject(loadJSONFromAsset());
                            // String data=obj.getString("data");
                            JSONArray jsonArray = obj.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //     JSONArray jsonArray1=jsonArray.getJSONArray(0);
                                //  for(int j=0;j<12;j++){
                                JSONArray r = jsonArray.getJSONArray(i);
                                r.get(1);

                                for (int k = 0; k < 9; k++) {
                                    //   Log.e("qwqwqwi",r.getString(0));

                                    //  Log.e("qwqwqw",r.getString(2));
                                    //Log.e("qwqwqw",cityname);
                                    //Log.e("qwqwqw",prob[k]);

                                    if (cityname.equals(r.getString(0)))
                                        if (prob[k].equals(r.getString(2))) {
                                            String rr = r.getString(24);

                                            if (k == 0) f1.setText(rr);
                                            else if (k == 1) f2.setText(rr);
                                            else if (k == 2) f3.setText(rr);
                                            else if (k == 3) f4.setText(rr);
                                            else if (k == 4) f5.setText(rr);
                                            else if (k == 5) f6.setText(rr);
                                            else if (k == 6) f7.setText(rr);
                                            else if (k == 7) f8.setText(rr);
                                            else f9.setText(rr);
                                            //  else if (k==9)  f1.setText(rr);

                                            Log.e("qwqwqw", r.getString(2));

                                            Log.e("qwqwqwans", r.getString(24));


                                        }


                                    //    Log.e("qwqwqwi",r.getString(0));

                                    ////  Log.e("qwqwqw",r.getString(2));

                                }

                                setPieChart(f1.getText().toString(), f2.getText().toString(), f3.getText().toString(), f4.getText().toString(),
                                        f5.getText().toString(), f6.getText().toString(),
                                        f7.getText().toString(), f8.getText().toString(), f9.getText().toString());

                                //}
                            }
                            //  Log.e("comppdata",data);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                return rootView;
            } else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                View rootView1 = inflater.inflate(R.layout.fragment_main22, container, false);



                pieChart = rootView1.findViewById(R.id.piechart_1);
                f11 = (TextView) rootView1.findViewById(R.id.friendlist1);
                f22 = (TextView) rootView1.findViewById(R.id.friendlist3);
                f33 = (TextView) rootView1.findViewById(R.id.friendlist5);
                f44 = (TextView) rootView1.findViewById(R.id.friendlist7);
                f55 = (TextView) rootView1.findViewById(R.id.friendlist9);
                f66 = (TextView) rootView1.findViewById(R.id.friendlist11);
                f77 = (TextView) rootView1.findViewById(R.id.friendlist13);
                f88 = (TextView) rootView1.findViewById(R.id.friendlist15);
                f99 = (TextView) rootView1.findViewById(R.id.friendlist17);

                final int[] cityno = {0};
                Spinner cities = rootView1.findViewById(R.id.cities);
                // final EditText amt=findViewById(R.id.amt);
                List<String> category1 = new ArrayList<String>();
                category1.add(" --- All Cities --- ");

                category1.add("Andhra Pradesh");
                category1.add("Arunachal Pradesh");
                category1.add("Bihar");
                category1.add("Ahmedabad");
                category1.add("Gujarat");
                category1.add("Himachal Pradesh");
                category1.add("Rajasthan");
                category1.add("Delhi UT");
                category1.add("Punjab");
                category1.add("Karnataka");
                category1.add("Kerala");
                category1.add("Maharashtra");
                category1.add("Rajasthan");
                category1.add("Tamil Nadu");
                category1.add("Uttar Pradesh");
                category1.add("Madhya Pradesh");
                category1.add("Uttarakhand");
                category1.add("Karnataka");
                category1.add("West Bengal");
                category1.add("Odisha");
                category1.add("Meghalaya");
                category1.add("Manipur");
                category1.add("A & N Islands");


                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, category1);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cities.setAdapter(dataAdapter1);
                cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        cityname = String.valueOf(parent.getItemAtPosition(position));
                        cityno[0] = position;
                        //   set();

                        JSONObject obj = null;


                        try {
                            obj = new JSONObject(loadJSONFromAssetc());
                            // String data=obj.getString("data");
                            JSONArray jsonArray = obj.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //     JSONArray jsonArray1=jsonArray.getJSONArray(0);
                                //  for(int j=0;j<12;j++){
                                JSONArray r = jsonArray.getJSONArray(i);
                                r.get(1);

                                for (int k = 0; k < 9; k++) {
                                    //   Log.e("qwqwqwi",r.getString(0));

                                    //  Log.e("qwqwqw",r.getString(2));
                                    //Log.e("qwqwqw",cityname);
                                    //Log.e("qwqwqw",prob[k]);

                                    if (cityname.equals(r.getString(0)))
                                        if (prob1[k].equals(r.getString(2)))
                                        {
                                            String rr = r.getString(24);

                                            if (k == 0) f11.setText(rr);
                                            else if (k == 1) f22.setText(rr);
                                            else if (k == 2) f33.setText(rr);
                                            else if (k == 3) f44.setText(rr);
                                            else if (k == 4) f55.setText(rr);
                                            else if (k == 5) f66.setText(rr);
                                            else if (k == 6) f77.setText(rr);
                                            else if (k == 7) f88.setText(rr);
                                            else f99.setText(rr);
                                            //  else if (k==9)  f1.setText(rr);

                                            Log.e("crimrrgr", r.getString(2));

                                            Log.e("qwqwqwans", r.getString(24));


                                        }


                                    //    Log.e("qwqwqwi",r.getString(0));

                                    ////  Log.e("qwqwqw",r.getString(2));

                                }

                                setPieChart1(f11.getText().toString(), f22.getText().toString(), f33.getText().toString(), f44.getText().toString(),
                                        f55.getText().toString(), f66.getText().toString(),
                                        f77.getText().toString(), f88.getText().toString(), f99.getText().toString());

                                //}
                            }
                            //  Log.e("comppdata",data);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });















                return rootView1;

            }
            else return rootView;


        }













        public String loadJSONFromAsset() {
            String json = null;
            try {
                InputStream is = getContext().getAssets().open("naturalc.json");
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

        public String loadJSONFromAssetc() {
            String json = null;
            try {
                InputStream is = getContext().getAssets().open("unnaturalc.json");
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





        public void setPieChart(String a, String b, String c, String d, String e, String f, String g, String h, String i) {
            // this.pieChart = pieChart;
            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(true);
            pieChart.setExtraOffsets(10,20,10,10);
            pieChart.setDragDecelerationFrictionCoef(0.9f);
            pieChart.setTransparentCircleRadius(61f);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
            ArrayList<PieEntry> yValues = new ArrayList<>();
            yValues.add(new PieEntry(Float.parseFloat(a),"Avalanche"));
            yValues.add(new PieEntry(Float.parseFloat(b),"Cyclone"));
            yValues.add(new PieEntry(Float.parseFloat(c),"Tornado"));
            yValues.add(new PieEntry(Float.parseFloat(d),"Tsunami"));
            yValues.add(new PieEntry(Float.parseFloat(e),"Earthquake"));
            yValues.add(new PieEntry(Float.parseFloat(f),"Flood"));
            yValues.add(new PieEntry(Float.parseFloat(g),"Landslide"));
            yValues.add(new PieEntry(Float.parseFloat(h),"Lightning"));
            yValues.add(new PieEntry(Float.parseFloat(i),"Others"));


            PieDataSet dataSet = new PieDataSet(yValues, "Deaths");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            PieData pieData = new PieData((dataSet));
            pieData.setValueTextSize(8f);
            pieChart.setDrawSliceText(false);
            // pieChart.setDrawCenterText(false);

            pieData.setValueTextColor(Color.YELLOW);
            pieChart.setData(pieData);
            //PieChart Ends Here
        }


        public void setPieChart1(String a, String b, String c, String d, String e, String f, String g, String h, String i) {
            // this.pieChart = pieChart;
            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(true);
            pieChart.setExtraOffsets(10,20,10,10);
            pieChart.setDragDecelerationFrictionCoef(0.9f);
            pieChart.setTransparentCircleRadius(61f);
            pieChart.setHoleColor(Color.WHITE);
            pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
            ArrayList<PieEntry> yValues = new ArrayList<>();
            yValues.add(new PieEntry(Float.parseFloat(a),"Electrocution"));
            yValues.add(new PieEntry(Float.parseFloat(b),"Railway Accident"));
            yValues.add(new PieEntry(Float.parseFloat(c),"Road Accidents"));
            yValues.add(new PieEntry(Float.parseFloat(d),"Domestic Gas"));
            yValues.add(new PieEntry(Float.parseFloat(e),"AirCrash"));
            yValues.add(new PieEntry(Float.parseFloat(f),"Drowning"));
            yValues.add(new PieEntry(Float.parseFloat(g),"Ship"));
            yValues.add(new PieEntry(Float.parseFloat(h),"Collapse Dam"));
            yValues.add(new PieEntry(Float.parseFloat(i),"Collapse Bridge"));


            PieDataSet dataSet = new PieDataSet(yValues, "Deaths");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            PieData pieData = new PieData((dataSet));
            pieData.setValueTextSize(8f);
            pieChart.setDrawSliceText(false);
            // pieChart.setDrawCenterText(false);

            pieData.setValueTextColor(Color.YELLOW);
            pieChart.setData(pieData);
            //PieChart Ends Here
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }






















}
