package com.sabbir.walton.mywalton;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class About extends AppCompatActivity {

    private TextView textVersion;
    private TextView modeltext;

    private Button helpline1,helpline2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        // Initialize TextView
        textVersion = findViewById(R.id.text_version);

        // Get version number from manifest
        String versionName = getAppVersionName();
        String versionText = "Version " + versionName;
        textVersion.setText(versionText);


        modeltext = findViewById(R.id.text_version2);
        String modelname = Build.MODEL;
        String message = "Thank you for choosing " + modelname + ". If you encounter any issues, feel free to contact our customer care. Please tap the button to call helpline numbers (Local Toll Free) are mentioned below. Our customer service is available from 7:00 AM to 11:00 PM (365 Days).";
        modeltext.setText(message);


        helpline1 = findViewById(R.id.helpline1);
        helpline2 = findViewById(R.id.helpline2);

        helpline1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:16267"));
                startActivity(intent);
            }
        });

        helpline2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+8808000017277"));
                startActivity(intent);
            }
        });



    }

    private String getAppVersionName() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "Unknown";
        }
        }
    }
