package com.jskgmail.lifesaver;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProblemActivity extends AppCompatActivity {
int problem=0;
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

        final CheckedTextView spinner=findViewById(R.id.spinner2);
        final EditText desc=findViewById(R.id.editText2);
        final EditText name=findViewById(R.id.editText3);
        final EditText mob=findViewById(R.id.editText4);
        final EditText addr=findViewById(R.id.editText5);
        final String[] problems = {"Click here and select "};
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
                         if (!checkedTextView1.isChecked()) problems[0] =problems[0] +(" Water Shortage");
                          if (!checkedTextView2.isChecked()) problems[0] =problems[0] +(" Electricity Supply");
                          if (!checkedTextView3.isChecked()) problems[0] =problems[0] +(" Sewage Problem");
                         if (!checkedTextView4.isChecked()) problems[0] =problems[0] +(" Shelter Problem");
                         if (!checkedTextView5.isChecked()) problems[0] =problems[0] +(" Any other");
                        if (problems[0].equals(""))problems[0]="Click here and select ";
spinner.setText(problems[0]);

















                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();






            }
        });

spinner.setText(problems[0]);
        Button don=findViewById(R.id.button5);
        don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference myRef = database.getReference(String.valueOf(mob.getText().toString()));

                myRef.child("GPS").setValue(MainActivity.lat+","+MainActivity.longi);
                myRef.child("Prob").setValue(problems[0]);
                myRef.child("Description").setValue(desc.getText().toString());
                myRef.child("Name").setValue(name.getText().toString());
                myRef.child("Mobile").setValue(mob.getText().toString());
                myRef.child("Address").setValue(addr.getText().toString());

            }
        });


        TextView comp=findViewById(R.id.comp);
}




}
