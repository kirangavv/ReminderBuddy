package com.remainder.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.remainder.R;
import com.remainder.dao.RemainderDAO;

public class Main extends AppCompatActivity   {
    // DAO
    private RemainderDAO dao;
    ReminderList listRemainder;
    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new RemainderDAO(this);

        //fill list
        listRemainder =new ReminderList(this, dao.getRemainders());
        listView = (ListView) findViewById(R.id.listview_result);
        listView.setAdapter(listRemainder);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add:
                //calling add reminder page
                Intent addIntent = new Intent(this, AddReminder.class);
                addIntent.putExtra("Mode", "New");
                startActivity(addIntent);
                break;
            case R.id.menu_settings:
                // calling setting page
                Intent settingIntent = new Intent(this, Settings.class);
                startActivity(settingIntent);
                break;
        }

        // Close the database
        dao.close();

        return super.onOptionsItemSelected(item);
    }

}
