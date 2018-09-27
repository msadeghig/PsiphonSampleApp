package ee.traxnet.psiphonsampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.NativeAd;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.ViewBinder;

public class NativeBannerActivity extends Activity implements MoPubNative.MoPubNativeNetworkListener {

    private Button btn;
    private MoPubNative moPubNative;
    private ViewBinder viewBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        moPubNative = new MoPubNative(
                this,
                BuildConfig.nativeBannerAdUnitId,
                this
        );
        btn = findViewById(R.id.loadBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moPubNative.makeRequest();
            }
        });
        viewBinder = new ViewBinder.Builder(R.layout.item_native)
                .mainImageId(R.id.native_main_image)
                .iconImageId(R.id.native_icon_image)
                .titleId(R.id.native_title)
                .textId(R.id.native_text)
                .privacyInformationIconImageId(R.id.native_privacy_information_icon_image)
                .build();
        MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(viewBinder);
        moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onNativeLoad(NativeAd nativeAd) {
        LogUtils.LOGE("Native result: " + nativeAd.toString());
        Toast.makeText(this, "Native Banner Load", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNativeFail(NativeErrorCode nativeErrorCode) {
        LogUtils.LOGE("Native result: " + nativeErrorCode.toString());
        Toast.makeText(this, "Native Banner Failed", Toast.LENGTH_SHORT).show();
    }
}
