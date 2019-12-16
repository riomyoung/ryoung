package com.traf7.youngrio.chatroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    EditText username_edittext, email_edittext, password_edittext;
    String username, email, password = "";
    Button submit_butt;
    RadioGroup rdbGroup;
    //android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();

        username_edittext = findViewById( R.id.username );
        email_edittext = findViewById( R.id.email );
        password_edittext = findViewById( R.id.password );
        submit_butt = findViewById( R.id.signupbutt );


        submit_butt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if ( ! editTextNull( username_edittext) ) {
                    username = username_edittext.getText().toString();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_SHORT).show();
                }


                if ( ! editTextNull( email_edittext) ) {
                    email = email_edittext.getText().toString();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Email not found", Toast.LENGTH_SHORT).show();
                }

                if ( ! editTextNull( password_edittext ) ) {
                    password = password_edittext.getText().toString();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Password not found", Toast.LENGTH_SHORT).show();
                }

                if ( ! editTextNull( username_edittext) && ! editTextNull( email_edittext) && ! editTextNull( password_edittext )  )
                {
                    signupUser();
                }





                Intent intent = new Intent( SignupActivity.this, SignupActivity.class );

//                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

    }

    private void signupUser() {



        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                        /*
                        set username as user's display name
                         */
                            user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            user.updateProfile(profileUpdate);

                            addUserToDatabase(); //method to store user's data in cloud firestore

                            Toast.makeText(getApplicationContext(), "Registration successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Registration error. Check your details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void addUserToDatabase(){
        FirebaseFirestore database = FirebaseFirestore.getInstance(); //Initialize cloud firestore
        String key = user.getUid(); //retrieve the user id so we can later use as key in the database

        HashMap<String, String> userData = new HashMap<>();

        userData.put("a3_username", username);
        userData.put("a4_email", email);
        userData.put("a6_imageUrl", "none");

        Map<String, Object> update = new HashMap<>();
        update.put(key, userData);

        database.collection("users").document(key).set(update); //update user's details to Firestore
    }

    public boolean editTextNull( EditText editText )
    {
        if (  editText.getText().toString().equals( null )
            ||  editText.getText().toString().equals( "" ) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
