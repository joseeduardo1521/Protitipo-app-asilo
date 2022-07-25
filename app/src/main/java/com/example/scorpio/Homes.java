package com.example.scorpio;

import android.content.Intent;
import android.os.Trace;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Homes extends AppCompatActivity {

    private Button mbtnOut;
    private Button mbtnvisita;
    private TextView mTextViewName;
    private TextView mTextViewEmail;
    private TextView medad;
    private TextView msexo;
    private TextView msangre;
    private TextView malergia;
    private TextView mtrata;
    private TextView mhora;
    private TextView mtus;
    private TextView mss;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mbtnOut = (Button) findViewById(R.id.btnOut);
        mbtnvisita =(Button) findViewById(R.id.btnVisita);
        mTextViewName = (TextView) findViewById(R.id.textViewName);
        mTextViewEmail = (TextView) findViewById(R.id.textViewEmail);
        medad = (TextView) findViewById(R.id.TextViewEdad);
        msexo = (TextView) findViewById(R.id.TextViewSexo);
        msangre = (TextView) findViewById(R.id.textViewsangre);
        malergia = (TextView) findViewById(R.id.textViewalergi);
        mtrata = (TextView) findViewById(R.id.textViewtrata);
        mhora = (TextView) findViewById(R.id.textViewhora);
        mtus = (TextView) findViewById(R.id.textViewstatus);
        mss = (TextView) findViewById(R.id.textViewroms);

        mbtnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             mAuth.signOut();
             startActivity(new Intent(Homes.this, MainActivity.class));
             finish();
            }
        });

        mbtnvisita.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homes.this, Citas.class));

            }
        });

        getUserInfo();


    }



    private void getUserInfo(){
        String id= mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuario").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String age = dataSnapshot.child("age").getValue().toString();
                    String sex = dataSnapshot.child("sex").getValue().toString();
                    String blood = dataSnapshot.child("blood").getValue().toString();
                    String ale = dataSnapshot.child("ale").getValue().toString();
                    String tratam = dataSnapshot.child("tratam").getValue().toString();
                    String hour = dataSnapshot.child("hour").getValue().toString();
                    String status = dataSnapshot.child("status").getValue().toString();
                    String room = dataSnapshot.child("room").getValue().toString();
                    mTextViewName.setText(name);
                    mTextViewEmail.setText(email);
                    medad.setText(age);
                    msexo.setText(sex);
                    msangre.setText(blood);
                    malergia.setText(ale);
                    mtrata.setText(tratam);
                    mhora.setText(hour);
                    mtus.setText(status);
                    mss.setText(room);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
