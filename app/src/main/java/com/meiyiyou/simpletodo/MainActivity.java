package com.meiyiyou.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvItems;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemAdapter;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems();
        itemAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, items);
        lvItems.setAdapter(itemAdapter);
        setupListViewListener();
        launchEditItemActivity();
    }

    public void onAddItem(View v){
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    public void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch(IOException e){
            items = new ArrayList<String>();
        }
    }

    public void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void launchEditItemActivity(){
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent editItemIntent = new Intent(MainActivity.this, EditItemActivity.class);
                        editItemIntent.putExtra("position", position);
                        editItemIntent.putExtra("text", items.get(position));
                        startActivityForResult(editItemIntent, REQUEST_CODE);
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String newItemText = data.getExtras().getString("text");
            int newItemPosition = data.getExtras().getInt("position");
            items.set(newItemPosition, newItemText);
            itemAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

}
