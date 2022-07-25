package com.example.scorpio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private EditText TxtUsuario;
    private EditText TxtPass;
    private Button btnnews, btncre;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        TxtUsuario = (EditText) findViewById(R.id.TxtUsuario);
        TxtPass = (EditText) findViewById(R.id.TxtPass);
        btnnews = (Button) findViewById(R.id.btnnews);
        btncre  = (Button) findViewById(R.id.btncre);




        btnnews.setOnClickListener(this);
        btncre.setOnClickListener(this);


    }



    private void registrar(){
        String email = TxtUsuario.getText().toString().trim();
        String password = TxtPass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Ingrese email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Falta ingresar password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Realizando registro...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "registrado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "No se pudo", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });


    }

    private void ets(){
        String email = TxtUsuario.getText().toString().trim();
        String password = TxtPass.getText().toString().trim();

        final LottieAnimationView animationView = findViewById(R.id.animation_view);
        animationView.setAnimation("loadcomplet.json");
        animationView.setVisibility(View.GONE);

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Ingrese email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Falta ingresar password", Toast.LENGTH_SHORT).show();
            return;
        }

        animationView.setVisibility(View.VISIBLE);
        animationView.playAnimation();
        animationView.loop(true);


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                    Intent intencion = new Intent(getApplication(),Homes .class);
                    startActivity(intencion);
                    finish();
                } else {
                    animationView.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "cam", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public void entro(){
        Intent js = new Intent(this,Citas.class);
        startActivity(js);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){

            case R.id.btnnews:
                ets();
            break;
            case R.id.btncre:
                registrar();
                break;

        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, Homes.class));
            finish();
        }
    }


}
