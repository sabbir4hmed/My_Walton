package com.sabbir.walton.mywalton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ButtonTestActivity extends AppCompatActivity {
    private TextView buttonStatusTextView;
    private Button passButton;
    private Button failButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_test);

        buttonStatusTextView = findViewById(R.id.buttonStatusTextView);
        passButton = findViewById(R.id.passButton);
        failButton = findViewById(R.id.failButton);

        buttonStatusTextView.setText("Press Volume Up or Volume Down button");

        passButton.setOnClickListener(view -> {
            setResult(RESULT_OK, new Intent().putExtra("testResult", true));
            finish();
        });

        failButton.setOnClickListener(view -> {
            setResult(RESULT_OK, new Intent().putExtra("testResult", false));
            finish();
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                buttonStatusTextView.setText("Volume Up Button Pressed");
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                buttonStatusTextView.setText("Volume Down Button Pressed");
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}