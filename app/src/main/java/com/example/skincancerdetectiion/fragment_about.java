package com.example.skincancerdetectiion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class fragment_about extends Fragment {

    public fragment_about() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Element versionElement=new Element();
        versionElement.setTitle("version 6.2");
        // Inflate the layout for this fragment
//       nflater.inflate(R.layout.fragment_about, container, false);
        return   new AboutPage(getContext())
                .isRTL(false)

                .setDescription(getString(R.string.app_description))
                .addItem(versionElement)
                .addGroup("Connect with us")
                .setImage(R.mipmap.ic_launcher)
                .addEmail("jitendrakohar05@gmail.com")
                .addWebsite("https://jitendrakohar.github.io/portfolio/")
                .addFacebook("")
                .addTwitter("@jitendrakohar34")
                .addYoutube("")
                .addPlayStore("com.example.skincancerdetectiion")
                .addGitHub("jitendrakohar")
                .addInstagram("")
                .create();
    }
}