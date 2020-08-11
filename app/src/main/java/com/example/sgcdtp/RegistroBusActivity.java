package com.example.sgcdtp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistroBusActivity extends AppCompatActivity {


    String  TextNumPlaca,  TextNombreConductor, TextNombreCobrador, TextRuta, TextRucEmpresa ;
    Button buttonCerrar, buttonGrabar, buttonBuscar ;
    EditText editTextRucEmpresa, ediTextEmpTransporte,  editTextNumPlaca,  editTextNombreConductor, editTextNombreCobrador, editTextNumAsientos,editTextRuta;
    int intIdEmpresa,intNumAsientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrobus);


        buttonBuscar=(Button)findViewById(R.id.buttonBuscar);
        buttonCerrar=(Button)findViewById(R.id.buttonCerrar);
        buttonGrabar=(Button)findViewById(R.id.buttonGrabar);

        buttonGrabar.setEnabled(false);

        editTextRucEmpresa= (EditText)findViewById(R.id.editTextRucEmpresa);
        ediTextEmpTransporte= (EditText)findViewById(R.id.editTextEmpTransporte);
        editTextNumPlaca = (EditText)findViewById(R.id.editTextNumPlaca);
        editTextNombreConductor = (EditText)findViewById(R.id.editTextConductor);
        editTextNombreCobrador = (EditText)findViewById(R.id.editTextCobrador);
        editTextNumAsientos = (EditText)findViewById(R.id.editTextNumAsientos);
        editTextRuta = (EditText)findViewById(R.id.editTextRuta);

        editTextNumPlaca.setEnabled(false);
        editTextNombreConductor.setEnabled(false);
        editTextNombreCobrador.setEnabled(false);
        editTextNumAsientos.setEnabled(false);
        editTextRuta.setEnabled(false);


        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarEmpresa();
            }
        });


        buttonGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    GrabarBus();
            }
        });


    }

    private void BuscarEmpresa(){

        TextRucEmpresa = editTextRucEmpresa.getText().toString();

        if (ValidarRucEmpresa () ) {

            MainActivity ConexionSQL = new MainActivity();

            try {

                PreparedStatement pst=ConexionSQL.conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_EmpTransporte]  @Type = 'S', @idRuc = '" + TextRucEmpresa +"'" );

                //String base = ConexionSQL.conexionDBSQL().getCatalog();

                ResultSet rs = pst.executeQuery();

                if (rs.next())  {

                    Toast.makeText(getApplicationContext(), "Encontro Registro", Toast.LENGTH_SHORT).show();

                    intIdEmpresa = rs.getInt(1);
                    ediTextEmpTransporte.setText(rs.getString(2));

                    editTextRucEmpresa.setEnabled(false);
                    ediTextEmpTransporte.setEnabled(false);
                    buttonBuscar.setEnabled(false);

                    editTextNumPlaca.setEnabled(true);
                    editTextNombreConductor.setEnabled(true);
                    editTextNombreCobrador.setEnabled(true);
                    editTextNumAsientos.setEnabled(true);
                    editTextRuta.setEnabled(true);

                    buttonGrabar.setEnabled(true);

                }
            }
            catch (SQLException e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void GrabarBus() {

        if (ValidarVariables () ) {

            MainActivity ConexionSQL = new MainActivity();

            try {
                PreparedStatement pst=ConexionSQL.conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_Buses] @Type = 'I', @NumPlaca = '" + TextNumPlaca + "', @idEmpresa = '" + intIdEmpresa   +  "', @NombreConductor = '" + TextNombreConductor   +   "', @NombreCobrador = '" + TextNombreCobrador +  "', @NumAsientos = '" + intNumAsientos + "', @Ruta = '" + TextRuta + "'") ;
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

    private boolean ValidarRucEmpresa() {

        if (TextRucEmpresa.length() != 11 ) {
            Toast.makeText(getApplicationContext(), "RUC no es valido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean ValidarVariables() {

        TextNumPlaca = editTextNumPlaca.getText().toString().trim();
        TextNombreConductor = editTextNombreConductor.getText().toString().trim();
        TextNombreCobrador = editTextNombreCobrador.getText().toString().trim();
        intNumAsientos = Integer.parseInt(editTextNumAsientos.getText().toString());
        TextRuta = editTextRuta.getText().toString().trim();

        if (TextNumPlaca.equalsIgnoreCase("")  || TextNumPlaca.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar datos de Placa con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

       if (TextNombreConductor.equalsIgnoreCase("")  || TextNombreConductor.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar datos del Conductor con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextNombreCobrador.equalsIgnoreCase("")  || TextNombreCobrador.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar datos del Cobrador con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (intNumAsientos < 5  ) {
            Toast.makeText(getApplicationContext(), "Debe tener mas de 5 de asientos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextRuta.equalsIgnoreCase("") || TextRuta.length() < 3  ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar su ruta con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


 };






