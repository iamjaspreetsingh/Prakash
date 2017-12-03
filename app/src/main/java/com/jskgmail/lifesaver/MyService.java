package com.jskgmail.lifesaver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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

/**
 * Created by TutorialsPoint7 on 8/23/2016.
 */

public class MyService extends Service implements SensorEventListener {

    private Sensor senAccelerometer;
    private Sensor TemperatureSensor;
    private SensorManager senSensorManager;
    float lastx=0,lasty=0,lastz=0;
    long lastupdate = System.currentTimeMillis();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
public MyService()
{

}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
       Log.e("myservice","Ongoing");


final String TAG="service";



        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        TemperatureSensor = senSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        senSensorManager.registerListener(this,
                TemperatureSensor,
                SensorManager.SENSOR_DELAY_NORMAL);











       ApiInterfaceearthquake apiServiceearth = ApiClientearthquake.getClient().create(ApiInterfaceearthquake.class);
//TODO
        Call calle = apiServiceearth.getall();
        calle.enqueue(new Callback() {
            @Override
            public void onResponse(Call calle, Response response) {
                Log.e(TAG, "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
               Earthquakefinder mytask = new Earthquakefinder();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });



        if(MainActivity.flood.equals("1"))
{    Intent i=new Intent(MyService.this,MainActivity.class);
        startActivity(i);
onDestroy();}








        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }



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


















public class Earthquakefinder extends AsyncTask<String, Void, Integer> {



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
                    SharedPreferences.Editor editor=getSharedPreferences("flood",MODE_PRIVATE).edit();
                    editor.putString("flood","1");

                    editor.apply();








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
                if (speed > 4000) {
//TODO alert
                    MainActivity.flood = "1";
                    SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
                    editor.putString("flood", "1");
                    editor.apply();
                    Intent intent=new Intent(MyService.this,MainalertActivity.class);
                    startActivity(intent);
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

























}