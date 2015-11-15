package com.remainder.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.remainder.datamodels.Remainder;
import com.remainder.sql.RemainderSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satish on 11/14/2015.
 */
public class RemainderDAO {

    private SQLiteDatabase db;
    private RemainderSQLiteHelper dbHelper;

    public RemainderDAO(Context context) {
        dbHelper = new RemainderSQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Close the db
    public void close() {
        db.close();
    }

    /**
     * Create new Remainder object
     * @param remainder
     */
    public void createRemainder(Remainder remainder) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", remainder.getName());
        contentValues.put("details", remainder.getDetails());
        contentValues.put("phone", remainder.getPhone());
        contentValues.put("date", remainder.getDate());
        contentValues.put("email", remainder.getEmail());
        // Insert into DB
        db.insert("remainders", null, contentValues);
    }

    /**
     * Delete Remainder object
     * @param id
     */
    public void deleteRemainder(int id) {
        // Delete from DB where id match
        db.delete("remainders", "_id = " + id, null);
    }

    /**
     * Get all Remainders.
     * @return
     */
    public List<Remainder> getRemainders() {
        List<Remainder> remainderList = new ArrayList<Remainder>();

        // Name of the columns we want to select
        String[] tableColumns = new String[] {"_id","name","phone","email","date","details"};

        // Query the database
        Cursor cursor = db.query("remainders", tableColumns, null, null, null, null, null);
        cursor.moveToFirst();

        // Iterate the results
        while (!cursor.isAfterLast()) {
            Remainder remainder = new Remainder();
            // Take values from the DB
            remainder.setId(cursor.getInt(0));
            remainder.setName(cursor.getString(1));
            remainder.setEmail(cursor.getString(3));
            remainder.setPhone(cursor.getString(2));
            remainder.setDetails(cursor.getString(5));
            remainder.setDate(cursor.getString(4));

            // Add to the DB
            remainderList.add(remainder);

            // Move to the next result
            cursor.moveToNext();
        }

        return remainderList;
    }


}
