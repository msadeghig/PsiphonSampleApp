package ee.traxnet.psiphonsampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideo;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class RewardedVideoActivity extends Activity implements MoPubRewardedVideoListener {

    private Button loadButton;
    private Button showButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewarded_video);


        SdkConfiguration sdkConfiguration =
                new SdkConfiguration.Builder(BuildConfig.rewardedVideoAdUnitId).build();

        MoPub.initializeSdk(this, sdkConfiguration, null);

        LogUtils.LOGW("Sdk Init : " + String.valueOf(MoPub.isSdkInitialized()));
        MoPubRewardedVideos.setRewardedVideoListener(this);
        loadButton = findViewById(R.id.load_button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showButton.setEnabled(false);
                MoPubRewardedVideos.loadRewardedVideo(BuildConfig.rewardedVideoAdUnitId);
            }
        });

        showButton = findViewById(R.id.show_button);
        showButton.setEnabled(false);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoPubRewardedVideos.showRewardedVideo(BuildConfig.rewardedVideoAdUnitId);
            }
        });
    }

    @Override
    public void onRewardedVideoLoadSuccess(@NonNull String adUnitId) {
        Toast.makeText(this, "Video Load", Toast.LENGTH_SHORT).show();
        LogUtils.LOGI("load video successful");
        if (MoPubRewardedVideos.hasRewardedVideo(BuildConfig.rewardedVideoAdUnitId))
            showButton.setEnabled(true);
    }

    @Override
    public void onRewardedVideoLoadFailure(@NonNull String adUnitId, @NonNull MoPubErrorCode errorCode) {
        Toast.makeText(this, "Video Failed", Toast.LENGTH_SHORT).show();
        LogUtils.LOGE("Video load failed");
        showButton.setEnabled(false);
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
        showButton.setEnabled(false);
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
