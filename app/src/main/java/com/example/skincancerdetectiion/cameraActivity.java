package com.example.skincancerdetectiion;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class cameraActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private CameraManager cameraManager;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ProcessCameraProvider cameraProvider = null;
    private ImageCapture imageCapture;
    private VideoCapture videoCapture;

    private boolean flashEnabled = false;

    private PreviewView previewView;
    ImageButton flashButton;
    private Camera camera;
    private CameraControl cameraControl;
    private CameraInfo cameraInfo;

    boolean isFlashOn=true;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        previewView = (PreviewView) findViewById(R.id.surface_view);


        ImageButton captureButton = findViewById(R.id.capture_button);

        flashButton = findViewById(R.id.flash_button);
        ImageButton imageButton = findViewById(R.id.gallery_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUri();
            }
        });
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                capturePhoto();
            }
        });
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        // Get the camera control and camera info objects

        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                startCameraX(cameraProvider);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, getExecutor());

        flashButton.setOnClickListener(v -> {

        });

    }

    private Executor getExecutor() {
        return ContextCompat.getMainExecutor(this);
    }

    @SuppressLint("RestrictedApi")
    private void startCameraX(ProcessCameraProvider cameraProvider) {

        cameraProvider.unbindAll();


        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Preview preview = new Preview.Builder().build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());


        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        videoCapture = new VideoCapture.Builder()
                .setVideoFrameRate(30)
                .build();
        Camera camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, videoCapture);
        CameraControl cameraControl = camera.getCameraControl();


        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFlashOn){
                    disableFlash(cameraControl);
                }

                else{
                    enableFlash(cameraControl);
                }
            }
        });


        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, videoCapture);

    }


    private void capturePhoto() {
        long timeStamp = System.currentTimeMillis();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timeStamp);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");


        imageCapture.takePicture(
                new ImageCapture.OutputFileOptions.Builder(
                        getContentResolver(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues).build(),
                getExecutor(),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        Toast.makeText(cameraActivity.this, "Saving...", Toast.LENGTH_SHORT).show();
                        Uri imageUri = outputFileResults.getSavedUri();

                        Intent i = new Intent(cameraActivity.this, activityresult.class);
                        i.putExtra("Uri", imageUri.toString());
                        startActivity(i);

                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(cameraActivity.this, "Error: " + exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    public void loadUri() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Intent intent = new Intent(getApplicationContext(), activityresult.class);
            intent.putExtra("Uri", data.getData().toString());
            startActivity(intent);

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



    private void enableFlash(CameraControl cameraControls) {
        // Check if the camera has a flash unit

            // Enable the flash
            cameraControls.enableTorch(true);
            flashButton.setImageResource(R.drawable.flashoff);
            isFlashOn=true;

    }

    private void disableFlash(CameraControl cameracontrols) {
        // Disable the flash
        cameracontrols.enableTorch(false);
        flashButton.setImageResource(R.drawable.flashon);
        isFlashOn=false;
    }





}
