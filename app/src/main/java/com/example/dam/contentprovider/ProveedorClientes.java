package com.example.dam.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


/**
 * Created by 2dam on 10/11/2015.
 */
public class ProveedorClientes extends ContentProvider{

    //La Uri es cm una URL. LLegamos a un mismo sitio. UriMatcher establece si es un 1 haz una cosa, si es 2 haz otra cosa.
    public static final UriMatcher convierteUri2Int;
    public static final int CLIENTES = 1;
    public static final int CLIENTE_ID = 2;

    static{
        convierteUri2Int = new UriMatcher(UriMatcher.NO_MATCH);
        convierteUri2Int.addURI(ContratoCliente.TablaCliente.AUTHORITY, ContratoCliente.TablaCliente.TABLA, CLIENTES);//Le damos la instrucción de qué hacer a la URI
        convierteUri2Int.addURI(ContratoCliente.TablaCliente.AUTHORITY, ContratoCliente.TablaCliente.TABLA + "/#", CLIENTE_ID);
    }

    private Ayudante abd;

    @Override
    public boolean onCreate() {
        abd = new Ayudante(getContext());
        return true;
    }



    @Nullable
    @Override
    public String getType(Uri uri) {//Devuelve el tipo mime que corresponde a la uri con que se ha llamado
        switch (convierteUri2Int.match(uri)){
            case CLIENTES:
                return ContratoCliente.TablaCliente.MJLTIPLE_MIME;
            case CLIENTE_ID:
                return ContratoCliente.TablaCliente.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida " + uri);
        }
    }

    //METODO INSERT
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Comprobar que la uri utilizada para hacer la insercion es correcta
        if (convierteUri2Int.match(uri) != CLIENTES) {
            throw new IllegalArgumentException("URI desconocida : " + uri);//SI no es correcta la Uri
        }

        //Si el ContentValues es nulo, crea un ContentValues
        ContentValues contentValues;
        if (values == null) {
            throw new IllegalArgumentException("Cliente null ");
        }
        //Validar

        // Inserción de nueva fila
        SQLiteDatabase db = abd.getWritableDatabase();//Conectamos a la base de datos en modo escritura
        long rowId = db.insert(ContratoCliente.TablaCliente.TABLA, null, values);
        if (rowId > 0) {
            //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
            Uri uri_actividad = ContentUris.withAppendedId(ContratoCliente.TablaCliente.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(uri_actividad, null);
            return uri_actividad;
        }
            throw new SQLException("Error al insertar fila en : " + uri);

    }

    //METODO BORRAR
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();// Vuelve a abrir la base de datos para conectar con ella en modo escritura
        int match = convierteUri2Int.match(uri);//Obtengo la uri
        int affected;
        switch (match) {
            case CLIENTES: //Muchos clientes
                affected = db.delete(ContratoCliente.TablaCliente.TABLA,selection,selectionArgs);
                break;
            case CLIENTE_ID: //Un sólo cliente
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(ContratoCliente.TablaCliente.TABLA,ContratoCliente.TablaCliente._ID + "= ?" , new String [] {idActividad + ""});

                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        // Notificar cambio asociado a la urigetContext().getContentResolver().notifyChange(uri, null);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;//Devuelve el numero de filas borradas
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();
        int affected;
        switch (convierteUri2Int.match(uri)) {
            case CLIENTES:
                affected = db.update(ContratoCliente.TablaCliente.TABLA, values, selection, selectionArgs);
                break;
            case CLIENTE_ID:
                //Distintas formas de obtener el idActividad
                //uri.getLastPathSegment()
                //ContentUris.parseId(uri)
                //uri.getPathSegments().get(l)
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(ContratoCliente.TablaCliente.TABLA, values,
                        ContratoCliente.TablaCliente._ID + "= ?" , new String [] {idActividad});
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Obtener base de datos
        SQLiteDatabase db = abd.getReadableDatabase();
        // Comparar Uri
        int match = convierteUri2Int.match(uri);

        Cursor c;

        switch (match) {
            case CLIENTES:
                // Consultando todos los registros
                c = db.query(ContratoCliente.TablaCliente.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
                Log.v("Camino", "nos hemos metido por el camino del case CLIENTE_ID");
                break;
            case CLIENTE_ID:
                // Consultando un solo registro basado en el Id del Uri
                long idActividad = ContentUris.parseId(uri);
                c = db.query(ContratoCliente.TablaCliente.TABLA, projection, ContratoCliente.TablaCliente._ID + "= ? " , new String [] {idActividad + ""},
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        c.setNotificationUri(getContext().getContentResolver(),ContratoCliente.TablaCliente.CONTENT_URI);
        return c;
    }

}

