package com.example.scorpio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private EditText namesp;
    private EditText emailp;
    private EditText passwordp;
    private EditText agep, sexop, sangrep, alergiap, tratamip, horap, statp, cuarp;
    private Button btnp;
    private Button btnpas;

    //Variables a registrar
    private String name ="";
    private String email ="";
    private String password ="";
    private String age="";
    private String sex="";
    private String blood="";
    private String ale="";
    private String tratam="";
    private String hour="";
    private String status="";
    private String room="";
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        namesp = (EditText) findViewById(R.id.names);
        emailp = (EditText) findViewById(R.id.email);
        passwordp = (EditText) findViewById(R.id.password);
        agep = (EditText) findViewById(R.id.txt_edad);
        sexop = (EditText) findViewById(R.id.txt_sexo);
        sangrep =(EditText) findViewById(R.id.txt_sangre);
        alergiap =(EditText) findViewById(R.id.txt_alergia);
        tratamip = (EditText) findViewById(R.id.txt_tratamiento);
        horap = (EditText) findViewById(R.id.txt_hora);
        statp = (EditText) findViewById(R.id.txt_estatus);
        cuarp = (EditText) findViewById(R.id.txt_room);
        btnp = (Button) findViewById(R.id.btnRegistrar);
        btnpas =(Button) findViewById(R.id.btnlog);


        btnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = namesp.getText().toString();
                email = emailp.getText().toString();
                password = passwordp.getText().toString();
                age = agep.getText().toString();
                sex = sexop.getText().toString();
                blood = sangrep.getText().toString();
                ale= alergiap.getText().toString();
                tratam = tratamip.getText().toString();
                hour = horap.getText().toString();
                status = statp.getText().toString();
                room = cuarp.getText().toString();

                if (!name.isEmpty()&&!email.isEmpty()&&!password.isEmpty()&&!age.isEmpty()&&!sex.isEmpty()&&!blood.isEmpty()&&!ale.isEmpty()&&!tratam.isEmpty()&&!hour.isEmpty()&&!status.isEmpty()&&!room.isEmpty()){

                    if (password.length()>=6){
                        registerUser();
                    }
                    else{
                        Toast.makeText(Login.this, "La contraseña debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(Login.this, "Complete todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnpas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Login2.class));
            }
        });


    }

    private void registerUser(){

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    final Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email",email);
                    map.put("password", password);
                    map.put("age",age);
                    map.put("sex",sex);
                    map.put("blood",blood);
                    map.put("ale",ale);
                    map.put("tratam",tratam);
                    map.put("hour",hour);
                    map.put("status",status);
                    map.put("room",room);



                    final String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Usuario").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(Login.this, "Registrado", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Login.this, "Error al registrar datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(Login.this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(Login.this, Homes.class));
            finish();
        }
    }
}
