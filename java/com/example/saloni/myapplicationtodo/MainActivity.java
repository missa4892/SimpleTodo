package com.example.saloni.myapplicationtodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity implements MainEditDlg.SaveItemListener {

    private ArrayList<String> todoItems;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private EditText etEditText;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        etEditText = (EditText) findViewById(R.id.etTextItem);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                todoItems.remove(i);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                //get the item clicked
                String listViewText = (String)adapterView.getItemAtPosition(i);
                FragmentManager fm = getSupportFragmentManager();
                MainEditDlg editDlg = MainEditDlg.newInstance("Some Title");


                Bundle bundle = new Bundle();
                bundle.putString("editText", listViewText);
                bundle.putInt("selectedItemId", i);
                editDlg.setArguments(bundle);
                editDlg.show(fm, "fragment_edit_item");
                //Intent intent = new Intent(MainActivity.this, MainEditActivity.class);
//                Intent intent = new Intent(MainActivity.this, MainEditDlg.class);
//                intent.putExtra("editText", listViewText);
//                intent.putExtra("selectedItemId", i);
//                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onFinishEditDlg(String inputText, int position) {
        todoItems.remove(position);
        if (!inputText.isEmpty()) {
            //only add back if not empty
            todoItems.add(position, inputText);
        }
        itemsAdapter.notifyDataSetChanged();
        writeItems();
        // Toast the name to display temporarily on screen
        Toast.makeText(this, R.string.updatedListText, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String editedText = data.getExtras().getString("editedText");
            int selectedItemId = data.getExtras().getInt("selectedItemId", 0);
            todoItems.remove(selectedItemId);
            if (!editedText.isEmpty()) {
                //only add back if not empty
                todoItems.add(selectedItemId, editedText);
            }
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            // Toast the name to display temporarily on screen
            Toast.makeText(this, R.string.updatedListText, Toast.LENGTH_SHORT).show();
        }
    }

    public void populateArrayItems() {
        //todoItems = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }


    public void onAddItem(View view) {
        itemsAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
        }
    }
}
