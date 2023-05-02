package com.example.skincancerdetectiion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.skincancerdetectiion.databinding.FragmentReportfragmentBinding;


public class reportfragment extends Fragment {
    private FragmentReportfragmentBinding binding;
    preferenceManager preferenceManager;

    public reportfragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReportfragmentBinding.inflate(getLayoutInflater(), container, false);
        View view = binding.getRoot();
        preferenceManager = new preferenceManager(getContext());
        saveReport();

        binding.appointmentdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), ""+"appointment", Toast.LENGTH_SHORT).show();
                String location = "bti college, Boy's hostel, near wipro , Bangalore, USA";
                String query = "Dermatologist";

                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + query + "@" + location);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google. android. gms. maps");

                if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(getContext(), "Google Maps is not installed on your device", Toast.LENGTH_SHORT).show();
                }


            }
        });
        return view;
    }

    public void saveReport() {
        if (preferenceManager.getString(constants.RESULT_TYPE, "") != null && preferenceManager.getString(constants.RESULT, "") != null) {
           binding.reportName.setText("Name:"+ preferenceManager.getString(constants.NAME,""));
            binding.cancerResult.setText( preferenceManager.getString(constants.RESULT, ""));
            binding.cancerType.setText("TYPE: " + preferenceManager.getString(constants.RESULT_TYPE, ""));
            binding.cancerSymptoms.setText("Symptoms:\n" + preferenceManager.getString(constants.SYMPTOMS, ""));

        }

    }

}