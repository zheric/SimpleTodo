package com.example.hang.simpletodo;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    public void onDeleteItem(int pos, String text){
        items.remove(pos);
        items.add(pos, text);
        itemsAdapter.notifyDataSetChanged();
        writeItems();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                        //intent.putExtra("itemName", lvItems.getItemAtPosition(position).toString());
                        //intent.putExtra("itemPosition", position);
                        //startActivityForResult(intent, REQUEST_CODE);

                        showEditDialog(lvItems.getItemAtPosition(position).toString(), position);
                    }
                });
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"Todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException ie){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"Todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String text = data.getExtras().getString("itemName");
            int pos = data.getExtras().getInt("itemPosition", 0);
            items.remove(pos);
            items.add(pos, text);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void showEditDialog(String text, int pos) {
        FragmentManager fm = getSupportFragmentManager();
        DialogEdit editNameDialog = DialogEdit.newInstance(text, pos, this);
        editNameDialog.show(fm, "fragment_edit_name");
    }


    private int REQUEST_CODE = 20;
}
