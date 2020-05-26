package com.example.urbanutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.urbanutils.model.RequestedUserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowRequestedUserActivity extends AppCompatActivity implements RequestedUserAdapter.OnItemClickListener {

    private String profession,phonuNumber;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private RecyclerView recyclerView;
    private RequestedUserAdapter requestedUserAdapter;
    private ArrayList<String> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_requested_user);

        intialiseUI();



        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestedUserAdapter=new RequestedUserAdapter(this,arrayList);
        retrieveData();


        Toast.makeText(ShowRequestedUserActivity.this, "dataphone fetched", Toast.LENGTH_SHORT).show();



    }

    private void retrieveData() {
       // arrayList=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                   // RequestedUserModel obj=dataSnapshot1.getValue(RequestedUserModel.class);
                    arrayList.add(dataSnapshot1.getKey());
                }
                recyclerView.setAdapter(requestedUserAdapter);
              //  RequestedUserAdapter.OnItemClickListener listener=null;
              //  requestedUserAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void intialiseUI() {
        recyclerView=findViewById(R.id.recycler_view);
        Intent intent=getIntent();
        profession =intent.getStringExtra("PROVIDERPROFESSION");
        phonuNumber=intent.getStringExtra("MOBILE");
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("providerchat").child(profession).child(phonuNumber);


    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "item.is clicked", Toast.LENGTH_SHORT).show();
        String userPhone=arrayList.get(position);
        Intent intent=new Intent(ShowRequestedUserActivity.this,ProviderChatActivity.class);
        intent.putExtra("USERPHONE",userPhone);
        intent.putExtra("PROVIDERPHONE",phonuNumber);
        intent.putExtra("PROFESSION",profession);
        startActivity(intent);
        finish();

    }
}
