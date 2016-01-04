package com.meiyiyou.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText editItemText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String text = getIntent().getStringExtra("text");
        editItemText = (EditText) findViewById(R.id.editItem);
//        editItemText.requestFocus();
//        InputMethodManager imn = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imn.showSoftInput(editItemText, InputMethodManager.SHOW_IMPLICIT);
        editItemText.setText(text);
        editItemText.setSelection(editItemText.getText().length());


        onSaveBtn();
    }

    public void onSaveBtn(){
        Button btn = (Button) findViewById(R.id.saveBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editItemResultIntent = new Intent();
                editItemResultIntent.putExtra("text", editItemText.getText().toString());
                editItemResultIntent.putExtra("position", getIntent().getIntExtra("position", 0));
                setResult(RESULT_OK, editItemResultIntent);
                finish();
            }
        });
    }
}
