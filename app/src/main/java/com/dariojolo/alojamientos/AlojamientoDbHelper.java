package com.dariojolo.alojamientos;

/**
 * Created by Dario on 24/08/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AlojamientoDbHelper extends SQLiteOpenHelper {
    private static int version = 12;
    private static String name = "AlojamientoDb";
    private static CursorFactory factory = null;

    Context ApplicationContext;


    public AlojamientoDbHelper(Context context) {
        super(context, name, factory, version);
        ApplicationContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(this.getClass().toString(), "Creando base de datos");

        db.execSQL("CREATE TABLE ALOJAMIENTO(" +
                " _id INTEGER PRIMARY KEY," +
                " alo_categoria TEXT, " +
                " alo_nombre TEXT NOT NULL, " +
                " alo_habitaciones TEXT," +
                " alo_plazas TEXT," +
                " alo_domicilio TEXT," +
                " alo_barrio TEXT," +
                " alo_telefono TEXT," +
                " alo_email TEXT," +
                " alo_longitud TEXT," +
                " alo_latitud TEXT," +
                " alo_foto TEXT)");


        db.execSQL("CREATE UNIQUE INDEX _id ON ALOJAMIENTO(_id ASC)");

        Log.i(this.getClass().toString(), "Tabla ALOJAMIENTO creada");

   /*
    * Insertamos datos iniciales
    */


       /* db.execSQL("INSERT INTO ALOJAMIENTO (_id, alo_categoria,alo_nombre,alo_habitaciones,alo_plazas,alo_domicilio,alo_barrio,alo_telefono,alo_email,alo_longitud,alo_latitud) " +
                "  values (1,'3 estrellas','NORMANDIE HOTEL','59','118','RODRIGUEZ PEÑA 320','SAN NICOLAS','4371-7001 al 06','normandie@hotelnormandie.com.ar','-583.908','-346.054')");
        db.execSQL("INSERT INTO ALOJAMIENTO (_id, alo_categoria,alo_nombre,alo_habitaciones,alo_plazas,alo_domicilio,alo_barrio,alo_telefono,alo_email,alo_longitud,alo_latitud) " +
                "  values (2,'3 estrellas','COLUMBIA PALACE HOTEL','67','150','CORRIENTES 1533/35/37','SAN NICOLAS','4373-1906','reservas@columbiapalacehotel.com.ar','-583.884','-346.041')");
        db.execSQL("INSERT INTO ALOJAMIENTO (_id, alo_categoria,alo_nombre,alo_habitaciones,alo_plazas,alo_domicilio,alo_barrio,alo_telefono,alo_email,alo_longitud,alo_latitud) " +
                "  values (3,'4 estrellas','GRAND KING HOTEL','102','214','LAVALLE 560','SAN NICOLAS','4393-4052 / 4012','reservas@grandking.com.ar gerencia@grandking.com.ar','-583.747','-346.022')");
        db.execSQL("INSERT INTO ALOJAMIENTO (_id, alo_categoria,alo_nombre,alo_habitaciones,alo_plazas,alo_domicilio,alo_barrio,alo_telefono,alo_email,alo_longitud,alo_latitud) " +
                "  values (4,'4 estrellas','PRESIDENTE HOTEL','250','500','CERRITO 846','RETIRO','4816-2222','admin@hotelpresidente.com.ar','-583.826','-345.985')");
        db.execSQL("INSERT INTO ALOJAMIENTO (_id, alo_categoria,alo_nombre,alo_habitaciones,alo_plazas,alo_domicilio,alo_barrio,alo_telefono,alo_email,alo_longitud,alo_latitud) " +
                "  values (5,'1 estrella','BRISAS HOTEL','40','85','TACUARI 1621/25','CONSTITUCION','4300-5076 / 77','hotelbrisas1@gmail.com','-583.771','-346.271')");
*/
        Log.i(this.getClass().toString(), "Datos iniciales ALOJAMIENTO insertados");

        Log.i(this.getClass().toString(), "Base de datos creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  Toast.makeText(ApplicationContext, "Creando tabla desde onUpgrade", Toast.LENGTH_LONG).show();
        db.execSQL("DROP TABLE ALOJAMIENTO");
        db.execSQL("CREATE TABLE ALOJAMIENTO(" +
                " _id INTEGER PRIMARY KEY," +
                " alo_categoria TEXT, " +
                " alo_nombre TEXT NOT NULL, " +
                " alo_habitaciones TEXT," +
                " alo_plazas TEXT," +
                " alo_domicilio TEXT," +
                " alo_barrio TEXT," +
                " alo_telefono TEXT," +
                " alo_email TEXT," +
                " alo_longitud TEXT," +
                " alo_latitud TEXT," +
                " alo_foto TEXT)");


        db.execSQL("CREATE UNIQUE INDEX _id ON ALOJAMIENTO(_id ASC)");
        //Toast.makeText(ApplicationContext, "Tabla creada", Toast.LENGTH_LONG).show();
        Log.i(this.getClass().toString(), "Tabla ALOJAMIENTO creada");
    }
    
    public void onArchivo (SQLiteDatabase db,List<String[]> listado){
        db.execSQL("DELETE FROM ALOJAMIENTO");
        for (int i = 1; i < listado.size(); i++) {
            //CategoryList.add(list.get(i)[1]);
            String texto = listado.get(i)[1];

            String salida = "INSERT INTO ALOJAMIENTO (_id, alo_categoria,alo_nombre,alo_habitaciones,alo_plazas,alo_domicilio,alo_barrio,alo_telefono,alo_email,alo_longitud,alo_latitud,alo_foto) " +
                    "  values ("+listado.get(i)[0]+",'"+listado.get(i)[1]+"','"+listado.get(i)[2]+"','"+listado.get(i)[3]+"','"+
                    listado.get(i)[4]+"','"+listado.get(i)[5]+"','"+listado.get(i)[6]+"','"+listado.get(i)[7]+"','"+listado.get(i)[8]+"','"+listado.get(i)[9]+"')";

            //Toast.makeText(ApplicationContext, "Lectura de archivo: " + salida, Toast.LENGTH_LONG).show();
           db.execSQL("INSERT INTO ALOJAMIENTO (_id, alo_categoria,alo_nombre,alo_habitaciones,alo_plazas,alo_domicilio,alo_barrio,alo_telefono,alo_email,alo_longitud,alo_latitud,alo_foto) " +
                    "  values (\'"+i+"\',\'"+listado.get(i)[1]+"\',\'"+listado.get(i)[2]+"\',\'"+listado.get(i)[3]+"\',\'"+
                    listado.get(i)[4]+"\',\'"+listado.get(i)[5]+"\',\'"+listado.get(i)[6]+"\',\'"+listado.get(i)[7]+"\',\'"+listado.get(i)[8]+"\',\'"+listado.get(i)[9]+"\',\'"+
                    listado.get(i)[10]+"\',ifnull(lower(replace(\'"+listado.get(i)[11]+"\',' ','')),'foto') )");

            }
        };
}