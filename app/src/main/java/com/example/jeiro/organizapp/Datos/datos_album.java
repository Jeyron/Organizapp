package com.example.jeiro.organizapp.Datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.jeiro.organizapp.BD.*;
import com.example.jeiro.organizapp.Modelo.*;

import java.util.ArrayList;

/**
 * Created by rcrodriguez on 8/10/2017.
 */

public class datos_album
{
    public datos_album() {}

    public boolean insertar_album(Album album, boolean insertar, Context context)
    {
        base_de_datos helper = new base_de_datos(context);
        try
        {
            ContentValues values = new ContentValues();
            values.put(tablas.tabla_album.COLUMN_NAME_PADRE,   album.getPadre().getNombre());
            values.put(tablas.tabla_album.COLUMN_NAME_NOMBRE,  album.getNombre());
            values.put(tablas.tabla_album.COLUMN_NAME_USUARIO, album.getUsuario());
            if (insertar) // insertar
            {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.insert(tablas.tabla_album.TABLE_NAME, null, values);
                db.close();
            }
            else // modificar
            {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.update(tablas.tabla_album.TABLE_NAME, values, "_id=" + album.getId(), null);
                db.close();
            }

        }
        catch (Exception exc)
        {
            return false;
        }
        return true;
    }

    public ArrayList<Album> obtener_albums (Context context)
    {
        ArrayList datos = new ArrayList<Album>();
        base_de_datos helper = new base_de_datos(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        try
        {
            Cursor c = db.query
                    (
                            tablas.tabla_album.TABLE_NAME, // The table to query
                            null, // The columns to return
                            null, // The columns for the WHERE clause
                            null, // The values for the WHERE clause
                            null, // don't group the rows
                            null, // don't filter by row groups
                            null // The sort order
                    );
            if (c.moveToFirst())
                do
                {
                    Album a = new Album(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
                    datos.add(a);
                } while (c.moveToNext());
        }
        catch (Exception exc)
        {
            db.close();
            return new ArrayList<Album>();
        }
        db.close();
        return datos;
    }

    public Album obtener_albums (Context context, Album album)
    {
        ArrayList<Album> datos = new ArrayList();
        base_de_datos helper = new base_de_datos(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        try
        {
            Cursor c = db.query
                    (
                            tablas.tabla_album.TABLE_NAME, // The table to query
                            null, // The columns to return
                            null, // The columns for the WHERE clause
                            null, // The values for the WHERE clause
                            null, // don't group the rows
                            null, // don't filter by row groups
                            null // The sort order
                    );
            if (c.moveToFirst())
                do
                {
                    Album a = new Album(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
                    datos.add(a);
                } while (c.moveToNext());
        }
        catch (Exception exc)
        {
            db.close();
            return null;
        }
        db.close();
        for (int i = 0; i < datos.size();i++)
        {
            Album temp = datos.get(i);
            if(temp.getNombre().equals(album.getNombre()))
                return temp;
        }
        return null;
    }

    public boolean eliminar_album (Album album, Context context)
    {
        base_de_datos helper = new base_de_datos(context);
        try
        {
            SQLiteDatabase db = helper.getWritableDatabase();
            String whereClause = "_id=?";
            String[] whereArgs = new String[] { String.valueOf(album.getId()) };
            db.delete(tablas.tabla_album.TABLE_NAME, whereClause, whereArgs);
        }
        catch (Exception exc)
        {
            return false;
        }
        return true;
    }
}
