package com.example.ejercicio1.Configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLITEConnection extends SQLiteOpenHelper
{
    public SQLITEConnection(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        /* Creacion de objectos de base de datos */
        sqLiteDatabase.execSQL(ConfigDB.CreateTBPersona);  // Creando la tabla de Persona en sqlite..
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        sqLiteDatabase.execSQL(ConfigDB.DropTBPersona);
        onCreate(sqLiteDatabase);
    }
}

