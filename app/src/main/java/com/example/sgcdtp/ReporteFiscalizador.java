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

public class ReporteFiscalizador extends AppCompatActivity {

    Button buttonBuscar, buttonCerrar;
    EditText editTextRuc,editTextEmpresa;
    TextView TextViewEmpresa;
    String strTextRuc,strEmpresa;

       /*
    String Registros[] = new String[4] ;
    String cabeceras[] = {"Placa","Conductor", "Incidencia","#Casos"};
    */


    String Registros[] = new String[2] ;
    String cabeceras[] = {"Problema","#Casos"};
    Integer CabeceraTextTamano[] = {20,20};
    Integer FilaTextTamano[]= {15,15};

    TableLayout tabla;

    Integer intIdEmpresa;

    TextView txtCell;
    TableRow tableRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportefiscalizador);

        buttonBuscar = (Button) findViewById(R.id.buttonBuscar);
        buttonCerrar = (Button) findViewById(R.id.buttonCerrar);
        editTextRuc = (EditText) findViewById(R.id.editTextRuc);

        TextViewEmpresa= (TextView) findViewById(R.id.textView9);
        tabla = (TableLayout)findViewById(R.id.tableLayout2);


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

    }


    private void BuscarEmpresa() {

        strTextRuc = editTextRuc.getText().toString();

        if (ValidarRucEmpresa()) {

            MainActivity ConexionSQL = new MainActivity();

            try {

                PreparedStatement pst = ConexionSQL.conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_EmpTransporte]  @Type = 'S', @idRuc = '" + strTextRuc + "'");
                //String base = ConexionSQL.conexionDBSQL().getCatalog();
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    Toast.makeText(getApplicationContext(), "Encontro Registro", Toast.LENGTH_SHORT).show();
                    intIdEmpresa = rs.getInt(1);
                    TextViewEmpresa.setText(rs.getString(2));
                    Reporte();
                }
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }


    private boolean ValidarRucEmpresa () {

            if (strTextRuc.length() != 11) {
                Toast.makeText(getApplicationContext(), "RUC no es valido", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
    }

    private void Reporte(){

            MainActivity ConexionSQL2 = new MainActivity();

            try {

                PreparedStatement pst2 = ConexionSQL2.conexionDBSQL().prepareStatement(" EXEC [dbo].[SP_SGCDTP_Reporte_Fiscalizar_Incidencias]  @idEmpresa = '" + intIdEmpresa + "'");
                //String base = ConexionSQL.conexionDBSQL().getCatalog();
                ResultSet rs2 = pst2.executeQuery();

                Toast.makeText(getApplicationContext(), "Encontro Registro", Toast.LENGTH_SHORT).show();

                AddCabecera();

                while (rs2.next()) {

                    Registros[0] = rs2.getString(1);
                    Registros[1] = rs2.getString(2);
                    AddFila();
                    }

            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

    }


    // Creación del Reporte

    private void AddCabecera(){

        if (tabla.getChildCount()>1) {
            tabla.removeAllViews();
            tabla.setColumnStretchable (1,true);
            tabla.setColumnStretchable(2,true);
        }

        // Añadir Cabecera

        TableRow FiladeCabecera = new TableRow(this);

        for (int i=0; i < cabeceras.length; i++ ) {

            txtCell =new TextView(this);

            txtCell.setGravity(Gravity.CENTER);
            txtCell.setTextSize(CabeceraTextTamano[i]);
            txtCell.setBackgroundColor(Color.BLUE);
            txtCell.setTextColor(Color.WHITE);
            txtCell.setText(cabeceras[i]);

            FiladeCabecera.addView(txtCell,newTableRowParams());

        }
        tabla.addView(FiladeCabecera);

       }

    private void AddFila(){

        // Añadiendo Datos

        TableRow FiladeDatos = new TableRow(this);

        for (int f = 0; f < Registros.length; f++) {

            txtCell =new TextView(this);

            if (f==0){txtCell.setGravity(Gravity.LEFT);}
            else {txtCell.setGravity(Gravity.RIGHT); }
            txtCell.setTextSize(FilaTextTamano[f]);
            txtCell.setBackgroundColor(Color.WHITE);
            txtCell.setTextColor(Color.BLACK);
            txtCell.setText(Registros[f]);

            FiladeDatos.addView(txtCell, newTableRowParams());
        }
        tabla.addView(FiladeDatos,newTableRowParams());

    }

    private TableRow.LayoutParams newTableRowParams(){

        TableRow.LayoutParams params=new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight=1;
        return params;
    };


    // Fin de creación de reporte


}

