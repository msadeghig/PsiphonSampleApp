package ee.traxnet.psiphonsampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.mopub.mobileads.MoPubView;

public class BannerActivity extends Activity implements MoPubView.BannerAdListener {

    private final String TAG = "PsiphonSampleApp";
    private MoPubView moPubView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        moPubView = findViewById(R.id.adview);
        moPubView.setBannerAdListener(this);
        moPubView.setAdUnitId(BuildConfig.bannerAdUnitId);
        btn = findViewById(R.id.loadBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moPubView.loadAd();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moPubView.destroy();
    }

    @Override
    public void onBannerLoaded(MoPubView moPubView) {
        LogUtils.LOGI("Banner Loded");
        Toast.makeText(this, "Banner Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBannerFailed(MoPubView moPubView, MoPubErrorCode moPubErrorCode) {
        LogUtils.LOGE("Banner load failed" + moPubErrorCode.toString());
        Toast.makeText(this, "Banner Failed", Toast.LENGTH_SHORT);
    }

    @Override
    public void onBannerClicked(MoPubView moPubView) {
        LogUtils.LOGI("Banner Clicked");
        Toast.makeText(this, "Banner Clicked", Toast.LENGTH_SHORT);
    }

    @Override
    public void onBannerExpanded(MoPubView moPubView) {
        LogUtils.LOGW("Banner Expanded");
    }

    @Override
    public void onBannerCollapsed(MoPubView moPubView) {
        LogUtils.LOGI("Banner Collapsed");
    }
}
