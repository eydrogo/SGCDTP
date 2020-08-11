package com.example.sgcdtp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegistroFiscalizadorActivity extends AppCompatActivity {


    String  TextNombre,  TextPaterno, TextMaterno, TextDNI, TextUsuario, TextClave, TextCelular, TextCodigo;
    Button buttonCerrar, buttonGrabar;
    EditText  editTextNombre, editTextPaterno, editTextMaterno,editTextDNI, editTextUsuario, editTextClave, editTextCelular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrofiscalizador);

        buttonCerrar=(Button)findViewById(R.id.buttonCerrar);
        buttonGrabar=(Button)findViewById(R.id.buttonGrabar);

        editTextNombre = (EditText)findViewById(R.id.editTextNombre);
        editTextPaterno = (EditText)findViewById(R.id.editTextPaterno);
        editTextMaterno = (EditText)findViewById(R.id.editTextMaterno);
        editTextDNI = (EditText)findViewById(R.id.editTextDNI);
        editTextCelular = (EditText)findViewById(R.id.editTextCelular);
        editTextUsuario = (EditText)findViewById(R.id.editTextUsuario);
        editTextClave = (EditText)findViewById(R.id.editTextClave);


        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    GrabarFiscalizador();
            }
        });
    }

    private void GrabarFiscalizador() {

        if (ValidarVariables () ) {

            MainActivity ConexionSQL = new MainActivity();

            try {
                PreparedStatement pst=ConexionSQL.conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_Fiscalizador] @Type = 'I', @Nombre = '" + TextNombre + "', @Apellido_Paterno = '" + TextPaterno   +  "', @Apellido_Materno = '" + TextMaterno   +   "', @DNI = '" + TextDNI +  "', @celular = '" + TextCelular + "', @usuario = '" + TextUsuario + "', @Clave ='" + TextClave + "'"   );
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

        TextNombre = editTextNombre.getText().toString().trim();
        TextPaterno = editTextPaterno.getText().toString().trim();
        TextMaterno = editTextMaterno.getText().toString().trim();
        TextDNI = editTextDNI.getText().toString().trim();
        TextCelular = editTextCelular.getText().toString().trim();
        TextUsuario = editTextUsuario.getText().toString().trim();
        TextClave = editTextClave.getText().toString().trim();

        if (TextNombre.equalsIgnoreCase("")  || TextNombre.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar sus Datos con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextPaterno.equalsIgnoreCase("")  || TextPaterno.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar sus Datos con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextMaterno.equalsIgnoreCase("")  || TextMaterno.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar sus Datos con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextDNI.equalsIgnoreCase("")  || TextDNI.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar sus Datos de 3 números", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (TextUsuario.equalsIgnoreCase("") || TextUsuario.length() < 3  ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar su usuario con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextClave.equalsIgnoreCase("") || TextClave.length() < 3  ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar su Clave con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextCelular.equalsIgnoreCase("") || TextCelular.length() != 9 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar Celular Valido", Toast.LENGTH_SHORT).show();
            return false;
        }

        TextCelular = "+51" + TextCelular;
        return true;
    }


 };






