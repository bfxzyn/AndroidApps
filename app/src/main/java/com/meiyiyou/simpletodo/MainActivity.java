package com.meiyiyou.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.meiyiyou.simpletodo.com.meiyiyou.simpletodo.model.Item;
import com.meiyiyou.simpletodo.com.meiyiyou.simpletodo.model.ItemsAdapter;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView lvItems;
    private ArrayList<Item> items;
    private ArrayAdapter<Item> itemAdapter;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<Item>();
        items = Item.showAllItems();
        itemAdapter = new ItemsAdapter(this, items);
        lvItems.setAdapter(itemAdapter);
        setupListViewListener();
        launchTaskDetailsActivity();
    }

    public void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemAdapter.notifyDataSetChanged();
                        Item.saveItems(items);
                        return true;
                    }
                });
    }


    public void launchTaskDetailsActivity(){
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent taskDetailsIntent = new Intent(MainActivity.this, TaskDetailsActivity.class);
                        taskDetailsIntent.putExtra("taskName", items.get(position).itemName);
                        taskDetailsIntent.putExtra("position", position);
                        taskDetailsIntent.putExtra("taskDueDateDay",items.get(position).itemDueDateDay);
                        taskDetailsIntent.putExtra("taskDueDateMonth", items.get(position).itemDueDateMonth);
                        taskDetailsIntent.putExtra("taskDueDateYear", items.get(position).itemDueDateYear);
                        taskDetailsIntent.putExtra("taskPriority", items.get(position).itemPriority);
                        startActivityForResult(taskDetailsIntent, REQUEST_CODE);

                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String newItemText = data.getExtras().getString("taskName");
            int newItemPosition = data.getExtras().getInt("position");
            String newItemPriority = data.getExtras().getString("taskPriority");
            int taskDueDateDay = data.getIntExtra("taskDueDateDay", -1);
            int taskDueMonth = data.getIntExtra("taskDueDateMonth", -1);
            int taskDueDateYear = data.getIntExtra("taskDueDateYear", -1);
            Item itemTemp = new Item(newItemText, taskDueDateDay, taskDueMonth, taskDueDateYear, newItemPriority);
            items.add(newItemPosition, itemTemp);
            itemAdapter.notifyDataSetChanged();
            Item.saveItems(items);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.action_EditItem){
            Intent editItemIntent = new Intent(MainActivity.this, NewTaskActivity.class);
            editItemIntent.putExtra("position", items.size());
            startActivityForResult(editItemIntent, REQUEST_CODE);
        }
        return true;
    }

}
