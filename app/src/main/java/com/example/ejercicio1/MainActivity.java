package com.example.ejercicio1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejercicio1.Configuracion.ConfigDB;
import com.example.ejercicio1.Configuracion.SQLITEConnection;
public class MainActivity extends AppCompatActivity {

    EditText id, nombresP, apellidosP, edades, correos, direcciones;

    Button btningresar;

    private int idConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText) findViewById(R.id.txtconsulta);
        nombresP = (EditText) findViewById(R.id.txtnombre);
        apellidosP = (EditText) findViewById(R.id.txtapellido);
        edades = (EditText) findViewById(R.id.txtedad);
        correos = (EditText) findViewById(R.id.txtcorreo);
        direcciones = (EditText) findViewById(R.id.txtdireccion);
        btningresar = (Button) findViewById(R.id.btnguardar);

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar_datos();
            }
        });

        Button btnconsulta = findViewById(R.id.btnconsultar);
        final EditText txtconsulta = findViewById(R.id.txtconsulta);
        btnconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idConsulta = txtconsulta.getText().toString();
                if (!idConsulta.isEmpty()) {
                    obtenerDatosConsulta(idConsulta);
                }
            }
        });
        /////Eliminar//////////////////////////////////////////////////////////////////////////////////
        Button btneliminar = findViewById(R.id.btneliminar);
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarRegistro();
            }
        });
        //////Actualizar//////////////////////////////////////////////////////////////////////////////////////
        Button btnActualizar = findViewById(R.id.btnactualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarRegistro();
            }
        });
        Button btnsiguiente = findViewById(R.id.btnlista);
        btnsiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Iniciar la actividad SonListItemActivity
                Intent intent = new Intent(MainActivity.this, ActivityList.class);
                startActivity(intent);
            }
        });

    }
    ////////Botón para pasar a la actividad lista////////////////////////////////////////////////

////////////7Meotodo actualizar registro//////////////////////////////////////////////////////////7
    private void actualizarRegistro() {

        SQLITEConnection conexion = new SQLITEConnection(this, ConfigDB.namebd, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ConfigDB.nombres, nombresP.getText().toString());
        values.put(ConfigDB.apellidos, apellidosP.getText().toString());
        values.put(ConfigDB.edad, edades.getText().toString());
        values.put(ConfigDB.correo, correos.getText().toString());

        int filasActualizadas = db.update(ConfigDB.tblpersonas, values, ConfigDB.id + "=?", new String[]{String.valueOf(idConsulta)});

        if (filasActualizadas > 0) {
            Toast.makeText(this, "Registro actualizado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo actualizar el registro", Toast.LENGTH_SHORT).show();
        }

        db.close();

    }

    ////////////////Metodo de eliminar registro///////////////////////////////////////////////////////////
    private void eliminarRegistro() {

        SQLITEConnection conexion = new SQLITEConnection(this, ConfigDB.namebd, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        int filasEliminadas = db.delete(ConfigDB.tblpersonas, ConfigDB.id + "=?", new String[]{String.valueOf(idConsulta)});

        if (filasEliminadas > 0) {
            Toast.makeText(this, "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show();
            ClearScreen();
        } else {
            Toast.makeText(this, "No se pudo eliminar el registro", Toast.LENGTH_SHORT).show();
        }

        db.close();

    }

    ////////////////Metodo para consultar sobre los registros/////////////////////////////////////////////////////////////////////
    private void obtenerDatosConsulta(String idConsulta) {

        SQLITEConnection conexion = new SQLITEConnection(this, ConfigDB.namebd, null, 1);
        SQLiteDatabase db = conexion.getReadableDatabase();

        Cursor cursor = db.rawQuery(ConfigDB.SelectTBPersona + " WHERE " + ConfigDB.id + "=?", new String[]{idConsulta});

        if (cursor.moveToFirst()) {
             String nombres = cursor.getString(cursor.getColumnIndex(ConfigDB.nombres));
             String apellidos = cursor.getString(cursor.getColumnIndex(ConfigDB.apellidos));
             String edad = cursor.getString(cursor.getColumnIndex(ConfigDB.edad));
             String correo = cursor.getString(cursor.getColumnIndex(ConfigDB.correo));
             String direccion = cursor.getString(cursor.getColumnIndex(ConfigDB.direccion));

            nombresP.setText(nombres);
            apellidosP.setText(apellidos);
            edades.setText(edad);
            correos.setText(correo);
            direcciones.setText(direccion);

            this.idConsulta = Integer.parseInt(idConsulta); // Actualiza idConsulta con el valor correcto
        } else {
            Toast.makeText(this, "No se encontraron registros para la ID ingresada", Toast.LENGTH_SHORT).show();
        }


        cursor.close();
        db.close();
    }
    private void insertar_datos() {
        SQLITEConnection conexion = new SQLITEConnection(this, ConfigDB.namebd, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ConfigDB.nombres, nombresP.getText().toString());
        values.put(ConfigDB.apellidos, apellidosP.getText().toString());
        values.put(ConfigDB.edad, edades.getText().toString());
        values.put(ConfigDB.correo, correos.getText().toString());
        values.put(ConfigDB.direccion, direcciones.getText().toString());

        Long resultado = db.insert(ConfigDB.tblpersonas, ConfigDB.id, values);
        if (resultado > 0) {
            Toast.makeText(getApplicationContext(), "Registro ingresado con exito", Toast.LENGTH_LONG).show();
            ClearScreen(); // Limpia los campos de entrada después de la inserción exitosa
        } else {
            Toast.makeText(getApplicationContext(), "Registro no se ingreso", Toast.LENGTH_LONG).show();
        }

        db.close();
    }

    private void ClearScreen()
    {
        nombresP.setText(ConfigDB.Empty);
        apellidosP.setText(ConfigDB.Empty);
        direcciones.setText(ConfigDB.Empty);
        edades.setText(ConfigDB.Empty);
        correos.setText(ConfigDB.Empty);
    }
}