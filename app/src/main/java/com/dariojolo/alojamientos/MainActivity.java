package com.dariojolo.alojamientos;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    public static final String C_MODO  = "modo" ;
    public static final int C_VISUALIZAR = 551 ;

    private ArrayList categoryList = new ArrayList();
    static List<String[]> list;
    private AlojamientoDbAdapter dbAdapter;
    private Cursor cursor;
    private AlojamientoCursorAdapter alojamientoAdapter ;
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(android.R.id.list);

        dbAdapter = new AlojamientoDbAdapter(this);
        dbAdapter.abrir();


        /*
    * Declaramos el controlador de la BBDD y accedemos en modo escritura
    *
    */
      // Solo para primera corrida
        ////////////////LECTURA DE CSV

        String next[] = {};
        list = new ArrayList<String[]>();
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getAssets().open("alojamientos.csv")));//Specify asset file name
//in open();
            for (; ; ) {
                next = reader.readNext();
                if (next != null) {
                    list.add(next);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(getBaseContext(), "Exception: ", Toast.LENGTH_LONG).show();
            //  - See more at: http://www.theappguruz.com/blog/parse-csv-file-in-android-example-sample-code#sthash.hlfB75cM.dpuf

        }
        //Toast.makeText(getBaseContext(), "Lectura de archivo correcta", Toast.LENGTH_LONG).show();

        /////////////////FIN LECTURA CSV

        Log.i("Alojamiento", "Tamaño" + list.size());
        AlojamientoDbHelper dbHelper = new AlojamientoDbHelper(getBaseContext());

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        dbHelper.onArchivo(db,list);

        //Toast.makeText(getBaseContext(), "Base de datos preparada", Toast.LENGTH_LONG).show();

        consultar();
    }

    private void consultar()
    {
        cursor = dbAdapter.getCursor();
        startManagingCursor(cursor);
        alojamientoAdapter = new AlojamientoCursorAdapter(this, cursor);
        lista.setAdapter(alojamientoAdapter);
    }

    private void visualizar(long id)
    {
        // Llamamos a la Actividad HipotecaFormulario indicando el modo visualización y el identificador del registro
        Intent i = new Intent(MainActivity.this, AlojamientoFormulario.class);
        i.putExtra(C_MODO, C_VISUALIZAR);
        i.putExtra(AlojamientoDbAdapter.C_COLUMNA_ID, id);

        startActivityForResult(i, C_VISUALIZAR);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
         visualizar(id);
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
