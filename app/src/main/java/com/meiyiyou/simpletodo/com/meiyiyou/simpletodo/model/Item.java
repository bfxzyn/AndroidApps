package com.meiyiyou.simpletodo.com.meiyiyou.simpletodo.model;

/**
 * Created by Ian on 1/5/16.
 */
import android.content.Intent;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;


@Table(name = "Items")
public class Item extends Model {
    @Column(name="itemName")
    public String itemName;

    public Item(){
        super();
    }

    public Item(String itemName){
        super();
        this.itemName = itemName;
    }

    public static ArrayList<String> showAllItems(){
        ArrayList<String> itemsList = new ArrayList<String>();
        try {
            Select select = new Select();
            List<Item> itemsListItem = select.from(Item.class).execute();
            for(int i = 0; i < itemsListItem.size(); i++){
                itemsList.add(i, itemsListItem.get(i).itemName);
            }
        }catch(Exception e){
        }
        return itemsList;
    }

    public static void saveItems(ArrayList<String> items){
        try {
            for (int i = 0; i < items.size(); i++) {
                Item item = new Item(items.get(i).toString());
                item.save();
            }
        }catch(Exception e){
        }
    }
}
