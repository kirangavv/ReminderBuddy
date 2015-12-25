package com.remainder.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.remainder.R;
import com.remainder.common.Constants;
import com.remainder.dao.RemainderDAO;

import java.text.ParseException;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    Button buttonAboutUS;
    Spinner settingExpiredDays;
    ToggleButton settingAutomaticBackup;

    private RemainderDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        /*create DAO object */
        dao = new RemainderDAO(this);

        buttonAboutUS = (Button) findViewById(R.id.button_settings_aboutus);
        settingExpiredDays = (Spinner)findViewById(R.id.spinner_settings_delexpireddays);
        settingAutomaticBackup = (ToggleButton)findViewById(R.id.togglebutton_settings_automaticbackup);

        /*about us button*/
        buttonAboutUS.setOnClickListener(this);

        /*add back arrow */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*fill delete expired days combo */
        String[] items = new String[]{"30", "60", "90"};
        ArrayAdapter<String> adapterExpiredDays = new ArrayAdapter<String>(this, R.layout.spinner_layout, items);
        settingExpiredDays.setAdapter(adapterExpiredDays);

        /*load exisitng setting values*/
        String automaticBackupSettingValue = dao.getSettingValue(Constants.AUTOMATIC_BACKUP_SETTING_NAME);
        if ( automaticBackupSettingValue.toString().equals("true"))
        {
            settingAutomaticBackup.setChecked(true);
        }
        else
        {
            settingAutomaticBackup.setChecked(false);
        }

        String deleteExpiredDaysSettingValue = dao.getSettingValue(Constants.DELETE_EXPIRED_DAYS_SETTING_NAME);
        int expiredDaysPosition = adapterExpiredDays.getPosition(deleteExpiredDaysSettingValue);
        if (expiredDaysPosition < 0 ) expiredDaysPosition = 0;
        settingExpiredDays.setSelection(expiredDaysPosition);

        /*automatic backup setting update*/
        settingAutomaticBackup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dao.addUpdateSettings(Constants.AUTOMATIC_BACKUP_SETTING_NAME, "true");
                }
                else
                {
                    dao.addUpdateSettings(Constants.AUTOMATIC_BACKUP_SETTING_NAME, "false");
                }
            }
        });

        /*delete expired days */
        settingExpiredDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                dao.addUpdateSettings(Constants.DELETE_EXPIRED_DAYS_SETTING_NAME, parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
