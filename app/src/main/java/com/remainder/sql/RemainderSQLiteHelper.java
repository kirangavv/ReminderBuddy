package com.remainder.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by satish on 11/14/2015.
 */
public class RemainderSQLiteHelper extends SQLiteOpenHelper {

    public RemainderSQLiteHelper(Context context) {
        // Databse: remainders_db, Version: 1
        super(context, "reaminders_db", null, 1);
    }

    /**
     * Create simple table
     * remainders
     * 		_id 	- key
     * 		name	- name text
     * 	    details - details text
     * 	    phone - phone text
     * 	    email - email text
     * 	    date - date text
     * 	    type - type text
     * 	    sendWishes - sendWishes boolean
     * 	    wishesDetails -wishesDetails text
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute create table SQL
        db.execSQL("CREATE TABLE remainders (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL , details Text , phone Text , email Text , date Text, type Text, sendWishes Boolean , wishesDetails Text    );");
    }

    /**
     * Recreates table
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        // DROP table
        db.execSQL("DROP TABLE IF EXISTS remainders");
        // Recreate table
        onCreate(db);
    }

}

