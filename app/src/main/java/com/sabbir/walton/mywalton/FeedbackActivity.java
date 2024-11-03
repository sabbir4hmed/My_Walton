package com.sabbir.walton.mywalton;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sabbir.walton.mywalton.databinding.ActivityFeedbackBinding;
import com.sabbir.walton.mywalton.databinding.ActivityMainBinding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FeedbackActivity extends AppCompatActivity {

    @NonNull ActivityFeedbackBinding binding;
    String base_url = "https://script.google.com/macros/s/AKfycbyA3Lf1mU8eAQCjI6yZSISR7UWy1QCMbzTaaqMrhjhf_5R6gJGPeFTH1NluklZQ0oqK/exec?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback);*/



        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String modelname = android.os.Build.MODEL;

        ArrayList<String> districts = new ArrayList<>();
        ArrayAdapter<String> adapter;

        districts.add("Dhaka");
        districts.add("Faridpur");
        districts.add("Gazipur");
        districts.add("Gopalganj");
        districts.add("Jamalpur");
        districts.add("Kishoreganj");
        districts.add("Madaripur");
        districts.add("Manikganj");
        districts.add("Munshiganj");
        districts.add("Mymensingh");
        districts.add("Narayanganj");
        districts.add("Narsingdi");
        districts.add("Netrokona");
        districts.add("Rajbari");
        districts.add("Shariatpur");
        districts.add("Sherpur");
        districts.add("Tangail");
        districts.add("Bogura");
        districts.add("Joypurhat");
        districts.add("Naogaon");
        districts.add("Natore");
        districts.add("Nawabganj");
        districts.add("Pabna");
        districts.add("Rajshahi");
        districts.add("Sirajgonj");
        districts.add("Dinajpur");
        districts.add("Gaibandha");
        districts.add("Kurigram");
        districts.add("Lalmonirhat");
        districts.add("Nilphamari");
        districts.add("Panchagarh");
        districts.add("Rangpur");
        districts.add("Thakurgaon");
        districts.add("Barguna");
        districts.add("Barishal");
        districts.add("Bhola");
        districts.add("Jhalokati");
        districts.add("Patuakhali");
        districts.add("Pirojpur");
        districts.add("Bandarban");
        districts.add("Brahmanbaria");
        districts.add("Chandpur");
        districts.add("Chattogram");
        districts.add("Cumilla");
        districts.add("Cox's Bazar");
        districts.add("Feni");
        districts.add("Khagrachari");
        districts.add("Lakshmipur");
        districts.add("Noakhali");
        districts.add("Rangamati");
        districts.add("Habiganj");
        districts.add("Maulvibazar");
        districts.add("Sunamganj");
        districts.add("Sylhet");
        districts.add("Bagerhat");
        districts.add("Chuadanga");
        districts.add("Jashore");
        districts.add("Jhenaidah");
        districts.add("Khulna");
        districts.add("Kushtia");
        districts.add("Magura");
        districts.add("Meherpur");
        districts.add("Narail");
        districts.add("Satkhira");

        // Initialize adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter to spinner

        binding.stdlocation.setAdapter(adapter);
        binding.stdlocation.setSelection(0);

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.stdname.getText().toString();
                String mobilenumber = binding.stdnumber.getText().toString();
                String address = binding.stdaddress.getText().toString();
                String feedback = binding.stdfeedback.getText().toString();
                String suggestion = binding.stdsuggestion.getText().toString();
                String district = binding.stdlocation.getSelectedItem().toString();

                if (district.equals("Select your District")) {
                    Toast.makeText(FeedbackActivity.this, "Please select your district!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (name.isEmpty() || mobilenumber.isEmpty() || address.isEmpty() || feedback.isEmpty() || suggestion.isEmpty() || district.isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else {
                    buttonSubmit(name, modelname, mobilenumber, district, address, feedback, suggestion);
                }
            }

        });



    }

    private void buttonSubmit(String name, String model, String mobilenumber, String location, String address, String feedback, String suggestion) {
        try {
            // Get the current date and time
            Date currentTime = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
            String dateTimeString = dateFormat.format(currentTime);

            // Send the main data in one request
            String web_url = base_url + "action=create&name=" + URLEncoder.encode(name, "UTF-8") +
                    "&modelname=" + URLEncoder.encode(model, "UTF-8") +
                    "&mobilenumber=" + URLEncoder.encode(mobilenumber, "UTF-8") +
                    "&location=" + URLEncoder.encode(location, "UTF-8") +
                    "&address=" + URLEncoder.encode(address, "UTF-8") +
                    "&feedback=" + URLEncoder.encode(feedback, "UTF-8") +
                    "&suggestion=" + URLEncoder.encode(suggestion,"UTF-8") +
                    "&datetime=" + URLEncoder.encode(dateTimeString, "UTF-8");

            StringRequest stringRequest = new StringRequest(Request.Method.GET, web_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(FeedbackActivity.this, "Thanks for your feedback!", Toast.LENGTH_SHORT).show();
                    // Clear all fields
                    binding.stdname.setText("");
                    binding.stdnumber.setText("");
                    binding.stdaddress.setText("");
                    binding.stdfeedback.setText("");
                    binding.stdsuggestion.setText("");
                    binding.stdlocation.setSelection(0); // Reset the spinner to the hint message
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(FeedbackActivity.this, "Something wrong!!", Toast.LENGTH_SHORT).show();


                }
            });

            RequestQueue queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
            queue.add(stringRequest);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}