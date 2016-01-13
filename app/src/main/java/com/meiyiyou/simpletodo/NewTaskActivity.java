package com.meiyiyou.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NewTaskActivity extends Activity {
    EditText editItemText;
    DatePicker datePicker;
    int dueDateDay;
    int dueDateMonth;
    int dueDateYear;
    String dueDate;
    RadioGroup priorityGroup;
    String radiovalue;
    int year, day, month;
    int REQUEST_CODE = 20;
    boolean editDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        //taskName
        editItemText = (EditText) findViewById(R.id.editItem);
        if(getIntent().hasExtra("taskName")){
            editItemText.setText(getIntent().getStringExtra("taskName"));
            editDetails = true;
        }

        //task priority
        priorityGroup = (RadioGroup)findViewById(R.id.priorityRadioGroup);
        if(getIntent().hasExtra("taskPriorityText")){
            String taskPriorityTemp = getIntent().getStringExtra("taskPriorityText");
            if(taskPriorityTemp.equals("H")){
                priorityGroup.check(R.id.highBtn);
            }
            else if(taskPriorityTemp.equals("M")){
                priorityGroup.check(R.id.middleBtn);
            }
            else {
                priorityGroup.check(R.id.lowBtn);
            }
        }
        else{
            priorityGroup.check(R.id.highBtn);
        }

        //due date
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        if(getIntent().hasExtra("taskDueDateYear") && getIntent().hasExtra("taskDueDateMonth")
                && getIntent().hasExtra("taskDueDateDay")){
            year = getIntent().getIntExtra("taskDueDateYear", -1);
            month = getIntent().getIntExtra("taskDueDateMonth", -1);
            day = getIntent().getIntExtra("taskDueDateDay", -1);
            datePicker.updateDate(year, month, day);
        }
        onSaveBtn();
    }

    public void onSaveBtn(){
        Button btn = (Button) findViewById(R.id.saveBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editDetails){
                    Intent NewItemResultIntent = new Intent(NewTaskActivity.this, MainActivity.class);
                    NewItemResultIntent.putExtra("taskName", editItemText.getText().toString());
                    NewItemResultIntent.putExtra("position", getIntent().getIntExtra("position", 0));
                    dueDateDay = datePicker.getDayOfMonth();
                    dueDateMonth = datePicker.getMonth() + 1;
                    dueDateYear = datePicker.getYear();
                    radiovalue = ((RadioButton) findViewById(priorityGroup.getCheckedRadioButtonId())).getText().toString();
                    if (radiovalue.equals("High")) {
                        radiovalue = "H";
                    } else if (radiovalue.equals("Middle")) {
                        radiovalue = "M";
                    } else {
                        radiovalue = "L";
                    }
                    NewItemResultIntent.putExtra("taskPriority", radiovalue);
                    NewItemResultIntent.putExtra("taskDueDateDay", dueDateDay);
                    NewItemResultIntent.putExtra("taskDueDateMonth", dueDateMonth);
                    NewItemResultIntent.putExtra("taskDueDateYear", dueDateYear);
                    setResult(RESULT_OK, NewItemResultIntent);
                }
                else{
                    Intent editItemResultIntent = new Intent(NewTaskActivity.this, TaskDetailsActivity.class);
                    editItemResultIntent.putExtra("taskName", editItemText.getText().toString());
                    editItemResultIntent.putExtra("position", getIntent().getIntExtra("position", 0));
                    dueDateDay = datePicker.getDayOfMonth();
                    dueDateMonth = datePicker.getMonth();
                    dueDateYear = datePicker.getYear();
                    radiovalue = ((RadioButton) findViewById(priorityGroup.getCheckedRadioButtonId())).getText().toString();
                    if (radiovalue.equals("High")) {
                        radiovalue = "H";
                    } else if (radiovalue.equals("Middle")) {
                        radiovalue = "M";
                    } else {
                        radiovalue = "L";
                    }
                    editItemResultIntent.putExtra("taskPriority", radiovalue);
                    editItemResultIntent.putExtra("taskDueDateDay", dueDateDay);
                    editItemResultIntent.putExtra("taskDueDateMonth", dueDateMonth);
                    editItemResultIntent.putExtra("taskDueDateYear", dueDateYear);
                    startActivityForResult(editItemResultIntent, REQUEST_CODE);
                }
                finish();
            }
        });
    }
}
