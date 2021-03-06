package com.remainder.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.remainder.common.Constants;
import com.remainder.datamodels.Remainder;
import com.remainder.sql.RemainderSQLiteHelper;

import java.util.ArrayList;
import java.util.List;


public class RemainderDAO {

    private SQLiteDatabase db;
    private RemainderSQLiteHelper dbHelper;

    public RemainderDAO(Context context) {
        /*******un comment this to re create db*****/
        /////context.deleteDatabase("reaminders_db");
        dbHelper = new RemainderSQLiteHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    /*reminder table operations*/
    public void updateRemainder(Remainder remainder) {
        ContentValues contentValues = getContentValues(remainder);

        String strFilter = "_id = " + remainder.getId();
        db.update("remainders", contentValues, strFilter, null);
    }

    public void createRemainder(Remainder remainder) {
        ContentValues contentValues = getContentValues(remainder);
        // Insert into DB
        db.insert("remainders", null, contentValues);
    }

    @NonNull
    private ContentValues getContentValues(Remainder remainder) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", remainder.getName());
        contentValues.put("details", remainder.getDetails());
        contentValues.put("phone", remainder.getPhone());
        contentValues.put("date", remainder.getDate());
        contentValues.put("email", remainder.getEmail());
        contentValues.put("wishesDetails", remainder.getWishesDetails());
        contentValues.put("sendWishes",remainder.getSendWishes());
        contentValues.put("byPhone", remainder.getByPhone());
        contentValues.put("byEmail", remainder.getByEmail());
        contentValues.put("type", remainder.getType());
        contentValues.put("status", remainder.getStatus());

        return contentValues;
    }

    public void deleteRemainder(int id) {
        db.delete("remainders", "_id = " + id, null);
    }

    public Remainder getRemainder(int id) {
        Remainder remainder = new Remainder();
        String selectQuery = "SELECT * FROM remainders WHERE _id = "+ id;
        Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                remainder.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                remainder.setName(cursor.getString(cursor.getColumnIndex("name")));
                remainder.setDetails(cursor.getString(cursor.getColumnIndex("details")));
                remainder.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                remainder.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                remainder.setDate(cursor.getString(cursor.getColumnIndex("date")));
                remainder.setWishesDetails(cursor.getString(cursor.getColumnIndex("wishesDetails")));
                remainder.setSendWishes(cursor.getString(cursor.getColumnIndex("sendWishes")).equals("1"));
                remainder.setByEmail(cursor.getString(cursor.getColumnIndex("byEmail")).equals("1"));
                remainder.setByPhone(cursor.getString(cursor.getColumnIndex("byPhone")).equals("1"));
                remainder.setType(cursor.getString(cursor.getColumnIndex("type")));
                remainder.setStatus(cursor.getString(cursor.getColumnIndex("status")).equals("1"));
            }
        return  remainder;
    }

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
            remainder.setPhone(cursor.getString(2));
            remainder.setEmail(cursor.getString(3));
            remainder.setDate(cursor.getString(4));
            remainder.setDetails(cursor.getString(5));

            // Add to the DB
            remainderList.add(remainder);

            // Move to the next result
            cursor.moveToNext();
        }
        return remainderList;
    }


/*setting table operation*/
    //insert or udpate method
    //get value method

    public void addUpdateSettings(String settingName, String settingValue)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", settingName);
        contentValues.put("value", settingValue);
        contentValues.put("status", true);
        if (checkIsSettingExist(settingName))
        {
            //update
            String strFilter = "name = '" + settingName + "'";
            db.update("settings", contentValues, strFilter, null);
        }
        else
        {
            //insert
            db.insert("settings", null, contentValues);
        }
    }

    public String getSettingValue(String settingName) {
        String Query = "Select * from settings where name = '" + settingName + "'";
        Cursor cursor = db.rawQuery(Query, null);
        String value = Constants.EMPTY_STRING;
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            value = cursor.getString(cursor.getColumnIndex("value"));
        }
        cursor.close();
        return value;
    }

    public boolean checkIsSettingExist(String settingName) {
        String Query = "Select * from settings where name = '" + settingName + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


}
