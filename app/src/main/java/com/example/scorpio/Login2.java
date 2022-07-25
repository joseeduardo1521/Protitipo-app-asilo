package com.example.scorpio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login2 extends AppCompatActivity {

    private EditText emailp;
    private EditText passwordp;
    private Button btnCuentaP;

    private String email ="";
    private String password ="";

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        emailp = (EditText) findViewById(R.id.email);
        passwordp = (EditText) findViewById(R.id.password);
        btnCuentaP = (Button) findViewById(R.id.btnCuenta);
        mAuth = FirebaseAuth.getInstance();

        btnCuentaP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailp.getText().toString();
                password = passwordp.getText().toString();
                final LottieAnimationView animationView = findViewById(R.id.animation_view2);
                animationView.setAnimation("loadcomplet.json");
                animationView.setVisibility(View.GONE);

                if (!email.isEmpty() && !password.isEmpty()){
                    animationView.setVisibility(View.VISIBLE);
                    animationView.playAnimation();
                    animationView.loop(true);
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if(task.isSuccessful()){
                                startActivity(new Intent(Login2.this,Homes.class));
                                finish();
                            }
                            else{
                                animationView.setVisibility(View.GONE);
                                Toast.makeText(Login2.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    animationView.setVisibility(View.GONE);
                    Toast.makeText(Login2.this, "Complete sus datos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void loginUser() {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){
                    startActivity(new Intent(Login2.this,Homes.class));
                    finish();
                }
                else{

                    Toast.makeText(Login2.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(Login2.this, Homes.class));
            finish();
        }
    }


}
