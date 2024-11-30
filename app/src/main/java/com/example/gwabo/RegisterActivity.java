package com.example.gwabo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button registerButton = findViewById(R.id.btnRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Perform input validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // API URL
        String url = "https://wavemediatech.ca/register_user.php";

        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient();

        // Build the form body
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("email", email)
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
                    // Parse the response
                    assert response.body() != null;
                    String responseData = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseData);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    // Update the UI on the main thread
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        if (status.equals("success")) {
                            // Navigate to login or main activity
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {
                    // Handle error response
                    runOnUiThread(() ->
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(RegisterActivity.this, "Request failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
