package com.example.sgcdtp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroProblemaActivity extends AppCompatActivity {


    String  TextProblema;
    Button buttonCerrar, buttonGrabar;
    EditText  editTextProblema;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroproblema);

        buttonCerrar=(Button)findViewById(R.id.buttonCerrar);
        buttonGrabar=(Button)findViewById(R.id.buttonGrabar);

        editTextProblema = (EditText)findViewById(R.id.editTextProblema);

        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    GrabarProblema();
            }
        });


    }


    private void GrabarProblema() {

        if (ValidarVariables () ) {

            MainActivity ConexionSQL = new MainActivity();

            try {
                PreparedStatement pst=ConexionSQL.conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_Problemas] @Type = 'I', @Descripcion = '" + TextProblema + "'") ;
               pst.executeUpdate();
                pst.close();
                Toast.makeText(getApplicationContext(), "Datos Grabados Correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
            catch (SQLException e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }

    }
    private boolean ValidarVariables() {

        TextProblema = editTextProblema.getText().toString().trim();

        if (TextProblema.equalsIgnoreCase("")  || TextProblema.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar sus Datos con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

      return true;
    }


 };






