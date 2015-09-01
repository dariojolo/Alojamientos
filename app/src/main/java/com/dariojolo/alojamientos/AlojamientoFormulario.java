package com.dariojolo.alojamientos;

/**
 * Created by Dario on 26/08/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;

public class AlojamientoFormulario extends Activity {
    private AlojamientoDbAdapter dbAdapter;
    private Cursor cursor;

    //GoogleMap map;


    //
    // Modo del formulario
    //
    private int modo ;

    //
    // Identificador del registro que se edita cuando la opción es MODIFICAR
    //
    private long id ;

    //
    // Elementos de la vista
    //
    private EditText nombre;
    private EditText categoria;
    private EditText habitaciones;
    private EditText telefono;
    private EditText email;
    private EditText plazas;
    private EditText domicilio;
    private EditText barrio;
    private EditText longitud;
    private EditText latitud;
    private ImageView foto;
    private Button   btnMapa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alojamiento_formulario);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        if (extra == null) return;

        //
        // Obtenemos los elementos de la vista
        //
        nombre = (EditText) findViewById(R.id.nombre);
        categoria = (EditText) findViewById(R.id.categoria);
        habitaciones = (EditText) findViewById(R.id.habitaciones);
        telefono = (EditText) findViewById(R.id.telefono);
        email = (EditText) findViewById(R.id.email);
        plazas = (EditText) findViewById(R.id.plazas);
        domicilio = (EditText) findViewById(R.id.domicilio);
        barrio = (EditText) findViewById(R.id.barrio);
        longitud = (EditText) findViewById(R.id.longitud);
        latitud = (EditText) findViewById(R.id.latitud);
        foto = (ImageView)findViewById(R.id.imagen);
        btnMapa=(Button)findViewById(R.id.btnMapa);

        //
        // Creamos el adaptador
        //
        dbAdapter = new AlojamientoDbAdapter(this);
        dbAdapter.abrir();

        //
        // Obtenemos el identificador del registro si viene indicado
        //
        if (extra.containsKey(AlojamientoDbAdapter.C_COLUMNA_ID))
        {
            id = extra.getLong(AlojamientoDbAdapter.C_COLUMNA_ID);
            consultar(id);
        }

        //
        // Establecemos el modo del formulario
        //
        establecerModo(extra.getInt(MainActivity.C_MODO));

    }

    private void establecerModo(int m)
    {
        this.modo = m ;

        if (modo == MainActivity.C_VISUALIZAR)
        {
            this.setTitle(nombre.getText().toString());
            this.setEdicion(false);
        }
    }

    private void consultar(long id) {
        //
        // Consultamos el centro por el identificador
        //
        cursor = dbAdapter.getRegistro(id);

        nombre.setText(cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_NOMBRE)));
        categoria.setText(cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_CATEGORIA)));
        habitaciones.setText(cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_HABITACIONES)));
        plazas.setText(cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_PLAZAS)));
        domicilio.setText(cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_DOMICILIO)));
        barrio.setText(cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_BARRIO)));
        telefono.setText(cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_TELEFONO)));
        email.setText(cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_EMAIL)));
        longitud.setText(cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_LONGITUD)) + "");
        latitud.setText(cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_LATITUD)) + "");
        int imagen = getResources().getIdentifier("Aojamiento:drawable/" + cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_FOTO))/*nombre.getText().toString().toLowerCase()*/, null, null);
        //Toast.makeText(getBaseContext(), "ID Foto: "+cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_FOTO)), Toast.LENGTH_LONG).show();
        String nombreFoto = cursor.getString(cursor.getColumnIndex(AlojamientoDbAdapter.C_COLUMNA_FOTO));
        String uri = "@drawable/" + nombreFoto;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        foto.setImageDrawable(res);
        //map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

        btnMapa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("Map", "Map clicked");
               /* Double longitudBD = new Double(Double.parseDouble(longitud.getText().toString()));
                Double latitudBD = new Double(Double.parseDouble(latitud.getText().toString()));
                LatLng LatLongHotel = new LatLng(longitudBD, latitudBD);
                Marker hotel = map.addMarker(new MarkerOptions()
                        .position(LatLongHotel)
                        .title(nombre.getText().toString()));
                hotel.showInfoWindow();  //use .hideInfoWindow() to hide.
                // Instantiates a new CircleOptions object and defines the center and radius
                CircleOptions circleOptions = new CircleOptions()
                        .center(new LatLng(longitudBD, latitudBD))
                        .radius(1000); // In meters*/
                // Llamamos a la Actividad HipotecaFormulario indicando el modo visualización y el identificador del registro
                Intent i = new Intent(AlojamientoFormulario.this, MapaNuevo.class);
                i.putExtra("modo", 551);
                i.putExtra("NOMBRE", nombre.getText().toString());
                i.putExtra("LATITUD", Double.parseDouble(latitud.getText().toString()));
                i.putExtra("LONGITUD", Double.parseDouble(longitud.getText().toString()));
                //   i.putExtra(AlojamientoDbAdapter.C_COLUMNA_ID, id);
                startActivity(i);
            }
        });
    }

        //Toast.makeText(getApplicationContext(), "Encontro el mapa", Toast.LENGTH_SHORT).show();
       //     map.setMyLocationEnabled(true); //false to disable
            //LatLng LatLongHotel = new LatLng(Float.parseFloat(longitud.getText().toString()),Float.parseFloat(latitud.getText().toString()));
         //   Double longitudBD = new Double(Double.parseDouble(longitud.getText().toString()));
         //   Double latitudBD = new Double(Double.parseDouble(latitud.getText().toString()));
         //   LatLng LatLongHotel = new LatLng(longitudBD,latitudBD );
            //Toast.makeText(getBaseContext(), "Longitud: " + longitudBD, Toast.LENGTH_LONG).show();
            //Toast.makeText(getBaseContext(), "Latitud: " + latitudBD, Toast.LENGTH_LONG).show();
            //LatLongHotel = (Float.parseFloat(longitud.getText().toString()),Float.parseFloat(latitud.getText().toString()));
         //   Marker hotel = map.addMarker(new MarkerOptions()
         //           .position(LatLongHotel)
         //           .title(nombre.getText().toString()));
         //   hotel.showInfoWindow();  //use .hideInfoWindow() to hide.
            // Instantiates a new CircleOptions object and defines the center and radius
         //   CircleOptions circleOptions = new CircleOptions()
         //           .center(new LatLng(longitudBD,latitudBD))
         //                   .radius(1000); // In meters



    private void setEdicion(boolean opcion)
    {
        nombre.setEnabled(opcion);
        categoria.setEnabled(opcion);
        habitaciones.setEnabled(opcion);
        plazas.setEnabled(opcion);
        domicilio.setEnabled(opcion);
        barrio.setEnabled(opcion);
        telefono.setEnabled(opcion);
        email.setEnabled(opcion);
        longitud.setEnabled(false);
        latitud.setEnabled(false);
    }

}
