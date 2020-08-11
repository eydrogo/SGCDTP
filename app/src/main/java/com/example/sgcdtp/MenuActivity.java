package com.example.sgcdtp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    Button buttonSalir,buttonRegistrarInc,buttonVerInfo;
    String idPasajero2,usuario2;
    TextView textViewUsuario2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonSalir=(Button)findViewById(R.id.buttonCerrar);
        buttonRegistrarInc= (Button)findViewById(R.id.buttonRegistrarProblema);
        buttonVerInfo = (Button)findViewById(R.id.buttonVerInfo);

        RecibirDatos2();

        // Boton VerInfo
        buttonVerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i7 = new Intent(MenuActivity.this, ReporteIncidenciasActivity.class);
               // i5.putExtra("usuario3",usuario2);
                //i5.putExtra("IdPasajero3", idPasajero2 );
                startActivity(i7);
            }
        });


        // Boton Cerrar
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //finish();

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        // Boton Registrar Incidencia

        buttonRegistrarInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i3 = new Intent(MenuActivity.this, RegistroIncidenciasActivity.class);
                i3.putExtra("usuario3",usuario2);
                i3.putExtra("IdPasajero3", idPasajero2 );
                startActivity(i3);
                            }
        });
    }

    private void RecibirDatos2() {

        Bundle DatosExtras2 = getIntent().getExtras();

        idPasajero2 = DatosExtras2.getString("IdPasajero");
        usuario2 = DatosExtras2.getString("usuario");

        textViewUsuario2 = (TextView) findViewById(R.id.textViewUsuario2);
        //textViewUsuario2.setText("Bienvenido " + usuario2 + " ID: " + idPasajero2.toString() );
        textViewUsuario2.setText("Bienvenido " + usuario2 );

    }
}
