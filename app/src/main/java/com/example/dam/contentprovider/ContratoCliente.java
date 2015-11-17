package com.example.dam.contentprovider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by 2dam on 10/11/2015.
 */
public class ContratoCliente {

    private ContratoCliente(){
    }

    public static abstract class TablaCliente implements BaseColumns {
        public static final String TABLA = "cliente";
        public static final String NOMBRE = "nombre";
        public static final String TELEFONO = "telefono";

        //La autoridad es la cadena q identifica a qué contentprovider se llama
        public final static String AUTHORITY = "com.example.dam.contentprovider.ProveedorClientes";
        //Definir como llego a la tabla cliente (a q tabla estoy llegando)
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;

    }
}