package com.example.sgcdtp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class RecuperarClaveActivity extends AppCompatActivity {



    Button buttonCerrar, buttonEnviar;
    EditText editTextUsuario, editTextCelular,editTextClave;
    String TextUsuario, TextCelular;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperarclave);


        editTextUsuario = (EditText)findViewById(R.id.editTextUsuario);
        editTextCelular = (EditText)findViewById(R.id.editTextCelular);
        editTextClave = (EditText)findViewById(R.id.editTextClave);

        buttonCerrar=(Button)findViewById(R.id.buttonCerrar);
        buttonEnviar=(Button)findViewById(R.id.buttonEnviar);

        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextClave.setText("");
                EnviarClave();
            }
        });

        editTextClave.setVisibility(View.GONE);

    }

     // Inicio de  Funciones

    private void EnviarClave() {

        if (ValidarVariables () ) {

            MainActivity ConexionSQL = new MainActivity();

            try {
                PreparedStatement pst=ConexionSQL.conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_Pasajero] @Type = 'C', @usuario = '" + TextUsuario +"' , @celular ='" + TextCelular +"'");
                ResultSet rs = pst.executeQuery( );

                if (rs.next())  {

                    String mensaje = "Su clave es: " + rs.getString(1);
                    editTextClave.setVisibility(View.VISIBLE);
                    editTextClave.setText(mensaje);

                    //sms.sendTextMessage(txtCelular,null, mensaje , null,null);
                    Toast.makeText(getApplicationContext(), "Su clave es: " + mensaje, Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Usuario y/o celular no existe", Toast.LENGTH_SHORT).show();
                }
            }
            catch (SQLException e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean ValidarVariables() {

        TextCelular = editTextCelular.getText().toString().trim();
        TextUsuario = editTextUsuario.getText().toString().trim();

        if (TextUsuario.equalsIgnoreCase("") || TextUsuario.length() < 3  ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar su usuario con mas de 3 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextCelular.equalsIgnoreCase("") || TextCelular.length() != 9 ) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar Celular Valido", Toast.LENGTH_SHORT).show();
            return false;
        }

        //TextCelular = "+51" + TextCelular;
        return true;

    }





}
