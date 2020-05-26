package com.example.urbanutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class providerList extends AppCompatActivity {
    ListView listOfServiceProviderforApproval;
    FirebaseDatabase database;
    DatabaseReference Ref;
    ArrayList<Provider> listForApproval = new ArrayList<Provider>();
    MyCustomAdapter myadapter;
    Provider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_list);
        provider = new Provider();
        listOfServiceProviderforApproval = (ListView) findViewById(R.id.listOfServiceProviderforApproval);
        database = FirebaseDatabase.getInstance();
        Ref = database.getReference("providers");

        Ref.child("Doctor").addValueEventListener(new ValueEventListener() {
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
                    String upiID = provider.getUpiID();
                    String rating = provider.getRating();
                    int noOfCustomer=provider.getNoOfCustomer();
                    if(approval== false)
                    listForApproval.add(new Provider(name,address,businessType,email,lattitude,longitude,phoneNumber,approval,rating,upiID,noOfCustomer));
                }
                myadapter = new MyCustomAdapter(listForApproval);
                listOfServiceProviderforApproval.setAdapter(myadapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        Ref.child("Electrician").addValueEventListener(new ValueEventListener() {
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
                    String upiID = provider.getUpiID();
                    String rating = provider.getRating();
                    int noOfCustomer = provider.getNoOfCustomer();
                    //list.add(name+" ");
                    if(approval==false)
                    listForApproval.add(new Provider(name,address,businessType,email,lattitude,longitude,phoneNumber,approval,rating,upiID,noOfCustomer));
                }
                 myadapter = new MyCustomAdapter(listForApproval);
                 listOfServiceProviderforApproval.setAdapter(myadapter);

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
            TextView prof =(TextView)myView.findViewById(R.id.prof);
            prof.setText(s.getBusinessType());
            TextView rate = (TextView)myView.findViewById(R.id.rate);
            rate.setText(s.getRating());
            RatingBar ratingBar =(RatingBar)myView.findViewById(R.id.rating);
            ratingBar.setRating(Float.parseFloat(s.getRating()));

            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), approveProvider.class);
                    intent.putExtra("providerapproval",  s);
                    startActivity(intent);
                    finish();


                }
            });

            return myView;
        }

    }
}
