package com.example.sgcdtp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsuario, editTextClave;
    Button buttonAceptar,buttonSalir,buttonRegistrarPasajero,buttonRegistrarFiscalizador,  buttonOlvideClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsuario=(EditText)findViewById(R.id.editTextNumAsientos);
        editTextClave=(EditText)findViewById(R.id.editTextClave);
        buttonAceptar=(Button)findViewById(R.id.buttonAceptar);
        buttonSalir=(Button)findViewById(R.id.buttonCerrar);
        buttonRegistrarPasajero=(Button)findViewById(R.id.buttonRegistrarPasajero);
        buttonOlvideClave=(Button)findViewById(R.id.buttonOlvideClave);


        //Boton ACEPTAR

        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsultarBD();
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

        //Boton Registrar Usuario

        buttonRegistrarPasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i3 = new Intent(MainActivity.this, RegistroPasajeroActivity.class);
                startActivity(i3);
            }
        });


        // Boton Olvido Clave

        buttonOlvideClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i6 = new Intent(MainActivity.this, RecuperarClaveActivity.class);
                startActivity(i6);
            }
        });

    }
    public Connection conexionDBSQL() {

        Connection conexion = null;

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.9;databaseName=SGCDTP;user=eydrogo;password=Al3jandr0;");
            //conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://bdeduardo.database.windows.net:1433;databaseName=SGCDTP;user=usuario;password=<Al3jandr0>;");
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://bdeyr-1.cq1ojcdnxlyi.us-east-1.rds.amazonaws.com:1433;databaseName=SGCDTP;user=usuario;password=pwdusuario;");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }

    public void ConsultarBD() {

        if (ValidarVariables()) {

            try {

                 PreparedStatement pst=conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_Pasajero] @Type = 'S', @usuario = '" + editTextUsuario.getText().toString()+"' , @clave ='" + editTextClave.getText().toString()+"'");
                //Toast.makeText(getApplicationContext(),"Consulta Correcta",Toast.LENGTH_SHORT).show();

                //String base = conexionDBSQL().getCatalog();
                ResultSet rs = pst.executeQuery();

                if (rs.next())  {

                    Toast.makeText(getApplicationContext(), "Encontro Registro", Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(MainActivity.this, MenuActivity.class);
                    i2.putExtra("IdPasajero", rs.getString(1) );
                    i2.putExtra("usuario", rs.getString(2));
                    startActivity(i2);
                }
                else {

                    PreparedStatement pst2=conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_Fiscalizador] @Type = 'S', @usuario = '" + editTextUsuario.getText().toString()+"' , @clave ='" + editTextClave.getText().toString()+"'");
                    //Toast.makeText(getApplicationContext(),"Consulta Correcta",Toast.LENGTH_SHORT).show();

                    String base2 = conexionDBSQL().getCatalog();
                    ResultSet rs2 = pst2.executeQuery();

                    if (rs2.next())  {

                        Toast.makeText(getApplicationContext(), "Encontro Registro", Toast.LENGTH_SHORT).show();
                        Intent i7 = new Intent(MainActivity.this, MenuFiscalizadorActivity.class);
                        i7.putExtra("IdFiscalizador", rs2.getString(1) );
                        i7.putExtra("usuario", rs2.getString(2));
                        startActivity(i7);
                    }
                    else
                    {
                       Toast.makeText(getApplicationContext(), "Usuario o Clave no existe", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (SQLException e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }

        //Intent i = new Intent(MainActivity.this, MenuActivity.class);
        //startActivity(i);
    }

    private boolean ValidarVariables() {

        if (editTextUsuario.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar Usuario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextClave.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar Clave", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }





}
