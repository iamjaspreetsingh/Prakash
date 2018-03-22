package com.jskgmail.lifesaver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class MainintroActivity extends AppIntro {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askForPermissions(new String[]{android.Manifest.permission.CAMERA}, 2); // OR
        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.


        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("title", "description", R.drawable.bt, R.color.colorred));
        addSlide(AppIntroFragment.newInstance("titxxxxxxle", "description", R.drawable.bt, R.color.colorred));

        // OPTIONAL METHODS
        // Override bar/separator color.


        addSlide(AppIntroFragment.newInstance("txcxcxitle", "description", R.drawable.bt, R.color.colorred));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));
setBackButtonVisibilityWithDone(true);
        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
setNextArrowColor(R.color.colorred);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);

        // OPTIONAL METHODS

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        Intent i=new Intent(MainintroActivity.this,EmailPasswordActivity.class);
        startActivity(i);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Intent i=new Intent(MainintroActivity.this,EmailPasswordActivity.class);
        startActivity(i);

        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }





}
