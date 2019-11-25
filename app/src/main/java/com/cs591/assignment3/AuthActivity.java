package com.cs591.assignment3;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import java.util.HashMap;

public class AuthActivity extends AppCompatActivity {

    private EditText mEmailLogin, mPasswordLogin, mEmailRegistration, mPasswordRegistration;

    private Button mButtonLogin, mButtonRegistration;
    private FirebaseAuth mAuth;

    private  FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mEmailLogin = (EditText) findViewById(R.id.emailLogin);
        mPasswordLogin = (EditText) findViewById(R.id.passwordLogin);
        mEmailRegistration = (EditText) findViewById(R.id.emailRegistration);
        mPasswordRegistration = (EditText) findViewById(R.id.passwordRegistration);

        mButtonLogin = (Button) findViewById(R.id.buttonLogin);
        mButtonRegistration = (Button) findViewById(R.id.buttonRegistration);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null) {

                    DatabaseReference ifuser = FirebaseDatabase.getInstance().getReference();
                    ifuser.child("users").child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Intent intent = GameActivity.newIntent(AuthActivity.this, "yongzhou168");
                                startActivity(intent);
                            }
                            else{
                                Intent intent = GameActivity.newIntent(AuthActivity.this, "yongzhou168");
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

//                    Intent intent = new Intent(AuthActivity.this, GameActivity.class);
//                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mButtonRegistration.setOnClickListener( (v) -> {
            String email = mEmailRegistration.getText().toString();
            String passward = mPasswordRegistration.getText().toString();
            mAuth.createUserWithEmailAndPassword( email, passward).addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()) {
                        Toast.makeText(AuthActivity.this, "Registerion error", Toast.LENGTH_SHORT).show();
                    }
                     else{

                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                        String email = mEmailRegistration.getText().toString();


                        HashMap newPost = new HashMap();
                        newPost.put("name", email);
//                        newPost.put("score", Score);
//

                        current_user_db.setValue(newPost);
//                        DatabaseReference ref = database.getReference( ).child("Users").child("Drivers").child(user_id).child("name");
                        current_user_db.setValue(email);

                    }
                }
            });
        } );
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmailLogin.getText().toString();
                String passward = mPasswordLogin.getText().toString();
                mAuth.signInWithEmailAndPassword(email, passward).addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(AuthActivity.this, "sign up error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
