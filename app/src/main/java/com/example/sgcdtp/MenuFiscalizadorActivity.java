package com.example.sgcdtp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuFiscalizadorActivity extends AppCompatActivity {

    Button buttonCerrar,buttonVerReporte,buttonRegistrarEmpresa,buttonRegistrarBus, buttonRegistrarProblema,buttonRegistrarFiscalizador ;
    String idFiscalizador2,usuario2;
    TextView textViewUsuario2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menufiscalizador);

        buttonCerrar=(Button)findViewById(R.id.buttonCerrar);
        buttonVerReporte= (Button)findViewById(R.id.buttonVerReporte);
        buttonRegistrarEmpresa = (Button)findViewById(R.id.buttonRegistrarEmpresa);
        buttonRegistrarBus = (Button)findViewById(R.id.buttonRegistrarBus);
        buttonRegistrarProblema = (Button)findViewById(R.id.buttonRegistrarProblema);
        buttonRegistrarFiscalizador = (Button)findViewById(R.id.buttonRegistrarFiscalizador);



        RecibirDatos2();

        // Boton VerInfo
        buttonVerReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i7 = new Intent(MenuFiscalizadorActivity.this, ReporteFiscalizador.class);
               // i5.putExtra("usuario3",usuario2);
                //i5.putExtra("IdPasajero3", idPasajero2 );
                startActivity(i7);
            }
        });

// Boton Registrar Empresa
        buttonRegistrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i9 = new Intent(MenuFiscalizadorActivity.this, RegistroEmpresaActivity.class);
                // i5.putExtra("usuario3",usuario2);
                //i5.putExtra("IdPasajero3", idPasajero2 );
                startActivity(i9);
            }
        });

// Boton Registrar Buses
        buttonRegistrarBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i10 = new Intent(MenuFiscalizadorActivity.this, RegistroBusActivity.class);
                // i5.putExtra("usuario3",usuario2);
                //i5.putExtra("IdPasajero3", idPasajero2 );
                startActivity(i10);
            }
        });


        // Boton Registrar Problemas
        buttonRegistrarProblema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i11 = new Intent(MenuFiscalizadorActivity.this, RegistroProblemaActivity.class);
                // i5.putExtra("usuario3",usuario2);
                //i5.putExtra("IdPasajero3", idPasajero2 );
                startActivity(i11);
            }
        });

        // Boton Registrar Fiscalizador
        buttonRegistrarFiscalizador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i12 = new Intent(MenuFiscalizadorActivity.this, RegistroFiscalizadorActivity.class);
                // i5.putExtra("usuario3",usuario2);
                //i5.putExtra("IdPasajero3", idPasajero2 );
                startActivity(i12);
            }
        });

        // Boton Cerrar
        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

     }

    private void RecibirDatos2() {

        Bundle DatosExtras2 = getIntent().getExtras();

        idFiscalizador2 = DatosExtras2.getString("IdFiscalizador");
        usuario2 = DatosExtras2.getString("usuario");

        textViewUsuario2 = (TextView) findViewById(R.id.textViewUsuario2);
        //textViewUsuario2.setText("Bienvenido " + usuario2 + " ID: " + idPasajero2.toString() );
        textViewUsuario2.setText("Bienvenido " + usuario2 );

    }
}
