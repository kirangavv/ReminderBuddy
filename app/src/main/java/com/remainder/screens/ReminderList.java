package com.remainder.screens;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.remainder.R;
import com.remainder.dao.RemainderDAO;
import com.remainder.datamodels.Remainder;
import com.remainder.screens.AddReminder;
import com.remainder.screens.Main;

import java.util.List;

/**
 * List adapter for storing Remainders data
 *
 * @author itcuties
 *
 */
public class ReminderList extends ArrayAdapter<Remainder> {

    // List context
    private final Context context;
    private RemainderDAO dao;
    // List values
    private final List<Remainder> remainderList;

    public ReminderList(Context context, List<Remainder> remainderList) {
        super(context, R.layout.activity_main, remainderList);
        this.context = context;
        this.remainderList = remainderList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        dao = new RemainderDAO(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_reminder_list, parent, false);

        TextView remainderText = (TextView) rowView.findViewById(R.id.textview_reminderlist_name);
        remainderText.setText(remainderList.get(position).getName());

        TextView remainderEmail = (TextView) rowView.findViewById(R.id.textview_reminderlist_email);
        remainderEmail.setText(remainderList.get(position).getEmail());

        TextView remainderDetails = (TextView) rowView.findViewById(R.id.textview_reminderlist_details);
        remainderDetails.setText(remainderList.get(position).getDetails());

        TextView remainderPhone = (TextView) rowView.findViewById(R.id.textview_reminderlist_phone);
        remainderPhone.setText(remainderList.get(position).getPhone());

        TextView remainderDate = (TextView) rowView.findViewById(R.id.textview_reminderlist_date);
        remainderDate.setText(remainderList.get(position).getDate());

        ImageView remainderShowDetails = (ImageView) rowView.findViewById(R.id.button_reminderlist_details);

        remainderShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(context, AddReminder.class);
                View parentRow = (View) v.getParent().getParent().getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);
                addIntent.putExtra("Mode","Edit");
                addIntent.putExtra("ID", remainderList.get(position).getId());
                context.startActivity(addIntent);
            }
        });


        ImageView remainderDelete = (ImageView) rowView.findViewById(R.id.button_reminderlist_delete);

        remainderDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(context, AddReminder.class);
                View parentRow = (View) v.getParent().getParent().getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);
                int id = remainderList.get(position).getId();
                dao.deleteRemainder(id);
                Toast.makeText(context, "Remainder deleted!", Toast.LENGTH_LONG).show();
                dao.close();
                Intent mainIntent = new Intent(context, Main.class);
                context.startActivity(mainIntent);
            }
        });
        return rowView;
    }

}

