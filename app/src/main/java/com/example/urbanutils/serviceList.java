package com.example.urbanutils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class serviceList extends AppCompatActivity {
    private static final int REQUEST_CALL=1;
    String x="";
    TextView title_profession;
    ListView listOfService;
    FirebaseDatabase database;
    DatabaseReference Ref;
    ArrayList<Provider> serviceList = new ArrayList<Provider>();
    MyCustomAdapter myadapter;
    Provider provider;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_service_list);
            Bundle bundle=getIntent().getExtras();
            String profession = bundle.getString("profession");
            title_profession=(TextView)findViewById(R.id.title_profession);
            provider = new Provider();
            listOfService = (ListView) findViewById(R.id.listOfService);
            database = FirebaseDatabase.getInstance();
            Ref = database.getReference("providers");
            title_profession.setText(profession);

         Ref.child(profession).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    provider = ds.getValue(Provider.class);
                    String name = provider.getName();
                    String address = provider.getAddress();
                    String businessType = provider.getBusinessType();
                    String email = provider.getEmail();
                    String phoneNumber= provider.getPhoneNumber();
                    double lattitude = provider.getLattitude();
                    double longitude = provider.getLongitude();
                    boolean approval = provider.isApproval();
                    String rating = provider.getRating();
                    String upi = provider.getUpiID();
                    int noOfCustomer= provider.getNoOfCustomer();

                    if(approval== true)
                        serviceList.add(new Provider(name,address,businessType,email,lattitude,longitude,phoneNumber,approval,rating,upi,noOfCustomer));
                }
                myadapter = new MyCustomAdapter(serviceList);
                listOfService.setAdapter(myadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public class MyCustomAdapter extends BaseAdapter {
        public ArrayList<Provider> listnewsDataAdpater;

        public MyCustomAdapter(ArrayList<Provider> listnewsDataAdpater) {
            this.listnewsDataAdpater = listnewsDataAdpater;

        }


        @Override
        public int getCount() {
            return listnewsDataAdpater.size();
        }

        @Override
        public String getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater mInflater = getLayoutInflater();
            View myView = mInflater.inflate(R.layout.itemsview, null);


            final Provider s = listnewsDataAdpater.get(position);

            TextView tvTitle = (TextView) myView.findViewById(R.id.tvtitle);
            tvTitle.setText(s.getName());
            TextView tvtitle2=(TextView)myView.findViewById(R.id.tvtitle2);
            tvtitle2.setText(s.getAddress());
            TextView rate = (TextView)myView.findViewById(R.id.rate);
            rate.setText(s.getRating());
            RatingBar ratingBar =(RatingBar)myView.findViewById(R.id.rating);
            ratingBar.setRating(Float.parseFloat(s.getRating()));
            TextView prof =(TextView)myView.findViewById(R.id.prof);
            ImageView pay = (ImageView)myView.findViewById(R.id.payment);
            ImageView call = (ImageView)myView.findViewById(R.id.call);
            ImageView map =(ImageView)myView.findViewById(R.id.map);
            ImageView chat =(ImageView)myView.findViewById(R.id.chat);

            prof.setText(s.getBusinessType());

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    x=s.getPhoneNumber();
                    callNow();
                }
            });

            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),pay.class);
                    intent.putExtra("providercustomer",  s);
                    startActivity(intent);
                }
            });
            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), customerService.class);
                    intent.putExtra("providercustomer",  s);
                    intent.putExtra("maplaunch","map");
                    startActivity(intent);
                }
            });
            chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), customerService.class);
                    intent.putExtra("providercustomer",  s);
                    intent.putExtra("maplaunch","chat");
                    startActivity(intent);


                }
            });

            return myView;
        }

    }

    private void callNow() {
        if(x.length()>0){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(serviceList.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:"+x;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   callNow();
            }else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
            }
        } }
    @Override
    protected void onRestart() {
        this.recreate();
        super.onRestart();
    }

}

