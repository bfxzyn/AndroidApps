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
import java.util.Arrays;
import java.util.List;


@Table(name = "Items")
public class Item extends Model {
    @Column(name="itemName")
    public String itemName;

    @Column(name="itemDueDateDay")
    public int itemDueDateDay;

    @Column(name="itemDueDateMonth")
    public int itemDueDateMonth;

    @Column(name="itemDueDateYear")
    public int itemDueDateYear;

    @Column(name="itemPriority")
    public String itemPriority;

    public Item(){
        super();
    }

    public Item(String itemName, int itemDueDateDay, int itemDueDateMonth, int itemDueDateYear, String itemPriority){
        super();
        this.itemName = itemName;
        this.itemDueDateDay = itemDueDateDay;
        this.itemDueDateMonth = itemDueDateMonth;
        this.itemDueDateYear = itemDueDateYear;
        this.itemPriority = itemPriority;
    }

    public static ArrayList<Item> showAllItems(){
        ArrayList<Item> itemsList = new ArrayList<Item>();
        try {
            Select select = new Select();
            List<Item> itemsListItem  = select.from(Item.class).execute();
            for(int i = 0; i < itemsListItem.size(); i++){
                itemsList.add(i, itemsListItem.get(i));
            }
        }catch(Exception e){
        }
        return itemsList;
    }

    public static void saveItems(ArrayList<Item> items){
        try {
            for (int i = 0; i < items.size(); i++) {
                Item item = new Item(items.get(i).itemName, items.get(i).itemDueDateDay,
                        items.get(i).itemDueDateMonth, items.get(i).itemDueDateYear, items.get(i).itemPriority);
                item.save();
            }
        }catch(Exception e){
        }
    }
}
