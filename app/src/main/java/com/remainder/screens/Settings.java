package com.remainder.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.remainder.R;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Button buttonAboutUS = (Button) findViewById(R.id.button_settings_aboutus);

        Spinner settingExpiredDays = (Spinner)findViewById(R.id.spinner_settings_delexpireddays);


        /*about us button*/
        buttonAboutUS.setOnClickListener(this);

        //add back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //fill delete days combo
        String[] items = new String[]{"30", "60", "90"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, items);
        settingExpiredDays.setAdapter(adapter);
      }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_settings_aboutus:
                Intent intent = new Intent(Settings.this, AboutUs.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
