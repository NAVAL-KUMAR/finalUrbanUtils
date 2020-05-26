package com.example.urbanutils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private String phoneNumber;
    private TextView mobileNumber;
    private  TextView profileTextview,registerProviderTextview,doctor,electrician,providerEnter;
    private Button admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        admin=(Button)findViewById(R.id.admin);

        // get saved phone number
        SharedPreferences prefs =  getApplicationContext().getSharedPreferences("USER_PREF",
                Context.MODE_PRIVATE);
        phoneNumber = prefs.getString("phoneNumber", NULL);

        mobileNumber = findViewById(R.id.mobileNumber);
        mobileNumber.setText(phoneNumber);

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        profileTextview=findViewById(R.id.profile_id);
        profileTextview.setOnClickListener(this);

        registerProviderTextview=findViewById(R.id.register_priveder_id);
        registerProviderTextview.setOnClickListener(this);

        doctor=findViewById(R.id.doctor_id);
        doctor.setOnClickListener(this);

        electrician=findViewById(R.id.electrician_id);
        electrician.setOnClickListener(this);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),providerList.class));
            }
        });

        providerEnter=findViewById(R.id.provider_id);
        providerEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_id:
                Toast.makeText(this, "Profile button is clicked", Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(ProfileActivity.this, MyProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.register_priveder_id:
                Toast.makeText(this, "register provider activit is called", Toast.LENGTH_SHORT).show();
                Intent intent6=new Intent(ProfileActivity.this,RegisterProviderActivity.class);
                startActivity(intent6);
                break;
            case R.id.doctor_id:
                //Toast.makeText(this,"Doctor is Selected",Toast.LENGTH_SHORT).show();
                Intent intent7=new Intent(ProfileActivity.this,serviceList.class);
                intent7.putExtra("profession","Doctor");
                startActivity(intent7);
                break;
            case R.id.electrician_id:
                //Toast.makeText(this,"Doctor is Selected",Toast.LENGTH_SHORT).show();
                Intent intent8=new Intent(ProfileActivity.this,serviceList.class);
                intent8.putExtra("profession","Electrician");
                startActivity(intent8);
                break;

            case R.id.provider_id:
                startActivity(new Intent(ProfileActivity.this,ProviderLoginActivity.class));
                break;
        }
    }
}
/*
* <!--
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ProfileActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:orientation="vertical">

        <ImageView
            android:layout_width="100dp"
            android:layout_height="100dp"
            android:background="@drawable/user"
            android:foregroundGravity="center" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="30dp"
            android:text="Welcome"
            android:textAppearance="@style/Base.TextAppearance.AppCompat.Large"
            android:textColor="@color/grey_80"
            android:textStyle="bold" />

        <TextView
            android:id="@+id/mobileNumber"
            android:layout_width="280dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="5dp"
            android:gravity="center"
            android:text="Number"
            android:textAppearance="@style/Base.TextAppearance.AppCompat.Subhead"
            android:textColor="@color/grey_60" />

        <androidx.appcompat.widget.AppCompatButton
            android:id="@+id/buttonLogout"
            android:layout_width="200dp"
            android:layout_marginTop="30dp"
            android:layout_height="wrap_content"
            android:background="@drawable/btn_rounded_red"
            android:text="LOGOUT"
            android:textColor="@android:color/white" />



    </LinearLayout>

</RelativeLayout>-->
*/