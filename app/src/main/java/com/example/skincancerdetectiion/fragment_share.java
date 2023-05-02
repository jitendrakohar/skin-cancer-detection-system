package com.example.skincancerdetectiion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.skincancerdetectiion.databinding.FragmentShareBinding;

public class fragment_share extends Fragment {
    private FragmentShareBinding binding;

    public fragment_share() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShareBinding.inflate(getLayoutInflater(), container, false);
        View root = binding.getRoot();
        shareApp();
        return root;
    }

    public void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareSubject = "Check out this app!";
        String shareMessage = "I found this great app and wanted to share it with you: " +
                "https://play.google.com/store/apps/details?id=" + getContext().getPackageName();
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }
}