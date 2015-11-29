package com.remainder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.remainder.datamodels.Remainder;

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
    // List values
    private final List<Remainder> remainderList;

    public ReminderList(Context context, List<Remainder> remainderList) {
        super(context, R.layout.activity_main, remainderList);
        this.context = context;
        this.remainderList = remainderList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_reminder_list, parent, false);

        TextView remainderText = (TextView) rowView.findViewById(R.id.name);
        remainderText.setText(remainderList.get(position).getName());

        TextView remainderEmail = (TextView) rowView.findViewById(R.id.email);
        remainderEmail.setText(remainderList.get(position).getEmail());

        TextView remainderDetails = (TextView) rowView.findViewById(R.id.details);
        remainderDetails.setText(remainderList.get(position).getDetails());

        TextView remainderPhone = (TextView) rowView.findViewById(R.id.phone);
        remainderPhone.setText(remainderList.get(position).getPhone());

        TextView remainderDate = (TextView) rowView.findViewById(R.id.date);
        remainderDate.setText(remainderList.get(position).getDate());

        return rowView;
    }

}

