package com.remainder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button saveButton;	// Save button

    // DAO
    private RemainderDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_reminder);// Create DAO object

        dao = new RemainderDAO(this);

        remainderName 		= (EditText)findViewById(R.id.newRemainderName);
        remainderDetails 	= (EditText)findViewById(R.id.newRemainderDetails);
        remainderDate 		= (EditText)findViewById(R.id.newRemainderDate);
        remainderEmail 		= (EditText)findViewById(R.id.newRemainderEmail);
        remainderPhone 		= (EditText)findViewById(R.id.newRemainderPhone);
        saveButton 	        = (Button)findViewById(R.id.button_addremainder_save);

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

            Remainder newRemainderObj= new Remainder();
            newRemainderObj.setName(remainderNameTextValue);
            newRemainderObj.setDate(remainderDate.getText().toString());
            newRemainderObj.setDetails(remainderDetails.getText().toString());
            newRemainderObj.setPhone(remainderPhone.getText().toString());
            newRemainderObj.setEmail(remainderEmail.getText().toString());

            // Add reaminder data to the database
            dao.createRemainder(newRemainderObj);

            // Display success information
            Toast.makeText(getApplicationContext(), "New Remainder added!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, Main.class);
            // Start activity
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
