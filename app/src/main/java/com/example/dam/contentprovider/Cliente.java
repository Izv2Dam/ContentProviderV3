package com.example.dam.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by 2dam on 17/11/2015.
 */
public class Cliente {
    private long id;
    private String nombre, telefono;

    public Cliente(){
        this(0,"","");

    }

    public Cliente(long id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        //cv.put(ContratoCliente.TablaCliente._ID,this.id);
        cv.put(ContratoCliente.TablaCliente.NOMBRE,this.nombre);
        cv.put(ContratoCliente.TablaCliente.TELEFONO,this.telefono);
        return cv;
    }

    public void set(Cursor c){ //A partir del cursor recuperar nombre, apellido y telefono
        this.id = c.getLong(c.getColumnIndex(ContratoCliente.TablaCliente._ID));
        this.nombre = c.getString(c.getColumnIndex(ContratoCliente.TablaCliente.NOMBRE));
        this.telefono= c.getString(c.getColumnIndex(ContratoCliente.TablaCliente.TELEFONO));

    }


}
