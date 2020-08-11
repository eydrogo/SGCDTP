package com.example.sgcdtp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroEmpresaActivity extends AppCompatActivity {


    String  TextRuc,  TextDescripcion, TextDireccion;
    Button buttonCerrar, buttonGrabar;
    EditText  editTextRuc, editTextDescripcion, editTextDireccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroempresa);



        buttonCerrar=(Button)findViewById(R.id.buttonCerrar);
        buttonGrabar=(Button)findViewById(R.id.buttonGrabar);

        editTextRuc = (EditText)findViewById(R.id.editTextRuc);
        editTextDescripcion = (EditText)findViewById(R.id.editTextDescripcion);
        editTextDireccion    = (EditText)findViewById(R.id.editTextDireccion);


        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    GrabarEmpresa();
            }
        });


    }


    private void GrabarEmpresa() {

        if (ValidarVariables () ) {

            MainActivity ConexionSQL = new MainActivity();

            try {
                PreparedStatement pst=ConexionSQL.conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_EmpTransporte] @Type = 'I', @idRuc = '" + TextRuc + "', @Descripcion = '" + TextDescripcion   +  "', @Direccion = '" + TextDireccion + "'");
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

        TextRuc = editTextRuc.getText().toString().trim();
        TextDescripcion = editTextDescripcion.getText().toString().trim();
        TextDireccion = editTextDireccion.getText().toString().trim();

        if (TextRuc.equalsIgnoreCase("")  || TextRuc.length() != 11 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar sus Datos con mas de 1ma caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextDescripcion.equalsIgnoreCase("")  || TextDescripcion.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar sus Datos con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextDireccion.equalsIgnoreCase("")  || TextDireccion.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar sus Datos con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


 };






