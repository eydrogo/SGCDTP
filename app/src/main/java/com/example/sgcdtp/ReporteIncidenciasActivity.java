package com.example.sgcdtp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReporteIncidenciasActivity extends AppCompatActivity {

    Button btnCerrar, btnBuscar;
    EditText editTextPlaca;
    private TextView txtCell;
    private TableRow tableRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporteincidencias);

        btnCerrar=(Button)findViewById(R.id.btnCerrar);
        btnBuscar=(Button)findViewById(R.id.btnBuscar);
        editTextPlaca = (EditText)findViewById(R.id.editTextPlaca);

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarPlaca();
            }
        });

    }

    public String Registros[] = new String[4] ;
    public String cabeceras[] = {"Servicio","Ruta","Semáforos", "Limpieza"};

    private void BuscarPlaca() {

        if (ValidarPlaca () ) {

            MainActivity ConexionSQL = new MainActivity();

            try {

                PreparedStatement pst= ConexionSQL.conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_Reporte_Incidencia]  @numPlaca = '" + editTextPlaca.getText().toString()+"'");
                ResultSet rs = pst.executeQuery();
                //rs.last();
               if (rs.next())  {
                   Registros[0] = rs.getString(2);
                   Registros[1] = rs.getString(3);
                   Registros[2] = rs.getString(4);
                   Registros[3] = rs.getString(5);
                   //Registros[4] = rs.getString(5);
                   //Registros[5] = rs.getString(6);

                   PrepareTableLayout();
               }
                else {
                   Toast.makeText(getApplicationContext(), "Placa no existe", Toast.LENGTH_SHORT).show();

                }
            }
            catch (SQLException e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean ValidarPlaca() {

        if (editTextPlaca.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(getApplicationContext(), "Debe Ingresar Placa", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void PrepareTableLayout( ){

            TableLayout tabla;
            tabla = (TableLayout)findViewById(R.id.tableLayout);

            if (tabla.getChildCount()>1) {
                tabla.removeAllViews();
            }



    /*
            TableRow FiladeTitulo = new TableRow(this);
            newCell();
            txtCell.setText("Calificación de Prestación de Servicio");
            txtCell.setBackgroundColor(Color.CYAN);
            txtCell.setTextColor(Color.GRAY);
            FiladeTitulo.addView(txtCell,newTableRowParams());
            tabla.addView(FiladeTitulo);

        */

        // Añadir Cabecera

            TableRow FiladeCabecera = new TableRow(this);

            for (int i=0; i < cabeceras.length; i++ ) {

               newCell();
               txtCell.setText(cabeceras[i]);
               txtCell.setBackgroundColor(Color.BLUE);
               txtCell.setTextColor(Color.WHITE);
               FiladeCabecera.addView(txtCell,newTableRowParams());

            }
            tabla.addView(FiladeCabecera);

            TableRow FiladeDatos = new TableRow(this);

            // Añadiendo Datos

            for (int f = 0; f < Registros.length; f++) {

                    newCell();
                    txtCell.setText(Registros[f]);
                    txtCell.setBackgroundColor(Color.WHITE);
                    if   ( Integer.parseInt(Registros[f]) <=1) {
                       txtCell.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bueno, 0, 0, 0);
                     }
                    else {
                        txtCell.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_malo, 0, 0, 0);
                    }

                    FiladeDatos.addView(txtCell, newTableRowParams());
            }
            tabla.addView(FiladeDatos,newTableRowParams());


            /*

            //izquierda
            tuTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.gatito, 0, 0, 0);
            //parte superior
            tuTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.gatito, 0, 0);
            //derecha
            tuTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gatito, 0);
            //Al fondo
            tuTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.gatito);

             */

    }

    private TableRow.LayoutParams newTableRowParams(){

            TableRow.LayoutParams params=new TableRow.LayoutParams();
            params.setMargins(1,1,1,1);
            params.weight=1;
            //params.width=225;
            return params;
    };

    private void newCell(){

        txtCell =new TextView(this);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(20);

    }

}
