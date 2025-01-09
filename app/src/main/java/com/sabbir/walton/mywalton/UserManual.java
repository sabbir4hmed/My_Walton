package com.sabbir.walton.mywalton;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserManual extends AppCompatActivity {
    private PDFView pdfView;
    private DatabaseReference database;
    private ProgressDialog progressDialog;
    private static final String TAG = "PDF_VIEWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manual);
        initializeViews();
        String deviceName = Build.MODEL;
        Log.d(TAG, "Device Name: " + deviceName);
        loadPDFFromDatabase(deviceName);
    }

    private void initializeViews() {
        pdfView = findViewById(R.id.pdfview);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading PDF...");
        progressDialog.setCancelable(false);
        database = FirebaseDatabase.getInstance().getReference().child("device_pdf");
    }

    private void loadPDFFromDatabase(String deviceName) {
        progressDialog.show();
        Log.d(TAG, "Searching PDF for device: " + deviceName);

        database.get().addOnSuccessListener(snapshot -> {
            boolean pdfFound = false;
            for (DataSnapshot pdfData : snapshot.getChildren()) {
                String dbDeviceName = pdfData.child("device_name").getValue(String.class);
                Log.d(TAG, "Checking device: " + dbDeviceName);

                if (deviceName.equals(dbDeviceName)) {
                    String pdfUrl = pdfData.child("pdf_url").getValue(String.class);
                    // Direct URL is already in correct format, no need to extract ID
                    Log.d(TAG, "PDF found. URL: " + pdfUrl);
                    downloadPDF(pdfUrl);
                    pdfFound = true;
                    break;
                }
            }
            if (!pdfFound) {
                progressDialog.dismiss();
                Log.d(TAG, "No PDF found for device: " + deviceName);
                Toast.makeText(UserManual.this, "No user manual found for this device: " + deviceName, Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Log.e(TAG, "Database Error: " + e.getMessage());
            Toast.makeText(UserManual.this, "Failed to connect to database", Toast.LENGTH_SHORT).show();
        });
    }

    private void downloadPDF(String pdfUrl) {
        Log.d(TAG, "Starting PDF download from: " + pdfUrl);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(pdfUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Download Error: " + e.getMessage());
                runOnUiThread(() -> {
                    progressDialog.dismiss();
                    Toast.makeText(UserManual.this, "Failed to load PDF", Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    runOnUiThread(() -> displayPDF(inputStream));
                } else {
                    runOnUiThread(() -> {
                        progressDialog.dismiss();
                        Toast.makeText(UserManual.this, "Failed to load PDF: " + response.code(), Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }



    private void displayPDF(InputStream input) {
        pdfView.fromStream(input)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .onLoad(nbPages -> {
                    progressDialog.dismiss();
                    Log.d(TAG, "PDF loaded with " + nbPages + " pages");
                    Toast.makeText(UserManual.this, "User manual loaded successfully", Toast.LENGTH_SHORT).show();
                })
                .onError(t -> {
                    progressDialog.dismiss();
                    Log.e(TAG, "PDF Display Error: " + t.getMessage());
                    Toast.makeText(UserManual.this, "Error displaying PDF", Toast.LENGTH_LONG).show();
                })
                .load();
    }
}

