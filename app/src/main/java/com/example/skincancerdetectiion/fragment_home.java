package com.example.skincancerdetectiion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.skincancerdetectiion.adapter.CustomAdapter;
import com.example.skincancerdetectiion.databinding.FragmentHomeBinding;
import com.example.skincancerdetectiion.model.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class fragment_home extends Fragment {

    HashMap<Integer, String> uristore;
    List<model> dataList;
    Button btnTes;
    ImageView ivFamily;
    View view;
    private FragmentHomeBinding binding;

    public fragment_home() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dataList = new ArrayList<model>();
        binding.btnTest.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), cameraActivity.class));
        });
        uristore = new HashMap<>();
        setRecyclerview();

        binding.ivFamily.setOnClickListener(v -> {
            Glide.with(this)
                    .load("https://www.onmanorama.com/content/dam/mm/en/lifes…/7/skin-problem-c.jpg.transform/845x440/image.jpg")
                    .into(binding.ivFamily);

        });

        return root;
    }


    public void setRecyclerview() {
        binding.rcv1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Your data list
        dataList.add(new model("Basal Cell Carcinoma", Uri.parse("https://www.skincancer.org/wp-content/uploads/basal-cell-carcinoma-1-shiny-bump-340x240.png"), 2));
        dataList.add(new model("Vascular Lesions", Uri.parse("https://www.millenniummedcosmetics.com/files/2019/08/cutera-portwinestain-before.jpg"), 7));
        dataList.add(new model("Benign Keratosis-like Lesions", Uri.parse("https://post.healthline.com/wp-content/uploads/2020/08/648x364_Seborrheic-Keratosis.jpg"), 3));
        dataList.add(new model("Dermatofibroma", Uri.parse("https://m4b6f3p8.rocketcdn.me/app/uploads/2021/04/dermatofibroma_1318_lg.jpg"), 4));
        dataList.add(new model("Melanoma", Uri.parse("https://assets.nhs.uk/nhsuk-cms/images/C0044260-Melanoma_skin_cancer-SPL.max-600x600.jpg"), 5));
        dataList.add(new model("Melanocytic Nevi", Uri.parse("https://healthjade.com/wp-content/uploads/2019/02/nevus.jpg"), 6));
        dataList.add(new model("Actinic Keratoses", Uri.parse("https://goldencoastdermatology.com/wp-content/uploads/2020/02/ACTINIC-KERATOSES.jpg"), 1));
        CustomAdapter adapter = new CustomAdapter(dataList, uristore);
        binding.rcv1.setAdapter(adapter);


    }


}