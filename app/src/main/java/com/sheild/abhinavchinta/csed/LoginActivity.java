package com.sheild.abhinavchinta.csed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abhinavchinta.csed.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button btnSignIn;
    private EditText editText, editText2;
    private ProgressBar progressBar;
///git
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_login);
        editText=(EditText)findViewById(R.id.email);
        editText2=(EditText)findViewById(R.id.password);
        TextView textView = (TextView) findViewById(R.id.textView2);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        btnSignIn=(Button)findViewById(R.id.email_sign_in_button);
        //btnReg=(Button)findViewById(R.id.button4);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(editText.getText().toString())) {
                    editText.setError("Username or Email ID cannot be empty");
                    editText.requestFocus();
                    return;
                }
                if("".equals(editText2.getText().toString())) {
                    editText2.setError("Password cannot be empty");
                    editText2.requestFocus();
                    return;
                }
                editText.setEnabled(false);
                editText2.setEnabled(false);
                btnSignIn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                String email = editText.getText().toString().trim();
                String password = editText2.getText().toString().trim();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    btnSignIn.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    editText.setEnabled(true);
                                    editText2.setEnabled(true);
                                    //Toast.makeText(LoginActivity.this, "User Authentication Failed: " + task.getException(), Toast.LENGTH_SHORT).show();

                                        try {
                                            throw task.getException();
                                        } catch(FirebaseAuthWeakPasswordException e) {
                                            editText2.setError("Incorrect password");
                                            editText2.requestFocus();
                                        } catch(FirebaseAuthInvalidCredentialsException e) {
                                            editText2.setError("Incorrect password");
                                            editText2.requestFocus();
                                        } catch(FirebaseAuthInvalidUserException e) {
                                            editText.setError("Username does not exist");
                                            editText.requestFocus();
                                        } catch(Exception e) {
                                            //Log.e(TAG, e.getMessage());
                                        }
                                } else {


                                    Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
