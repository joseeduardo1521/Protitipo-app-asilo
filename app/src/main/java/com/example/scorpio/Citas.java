package com.example.scorpio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scorpio.model.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Citas extends AppCompatActivity implements View.OnClickListener {

    EditText nameP, hourP, dateP;
    Button btncita;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);
        nameP = findViewById(R.id.txt_nombrePersona);
        hourP = findViewById(R.id.txt_horarios);
        dateP = findViewById(R.id.txt_fechas);
        btncita = findViewById(R.id.btncita);

        btncita.setOnClickListener(this);
        inicializarFirebase();


    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void onClick(View v) {
        String nombre = nameP.getText().toString();
        String horario = hourP.getText().toString();
        String fecha = dateP.getText().toString();
        switch (v.getId()){
            case R.id.btncita:
                if (nombre.equals("")||horario.equals("")||fecha.equals("")){
                    validacion();
                }
                else{
                    Persona p = new Persona();
                    p.setUid(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setHorario(horario);
                    p.setFecha(fecha);
                    databaseReference.child("Visita").child(p.getUid()).setValue(p);
                    limpiarCajas();
                    Toast.makeText(this, "Cita Agregada...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void limpiarCajas() {
        nameP.setText("");
        hourP.setText("");
        dateP.setText("");
    }

    private void validacion(){
        String nombre = nameP.getText().toString();
        String horario = hourP.getText().toString();
        String fecha = dateP.getText().toString();
        if (nombre.equals("")){
            nameP.setError("Required");
        }
        else if (horario.equals("")){
            hourP.setError("Required");
        }
        else if (fecha.equals("")){
            dateP.setError("Required");
        }
    }

}
