package com.jskgmail.lifesaver;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainsettingActivity extends AppCompatActivity {
    static String username="unknown",myno="087987879879",name="unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        setContentView(R.layout.activity_mainsetting);
        actionBar.setDisplayShowHomeEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Spinner currsemin = (Spinner) findViewById(R.id.spinner);

        List<String> category1 = new ArrayList<String>();
        category1.add("My Emergency Contacts");
        category1.add("Nearest Hospital");
        category1.add("Both");



        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        currsemin.setAdapter(dataAdapter1);

        currsemin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final Switch acc=(Switch)findViewById(R.id.switch3);
        final Switch temp=(Switch)findViewById(R.id.switch4);


        SharedPreferences preference=getSharedPreferences("sensor",MODE_PRIVATE);
        String acce=preference.getString("accn","1");

        SharedPreferences preference1=getSharedPreferences("sensor",MODE_PRIVATE);
        String  tempp=preference1.getString("temp","1");


if(acce.equals("1")  )acc.setChecked(true);else acc.setChecked(false);
        if (tempp.equals("1"))temp.setChecked(true);else temp.setChecked(false);




        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(acc.isChecked())
                {
                    SharedPreferences.Editor editor=getSharedPreferences("sensor",MODE_PRIVATE).edit();
                    editor.putString("accn","1");

                    editor.apply();
                }
                else {

                    SharedPreferences.Editor editor=getSharedPreferences("sensor",MODE_PRIVATE).edit();
                    editor.putString("accn","0");

                    editor.apply();
                }

            }
        });




        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(temp.isChecked())
                {
                    SharedPreferences.Editor editor=getSharedPreferences("sensor",MODE_PRIVATE).edit();
                    editor.putString("temp","1");

                    editor.apply();
                }
                else {

                    SharedPreferences.Editor editor=getSharedPreferences("sensor",MODE_PRIVATE).edit();
                    editor.putString("temp","0");

                    editor.apply();
                }




            }
        });





Button but=(Button)findViewById(R.id.but);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Long click on emergency contact which you want to delete in main screen",Toast.LENGTH_LONG).show();
            }
        });



        final EditText user=(EditText)findViewById(R.id.user);
        Button don=(Button)findViewById(R.id.don);
       final EditText no=(EditText)findViewById(R.id.no);
        final EditText name1=(EditText)findViewById(R.id.editText);
        DatabaseFriend db = new DatabaseFriend(getApplicationContext());
        List<Friends> contacts = db.getAllContacts();

        for (Friends cn : contacts) {
           if(!(cn.getName().equals("")))
           {
               user.setText(cn.getName());
name1.setText(cn.getNameD());
               no.setText(cn.getNameDD());
               username=cn.getName();
               name=cn.getNameD();
               myno=cn.getNameDD();
           }

        }





        don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=user.getText().toString();
                myno=no.getText().toString();
                name=name1.getText().toString();
                DatabaseFriend db = new DatabaseFriend(getApplicationContext());

                List<Friends> contacts = db.getAllContacts();

                for (Friends cn : contacts) {
                    if(!(cn.getName().equals("")))
                    db.deleteContact(cn);
                }


                db.addContact(new Friends(username,name1.getText().toString(),myno));




finish();
            }
        });



    }
}
