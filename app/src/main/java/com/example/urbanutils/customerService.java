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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class customerService extends AppCompatActivity {

    private TextView lattitudeTextview,longitudeTextview;
    private TextView customerLatTextview,customerLongTextview;
    private EditText customerLocation;

    private Button openMapButton,fetchLocationCustomer,chatButton;
    private double latCus=0.0,longCus=0.0;
    private double latSer=0.0,longSer=0.0;

    private String customerPhone="";
    private String providerPhone="";
    private String providerProfession="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        Bundle b=getIntent().getExtras();

        //Object of provider after clicking on list items in service list

        lattitudeTextview=findViewById(R.id.lattitude_id);
        longitudeTextview=findViewById(R.id.longitude_id);

        customerLatTextview=findViewById(R.id.lattitude_cus_id);
        customerLongTextview=findViewById(R.id.longitude_cus_id);

        customerLocation=findViewById(R.id.customer_location);

        final Provider provider=(Provider)b.getSerializable("providercustomer");

        longitudeTextview.setText(""+provider.getLongitude());
        lattitudeTextview.setText(""+provider.getLattitude());

        latSer=provider.getLattitude();
        longSer=provider.getLongitude();

        providerPhone=provider.getPhoneNumber();
        fetchCustomerPhone();
        providerProfession=provider.getBusinessType();





        fetchLocationCustomer=findViewById(R.id.fetch_location_id);

        String launch =b.getString("maplaunch");

        fetchLocationCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                init();
               }
        });



        openMapButton=findViewById(R.id.open_map_id);
        if(launch.equals("map")){
            openMap();
        }
        openMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

        
        chatButton=findViewById(R.id.chat_id);
        if(launch.equals("chat")){
            openChat();
        }
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChat();
            }
        });



    }

    private void fetchCustomerPhone() {
      //
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("users");
        String userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               customerPhone= dataSnapshot.child("phoneNumber").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void openChat() {
        Intent intent=new Intent(customerService.this, CustomerChatActivity.class);
        intent.putExtra("PROVIDERPHONE",providerPhone);
        intent.putExtra("CUSTOMERPHONE",customerPhone);
        intent.putExtra("PROFESSION",providerProfession);

        startActivity(intent);
        finish();

    }

    private void openMap() {
            MapLatLonClass obj=new MapLatLonClass(latCus,longCus,latSer,longSer,providerPhone,customerPhone);
             Intent intent1=new Intent(customerService.this,MapActivity.class);
             intent1.putExtra("CONTENT", (Serializable) obj);
             startActivity(intent1);
             finish();
    }

    private void init() {

         String address=customerLocation.getText().toString().trim();
        if(address.length()<=0)
        {
            customerLocation.setError("required");
            return;
        }

        Toast.makeText(customerService.this, address, Toast.LENGTH_LONG).show();
         findLocation(address);
        customerLongTextview.setText(""+longCus);
        customerLatTextview.setText(""+latCus);
    }

    private void findLocation(String address) {
        Toast.makeText(customerService.this, "Location fetched", Toast.LENGTH_SHORT).show();


        Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList=geocoder.getFromLocationName(address,1);
            if(addressList!=null&&addressList.size()>0)
            {
                Address address1=addressList.get(0);
                latCus=address1.getLatitude();
                longCus=address1.getLongitude();
                StringBuilder stringBuilder=new StringBuilder("");
                stringBuilder.append(latCus).append(longCus).toString();
                if(stringBuilder==null)
                {
                    Toast.makeText(this, "enter correct loaction", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, address+" "+latCus+" "+longCus, Toast.LENGTH_LONG).show();
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
