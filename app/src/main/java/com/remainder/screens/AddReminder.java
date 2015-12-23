package com.remainder.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.remainder.R;
import com.remainder.common.Helper;
import com.remainder.dao.RemainderDAO;
import com.remainder.datamodels.Remainder;
import com.remainder.common.DatePickerFragment;

import java.util.Calendar;

public class AddReminder extends AppCompatActivity implements View.OnClickListener {

    // GUI components
    private EditText remainderName;		// Text field
    private EditText remainderDetails;		// Text field
    private EditText remainderDate;		// Text field
    private EditText remainderEmail;		// Text field
    private EditText remainderPhone;		// Text field
    private ImageView saveButton;	// Save button
    private ImageView dateSelectionButton;	// Save button
    private EditText remainderWishesMessage; //Text filed wishes message

    private DatePicker datePicker;
    private Calendar calendar;
    //private TextView dateView;
    //private int year, month, day;

    static final int DATE_PICKER_DIALOG_ID = 1111;
    // DAO
    private RemainderDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_reminder);// Create DAO object

        dao = new RemainderDAO(this);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner_addremainder_remaindertype);
        String[] items = new String[]{"OneTime", "Week", "Mont", "Year"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        remainderName 		= (EditText)findViewById(R.id.edittext_addremainder_name);
        remainderDetails 	= (EditText)findViewById(R.id.edittext_addremainder_details);
        remainderDate 		= (EditText)findViewById(R.id.edittext_addremainder_date);
        remainderEmail 		= (EditText)findViewById(R.id.edittext_addremainder_email);
        remainderPhone 		= (EditText)findViewById(R.id.edittext_addremainder_phone);
        remainderWishesMessage 		= (EditText)findViewById(R.id.edittext_addremainder_message);

        saveButton 	        = (ImageView)findViewById(R.id.button_addremainder_save);
        dateSelectionButton = (ImageView)findViewById(R.id.button_addremainder_setdate);

        //showing default date for date.
        final Calendar c = Calendar.getInstance();
        remainderDate.setText(new StringBuilder()
             .append(c.get(Calendar.MONTH) + 1).append("-").append(c.get(Calendar.DAY_OF_MONTH)).append("-").append(c.get(Calendar.YEAR)));

        Intent intent = getIntent();
        String mode =  intent.getExtras().getString("Mode");
        if (mode.equals("Edit")) {
            int id = intent.getExtras().getInt("ID");
            Remainder remainder =  dao.getRemainder(id);
            remainderName.setText(remainder.getName());
            remainderDetails.setText(remainder.getDetails());
            remainderPhone.setText(remainder.getPhone());
            remainderDate.setText(remainder.getDate());
            remainderEmail.setText(remainder.getEmail());
            remainderWishesMessage.setText(remainder.getWishesDetails());
        }

        //save button click
        saveButton.setOnClickListener(this);

        //select date button click
        dateSelectionButton.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_addremainder_save :
                remainderSaveButtonClicked();
                break;

            case  R.id.button_addremainder_setdate :
                remainderSetDateClicked();
                break;
        }
    }

    private void remainderSetDateClicked() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void remainderSaveButtonClicked() {
        //String remainderNameTextValue = remainderName.getText().toString();
        //remainderName.setText("");

        Helper helper = new Helper();

        if (!helper.isNotEmptyNullText(remainderName.getText().toString()))
        {
            remainderName.setError("Name should not be empty!");
            return;
        }

        if (!helper.isValidDate(remainderDate.getText().toString()))
        {
            remainderDate.setError("Enter valid date in format 'mm-dd-yyyy'!");
            return;
        }


        Remainder remainderObj = new Remainder();
        //remainderObj.setName(remainderNameTextValue);
        remainderObj.setName(remainderName.getText().toString());
        remainderObj.setDate(remainderDate.getText().toString());
        remainderObj.setDetails(remainderDetails.getText().toString());
        remainderObj.setPhone(remainderPhone.getText().toString());
        remainderObj.setEmail(remainderEmail.getText().toString());
        remainderObj.setWishesDetails(remainderWishesMessage.getText().toString());

            Intent intent = getIntent();
            String mode =  intent.getExtras().getString("Mode");

            if (mode.equals("New")) {
                // Add reaminder data to the database
                dao.createRemainder(remainderObj);
                // Display success information
                Toast.makeText(getApplicationContext(), "Remainder added!", Toast.LENGTH_LONG).show();
            }
            else
            {
                int id =  intent.getExtras().getInt("ID");
                remainderObj.setId(id);
                //Update reaminder
                dao.updateRemainder(remainderObj);
                // Display success information
                Toast.makeText(getApplicationContext(), "Updated reminder!", Toast.LENGTH_LONG).show();
            }
            intent = new Intent(this, Main.class);
            startActivity(intent);

        this.finish();
        // Close the database
        dao.close();
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
