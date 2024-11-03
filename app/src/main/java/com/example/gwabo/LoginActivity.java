package com.example.gwabo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText nameEditText, passwordEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // Initialize the database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize the UI elements
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.btnLogin);
        Button registerButton = findViewById(R.id.btnRegister);
        TextView langBtn = findViewById(R.id.lang_btn);
        langBtn.setOnClickListener(v -> showLanguageDialog());

        // Set onClickListener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        // Set onClickListener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the registration screen
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showLanguageDialog() {
        String[] languages = {"English", "Spanish", "French", "German"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Language")
                .setItems(languages, (dialog, which) -> {
                    String selectedLanguage;
//                    selectedLanguage = languages[which];
                    switch (which) {
                        case 1: selectedLanguage = "es"; break;
                        case 2: selectedLanguage = "fr"; break;
                        case 3: selectedLanguage = "de"; break;
                        default: selectedLanguage = "en"; break;
                    }
                    changeLanguage(selectedLanguage);
        });
        builder.show();
    }

    private void changeLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Restart the activity for changes to take effect
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        Toast.makeText(this, "Language set to: " + languageCode, Toast.LENGTH_SHORT).show();
    }

    private void loginUser() {
        String username = nameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Query the database for the user
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});

        if (cursor != null && cursor.moveToFirst()) {
            // Login successful
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            // Navigate to home screen (e.g., MainActivity)
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            // Login failed
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }
}
