package com.example.dam.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 2dam on 10/11/2015.
 */
public class Ayudante extends SQLiteOpenHelper{

    public static final String DATABASE_NAME ="cliente.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {

        String sql="drop table if exists "
                + ContratoCliente.TablaCliente.TABLA;
        db.execSQL(sql);
        onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {//Cuando se baja la aplicación y se crea por primera vez(no hay versión previa de la aplicación)

        String sql;
        sql="create table "+ContratoCliente.TablaCliente.TABLA+ " ("+
                ContratoCliente.TablaCliente._ID+ " integer primary key autoincrement, "+
                ContratoCliente.TablaCliente.NOMBRE+" text, "+
                ContratoCliente.TablaCliente.TELEFONO+" text)";

        db.execSQL(sql);
    }

}
