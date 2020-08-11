package com.example.sgcdtp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistroIncidenciasActivity extends AppCompatActivity {

    Button buttonCerrar, buttonGrabar;
    //TextView textViewUsuario3;

    //String idPasajero3;

    String FechaOcurrencia, Observacion,NumPlaca,HoraMinOcurrencia;

    int IdPasajero,IdProblema1,IdProblema2,IdProblema3,IdProblema4;

    EditText editTextPlaca,editTextFechaOcurrencia,editTextHora,editTextMinuto,editTextObservacion;
    RadioButton radioButtonTodoOk;
    CheckBox checkBox1,checkBox2,checkBox3,checkBox4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registroincidencias);

        RecibirDatos3();

        buttonCerrar=(Button)findViewById(R.id.buttonCerrar);
        buttonGrabar=(Button)findViewById(R.id.buttonGrabar);

        editTextPlaca = (EditText)findViewById(R.id.editTextPlaca);
        editTextFechaOcurrencia = (EditText)findViewById(R.id.editTextFechaOcurrencia);
        editTextHora = (EditText)findViewById(R.id.editTextHora);
        editTextMinuto = (EditText)findViewById(R.id.editTextMinutos);
        editTextObservacion = (EditText)findViewById(R.id.editTextObservacion);



        //radioButtonTodoOk = (RadioButton)findViewById(R.id.radioButtonTodoOk);

        checkBox1= (CheckBox)findViewById(R.id.checkBox1);
        checkBox2= (CheckBox)findViewById(R.id.checkBox2);
        checkBox3= (CheckBox)findViewById(R.id.checkBox3);
        checkBox4= (CheckBox)findViewById(R.id.checkBox4);

        /*
        SimpleDateFormat mdFecha = new SimpleDateFormat("dd/MM/yyyy");
        String strFecha = mdFecha.format(new Date());
        editTextFechaOcurrencia.setText(strFecha);

        SimpleDateFormat mdHora = new SimpleDateFormat("HH:mm");
        String strHora = mdHora.format(new Date());
        */
        // Boton Grabar
        buttonGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  GrabarIncedencia();

            }
        });
        // Boton Cerrar
        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




    }
    private void RecibirDatos3(){

        Bundle DatosExtras3 = getIntent().getExtras();
        IdPasajero = Integer.parseInt( DatosExtras3.getString("IdPasajero3"));
    }

    // Grabar Incidencia

     private void GrabarIncedencia() {

             FechaOcurrencia =  editTextFechaOcurrencia.getText().toString();
             Observacion = editTextObservacion.getText().toString();
             NumPlaca = editTextPlaca.getText().toString();
             HoraMinOcurrencia = editTextHora.getText().toString() + ":" + editTextMinuto.getText().toString() ;

             if (ValidarVariables () && ValidarFecha() && ValidarHoraMinuto()) {

                MainActivity ConexionSQL = new MainActivity();

                try {
                    PreparedStatement pst=ConexionSQL.conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_Incidencias] @Type = 'I', @IdPasajero = " + IdPasajero +  ",@numPlaca ='" +  NumPlaca + "', @idProblema1 = " +  IdProblema1 + ", @idProblema2 = " + IdProblema2 + ", @idProblema3 = " + IdProblema3 + " , @idProblema4 = " + IdProblema4 + ", @FecOcurrencia = '" + FechaOcurrencia + "', @HoraMinOcurrencia = '" + HoraMinOcurrencia  + "' , @Comentario = '" + Observacion +"'" );
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

     private  boolean ValidarVariables(){

         IdProblema1 = 0;
         IdProblema2 = 0;
         IdProblema3 = 0;
         IdProblema4 = 0;

         if (checkBox1.isChecked()) {
             IdProblema1 = 1;
         }


         if (checkBox2.isChecked()) {
             IdProblema2 = 1;
         }

         if (checkBox3.isChecked()) {
             IdProblema3 = 1;
         }

         if (checkBox4.isChecked()) {
             IdProblema4 = 1;
         }


          if (NumPlaca.toString().trim().equalsIgnoreCase("")){
              Toast.makeText(getApplicationContext(), "Debe Ingresar Placa del Vehículo", Toast.LENGTH_SHORT).show();
              return false;
         }
          if (FechaOcurrencia.toString().trim().equalsIgnoreCase("")){
              Toast.makeText(getApplicationContext(), "Debe Ingresar Fecha de Ocurrencia", Toast.LENGTH_SHORT).show();
              return false;
          }


        return true;
     }

     private boolean ValidarFecha() {

            try {

                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
                formatoFecha.setLenient(false);
                formatoFecha.parse(FechaOcurrencia);

            } catch (ParseException e) {
                Toast.makeText(getApplicationContext(), "La Fecha Invalida", Toast.LENGTH_SHORT).show();
                return false;
            }

        return true;
     }

     private boolean ValidarHoraMinuto(){

            Integer intHoraOcurrencia, intMinOcurrencia;

            intHoraOcurrencia = Integer.parseInt(  editTextHora.getText().toString());
            intMinOcurrencia = Integer.parseInt(  editTextMinuto.getText().toString());

         if (intHoraOcurrencia < 0 || intHoraOcurrencia > 24 ) {
             Toast.makeText(getApplicationContext(), "La Hora de Ocurrencia es Invalida", Toast.LENGTH_SHORT).show();
             return  false;
         }  ;

         if (intMinOcurrencia < 0 || intMinOcurrencia > 60 ) {
             Toast.makeText(getApplicationContext(), "Los Minutos de Ocurrencia es Invalido", Toast.LENGTH_SHORT).show();
             return  false;
         }  ;


        return true;
     }




}
