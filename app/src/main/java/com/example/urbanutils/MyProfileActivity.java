package com.example.urbanutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView phoneTextview;
    private EditText nameEdittext, emailEdittext;
    private ImageView editButton;
    //DATABASE

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        initialiseUI();

        editButton.setOnClickListener(this);

    }

    private void initialiseUI() {
        nameEdittext =findViewById(R.id.name_id);
        emailEdittext =findViewById(R.id.email_id);
        phoneTextview=findViewById(R.id.phone_number_id);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference("users");

        fetchData();

        editButton= findViewById(R.id.edit_profile_id);
    }

    private void fetchData() {
        String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseReference.child(userid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           nameEdittext.setText(dataSnapshot.child("name").getValue().toString());
                           emailEdittext.setText(dataSnapshot.child("email").getValue().toString());
                           phoneTextview.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_profile_id:
               // startActivity(new Intent(MyProfileActivity.this,ProfileEditActivity.class));
                updateProfile();
                break;
        }

    }

    private void updateProfile() {
        String name=nameEdittext.getText().toString().trim();
        String email=emailEdittext.getText().toString().trim();
        if(name.length()==0)
        {
            nameEdittext.requestFocus();
            nameEdittext.setError("name is required");
            return;
        }
        if(email.length()==0){
            emailEdittext.requestFocus();
            emailEdittext.setError("email is required");
        }
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseReference.child(uid).child("name").setValue(name);
        mDatabaseReference.child(uid).child("email").setValue(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MyProfileActivity.this, "profile is updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
