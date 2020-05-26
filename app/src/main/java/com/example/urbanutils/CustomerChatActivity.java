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

import com.example.urbanutils.model.CustomerAdapter;
import com.example.urbanutils.model.ChatModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerChatActivity extends AppCompatActivity implements View.OnClickListener {

    private String providerPhone,customerPhone,providerProfession;
    private EditText messageEditText;
    private Button sendButton;

    //RECYCLERVIEW
    private RecyclerView recyclerView;
    private CustomerAdapter customerAdapter;
    private ArrayList<ChatModel> arrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_chat);

        initialiseUI();
        retrieveData();
        sendMessage();




    }

    private void retrieveData() {

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);

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
                    },100);
                }
            }
        });


       prepareData();



    }

    private void prepareData() {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.child("customerchat").child(customerPhone).child(providerProfession).child(providerPhone).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int count=0;
                          arrayList=new ArrayList<>();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            ChatModel obj=dataSnapshot1.getValue(ChatModel.class);
                            arrayList.add(obj);
                            count++;
                        }
                        customerAdapter=new CustomerAdapter(arrayList,customerPhone);
                        recyclerView.setAdapter(customerAdapter);
                        recyclerView.scrollToPosition(arrayList.size()-1);



                        Toast.makeText(CustomerChatActivity.this, "datafetched"+count, Toast.LENGTH_SHORT).show();
                        customerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }




                });
    }

    private void sendMessage() {
       messageEditText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

               if(s.toString().trim().length()>0){
                   sendButton.setEnabled(true);
               }
               else{
                   sendButton.setEnabled(false);
               }


           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

       sendButton.setOnClickListener(this);


    }

    private void initialiseUI() {
        Intent intent=getIntent();
        providerPhone=intent.getStringExtra("PROVIDERPHONE");
        customerPhone=intent.getStringExtra("CUSTOMERPHONE");
        providerProfession=intent.getStringExtra("PROFESSION");
       // Toast.makeText(this, ""+providerPhone+customerPhone, Toast.LENGTH_SHORT).show();
        messageEditText=findViewById(R.id.message_id);
        sendButton=findViewById(R.id.send_id);
        recyclerView=findViewById(R.id.recycler_view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_id:
                sendData();
                customerAdapter.notifyDataSetChanged();
                messageEditText.setText("");
                break;
        }
    }

    private void sendData() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        String message=messageEditText.getText().toString().trim();
        String randomKey=databaseReference.push().getKey();

        ChatModel obj=new ChatModel(message,customerPhone);

        databaseReference.child("customerchat").child(customerPhone).child(providerProfession).child(providerPhone).child(randomKey).setValue(obj);
        databaseReference.child("providerchat").child(providerProfession).child(providerPhone).child(customerPhone).child(randomKey).setValue(obj);

        customerAdapter.notifyDataSetChanged();
    }

}
