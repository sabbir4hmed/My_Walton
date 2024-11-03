package com.sabbir.walton.mywalton;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_service_center);

        recyclerView = findViewById(R.id.recyclerView);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search location and tap to call");

        dataModelList = loaddatafromjson();
        adapter = new DataAdapter(this,dataModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Ensure the hint is always shown
        try {
            Field mSearchSrcTextViewField = SearchView.class.getDeclaredField("mSearchSrcTextView");
            mSearchSrcTextViewField.setAccessible(true);
            SearchView.SearchAutoComplete mSearchSrcTextView = (SearchView.SearchAutoComplete) mSearchSrcTextViewField.get(searchView);

            mSearchSrcTextView.setHint("Search location and tap to call");
           // mSearchSrcTextView.setHintTextColor(getResources().getColor(R.color.hint_color)); // Optional: set hint text color
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

    private void filter(String text) {
        List<DataModel> filteredlist = new ArrayList<>();
        for(DataModel item : dataModelList)
        {
            if(item.getName().toLowerCase().contains(text.toLowerCase()) || item.getAddress().toLowerCase().contains(text.toLowerCase()))
            {
                filteredlist.add(item);
            }
            adapter.filterList(filteredlist);
        }
    }

    private List<DataModel> loaddatafromjson() {
        List<DataModel> dataModels  =new ArrayList<>();
        try {
            InputStream is = getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("WSMS Location");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String name = obj.getString("name");
                String contact = obj.getString("contact");
                String address = obj.getString("location");
                dataModels.add(new DataModel(name,address,contact));
            }
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        return dataModels;
    }

    }
