package com.remainder;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.remainder.dao.RemainderDAO;
import com.remainder.datamodels.Remainder;

public class MainActivity extends AppCompatActivity   {
    // DAO
    private RemainderDAO dao;

    ListRemainderAdapter listRemainderAdapter;
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

        listRemainderAdapter=new ListRemainderAdapter(this, dao.getRemainders());

        listView = (ListView) findViewById(R.id.listview_result);
        listView.setAdapter(listRemainderAdapter);
        // Set the list adapter and get Remainders list via DAO
        //setListAdapter(new ListRemainderAdapter(this, dao.getRemainders()));

    }

   /* @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // Remainder item that was clicked
        Remainder remainder = (Remainder)getListAdapter().getItem(position);

        // Delete Remainder object from the database
        dao.deleteRemainder(remainder.getId());

        // Set the list adapter and get Remainders list via DAO
        setListAdapter(new ListRemainderAdapter(this, dao.getRemainders()));

        // Display success information
        Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_LONG).show();

    }*/


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
        // Since we have only ONE option this code is not complicated :)

        // Create an intent
        Intent intent = new Intent(this, AddRemainder.class);
        // Start activity
        startActivity(intent);
        // Finish this activity
        this.finish();

        // Close the database
        dao.close();

        return super.onOptionsItemSelected(item);
    }

}
