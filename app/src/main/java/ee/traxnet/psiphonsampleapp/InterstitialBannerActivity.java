package ee.traxnet.psiphonsampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;

public class InterstitialBannerActivity extends Activity implements MoPubInterstitial.InterstitialAdListener {

    private MoPubInterstitial mInterstitial;
    private Button mLoadButton;
    private Button mShowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_banner);

        mLoadButton = findViewById(R.id.load_button);
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShowButton.setEnabled(false);
                if (mInterstitial == null) {
                    mInterstitial = new MoPubInterstitial(
                            InterstitialBannerActivity.this,
                            BuildConfig.interstitialBannerAdUnitId
                    );
                    mInterstitial.setInterstitialAdListener(InterstitialBannerActivity.this);
                }
                mInterstitial.load();
            }
        });

        mShowButton = findViewById(R.id.show_button);
        mShowButton.setEnabled(false);
        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInterstitial.show();
            }
        });
    }


    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {
        mShowButton.setEnabled(true);
        Toast.makeText(this, "Ad Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
        mShowButton.setEnabled(false);
        final String errorMessage = (errorCode != null) ? errorCode.toString() : "";
        Toast.makeText(this, "Ad Load Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {
        mShowButton.setEnabled(false);
        Toast.makeText(this, "Interstitial Shown", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {
        Toast.makeText(this, "Dismissed", Toast.LENGTH_SHORT).show();
    }
}
