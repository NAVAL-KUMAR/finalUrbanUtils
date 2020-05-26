package com.example.urbanutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProviderLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView professionEdittext,mobileEdittext;
    private Button loginButton;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String profession="",mobile="";

    private RadioButton generatedButton;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_login);

        initialiseUI();

        loginButton.setOnClickListener(this);


    }

    private void initialiseUI() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        professionEdittext=findViewById(R.id.profession_id);
        mobileEdittext=findViewById(R.id.mobile_id);
        loginButton=findViewById(R.id.login_id);
        radioGroup=findViewById(R.id.id_radiogroup);

        loginButton.setEnabled(false);

        fetchPhoneNumber();




    }

    private void fetchPhoneNumber() {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String userId=firebaseAuth.getCurrentUser().getUid();

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mobile=dataSnapshot.child("phoneNumber").getValue().toString();
                mobileEdittext.setText(mobile);
                loginButton.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        int selectedId=radioGroup.getCheckedRadioButtonId();


        if(selectedId==-1){
            professionEdittext.requestFocus();
            return;
        }
        generatedButton=(RadioButton)findViewById(selectedId);

        profession=generatedButton.getText().toString();

       /* String profession=professionEdittext.getText().toString();
        String mobile=mobileEdittext.getText().toString();*/
        String newMobile=mobile;

        databaseReference.child("providers").child(profession).child(newMobile).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(ProviderLoginActivity.this, "key exist", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(ProviderLoginActivity.this, ShowRequestedUserActivity.class);
                    intent.putExtra("PROVIDERPROFESSION",profession);
                    intent.putExtra("MOBILE",newMobile);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ProviderLoginActivity.this, "error1", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProviderLoginActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
