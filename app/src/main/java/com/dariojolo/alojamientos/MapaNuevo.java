package com.dariojolo.alojamientos;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by rodrigrl on 01/09/2015.
 */
public class MapaNuevo extends Activity {
    GoogleMap map;
    Double longitud;
    Double latitud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutmapa);

        map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();

        Bundle extras = getIntent().getExtras();
        String nombre = extras.getString("NOMBRE");
        longitud = Double.valueOf(extras.getDouble("LONGITUD"));
        latitud = Double.valueOf(extras.getDouble("LATITUD"));
        //Toast.makeText(getBaseContext(), "Nombre: " + nombre, Toast.LENGTH_LONG).show();
        //Toast.makeText(getBaseContext(), "Latitud 2: " + latitud, Toast.LENGTH_LONG).show();
        //Toast.makeText(getBaseContext(), "Longitud 2: " + longitud, Toast.LENGTH_LONG).show();
        map=((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getMap();
        if(map==null)
            Toast.makeText(getApplicationContext(), "Sorry! unable to create Map.", Toast.LENGTH_SHORT).show();
        else
        {
            map.setMyLocationEnabled(true); //false to disable
            LatLng HOTELNORMANDIE = new LatLng(latitud, longitud);
            map.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 14.0f) );
            Marker hotel = map.addMarker(new MarkerOptions()
                    .position(HOTELNORMANDIE)
                    .title(nombre));
            hotel.showInfoWindow();  //use .hideInfoWindow() to hide.
            // Instantiates a new CircleOptions object and defines the center and radius
            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng(latitud,longitud))
                    .radius(1000); // In meters

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
