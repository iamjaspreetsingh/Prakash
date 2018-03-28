package com.jskgmail.lifesaver;

        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.TextView;

        import com.victor.loading.rotate.RotateLoading;

        import org.json.JSONArray;
        import org.json.JSONException;

        import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MainbbActivity extends AppCompatActivity {
    private RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_mainbb);
        TextView t = (TextView) findViewById(R.id.textView20);
        t.setText("ZIP code : " + MainActivity.ZIP);

        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);
        rotateLoading.setLoadingColor(R.color.colorPrimary);

        FloatingTextButton call = findViewById(R.id.fab);
        FloatingTextButton dirl = findViewById(R.id.fab1);
        TextView l = (TextView) findViewById(R.id.ll);
        while (MainActivity.blba.equals("No BloodBank Found")) {
            if (MainActivity.blba.equals("No BloodBank Found")) {
                rotateLoading.start();
                l.setVisibility(View.VISIBLE);
            } else {
                rotateLoading.stop();
                l.setVisibility(View.GONE);
                break;
            }
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] em = MainActivity.bloodno.split(",");
                Uri call = Uri.parse("tel:" + em[0]);
                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                startActivity(surf);
            }
        });
        dirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + MainActivity.bloodloc;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });
        TextView tt = (TextView) findViewById(R.id.textView35);
        tt.setText(MainActivity.blba);
        TextView tt1 = (TextView) findViewById(R.id.textView351);
        tt1.setText(MainActivity.blba1);
        TextView tt11 = (TextView) findViewById(R.id.textView3511);
        tt11.setText(MainActivity.blba11);
        Log.e("bllba", MainActivity.blba);
        String b = null;
        try {
            JSONArray obj = new JSONArray(MainActivity.blba);
            for (int i = 0; i < obj.length(); i++) {
                b = b + (obj.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}


