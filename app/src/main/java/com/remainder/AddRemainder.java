package com.remainder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.remainder.dao.RemainderDAO;
import com.remainder.datamodels.Remainder;

public class AddRemainder extends AppCompatActivity implements View.OnClickListener {

    // GUI components
    private EditText remainderName;		// Text field
    private EditText remainderDetails;		// Text field
    private EditText remainderDate;		// Text field
    private EditText remainderEmail;		// Text field
    private EditText remainderPhone;		// Text field
    private Button addNewButton;	// Add new button
    private Button backButton;		// Back button

    // DAO
    private RemainderDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remainder);// Create DAO object
        dao = new RemainderDAO(this);

        remainderName 		= (EditText)findViewById(R.id.newRemainderName);
        remainderDetails 		= (EditText)findViewById(R.id.newRemainderDetails);
        remainderDate 		= (EditText)findViewById(R.id.newRemainderDate);
        remainderEmail 		= (EditText)findViewById(R.id.newRemainderEmail);
        remainderPhone 		= (EditText)findViewById(R.id.newRemainderPhone);
        addNewButton 	= (Button)findViewById(R.id.addNewTodoButton);
        backButton		= (Button)findViewById(R.id.menuGoBackButton);

        addNewButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // If add button was clicked
        if (addNewButton.isPressed()) {
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

            Intent intent = new Intent(this, MainActivity.class);
            // Start activity
            startActivity(intent);

        }
        //else if (backButton.isPressed()) {
            // When back button is pressed
            // Create an intent
           // Intent intent = new Intent(this, MainActivity.class);
            // Start activity
           // startActivity(intent);
            // Finish this activity
            this.finish();

            // Close the database
            dao.close();
       // }

    }

}
