package com.youtubedownloader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ReactApplicationContext;
import com.youtubedownloader.service.PackageMonitor;


public class MainActivity extends ReactActivity {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    @Override
    protected String getMainComponentName() {
        return "YoutubeDownloader";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_activity_layout);
        Log.d("bassam", "defining onClick ...");
//                initializeFloatingBubble();
//                finish(); // make the button vanish after click.
        PackageMonitor pm = new PackageMonitor((ReactApplicationContext)getReactInstanceManager().getCurrentReactContext());
        Log.d("bassam", "defining pm");
        pm.getCurrentForegroundActivity();
        Log.d("bassam", "onClick done!");
        initializeFloatingBubble();
    }

    /**
     * Set and initialize the floating bubble.
     */
    private void initializeFloatingBubble() {
        startService(new Intent(MainActivity.this, FloatingBubbleService.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (resultCode == RESULT_OK) {
                initializeFloatingBubble();
            } else {
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        stopService(new Intent(MainActivity.this, FloatingBubbleService.class));
    }
}
