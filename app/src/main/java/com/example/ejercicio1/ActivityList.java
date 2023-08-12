package com.example.ejercicio1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.ejercicio1.Configuracion.ConfigDB;
import com.example.ejercicio1.Configuracion.SQLITEConnection;
import com.example.ejercicio1.Configuracion.Personas;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity
{
    SQLITEConnection conexion;
    ListView list;
    ArrayList<Personas> listpersonas;
    ArrayList<String> arreglopersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        conexion = new SQLITEConnection(this, ConfigDB.namebd, null, 1);
        list = (ListView) findViewById(R.id.listview1);

        ObtenerTabla();

        ArrayAdapter apd = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arreglopersonas);
        list.setAdapter(apd);

    }

    private void ObtenerTabla()
    {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Personas person = null;
        listpersonas = new ArrayList<Personas>();

        // Cursor de Base de Datos
        Cursor cursor = db.rawQuery(ConfigDB.SelectTBPersona,null);

        // recorremos el cursor
        while(cursor.moveToNext())
        {
            person = new Personas();
            person.setId(cursor.getInt(0));
            person.setNombres(cursor.getString(1));
            person.setApellidos(cursor.getString(2));
            person.setEdad(cursor.getInt(3));
            person.setCorreo(cursor.getString(4));
            person.setDireccion(cursor.getString(5));
            listpersonas.add(person);
        }

        cursor.close();

        fillData();
    }

    private void fillData()
    {
        arreglopersonas = new ArrayList<String>();

        for(int i=0; i < listpersonas.size(); i++)
        {
            arreglopersonas.add(listpersonas.get(i).getId() + "-"
                    +listpersonas.get(i).getNombres() + " - "
                    +listpersonas.get(i).getApellidos() + " - "
                    +listpersonas.get(i).getEdad() + " - "
                    +listpersonas.get(i).getCorreo() + " - "
                    +listpersonas.get(i).getDireccion() + " - "
            );
        }
    }
}