package com.sabbir.walton.mywalton;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private List<DataModel> dataModelList;
    private Context context;

    public DataAdapter(Context context, List<DataModel> dataModelList) {
        this.context = context;
        this.dataModelList = dataModelList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datalayout, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DataModel dataModel = dataModelList.get(position);
        holder.locationName.setText(dataModel.getName());
        holder.locationAddress.setText(dataModel.getLocation());
        holder.locationContact.setText(dataModel.getContact());

        holder.itemView.setOnClickListener(v -> {
            v.animate()
                    .alpha(0.7f)
                    .setDuration(100)
                    .withEndAction(() -> {
                        v.animate().alpha(1.0f).setDuration(100);
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dataModel.getContact()));
                        v.getContext().startActivity(intent);
                    });
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public void filterList(List<DataModel> filteredList) {
        dataModelList = filteredList;
        notifyDataSetChanged();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView locationName, locationAddress, locationContact;

        DataViewHolder(View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.dataName);
            locationAddress = itemView.findViewById(R.id.dataAddress);
            locationContact = itemView.findViewById(R.id.dataContact);
        }
    }
}