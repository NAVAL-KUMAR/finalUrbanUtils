package com.example.urbanutils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class approveProvider extends AppCompatActivity {
    private TextView name;
    private TextView address;
    private TextView phoneNumber;
    private TextView email;
    private TextView businessType;
    private Button decline;
    private Button approve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_provider);




        name =(TextView)findViewById(R.id.name);
        address =(TextView)findViewById(R.id.address);
        phoneNumber =(TextView)findViewById(R.id.phoneNo);
        email =(TextView)findViewById(R.id.email);
        businessType =(TextView)findViewById(R.id.businessType);
        decline =(Button)findViewById(R.id.decline);
        approve=(Button)findViewById(R.id.approve);
        Bundle b=getIntent().getExtras();
        final Provider provider=(Provider)b.getSerializable("providerapproval");

        name.setText(provider.getName());
        address.setText(provider.getAddress());
        phoneNumber.setText(provider.getPhoneNumber());
        businessType.setText(provider.getBusinessType());
        email.setText(provider.getEmail());

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=getIntent().getExtras();
                String title1=b.getString("msg1");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


                Query applesQuery = ref.child("providers").child(provider.getBusinessType()).orderByChild("phoneNumber").equalTo(provider.getPhoneNumber());
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //  Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
                Intent intent1=new Intent(getApplicationContext(),providerList.class);
                startActivity(intent1);
                finish();
            }
        });

        //to update to firebase

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                Query query = rootRef.child("providers").child(provider.getBusinessType()).orderByChild("phoneNumber").equalTo(provider.getPhoneNumber());
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {

                            ds.child("approval").getRef().setValue(true);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };
                query.addListenerForSingleValueEvent(eventListener);
                Intent intent1=new Intent(getApplicationContext(),providerList.class);
                startActivity(intent1);
                finish();

            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), providerList.class);
        startActivity(intent);
        finish();
    }

}
