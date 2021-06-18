package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAct extends AppCompatActivity {

    TextView registerTv;
    TextInputEditText tiEmail, tiPassword;
    Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // initial progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        tiEmail = (TextInputEditText) findViewById(R.id.emailTl);
        tiPassword = (TextInputEditText) findViewById(R.id.passwordTl);
        registerTv = (TextView) findViewById(R.id.registerTe);
        btnLogin = (Button) findViewById(R.id.buttonLogin);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = tiEmail.getText().toString();
                String password = tiPassword.getText().toString();



                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tiEmail.setError("email required");
                }
                if (TextUtils.isEmpty(password)) {
                    tiPassword.setError("password required");
                }
                if (password.length()<6){
                    tiPassword.setError("password must be 6 character");
                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginAct.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginAct.this, "Success Login", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),DashboardAct.class));

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginAct.this, "email and password not registered", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }

            }
        });



        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAct.this, RegisterAct.class));
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}