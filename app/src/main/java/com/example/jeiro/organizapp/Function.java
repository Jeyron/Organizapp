package com.example.jeiro.organizapp;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by SHAJIB on 7/16/2017.
 */

public class Function {


    static final String KEY_ALBUM = "album_name";
    static final String KEY_PATH = "path";
    static final String KEY_TIPO = "tipo";
    static final String KEY_COUNT = "date";
    static final String KEY_TIPO_CONTENIDO = "tipo_contenido";
    static final String PHOTO_TYPE = "foto";
    static final String VIDEO_TYPE = "video";
    static final String ALBUM = "album";
    static final String CONTENIDO = "contenido";
    static final int    ID_IMAGE  = 1;
    static final int    ID_VIDEO  = 2;
    static final int    ID_ALBUM  = 3;

    public static  boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static HashMap<String, String> mappingInbox(String album, String path, String count, String tipo, String tipo_contenido)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_ALBUM, album);
        map.put(KEY_PATH, path);
        map.put(KEY_COUNT, count);
        map.put(KEY_TIPO, tipo);
        map.put(KEY_TIPO_CONTENIDO, tipo_contenido);
        return map;
    }



    public static String getCount(Context c, String album_name)
    {
        Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED };
        Cursor cursorExternal = c.getContentResolver().query(uriExternal, projection, "bucket_display_name = \""+album_name+"\"", null, null);
        Cursor cursorInternal = c.getContentResolver().query(uriInternal, projection, "bucket_display_name = \""+album_name+"\"", null, null);
        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal,cursorInternal});


        return cursor.getCount()+" Photos";
    }

    public static String converToTime(String timestamp)
    {
        long datetime = Long.parseLong(timestamp);
        Date date = new Date(datetime);
        DateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
        return formatter.format(date);
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static boolean crear_album (String path, String name)
    {
        File carpetaContenedora = new File(path, name);
        if (!carpetaContenedora.exists())
        {
            carpetaContenedora.mkdirs();
            return true;
        }

        else return false;
    }

    public static boolean importar_contenido (String path, String nuevo_path, String name)
    {
        File oldfolder = new File(path);
        File newfolder = new File(nuevo_path,name);
        oldfolder.renameTo(newfolder);
        return true;
    }

    public static boolean mover_contenido (String path, String nuevo_path, String name)
    {
        File oldfolder = new File(path,name);
        File newfolder = new File(nuevo_path,name);
        oldfolder.renameTo(newfolder);
        return true;
    }

    public static boolean rename_album (String path, String ex_name, String name)
    {
        File oldfolder = new File(path,ex_name);
        File newfolder = new File(path,name);
        oldfolder.renameTo(newfolder);
        return true;

    }

    public static boolean borrar_directorio(String path, String name)
    {
        File directorio = new File(path, name);
        delete_album_Recursive(directorio);
        return true;
    }

    private static void delete_album_Recursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                delete_album_Recursive(child);
            }
        }
        fileOrDirectory.delete();
    }

}
