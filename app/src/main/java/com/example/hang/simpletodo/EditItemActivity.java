package com.example.hang.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    String text;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText editText = (EditText)findViewById(R.id.editText);
        Button fab = (Button) findViewById(R.id.btnSubmit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newText = editText.getText().toString();
                Intent data = new Intent();
                data.putExtra("itemName", newText);
                data.putExtra("itemPosition", pos);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        text = getIntent().getStringExtra("itemName");
        pos = getIntent().getIntExtra("itemPosition", 0);
        editText.setText(text);
        editText.setSelection(text.length());
    }

}
