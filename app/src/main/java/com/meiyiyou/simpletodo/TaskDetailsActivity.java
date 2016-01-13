package com.meiyiyou.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.meiyiyou.simpletodo.com.meiyiyou.simpletodo.model.Item;

public class TaskDetailsActivity extends Activity {
    TextView taskName;
    TextView dueDate;
    TextView taskPriority;
    String taskNameText;
    String taskPriorityText;
    int taskDueDateDay;
    int taskDueDateMonth;
    int taskDueDateYear;
    int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        taskNameText = getIntent().getStringExtra("taskName");
        taskPriorityText = getIntent().getStringExtra("taskPriority");

        taskDueDateDay = getIntent().getIntExtra("taskDueDateDay", -1);
        taskDueDateMonth = getIntent().getIntExtra("taskDueDateMonth", -1);
        taskDueDateYear = getIntent().getIntExtra("taskDueDateYear", -1);
        StringBuilder sb = new StringBuilder()
                // Month is 0 based so add 1
                .append(taskDueDateMonth).append("-")
                .append(taskDueDateDay).append("-")
                .append(taskDueDateYear).append(" ");
        String dueDateShow = sb.toString();

        taskName = (TextView) findViewById(R.id.txtTaskNameShow);
        taskName.setText(taskNameText);
        taskPriority = (TextView) findViewById(R.id.txtPriorityShow);
        taskPriority.setText(taskPriorityText);
        dueDate = (TextView) findViewById(R.id.txtDueDateShow);
        dueDate.setText(dueDateShow);
        onEditBtn();
    }

    public void onEditBtn(){
        Button btn = (Button) findViewById(R.id.editTaskDetailsbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editItemResultIntent = new Intent(TaskDetailsActivity.this, NewTaskActivity.class);
                editItemResultIntent.putExtra("taskName", taskNameText);
                editItemResultIntent.putExtra("position", getIntent().getIntExtra("position", 0));
                editItemResultIntent.putExtra("taskPriority", taskPriorityText);
                editItemResultIntent.putExtra("taskDueDateDay", taskDueDateDay);
                editItemResultIntent.putExtra("taskDueDateMonth", taskDueDateMonth);
                editItemResultIntent.putExtra("taskDueDateYear", taskDueDateYear);
                startActivityForResult(editItemResultIntent, REQUEST_CODE);
                finish();
            }
        });
    }
}
