package com.example.admobrewardedadexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private RewardedAd rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(MainActivity.this);

        MaterialButton showAd = findViewById(R.id.showAd);

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(MainActivity.this, "ca-app-pub-3940256099942544/5224354917", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                rewardedAd = null;
                Toast.makeText(MainActivity.this, "Rewarded ad load failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                MainActivity.this.rewardedAd = rewardedAd;
                showAd.setEnabled(true);
                Toast.makeText(MainActivity.this, "Rewarded ad loaded successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        showAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rewardedAd != null) {
                    rewardedAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            Toast.makeText(MainActivity.this, "Ad shown successfully!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}