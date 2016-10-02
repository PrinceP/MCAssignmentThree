package com.iiitd.prince.graphgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Prince on 02-10-2016.
 */

public class DataBaseManager {

    private DataBaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DataBaseManager(Context c) {
        context = c;
    }

    public DataBaseManager open() throws SQLException {
        dbHelper = new DataBaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DataBaseHelper.VALUEX, name);
        database.insert(DataBaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DataBaseHelper._ID, DataBaseHelper.VALUEX};
        Cursor cursor = database.query(DataBaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.VALUEX, name);
        int i = database.update(DataBaseHelper.TABLE_NAME, contentValues, DataBaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DataBaseHelper.TABLE_NAME, DataBaseHelper._ID + "=" + _id, null);
    }

}
