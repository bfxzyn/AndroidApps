package com.meiyiyou.simpletodo.com.meiyiyou.simpletodo.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.meiyiyou.simpletodo.R;

import java.util.ArrayList;

/**
 * Created by Ian on 1/7/16.
 */
public class ItemsAdapter extends ArrayAdapter<Item>{
    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_item, parent, false);
        }
        // Lookup view for data population
        TextView taskName = (TextView) convertView.findViewById(R.id.itemTaskName);
        TextView teskPriority = (TextView) convertView.findViewById(R.id.itemTaskPriority);
        //TextView tvHome = (TextView) convertView.findViewById(R.id.tvHome);
        // Populate the data into the template view using the data object
        taskName.setText(item.itemName);
        teskPriority.setText(item.itemPriority);
        //tvHome.setText(user.hometown);
        // Return the completed view to render on screen
        return convertView;
    }


}

