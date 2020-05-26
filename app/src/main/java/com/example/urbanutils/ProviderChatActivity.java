package com.example.urbanutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.urbanutils.model.ChatModel;
import com.example.urbanutils.model.ProviderChatAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProviderChatActivity extends AppCompatActivity implements View.OnClickListener {


    private String providerPhone,userPhone,profession;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private RecyclerView recyclerView;
    private ProviderChatAdapter providerChatAdapter;
    private ArrayList<ChatModel> arrayList=new ArrayList<>();

    private EditText messaeEdittext;
    private Button sendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_chat);

        initialiseUI();
        retrieveData();
        sendMessage();
    }

    private void sendMessage() {
        messaeEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>0){
                    sendButton.setEnabled(true);
                }else{
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sendButton.setOnClickListener(this);

    }

    private void retrieveData() {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(bottom<oldBottom){
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(arrayList.size()>0)
                                recyclerView.smoothScrollToPosition(arrayList.size()-1);
                        }
                    },200);
                }
            }
        });
        prepareData();
    }

    private void prepareData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList=new ArrayList<>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    ChatModel obj=dataSnapshot1.getValue(ChatModel.class);
                    arrayList.add(obj);
                }
                providerChatAdapter=new ProviderChatAdapter(arrayList,providerPhone);
                recyclerView.setAdapter(providerChatAdapter);
                recyclerView.scrollToPosition(arrayList.size()-1);

                Toast.makeText(ProviderChatActivity.this, "data fetched", Toast.LENGTH_SHORT).show();
                providerChatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialiseUI() {
        Intent intent=getIntent();
        profession=intent.getStringExtra("PROFESSION");
        userPhone=intent.getStringExtra("USERPHONE");
        providerPhone=intent.getStringExtra("PROVIDERPHONE");

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("providerchat").child(profession).child(providerPhone).child(userPhone);

        recyclerView=findViewById(R.id.recycler_view);

        sendButton=findViewById(R.id.send_button_id);
        messaeEdittext=findViewById(R.id.message_edittext_id);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_button_id:
                sendData();
                providerChatAdapter.notifyDataSetChanged();
                messaeEdittext.setText("");
                break;
        }
    }

    private void sendData() {
        DatabaseReference mref=FirebaseDatabase.getInstance().getReference();
        String message=messaeEdittext.getText().toString().trim();
        String randomKey=mref.push().getKey();

        ChatModel obj=new ChatModel(message,providerPhone);

        mref.child("customerchat").child(userPhone).child(profession).child(providerPhone).child(randomKey).setValue(obj);
        mref.child("providerchat").child(profession).child(providerPhone).child(userPhone).child(randomKey).setValue(obj);

        providerChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ProviderChatActivity.this, ShowRequestedUserActivity.class);
        intent.putExtra("PROVIDERPROFESSION",profession);
        intent.putExtra("MOBILE",providerPhone);
        startActivity(intent);
    }
}
