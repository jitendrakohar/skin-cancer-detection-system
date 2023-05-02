package com.example.skincancerdetectiion.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.skincancerdetectiion.R;
import com.example.skincancerdetectiion.model.model;
import com.example.skincancerdetectiion.webviewActivity;

import java.util.HashMap;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<model> datalist;
    private HashMap<Integer,String> uriStore;
    Context context;

    public CustomAdapter(List<model> datalist,HashMap<Integer,String> uriStore) {
        
        this.datalist = datalist;
        this.uriStore=uriStore;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         holder.tvCancer.setText(datalist.get(position).getDescription());
         Glide.with(holder.itemView.getContext()).load(datalist.get(position).getImage())
                .into(holder.ivCancer);
         holder.ivCancer.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(v.getContext(), webviewActivity.class);
                 int p=datalist.get(position).getPosition();
                 intent.putExtra("hashmapString", datalist.get(position).getString(datalist.get(position).getPosition()));
                 v.getContext().startActivity(intent);
             }
         });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCancer;
        private ImageView ivCancer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCancer=itemView.findViewById(R.id.tvCancerDescription);
            ivCancer=itemView.findViewById(R.id.imgCancer);
        }
    }
}
