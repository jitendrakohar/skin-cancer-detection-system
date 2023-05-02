package com.example.skincancerdetectiion;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skincancerdetectiion.ml.Model;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class activityresult extends AppCompatActivity {
    int imageSize = 150;
    TextView result;
    CustomDialog customDialog;
    Bitmap image = null;
    Bitmap bitmap = null;
    ImageView imageView;
    TextView cancerOrNotCancerous;
    String imageUri = "";
    TextView tvCancerType;
    TextView Symptoms;
    Button viewMore;
    Mat ycrcbMat;
    String symptoms = "";
    String cancertype="";
    String CancerousOrNoncancerous = "";
    String viewmoreUri = "";
    preferenceManager preferenceManager;

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "static initializer: opencv debug failed");
        } else {
            Log.d(TAG, "static initializer: opencv debug failed");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityresult);
        result = findViewById(R.id.tvCancerType);
        imageView = findViewById(R.id.image1);
        cancerOrNotCancerous = findViewById(R.id.tvCancerResult);
        customDialog = new CustomDialog(activityresult.this);

        Symptoms = findViewById(R.id.tvSymptomsList);
        viewMore = findViewById(R.id.viewMore);
        preferenceManager = new preferenceManager(this);
        // Get the Intent that started this activity
        Intent intent = getIntent();

        if (intent != null) {
            imageUri = intent.getStringExtra("Uri");
        }


        image = getBitmap(imageUri);
        if (image != null) {
            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            imageView.setImageBitmap(image);
            if (isSkinPresent(image)) {
                classifyImage(image);
            } else {
                setnotAvailable();

                Toast.makeText(this, "skin not present", Toast.LENGTH_SHORT).show();
            }
        }


    }


    public void classifyImage(Bitmap image) {


        try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, imageSize, imageSize, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = -1;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            if (maxPos != -1 && maxConfidence >= 0.2) {
                String[] classes = {"Actinic Keratoses", "Basal Cell Carcinoma", "Benign Keratosis-like Lesions", "Dermatofibroma", "Melanoma", "Melanocytic Nevi", "Vascular Lesions"};
//                result.setText(classes[maxPos]);
                cancertype=classes[maxPos];
                setDescription(classes[maxPos]);
//                viewMore.setText(Arrays.toString(confidences));
            } else {
                result.setText("Not identified");

            }

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }


    }

    public Bitmap getBitmap(String uri) {
        Uri imageUri = Uri.parse(uri);
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            // Use the bitmap in your code
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void setDescription(String classCancer) {

//        String[] classes = {"Actinic Keratoses", "Basal Cell Carcinoma", "Benign Keratosis-like Lesions", "Dermatofibroma", "Melanoma", "Melanocytic Nevi", "Vascular Lesions"};


        switch (classCancer) {
            case "Actinic Keratoses":
                CancerousOrNoncancerous = "cancerous";
                symptoms = "1. Rough, scaly, or bumpy patches on the skin.\n" +
                        "2. Patches that may be pink, red, or brown in color.\n" +
                        "3. Itching or burning sensation in the affected area.\n" +
                        "4. Pain or tenderness in the affected area.\n" +
                        "5. Crusting or bleeding in severe cases.";
                viewmoreUri = "https://www.mayoclinic.org/diseases-conditions/actinic-keratosis/diagnosis-treatment/drc-20354975";
                break;
            case "Basal Cell Carcinoma":
                CancerousOrNoncancerous = "nonCancerous";
                symptoms = "1. A raised, pearly, or waxy bump on the skin, often with visible blood vessels on the surface\n" +
                        "2. A flat, flesh-colored or brown scar-like lesion\n" +
                        "3. A sore that does not heal or heals and then returns\n" +
                        "4. A red or irritated area of skin, or a pink growth with raised borders and a crusted indentation in the center\n" +
                        "5. A shiny, pink or red, slightly raised patch of skin that may be itchy or painful";
                viewmoreUri = "https://www.mayoclinic.org/diseases-conditions/basal-cell-carcinoma/symptoms-causes/syc-20354187";
                break;
            case "Benign Keratosis-like Lesions":
                CancerousOrNoncancerous = "Non Cancerous";
                symptoms = "1. Raised or flat, round or oval-shaped growths on the skin, ranging in color from white to black.\n" +
                        "2. A waxy or scaly surface that may look like it's stuck to the skin.\n" +
                        "3. Lesions that are not painful or itchy, but may bleed if scraped or irritated.\n" +
                        "4. An increase in the number or size of the lesions over time.";
                viewmoreUri = "https://www.mayoclinic.org/diseases-conditions/seborrheic-keratosis/symptoms-causes/syc-20353878";

                break;
            case "Dermatofibroma":
                CancerousOrNoncancerous = "Non-Cancerous";
                symptoms = "1. Small, firm, raised bumps on the skin, usually less than 1cm in diameter.\n" +
                        "2. A skin-colored or reddish-brown bump that feels hard and may be slightly tender to the touch.\n" +
                        "3. The bump may have a slightly different texture than the surrounding skin and may be dimpled or have a central depression.\n" +
                        "4. Itching, pain, or tenderness may occur in some cases.";
                viewmoreUri = "https://www.mayoclinic.org/diseases-conditions/dermatofibrosarcoma-protuberans/cdc-20352949";
                break;
            case "Melanoma":
                CancerousOrNoncancerous = "Cancerous";
                symptoms = "1. A mole, freckle, or other pigmented spot on the skin that changes in size, shape, or color.\n" +
                        "2. A new mole or growth on the skin.\n" +
                        "3. An asymmetrical mole or growth with irregular or ragged edges.\n" +
                        "4. A mole or growth that is larger than a pencil eraser in diameter.\n" +
                        "5. A mole or growth that is raised or has a rough, bumpy texture.";
                viewmoreUri = "https://www.mayoclinic.org/diseases-conditions/melanoma/symptoms-causes/syc-20374884";
                break;
            case "Melanocytic Nevi":
                CancerousOrNoncancerous = "Non-cancerous";
                symptoms = "1. Small, dark, round or oval-shaped spots on the skin that are usually less than 6mm in diameter.\n" +
                        "2. A mole or growth that has a uniform color and shape.\n" +
                        "3. A mole or growth that is raised or flat and may have a smooth or rough texture.\n" +
                        "4. A mole or growth that is present from birth or develops later in life.\n" +
                        "5. Itching, bleeding, or pain in a mole or growth.";
                viewmoreUri = "https://www.mayoclinic.org/diseases-conditions/moles/symptoms-causes/syc-20375200";


                break;
            case "Vascular Lesions":
                CancerousOrNoncancerous = "Non-cancerous";
                symptoms = "1. Small, red or purple spots on the skin that are usually less than 5mm in diameter.\n" +
                        "2. A birthmark or mole that is pink, red, or purple in color.\n" +
                        "3. A growth that is raised or flat and has a smooth or rough texture.\n" +
                        "4. A lesion that is painful, tender, or itchy.\n" +
                        "5. A lesion that bleeds easily.\n";
                viewmoreUri = "https://www.mayoclinic.org/departments-centers/vascular-anomalies-clinic/overview/ovc-20421863";
                break;
            default:
                CancerousOrNoncancerous = "Non Cancerous";
                symptoms = "NA";
                viewmoreUri = "https://www.mayoclinic.org/diseases-conditions/skin-cancer/symptoms-causes/syc-20377605";
                break;
        }
// saving a report in the preference manager
        saveReport();
        cancerOrNotCancerous.setText(CancerousOrNoncancerous);
        result.setText(classCancer);
        Symptoms.setText(symptoms);
        String finalViewmoreUri = viewmoreUri;
        viewMore.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), webviewActivity.class);
            i.putExtra("viewMore", finalViewmoreUri);
            startActivity(i);
        });

    }


    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i("OpenCV", "OpenCV loaded successfully");
                    ycrcbMat = new Mat();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public boolean isSkinPresent(Bitmap imageBitmap) {
        // Convert the Bitmap image to a Mat object
        if (imageBitmap == null) Toast.makeText(this, "image is null", Toast.LENGTH_SHORT).show();
        Mat imageMat = new Mat();
        Utils.bitmapToMat(imageBitmap, imageMat);

        // Convert the color space of the Mat object to RGB
        Imgproc.cvtColor(imageMat, imageMat, Imgproc.COLOR_RGBA2RGB);

        // Perform skin color segmentation and validation
        boolean isSkin = performSkinColorSegmentationAndValidation(imageMat);
        return isSkin;
    }


    private boolean performSkinColorSegmentationAndValidation(Mat imageMat) {
        // Convert the image to the YCrCb color space
        ycrcbMat = new Mat();
        Imgproc.cvtColor(imageMat, ycrcbMat, Imgproc.COLOR_RGB2YCrCb);

        // Extract the Y, Cr, and Cb channels from the image
        List<Mat> ycrcbChannels = new ArrayList<>();
        Core.split(ycrcbMat, ycrcbChannels);
        Mat yChannel = ycrcbChannels.get(0);
        Mat crChannel = ycrcbChannels.get(1);
        Mat cbChannel = ycrcbChannels.get(2);

        // Apply a binary threshold to the Cr and Cb channels to extract the skin region
        Mat skinMask = new Mat();
        Mat crMask = new Mat();
        Mat cbMask = new Mat();
        Imgproc.threshold(crChannel, crMask, 140, 255, Imgproc.THRESH_BINARY);
        Imgproc.threshold(cbChannel, cbMask, 95, 255, Imgproc.THRESH_BINARY);
        Core.bitwise_and(crMask, cbMask, skinMask);

        // Perform morphological operations to remove noise from the mask
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(7, 7));
        Imgproc.morphologyEx(skinMask, skinMask, Imgproc.MORPH_OPEN, kernel);
        Imgproc.morphologyEx(skinMask, skinMask, Imgproc.MORPH_CLOSE, kernel);

        // Calculate the percentage of skin pixels in the image
        double skinPixelRatio = (double) Core.countNonZero(skinMask) / (imageMat.width() * imageMat.height());

        // If the skin pixel ratio is above a certain threshold, consider the image to be a skin image
        return skinPixelRatio > 0.15;
    }

    public void setnotAvailable() {
        cancerOrNotCancerous.setText("Skin Not detected");
        result.setText("N/A");
        Symptoms.setText("N/A");


        // Create a new Handler object
        Handler handler = new Handler();

// Define a new Runnable object
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                showDialog();
            }
        };

// Post the Runnable with a delay of 1000 milliseconds (1 second)
        handler.postDelayed(myRunnable, 100);


    }

    void showDialog() {
        customDialog.setTitle("Skin Not Detected.");
        customDialog.show();
    }

    public void saveReport() {
        preferenceManager.saveString(constants.RESULT, CancerousOrNoncancerous);
        preferenceManager.saveString(constants.RESULT_TYPE,cancertype);
        preferenceManager.saveString(constants.SYMPTOMS,symptoms);
    }


}