package com.example.skincancerdetectiion;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class CustomDialog extends Dialog  {

    public Button barGeneratorBtn;
    public Button qrGeneratorBtn;
   public TextView descriptionTextView;
    public TextView dialogTitle;
    Context context;
    private ImageView ivGif;

    EditText text;
    public CustomDialog(Context context) {

        super(context);

        this.context=context;
        setContentView(R.layout.dialog_app_info);


        dialogTitle = findViewById(R.id.titleTextView);
        barGeneratorBtn = findViewById(R.id.dialogCancelButton);
        barGeneratorBtn.setText("GO BACK");
        ivGif=findViewById(R.id.ivgif);


        // Set click listener for button


        barGeneratorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,MainActivity.class);
                context.startActivity(i);
                dismiss();
            }
        });


    }

    public void setTitle(String title) {
        dialogTitle.setHighlightColor(getContext().getResources().getColor(R.color.red));
        dialogTitle.setElevation(4 * getContext().getResources().getDisplayMetrics().density);
        dialogTitle.setGravity(Gravity.CENTER);
        dialogTitle.setText(title);
        Glide.with(context).load(R.drawable.search).into(ivGif);

    }


}
