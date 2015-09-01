package com.dariojolo.alojamientos;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dario on 26/08/2015.
 */
public class AlojamientoDbAdapter {
    /**
     * Definimos constante con el nombre de la tabla
     */
    public static final String C_TABLA = "ALOJAMIENTO" ;

    /**
     * Definimos constantes con el nombre de las columnas de la tabla
     */
    public static final String C_COLUMNA_ID   = "_id";
    public static final String C_COLUMNA_CATEGORIA = "alo_categoria";
    public static final String C_COLUMNA_NOMBRE = "alo_nombre";
    public static final String C_COLUMNA_HABITACIONES = "alo_habitaciones";
    public static final String C_COLUMNA_PLAZAS = "alo_plazas";
    public static final String C_COLUMNA_DOMICILIO = "alo_domicilio";
    public static final String C_COLUMNA_BARRIO = "alo_barrio";
    public static final String C_COLUMNA_TELEFONO = "alo_telefono";
    public static final String C_COLUMNA_EMAIL = "alo_email";
    public static final String C_COLUMNA_LONGITUD = "alo_longitud";
    public static final String C_COLUMNA_LATITUD = "alo_latitud";
    public static final String C_COLUMNA_FOTO = "alo_foto";


    private Context contexto;
    private AlojamientoDbHelper dbHelper;
    private SQLiteDatabase db;

    /**
     * Definimos lista de columnas de la tabla para utilizarla en las consultas a la base de datos
     */
    private String[] columnas = new String[]{ C_COLUMNA_ID,C_COLUMNA_CATEGORIA,C_COLUMNA_NOMBRE,C_COLUMNA_HABITACIONES,C_COLUMNA_PLAZAS,C_COLUMNA_DOMICILIO,C_COLUMNA_BARRIO,C_COLUMNA_TELEFONO,C_COLUMNA_EMAIL,C_COLUMNA_LONGITUD,C_COLUMNA_LATITUD,C_COLUMNA_FOTO} ;

    public AlojamientoDbAdapter(Context context)
    {
        this.contexto = context;
    }

    public AlojamientoDbAdapter abrir() throws SQLException
    {
        dbHelper = new AlojamientoDbHelper(contexto);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        dbHelper.close();
    }

    /**
     * Devuelve cursor con todos las columnas de la tabla
     */
    public Cursor getCursor() throws SQLException
    {
        Cursor c = db.query( true, C_TABLA, columnas, null, null, null, null, null, null);

        return c;
    }
    /**
     * Devuelve cursor con todos las columnas del registro
     */
    public Cursor getRegistro(long id) throws SQLException
    {
        Cursor c = db.query( true, C_TABLA, columnas, C_COLUMNA_ID + "=" + id, null, null, null, null, null);

        //Nos movemos al primer registro de la consulta
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

}
