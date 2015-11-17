package com.example.dam.contentprovider;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Principal extends AppCompatActivity {
    private Uri uri = ContratoCliente.TablaCliente.CONTENT_URI;
    private TextView tv;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        tv = (TextView)findViewById(R.id.tvEstructura);
        tv2 = (TextView)findViewById(R.id.tvDatos);
        init();
    }

    public static String getEstructuraCursor(Cursor cursor){
        String registro = "";
        String[] nombres = cursor.getColumnNames();
        int contador = 0;
        for (String s: nombres){
            contador ++;
            registro += contador + " " + s + "\n";
        }
        return registro;
    }

    private void init(){
        Cliente cl = new Cliente(27, "Juan", "98979");
        Uri uri2 = getContentResolver().insert(uri, cl.getContentValues());


        getContentResolver().delete(uri, ContratoCliente.TablaCliente._ID + " > ? ", new String[]{"28"});

        Cursor c = getContentResolver().query(uri, null, null, null, null);//Mi cursor de mi content provider
        tv.setText(Principal.getEstructuraCursor(c));

        //Construir la uri a partir del id
        long id = 28;
        Uri uril = ContentUris.withAppendedId(uri,id);
        c = getContentResolver().query(uril, null, null, null, null);



        Log.v("asd", "la uri es: " + uri2.toString());
        String todo ="";

        while(c.moveToNext()){
            cl = new Cliente();
            cl.set(c);
            todo += cl.toString()+ "\n";
        }
        tv2.setText(todo);
    }
}
