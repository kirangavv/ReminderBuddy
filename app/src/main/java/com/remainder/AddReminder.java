package com.remainder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.remainder.dao.RemainderDAO;
import com.remainder.datamodels.Remainder;

public class AddReminder extends AppCompatActivity implements View.OnClickListener {

    // GUI components
    private EditText remainderName;		// Text field
    private EditText remainderDetails;		// Text field
    private EditText remainderDate;		// Text field
    private EditText remainderEmail;		// Text field
    private EditText remainderPhone;		// Text field
    private ImageView saveButton;	// Save button

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
        saveButton 	        = (ImageView)findViewById(R.id.button_addremainder_save);


        Intent intent = getIntent();
        String mode =  intent.getExtras().getString("Mode");
        if (mode.equals("Edit")) {
            String name = intent.getExtras().getString("Name");
            remainderName.setText(name);
            String details = intent.getExtras().getString("Details");
            remainderDetails.setText(details);
            String date = intent.getExtras().getString("Date");
            remainderDate.setText(date);
            String email = intent.getExtras().getString("Email");
            remainderEmail.setText(email);
            String phone = intent.getExtras().getString("Phone");
            remainderPhone.setText(phone);
        }

        saveButton.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        // If add button was clicked
        if (saveButton.isPressed()) {
            // Get entered text
            String remainderNameTextValue = remainderName.getText().toString();
            remainderName.setText("");

            Remainder remainderObj= new Remainder();
            remainderObj.setName(remainderNameTextValue);
            remainderObj.setDate(remainderDate.getText().toString());
            remainderObj.setDetails(remainderDetails.getText().toString());
            remainderObj.setPhone(remainderPhone.getText().toString());
            remainderObj.setEmail(remainderEmail.getText().toString());

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
                //update reaminder
                dao.updateRemainder(remainderObj);
                // Display success information
                Toast.makeText(getApplicationContext(), "Updated reminder!", Toast.LENGTH_LONG).show();
            }
            intent = new Intent(this, Main.class);
            startActivity(intent);
        }
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
