package com.example.photogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";
    private TextInputEditText etName;
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private TextInputEditText etPdConfirm;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPdConfirm = findViewById(R.id.etPdConfirm);
        btnSignup = findViewById(R.id.btnSignup);

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }
        btnSignup.setEnabled(false);
        etName.addTextChangedListener(loginTextWatcher);
        etUsername.addTextChangedListener(loginTextWatcher);
        etPassword.addTextChangedListener(loginTextWatcher);
        etPdConfirm.addTextChangedListener(loginTextWatcher);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the ParseUser
                ParseUser user = new ParseUser();
                // Set core properties
                user.setUsername(etUsername.getText().toString());
                user.setPassword(etPassword.getText().toString());
                user.put("name", etName.getText().toString());

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String pdConfirm = etPdConfirm.getText().toString();
                if (password.equals(pdConfirm)) {
                    // Invoke signUpInBackground
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(SignupActivity.this, "Successful Sign Up!", Toast.LENGTH_SHORT).show();
                                loginUser(username, password);
                            } else {
                                ParseUser.logOut();
                                Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                Toast.makeText(SignupActivity.this, "Passwords are not the same!", Toast.LENGTH_LONG).show();

            }
        });

    }

    private final TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String name = etName.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String pdConfirm = etPdConfirm.getText().toString().trim();
            btnSignup.setEnabled(!username.isEmpty() && !password.isEmpty() && !pdConfirm.isEmpty() && !name.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(SignupActivity.this, "Issue with login", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Navigate to the main activity if the user has signed in properly
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}