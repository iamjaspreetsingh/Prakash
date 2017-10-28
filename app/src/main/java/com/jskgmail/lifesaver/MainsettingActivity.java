package com.jskgmail.lifesaver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainsettingActivity extends AppCompatActivity {
    static String username="",myno="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsetting);
        final EditText user=(EditText)findViewById(R.id.user);
        Button don=(Button)findViewById(R.id.don);
       final EditText no=(EditText)findViewById(R.id.no);
        final EditText name=(EditText)findViewById(R.id.editText);
        DatabaseFriend db = new DatabaseFriend(getApplicationContext());
        List<Friends> contacts = db.getAllContacts();

        for (Friends cn : contacts) {
           if(!(cn.getName().equals("")))
           {
               user.setText(cn.getName());
name.setText(cn.getNameD());
               no.setText(cn.getNameDD());
           }

        }





        don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=user.getText().toString();
                myno=no.getText().toString();
                DatabaseFriend db = new DatabaseFriend(getApplicationContext());
                List<Friends> contacts = db.getAllContacts();

                for (Friends cn : contacts) {
                    if(!(cn.getName().equals("")))
                    db.deleteContact(cn);
                }


                db.addContact(new Friends(username,name.getText().toString(),myno));





            }
        });



    }
}
