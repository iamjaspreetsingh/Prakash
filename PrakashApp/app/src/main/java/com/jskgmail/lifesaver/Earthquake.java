package com.jskgmail.lifesaver;

import android.os.AsyncTask;
import android.util.Log;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by JASPREET SINGH on 01-04-2018.
 */

public class Earthquake {


    public static class ApiClientearthquake {

        public static final String BASE_URL ="https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/";

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



    public interface ApiInterfaceearthquake {

        @GET("significant_hour.geojson")
        Call<ResponseBody> getall();

    }


















    public static class Earthquakefinder extends AsyncTask<String, Void, Integer> {



        public Earthquakefinder() {

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
                    String geo=  obj.getString("bbox");

                    geo=  geo.replace("[","").replace("]","");
                    Log.e("geometryyy",geo);
                    String[] geom=geo.split(",");
                    String minlatlong=geom[1]+","+geom[0];
                    String maxlatlong=geom[4]+","+geom[3];
                    Log.e("geomax",maxlatlong);
                    String[] latlongit=MainActivity.latlong.split(",");
                    //using the usgs api and checking if current location lies in the range of affected area or not
                    if((Double.valueOf(geom[1])<Double.valueOf(latlongit[0]))&&(Double.valueOf(latlongit[0])<Double.valueOf(geom[4]))
                            &&(Double.valueOf(geom[0])<Double.valueOf(latlongit[1]))&&(Double.valueOf(latlongit[1])<Double.valueOf(geom[3])))
                    {  MainActivity.flood="1";



                    }
                    result = 1; // Successful

                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {

                Log.d("yt", e.getLocalizedMessage());

            }

            return result; //"Failed to fetch data!";

        }

    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

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
