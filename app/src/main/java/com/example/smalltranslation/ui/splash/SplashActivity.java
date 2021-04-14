package com.example.smalltranslation.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.smalltranslation.R;
import com.example.smalltranslation.ui.translation.TranslateActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Start Translate Activity
        startActivity(new Intent(SplashActivity.this, TranslateActivity.class));
        // Finish Splash Activity
        finish();
    }

}