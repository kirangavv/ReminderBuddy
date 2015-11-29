package com.remainder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

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
        // Create DAO object
        dao = new RemainderDAO(this);
        //Remainder newRemainderObj= new Remainder();
       // newRemainderObj.setName("Testing");
      //  dao.createRemainder(newRemainderObj);

      //  newRemainderObj.setName("Testing11");
      //  dao.createRemainder(newRemainderObj);

        listRemainder =new ReminderList(this, dao.getRemainders());

        listView = (ListView) findViewById(R.id.listview_result);
        listView.setAdapter(listRemainder);
        // Set the list adapter and get Remainders list via DAO
        //setListAdapter(new ListRemainder(this, dao.getRemainders()));

    }


    /* ************************************************************* *
	 * Menu service methods
	 * ************************************************************* */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //calling add reminder page
        Intent intent = new Intent(this, AddReminder.class);
        startActivity(intent);

        // Close the database
        dao.close();

        return super.onOptionsItemSelected(item);
    }

}
