package com.example.skincancerdetectiion.model;

import android.net.Uri;

import java.util.HashMap;

public class model {
    private String name;
    private Uri image;
    private HashMap<Integer, String> uristore;

    private int position;

    public model(String name, Uri image, int position) {
        uristore = new HashMap<>();
        StoreHashMap();
        this.name = name;
        this.image = image;
        this.position = position;
    }

    public String getDescription() {
        return name;
    }

    public void setDescription(String name) {
        this.name = name;
    }

    public Uri getImage() {
        return image;
    }

    public int getPosition() {
        return position;
    }

    public String getString(int position) {
        return uristore.get(position);
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public void StoreHashMap() {

//                list-> Actinic Keratoses"-> "https://www.mayoclinic.org/diseases-conditions/actinic-keratosis/diagnosis-treatment/drc-20354975";
//                Basal Cell Carcinoma-> "https://www.mayoclinic.org/diseases-conditions/seborrheic-keratosis/symptoms-causes/syc-20353878"
//                "Benign Keratosis-like Lesions" -> "https://www.mayoclinic.org/diseases-conditions/seborrheic-keratosis/symptoms-causes/syc-20353878";
//                Dermatofibroma-> "https://www.mayoclinic.org/diseases-conditions/dermatofibrosarcoma-protuberans/cdc-20352949"
//                Melanoma->https://www.mayoclinic.org/diseases-conditions/melanoma/symptoms-causes/syc-20374884"
//                Melanocytic Nevi-> "https://www.mayoclinic.org/diseases-conditions/moles/symptoms-causes/syc-20375200";
//                "Vascular Lesions->     "https://www.mayoclinic.org/departments-centers/vascular-anomalies-clinic/overview/ovc-20421863";
        uristore.put(1, "https://www.mayoclinic.org/diseases-conditions/actinic-keratosis/diagnosis-treatment/drc-20354975");
        uristore.put(2, "https://www.mayoclinic.org/diseases-conditions/basal-cell-carcinoma/symptoms-causes/syc-20354187");
        uristore.put(3, "https://www.mayoclinic.org/diseases-conditions/seborrheic-keratosis/symptoms-causes/syc-20353878");
        uristore.put(4, "https://www.mayoclinic.org/diseases-conditions/dermatofibrosarcoma-protuberans/cdc-20352949");
        uristore.put(5, "https://www.mayoclinic.org/diseases-conditions/melanoma/symptoms-causes/syc-20374884");
        uristore.put(6, "https://www.mayoclinic.org/diseases-conditions/moles/symptoms-causes/syc-20375200");
        uristore.put(7, "https://www.mayoclinic.org/departments-centers/vascular-anomalies-clinic/overview/ovc-20421863");


    }
}
