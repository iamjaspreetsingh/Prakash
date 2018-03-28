package com.jskgmail.lifesaver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.jskgmail.lifesaver.beaconreference.MonitoringActivity;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.HashMap;

public class Main99Activity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;


    private SliderLayout mDemoSlider;
    private BoomMenuButton bmb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main99);


        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_3);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_3);
Log.e("ppp", ""+bmb.getPiecePlaceEnum().pieceNumber());

        final Uri[] call = new Uri[1];
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            if(index==0)
                            call[0] =Uri.parse("tel:"+"102");
                             else if (index==1)call[0] =Uri.parse("tel:"+"100");
                            else if (index==2)call[0] =Uri.parse("tel:"+"101");
                            else if (index==3)call[0] =Uri.parse("tel:"+"181");
                            else if (index==4)call[0] =Uri.parse("tel:"+"108");
                            else if (index==5) call[0] =Uri.parse("tel:"+"1098");

                            Intent surf=new Intent(Intent.ACTION_DIAL, call[0]);
                            startActivity(surf);

                        }
                    })

                    .normalImageRes(BuilderManager.getImageResource())
                    .normalText(BuilderManager.getImageText())
                    .pieceColor(Color.WHITE).normalColor(Color.WHITE);


            bmb.addBuilder(builder);
        }





        mDemoSlider = (SliderLayout)findViewById(R.id.slider);



        mViewPager = (ViewPager) findViewById(R.id.viewPager);



        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_2));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_3));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_4));
        mCardAdapter.addCardItem(new CardItem(R.string.title_5, R.string.text_5));
        mCardAdapter.addCardItem(new CardItem(R.string.title_6, R.string.text_6));
        mCardAdapter.addCardItem(new CardItem(R.string.title_7, R.string.text_7));
        mCardAdapter.addCardItem(new CardItem(R.string.title_8, R.string.text_8));
        mCardAdapter.addCardItem(new CardItem(R.string.title_9, R.string.text_9));

             Log.e( "clclclclcl", String.valueOf(mViewPager.getCurrentItem()));
        mViewPager.setClickable(true);


        Log.e("clcl", String.valueOf(mViewPager.hasOnClickListeners()));
        mViewPager.setAdapter(mCardAdapter);

        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);















        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Safety Tips: Earthquake",R.mipmap.earthquake);
        file_maps.put("Safety Tips: Flood",R.mipmap.flood);
        file_maps.put("Safety Tips: Accidents",R.drawable.acc);
        file_maps.put("Safety Tips: Fire", R.drawable.fire);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);


        ImageView ho=(ImageView)findViewById(R.id.ho);
        ImageView bb=(ImageView)findViewById(R.id.bb);
        ImageView ps=(ImageView)findViewById(R.id.ps);
        ho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Main99Activity.this,MainhospActivity.class);
                startActivity(i);
            }
        });
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Main99Activity.this,MainbbActivity.class);
                startActivity(i);
            }
        });
        ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Main99Activity.this,MainppActivity.class);
                startActivity(i);
            }
        });


        ImageView comp=(ImageView)findViewById(R.id.hoo);
        ImageView bea=(ImageView)findViewById(R.id.bob);
        ImageView img=(ImageView)findViewById(R.id.poss);
        ImageView emer=(ImageView)findViewById(R.id.pos);

        comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Main99Activity.this,ProblemActivity.class);
                startActivity(i);
            }
        });

        bea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Main99Activity.this,MonitoringActivity.class);
                startActivity(i);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        emer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.layoutemergency, null);

                AlertDialog.Builder alert = new AlertDialog.Builder(Main99Activity.this);

                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setTitle("Emergency Contacts ");
                alert.setIcon(R.drawable.ic_contacts_black_24dp);

                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();






            }
        });

    }



    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        if(slider.getBundle().get("extra").equals("Safety Tips: Earthquake"))
        { Intent i=new Intent(Main99Activity.this,Main3Activity.class);
        startActivity(i);}
        else     if(slider.getBundle().get("extra").equals("Safety Tips: Flood"))
        { Intent i=new Intent(Main99Activity.this,Main4Activity.class);
            startActivity(i);}
        else     if(slider.getBundle().get("extra").equals("Safety Tips: Fire"))
        { Intent i=new Intent(this,Main5Activity.class);
            startActivity(i);}
        else     if(slider.getBundle().get("extra").equals("Safety Tips: Accidents"))
        {  String videoId = "XpECMpNCtRE";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
            intent.putExtra("VIDEO_ID", videoId);
            startActivity(intent);}
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {


        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}



































}
