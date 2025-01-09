package com.sabbir.walton.mywalton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ServiceCenter extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private List<DataModel> dataModelList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_center);

        recyclerView = findViewById(R.id.recyclerView);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search location and tap to call");

        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("WSMS Location");

        dataModelList = new ArrayList<>();
        adapter = new DataAdapter(this, dataModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Load data from Firebase
        loadDataFromFirebase();

        // SearchView setup code remains the same
        try {
            Field mSearchSrcTextViewField = SearchView.class.getDeclaredField("mSearchSrcTextView");
            mSearchSrcTextViewField.setAccessible(true);
            @SuppressLint("RestrictedApi") SearchView.SearchAutoComplete mSearchSrcTextView =
                    (SearchView.SearchAutoComplete) mSearchSrcTextViewField.get(searchView);
            mSearchSrcTextView.setHint("Search location and tap to call");
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void loadDataFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataModelList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String contact = dataSnapshot.child("contact").getValue(String.class);
                    String address = dataSnapshot.child("location").getValue(String.class);

                    dataModelList.add(new DataModel(name, address, contact));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ServiceCenter.this, "Error loading data: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text) {
        List<DataModel> filteredlist = new ArrayList<>();
        for(DataModel item : dataModelList) {
            if(item.getName().toLowerCase().contains(text.toLowerCase()) ||
                    item.getLocation().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        adapter.filterList(filteredlist);
    }
}