package com.example.skincancerdetectiion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skincancerdetectiion.databinding.ActivityInputactivityBinding;

public class inputactivity extends AppCompatActivity {
    private ActivityInputactivityBinding binding;
    String name;
    EditText etName, etGmail;
    Context context;
    Button btnsave;
    String email;
    preferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       preferenceManager=new preferenceManager(this);


        if(!preferenceManager.getString(constants.GMAIL,"").isEmpty()&&!preferenceManager.getString(constants.NAME,"").isEmpty())
        {
            Intent i=new Intent(inputactivity.this,MainActivity.class);
            startActivity(i);
        }
        else {
            setContentView(R.layout.activity_inputactivity);


            context = this;
            etName = findViewById(R.id.etName);
            etGmail = findViewById(R.id.etEmail);
            btnsave = findViewById(R.id.btnsave);

            preferenceManager preferenceManager = new preferenceManager(inputactivity.this);


            btnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = etName.getText().toString();
                    email = etGmail.getText().toString();
                    if (isValidName(name)) {
                        etName.setError("Please enter a valid name");
                        Toast.makeText(context, "" + name + " and gmail: " + email, Toast.LENGTH_SHORT).show();
                    } else if (email == null || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        etGmail.setError("Please enter a valid Email");
                    } else {

                        preferenceManager.saveString(constants.NAME, name);
                        preferenceManager.saveString(constants.GMAIL, email);
                        if (preferenceManager.getString(constants.NAME, "") != null && preferenceManager.getString(constants.GMAIL, "") != null) {
                            Intent i = new Intent(inputactivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    }
                }

            });
        }

    }

    public boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty() || !name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) {
            return false;
        }
        return true;
    }

    public  boolean isValidEmail(String email) {
        if (email == null || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        return true;
    }

}
