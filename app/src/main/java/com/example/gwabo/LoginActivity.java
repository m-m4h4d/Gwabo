package com.example.gwabo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Locale;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText nameEditText, passwordEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

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
        String username = nameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Perform input validation
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // API URL for login
        String url = "https://wavemediatech.ca/login_user.php";

        // Create OkHttpClient instance
        OkHttpClient client = new OkHttpClient();

        // Build the form body
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        // Create the request
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        // Execute the request in a background thread
        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    // Parse response
                    assert response.body() != null;
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        if (status.equals("success")) {
                            // Navigate to home screen
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(LoginActivity.this, "Login failed: Server error", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(LoginActivity.this, "Request failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
