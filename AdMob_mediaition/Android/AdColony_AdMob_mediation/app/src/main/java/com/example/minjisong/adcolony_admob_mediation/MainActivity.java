package com.example.minjisong.adcolony_admob_mediation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class MainActivity extends AppCompatActivity {

    private Button initialize;
    private Button interstitialReq;
    private Button interstitialShow;
    private Button rewardedReq;
    private Button rewardedShow;

    private String appID;
    private String interstitialUnitID;
    private String rewardedUnitID;

    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appID = getString(R.string.admob_app_id);
        interstitialUnitID = getString(R.string.admob_interstitial_adunit_id);
        rewardedUnitID = getString(R.string.admob_rewarded_video_adunit_id);

        initialize = (Button)findViewById(R.id.initializeButton);
        interstitialReq = (Button)findViewById(R.id.interstitialReq);
        interstitialShow = (Button)findViewById(R.id.interstitialShow);
        rewardedReq = (Button)findViewById(R.id.rewardedReq);
        rewardedShow = (Button)findViewById(R.id.rewardedShow);

        initialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Ads","Initializing");
                MobileAds.initialize(MainActivity.this,appID);

                //prepare InterstitialAd
                mInterstitialAd = new InterstitialAd(MainActivity.this);
                mInterstitialAd.setAdUnitId(interstitialUnitID);
                mInterstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                        Log.i("Interstitial Ads", "onAdLoaded");
                        changeToClickable(interstitialShow);
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                        Log.i("Interstitial Ads", "onAdFailedToLoad");
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                        Log.i("Interstitial Ads", "onAdOpened");
                        changeToNotClickable(interstitialShow);
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                        Log.i("Interstitial Ads", "onAdLeftApplication");
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when when the interstitial ad is closed.
                        Log.i("Interstitial Ads", "onAdClosed");
                    }
                });

                //prepare RewardedAd
                mAd = MobileAds.getRewardedVideoAdInstance(MainActivity.this);
                mAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onRewardedVideoAdLoaded() {
                        Log.i("Rewarded Ads", "onAdLoaded");
                        changeToClickable(rewardedShow);
                    }

                    @Override
                    public void onRewardedVideoAdOpened() {
                        Log.i("Rewarded Ads", "onAdOpened");
                        changeToNotClickable(rewardedShow);
                    }

                    @Override
                    public void onRewardedVideoStarted() {
                        Log.i("Rewarded Ads", "onVideoStarted");
                    }

                    @Override
                    public void onRewardedVideoAdClosed() {
                        Log.i("Rewarded Ads", "onVideoClosed");
                    }

                    @Override
                    public void onRewarded(RewardItem rewardItem) {
                        Log.i("Rewarded Ads", "onRewarded: "+ rewardItem.getAmount());
                    }

                    @Override
                    public void onRewardedVideoAdLeftApplication() {
                        Log.i("Rewarded Ads", "onLeftApp");
                    }

                    @Override
                    public void onRewardedVideoAdFailedToLoad(int i) {
                        Log.i("Rewarded Ads", "onFailToLoad: "+i);
                    }
                });

                //enable below buttons (interstitial req, rewarded req)
                changeToClickable(interstitialReq);
                changeToClickable(rewardedReq);
            }
        });

        interstitialReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Interstitial Ads","request");
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        rewardedReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Rewarded Ads","request");
                mAd.loadAd(rewardedUnitID, new AdRequest.Builder().build());
            }
        });

        interstitialShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Interstitial Ads","show");
                mInterstitialAd.show();
            }
        });

        rewardedShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Interstitial Ads","show");
                mAd.show();
            }
        });
    }

    public void changeToClickable(Button btn){
        btn.setClickable(true);
        btn.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccect_button, null));
    }

    public void changeToNotClickable(Button btn){
        btn.setClickable(false);
        btn.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisable_button, null));
    }
}
