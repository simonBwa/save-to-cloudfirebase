package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RegisterAct extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private FirebaseDatabase mRef;
    private DatabaseReference mDatabase;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    private ProgressDialog dialog;


    private ImageView arrowBack, imProfile;
    private Button btnRegister;
    private  TextInputEditText nameR, phoneR, emailR, passwordR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imProfile = (ImageView) findViewById(R.id.ivProfile);
        nameR = (TextInputEditText) findViewById(R.id.nameTr);
        emailR = (TextInputEditText) findViewById(R.id.emailTr);
        phoneR = (TextInputEditText) findViewById(R.id.phoneTr);
        passwordR = (TextInputEditText) findViewById(R.id.passwordTr);


        arrowBack = (ImageView) findViewById(R.id.arrowBr);

        btnRegister = (Button) findViewById(R.id.buttonRegister);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();








        // initial progress dialog
        ProgressDialog dialog = new ProgressDialog(RegisterAct.this);
        dialog.setMessage("please wait...");
        dialog.show();
        dialog.dismiss();


        imProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ambil gambar

            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

                String name = nameR.getText().toString();
                String phone = phoneR.getText().toString();
                String email = emailR.getText().toString();
                String password = passwordR.getText().toString();



                if (name.isEmpty()){
                    nameR.setError("Insert your name...");
                }
                if (phone.isEmpty()){
                    phoneR.setError("Insert your phone number...");
                }
                if (email.isEmpty()){
                    emailR.setError("Insert your email...");
                }
                if (password.isEmpty()){
                    passwordR.setError("Insert your password...");
                }
                if (password.length()<6){
                    passwordR.setError("Your password minimal 6 character...");
                }


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                            dialog.setMessage("Please wait...");

                        if (task.isSuccessful()){
                            Toast.makeText(RegisterAct.this,"Success Register",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),DashboardAct.class));


                            User user = new User(name,phone, email,password);

                            //get user Id
                            String userId = task.getResult().getUser().getUid();




                            // Add a new document with a generated ID
                            db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {

                                            dialog.setMessage("Success added to claud");

                                            Toast.makeText(RegisterAct.this,"Document Cloud Success added",Toast.LENGTH_SHORT).show();


                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterAct.this, "Error",Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
//

                    }
                });




            }
        });
        //end btn register





    //activity
    }








//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() !=null){
//            Uri filePath = data.getData();
//            Picasso.get().load(filePath).fit().centerInside().into(imProfile);
//        }
//        else {
//            Toast.makeText(this,"Not Image this is", Toast.LENGTH_SHORT).show();
//        }
//    }







    //public class
}