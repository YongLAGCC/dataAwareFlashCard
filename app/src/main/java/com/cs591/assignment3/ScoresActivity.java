package com.cs591.assignment3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ScoresActivity extends AppCompatActivity {
    TextView a, b, c, d, e;

    String array [] = new String [5];
    Button btnShowFive;
    DatabaseReference reff;


    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersdRef=rootRef.child("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        Toast.makeText(getApplicationContext(),  " score" +100, Toast.LENGTH_SHORT).show();

    }

//    ValueEventListener eventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            for(DataSnapshot ds: dataSnapshot.getChildren()){
//
//                String name = ds.child("name").getValue(String.class);
//                Log.d("TAG", name);
//
//                array.add(name);
//            }
//            ArrayAdapter<String> adapter = new ArrayAdapter<>()
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    }

}
