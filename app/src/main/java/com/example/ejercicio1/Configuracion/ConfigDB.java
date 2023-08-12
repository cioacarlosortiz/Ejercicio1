package com.example.ejercicio1.Configuracion;

public class ConfigDB
{
    // Configuracion de los parametros de la base de datos local en sqlite
    //Nombre de la base de datos
    public static final String namebd = "DBTLeng";

    // Tablas de las bases de datos
    public static final String tblpersonas = "PERSONAS";

    // Campos de la tabla personas
    public static final String id = ("id");
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = ("edad");
    public static final String correo = "correo";
    public static final String direccion = "direccion";



    // Creacion de objetos DDL - CREATE - DROP - ALTER
    public static final String CreateTBPersona = "CREATE TABLE PERSONAS (id INTEGER PRIMARY KEY AUTOINCREMENT, nombres TEXT," +
            "apellidos TEXT, edad INTEGER, correo TEXT, direccion TEXT)";

    public static final String DropTBPersona = "DROP TABLE IF EXISTS PERSONAS";

    // Creacion de objetos DML para poder seleccionar informacion de la base de datos
    public static final String SelectTBPersona = "SELECT * FROM " + ConfigDB.tblpersonas;

    public static final String Empty = "";

}