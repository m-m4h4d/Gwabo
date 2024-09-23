package com.example.gwabo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText usernameEditText, emailEditText, passwordEditText;
    private Button registerButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize database helper and UI elements
        dbHelper = new DatabaseHelper(this);
        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.btnRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Insert the new user into the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);

        long userId = db.insert("users", null, values);
        if (userId != -1) {
            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
            finish(); // Return to the login screen
        } else {
            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
        }
        // Inside RegisterActivity.java, after successful registration
        DatabaseHelper helper = new DatabaseHelper(this);

        // Fetching all users
        Cursor cursor = helper.getAllUsers();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No users found", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder usersData = new StringBuilder();
            while (cursor.moveToNext()) {
                usersData.append("ID: ").append(cursor.getString(0)).append("\n");
                usersData.append("Username: ").append(cursor.getString(1)).append("\n");
                usersData.append("Email: ").append(cursor.getString(2)).append("\n");
                usersData.append("Password: ").append(cursor.getString(3)).append("\n\n");
            }
            // Display the data in a Toast message or log it
            Toast.makeText(this, usersData.toString(), Toast.LENGTH_LONG).show();
            Log.d("UserRecords", usersData.toString());
        }
        cursor.close();

        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
