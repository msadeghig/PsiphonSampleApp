package ee.traxnet.psiphonsampleapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements MoPubRewardedVideoListener {

    private Button bannerButton, interstitialButton, loadRewardedVideoButton, showRewardedVideoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRewardedVideoButton = findViewById(R.id.loadRewardedVideo);
        showRewardedVideoButton = findViewById(R.id.showRewardedVideo);
        bannerButton = findViewById(R.id.banner);
        interstitialButton = findViewById(R.id.interstitial);

        SdkConfiguration sdkConfiguration =
                new SdkConfiguration.Builder(BuildConfig.rewardedVideoAdUnitId).build();
        if (!MoPub.isSdkInitialized())
            MoPub.initializeSdk(this, sdkConfiguration, null);

        MoPubRewardedVideos.setRewardedVideoListener(this);
        loadRewardedVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRewardedVideoButton.setEnabled(false);
                MoPubRewardedVideos.loadRewardedVideo(BuildConfig.rewardedVideoAdUnitId);
            }
        });

        showRewardedVideoButton.setEnabled(false);
        showRewardedVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoPubRewardedVideos.showRewardedVideo(BuildConfig.rewardedVideoAdUnitId);
            }
        });

        bannerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BannerActivity.class);
                startActivity(intent);
            }
        });

        interstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InterstitialBannerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
        Toast.makeText(this, "Video Load", Toast.LENGTH_SHORT).show();
        LogUtils.LOGI("load video successful");
        if (MoPubRewardedVideos.hasRewardedVideo(BuildConfig.rewardedVideoAdUnitId))
            showRewardedVideoButton.setEnabled(true);
    }

    @Override
    public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
        Toast.makeText(this, "Rewarded Video Failed to load: " + errorCode.toString() , Toast.LENGTH_SHORT).show();
        LogUtils.LOGE("Rewarded Video Failed to load: " + errorCode.getIntCode() + ", " + errorCode.toString());
        showRewardedVideoButton.setEnabled(false);
    }

    @Override
    public void onRewardedVideoStarted(@NonNull String adUnitId) {
        LogUtils.LOGI("Video start");
        Toast.makeText(this, "Video Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoPlaybackError(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
        LogUtils.LOGE("Rewarded video playback error: " + errorCode.toString());
        Toast.makeText(this, "Video playback error", Toast.LENGTH_SHORT).show();
        showRewardedVideoButton.setEnabled(false);
    }

    @Override
    public void onRewardedVideoClicked(@NonNull String adUnitId) {
        Toast.makeText(this, "Video Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoClosed(@NonNull String adUnitId) {
        Toast.makeText(this, "Video Closed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoCompleted(@NonNull Set<String> adUnitIds, @NonNull MoPubReward reward) {
        Toast.makeText(this, "Video Completer", Toast.LENGTH_SHORT).show();
        LogUtils.LOGI("Rewarded video completed with reward " + reward.getAmount() + " " + reward.getLabel());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MoPub.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MoPub.onResume(this);
    }
}
