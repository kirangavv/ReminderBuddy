package com.remainder.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RemainderSQLiteHelper extends SQLiteOpenHelper {

    public RemainderSQLiteHelper(Context context) {
        // Databse: remainders_db, Version: 1
        super(context, "reaminders_db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute create table SQL
        //db.execSQL("CREATE TABLE remainders (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL , details Text , phone Text , email Text , date Text, type Text, sendWishes Boolean , wishesDetails Text );");
        db.execSQL("CREATE TABLE remainders (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL , details Text , phone Text , email Text , date Text, type Text, sendWishes Boolean ,byPhone Boolean, byEmail Boolean, wishesDetails Text , status Boolean   );");
        db.execSQL("CREATE TABLE settings (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL , value Text, status Boolean   );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        // DROP table
        db.execSQL("DROP TABLE IF EXISTS remainders");

        // Recreate table
        onCreate(db);
    }

}

