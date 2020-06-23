package com.example.interstitialandimprint;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private Button button1;
    private InterstitialAd mInterstitialAd;
    private String id;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
            }
        });

        mInterstitialAd = newInterstitialAd();
        loadInterstitial();

        //id = UUID.randomUUID().toString();
        id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showId();
            }
        });
    }


    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                button1.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                button1.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                goToNextLevel();
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private void loadInterstitial() {
        button1.setEnabled(false);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void goToNextLevel() {
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    private void showId(){
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();
    }
}