package com.example.saloni.myapplicationtodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainEditActivity extends AppCompatActivity {

    EditText editText;
    int selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editText = (EditText) findViewById(R.id.editText);

        String toBeEditedText = getIntent().getStringExtra("editText");
        selectedItemId = getIntent().getIntExtra("selectedItemId", 0);
        editText.setText(toBeEditedText);
        editText.setSelection(editText.getText().length()); //cursor to be at end of text
    }

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;

    public void onClickSave(View view) {
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("editedText", editText.getText().toString());
        data.putExtra("selectedItemId", selectedItemId);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
