package com.apockestafe.team19;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SetiingsActivity extends AppCompatActivity {
    private Button backButton, signOutButton;
    private SharedPreferencesEditor editor;
    private EditText firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setiings);

        firstName = (EditText) findViewById(R.id.firstNameEditText);
        lastName = (EditText) findViewById(R.id.lastNameEditText);
        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                editor = new SharedPreferencesEditor(getSharedPreferences("login", MODE_PRIVATE));
                if (firstName.getText().length() != 0)
                    editor.addFirstName(firstName.getText().toString());
                if (lastName.getText().length() != 0)
                    editor.addLastName(lastName.getText().toString());

                startActivity(new Intent(SetiingsActivity.this, MainActivity.class));
            }
        });

        signOutButton = (Button) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetiingsActivity.this, SigninActivity.class));
            }
        });
    }

}