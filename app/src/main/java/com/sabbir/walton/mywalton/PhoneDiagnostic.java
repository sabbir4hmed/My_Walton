package com.sabbir.walton.mywalton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PhoneDiagnostic extends AppCompatActivity {

    private Button simCardTestButton;
    private Button sdCardTestButton;
    private Button lcdTestButton;
    private Button mtTestButton;
    private Button stTestButton;
    private Button flashlightButton;
    private Button keyButton;
    private Button speakerButton;
    private Button recieverButton;
    private Button earphoneButton;
    private Button micButton;
    private Button callButton;
    private Button vibrateButton;
    private Button gpsButton;
    private Button wifiButton;
    private Button btButton;
    private Button fpButton;
    private Button rcButton;
    private Button fcButton;
    private Button psButton;
    private Button ctButton;
    private Button bstButton;
    private Button fullTestButton;

    private boolean fullTestInProgress = false;
    private int currentTestIndex = 0;

    private final Class<?>[] testActivities = {
            SimCardTestActivity.class,
            SdCardTestActivity.class,
            LcdTestActivity.class,
            MultiTouchTestActivity.class,
            SingleTouchTestActivity.class,
            FlashLightTestActivity.class,
            ButtonTestActivity.class,
            SpeakerTestActivity.class,
            RecieverTestActivity.class,
            EarphoneTestActivity.class,
            MicTestActivity.class,
            CallTestActivity.class,
            VibrationTestActivity.class,
            GpsTestActivity.class,
            WifiTestActivity.class,
            BlutoothTestActivity.class,
            FingerprintTestActivity.class,
            BatteryStatusTestActivity.class,
            RearCameraTestActivity.class,
            FrontCameraTestActivity.class,
            ProximitySensorTestActivity.class,
            ChargingTestActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phone_diagnostic);

        // Initialize buttons
        simCardTestButton = findViewById(R.id.simCardTestButton);
        sdCardTestButton = findViewById(R.id.sdCardTestButton);
        lcdTestButton = findViewById(R.id.lcdTestButton);
        mtTestButton = findViewById(R.id.multiTouchTestButton);
        stTestButton = findViewById(R.id.singleTouchTestButton);
        flashlightButton = findViewById(R.id.flashLightTestButton);
        keyButton = findViewById(R.id.keyTestButton);
        speakerButton = findViewById(R.id.speakerTestButton);
        recieverButton = findViewById(R.id.recieverTestButton);
        earphoneButton = findViewById(R.id.earphoneTestButton);
        micButton = findViewById(R.id.micTestButton);
        callButton = findViewById(R.id.callTestButton);
        vibrateButton = findViewById(R.id.vibrationTestButton);
        gpsButton = findViewById(R.id.gpsTestButton);
        wifiButton = findViewById(R.id.wifiTestButton);
        btButton = findViewById(R.id.BTTestButton);
        fpButton = findViewById(R.id.fPTestButton);
        rcButton = findViewById(R.id.rcTestButton);
        fcButton = findViewById(R.id.fcTestButton);
        psButton = findViewById(R.id.PsTestButton);
        ctButton = findViewById(R.id.ctTestButton);
        bstButton = findViewById(R.id.bttryTestButton);
        fullTestButton = findViewById(R.id.fullTestButton);

        // Set button listeners
        setButtonListeners();

        fullTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFullTest();
            }
        });
    }

    private void setButtonListeners() {
        for (int i = 0; i < testActivities.length; i++) {
            final int index = i;
            getButtonByRequestCode(i).setOnClickListener(v -> startTestActivity(testActivities[index], index));
        }
    }

    private void startTestActivity(Class<?> activityClass, int requestCode) {
        Intent intent = new Intent(PhoneDiagnostic.this, activityClass);
        startActivityForResult(intent, requestCode);
    }

    private void startFullTest() {
        if (fullTestInProgress) {
            Toast.makeText(this, "Full Test already in progress", Toast.LENGTH_SHORT).show();
            return;
        }

        fullTestInProgress = true;
        currentTestIndex = 0;
        disableGesturesAndNavigation();
        startNextTest();
    }

    private void startNextTest() {
        if (currentTestIndex < testActivities.length) {
            Intent intent = new Intent(this, testActivities[currentTestIndex]);
            startActivityForResult(intent, currentTestIndex);
        } else {
            fullTestInProgress = false;
            enableGesturesAndNavigation();
            Toast.makeText(this, "Full Test completed", Toast.LENGTH_SHORT).show();
        }
    }

    private void disableGesturesAndNavigation() {
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
    }

    private void enableGesturesAndNavigation() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("testResult")) {
                boolean testResult = data.getBooleanExtra("testResult", false);
                Button testButton = getButtonByRequestCode(requestCode);

                if (testButton != null) {
                    testButton.setBackgroundColor(testResult ? Color.GREEN : Color.RED);
                }
            }
        }

        if (fullTestInProgress) {
            currentTestIndex++;
            startNextTest();
        }
    }

    private Button getButtonByRequestCode(int requestCode) {
        switch (requestCode) {
            case 0: return simCardTestButton;
            case 1: return sdCardTestButton;
            case 2: return lcdTestButton;
            case 3: return mtTestButton;
            case 4: return stTestButton;
            case 5: return flashlightButton;
            case 6: return keyButton;
            case 7: return speakerButton;
            case 8: return recieverButton;
            case 9: return earphoneButton;
            case 10: return micButton;
            case 11: return callButton;
            case 12: return vibrateButton;
            case 13: return gpsButton;
            case 14: return wifiButton;
            case 15: return btButton;
            case 16: return fpButton;
            case 17: return bstButton;
            case 18: return rcButton;
            case 19: return fcButton;
            case 20: return psButton;
            case 21: return ctButton;
            default: return null;
        }
    }
}