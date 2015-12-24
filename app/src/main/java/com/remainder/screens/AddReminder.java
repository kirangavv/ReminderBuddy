package com.remainder.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.remainder.R;
import com.remainder.common.Helper;
import com.remainder.dao.RemainderDAO;
import com.remainder.datamodels.Remainder;
import com.remainder.common.DatePickerFragment;

import java.util.Calendar;

public class AddReminder extends AppCompatActivity implements View.OnClickListener {

    /* GUI components */
    private EditText remainderName;		// Text field
    private EditText remainderDetails;		// Text field
    private EditText remainderDate;		// Text field
    private EditText remainderEmail;		// Text field
    private EditText remainderPhone;		// Text field
    private EditText remainderWishesMessage; //Text filed wishes message
    private ImageView saveButton;	// Save button
    private ImageView dateSelectionButton;	// Save button
    private ToggleButton remainderStatus;
    private ToggleButton remainderSendWishes;
    private CheckBox remainderByEmail;
    private CheckBox remainderByPhone;


    private  Spinner remainderType;

    /*variables */
    private DatePicker datePicker;
    private Calendar calendar;
    static final int DATE_PICKER_DIALOG_ID = 1111;
    private RemainderDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_reminder);// Create DAO object

        dao = new RemainderDAO(this);

        //Spinner dropdown = (Spinner)findViewById(R.id.spinner_addremainder_remaindertype);
        String[] items = new String[]{"OneTime", "Week", "Month", "Year"};
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        remainderName 		= (EditText)findViewById(R.id.edittext_addremainder_name);
        remainderDetails 	= (EditText)findViewById(R.id.edittext_addremainder_details);
        remainderDate 		= (EditText)findViewById(R.id.edittext_addremainder_date);
        remainderEmail 		= (EditText)findViewById(R.id.edittext_addremainder_email);
        remainderPhone 		= (EditText)findViewById(R.id.edittext_addremainder_phone);
        remainderWishesMessage 		= (EditText)findViewById(R.id.edittext_addremainder_message);

        remainderStatus = (ToggleButton)findViewById(R.id.togglebutton_addremainder_reminderstatus);
        remainderSendWishes = (ToggleButton)findViewById(R.id.togglebutton_addremainder_sendwishes);

        remainderByPhone = (CheckBox)findViewById(R.id.checkbox_addremainder_phone);
        remainderByEmail = (CheckBox)findViewById(R.id.checkbox_addremainder_email);

        remainderType = (Spinner)findViewById(R.id.spinner_addremainder_remaindertype);

        saveButton 	        = (ImageView)findViewById(R.id.button_addremainder_save);
        dateSelectionButton = (ImageView)findViewById(R.id.button_addremainder_setdate);

        /*showing default date for date.*/
        final Calendar c = Calendar.getInstance();
        remainderDate.setText(new StringBuilder()
                .append(c.get(Calendar.MONTH) + 1).append("-").append(c.get(Calendar.DAY_OF_MONTH)).append("-").append(c.get(Calendar.YEAR)));

        /* set remainder status active by default */
        remainderStatus.setChecked(true);

        /*set default type values */
        remainderType.setAdapter(adapterType);

        /*disable check box and message edit*/
        remainderByPhone.setEnabled(false);
        remainderByEmail.setEnabled(false);
        remainderWishesMessage.setEnabled(false);

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
            remainderSendWishes.setChecked(remainder.getSendWishes());
            remainderByEmail.setChecked(remainder.getByEmail());
            remainderByPhone.setChecked(remainder.getByPhone());
            remainderStatus.setChecked(remainder.getStatus());
            int remainderTypePosition = adapterType.getPosition(remainder.getType());
            remainderType.setSelection(remainderTypePosition);

            /*enable byphone email and message*/
            remainderByPhone.setEnabled(remainderSendWishes.isChecked());
            remainderByEmail.setEnabled(remainderSendWishes.isChecked());
            remainderWishesMessage.setEnabled(remainderSendWishes.isChecked());

        }

        /*save button click */
        saveButton.setOnClickListener(this);

        /*send wishes toggle button*/
        remainderSendWishes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                remainderByPhone.setEnabled(isChecked);
                remainderByEmail.setEnabled(isChecked);
                remainderWishesMessage.setEnabled(isChecked);
            }
        });


        /*select date button click */
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
        //Helper helper = new Helper();

        /*call validations */
        if (!DoValidations()) return;

        Remainder remainderObj = new Remainder();
        remainderObj.setName(remainderName.getText().toString());
        remainderObj.setDate(remainderDate.getText().toString());
        remainderObj.setDetails(remainderDetails.getText().toString());
        remainderObj.setPhone(remainderPhone.getText().toString());
        remainderObj.setEmail(remainderEmail.getText().toString());
        remainderObj.setType(remainderType.getSelectedItem().toString());
        remainderObj.setSendWishes(remainderSendWishes.isChecked());
        remainderObj.setByPhone(remainderByPhone.isChecked());
        remainderObj.setByEmail(remainderByEmail.isChecked());
        remainderObj.setWishesDetails(remainderWishesMessage.getText().toString());
        remainderObj.setStatus(remainderStatus.isChecked());

        Intent intent = getIntent();
        String mode =  intent.getExtras().getString("Mode");

        if (mode.equals("New")) {
             // Add reaminder data to the database
             dao.createRemainder(remainderObj);
             // Display success information
             Toast.makeText(getApplicationContext(), "Remainder added!", Toast.LENGTH_LONG).show();
            }
        else {
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

    /*validations*/
    private Boolean DoValidations() {
        Helper helper = new Helper();
        if (!helper.isNotEmptyNullText(remainderName.getText().toString()))
        {
            remainderName.setError("Name should not be empty!");
            return false;
        }

        if (!helper.isValidDate(remainderDate.getText().toString()))
        {
            remainderDate.setError("Enter valid date in format 'mm-dd-yyyy'!");
            return false;
        }

        if (remainderSendWishes.isChecked())
        {
            if (!helper.isNotEmptyNullText(remainderWishesMessage.getText().toString()))
            {
                remainderWishesMessage.setError("Send wishes message should not be empty when send wishes is on!");
                return false;
            }

            if (!remainderByEmail.isChecked() && !remainderByPhone.isChecked())
            {
                remainderByEmail.setError("Phone or Email should be checked when send wishes is on");
                remainderByPhone.setError("Phone or Email should be checked when send wishes is on");
                return  false;
            }

            if (remainderByEmail.isChecked())
            {
                if(!helper.isValidEmail(remainderEmail.getText().toString()))
                {
                    remainderEmail.setError("Enter valid email");
                    return  false;
                }
            }

            if (remainderByPhone.isChecked())
            {
                if(!helper.isValidPhoneNumber(remainderPhone.getText().toString()))
                {
                    remainderPhone.setError("Enter valid phone number");
                    return  false;
                }
            }
        }

        return  true;
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
