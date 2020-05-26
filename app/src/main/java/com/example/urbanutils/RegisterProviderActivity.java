package com.example.urbanutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegisterProviderActivity extends AppCompatActivity {

    private TextInputEditText mName,mBusiness,mEmail,mAddress;

    private RadioGroup radioGroup;
    private RadioButton rDoctor,rElectrician;

    private Button btnRegister;

   private EditText upiID;
   private String bussType="";
    private double lattitude=0.0;
    private double longitude=0.0;
    private  String Address="";
    private String phoneNumber;
    private String upi;

    private  FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider);

        mName=findViewById(R.id.name_id);
        mBusiness=findViewById(R.id.business_id);
        mAddress=findViewById(R.id.address_id);
        mEmail=findViewById(R.id.email_id);
        rDoctor=findViewById(R.id.doctor_id);
        rElectrician=findViewById(R.id.electrician_id);

        radioGroup=findViewById(R.id.radiogroup);

        textView=findViewById(R.id.phone_id);
        upiID=findViewById(R.id.upiID);

        fetchPhoneNumber();


        btnRegister=findViewById(R.id.register_priveder_id);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });



        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("providers");

    }

    private void init() {

         String name=mName.getText().toString().trim();
         if(name.length()==0)
         {
             mName.requestFocus();
             mName.setError("name is required");
             return;
         }

         String businessType=mBusiness.getText().toString().trim();
         if(businessType.length()==0)
         {
             mBusiness.requestFocus();
             mBusiness.setError("business type is requtrd");
             return;
         }

         String email=mEmail.getText().toString().trim();
         if(email.length()==0)
         {
             mEmail.requestFocus();
             mEmail.setError("email is required");
             return;
         }

         String address=mAddress.getText().toString().trim();
         if(address.length()==0)
         {
             mAddress.requestFocus();
             mAddress.setError("address is required");
             return;
         }

         int selectedId=radioGroup.getCheckedRadioButtonId();

         if(selectedId==-1)
         {
             Toast.makeText(this, "select one business type", Toast.LENGTH_SHORT).show();
             return;
         }
         else
         {
             RadioButton radioButton=findViewById(selectedId);
              bussType=radioButton.getText().toString();
            Toast.makeText(this, "hello"+bussType+"hello", Toast.LENGTH_SHORT).show();
         }

         findLocation(address);
        Toast.makeText(this, "location has been fetched", Toast.LENGTH_SHORT).show();
         pushData(name,address,bussType,email);

    }

    private void pushData(String name,String address,String bussType,String email) {

        Toast.makeText(this,textView.getText().toString()+"hjhjhjh", Toast.LENGTH_LONG).show();

        phoneNumber=textView.getText().toString();
        upi=upiID.getText().toString();
        boolean approval = false;
        Provider provider=new Provider(name,address,bussType,email,lattitude,longitude,phoneNumber,approval,"0",upi,0);
        databaseReference.child(bussType).child(phoneNumber).setValue(provider);

    }

    private void fetchPhoneNumber() {
        String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String[] number = new String[10];
        DatabaseReference mDatabaseReference=FirebaseDatabase.getInstance().getReference("users");
        mDatabaseReference.child(userid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        textView.setText(dataSnapshot.child("phoneNumber").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(RegisterProviderActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    private void findLocation(String address) {
        Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList=geocoder.getFromLocationName(address,1);
           if(addressList!=null&&addressList.size()>0)
           {
               Address address1=addressList.get(0);
               lattitude=address1.getLatitude();
               longitude=address1.getLongitude();
               StringBuilder stringBuilder=new StringBuilder("");
               stringBuilder.append(lattitude).append(longitude).toString();
               if(stringBuilder==null)
               {
                   Toast.makeText(this, "enter correct loaction", Toast.LENGTH_SHORT).show();
                   return;
               }
               Toast.makeText(this, address+" "+lattitude+" "+longitude, Toast.LENGTH_LONG).show();
           }
           else
           {
               Toast.makeText(this, "enter correct loaction", Toast.LENGTH_SHORT).show();
               return;
           }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
