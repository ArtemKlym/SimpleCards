package com.example.simplecards.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simplecards.R;
import com.example.simplecards.database.MyDataBaseHelper;

public class UpdateActivity extends AppCompatActivity {

    EditText originTxt,translatedTxt;
    Button updateBtn;
    MyDataBaseHelper myDB;

    String id, origin, translated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        initViews();

        getIntentData();

        updateBtn.setOnClickListener(view -> {
            if(myDB.updateData(id.trim(),originTxt.getText().toString().trim(),translatedTxt.getText().toString().trim())){
                Toast.makeText(UpdateActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(UpdateActivity.this, "Fail to update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("origin") && getIntent().hasExtra("translated")){
            id = getIntent().getStringExtra("id");
            origin = getIntent().getStringExtra("origin");
            translated = getIntent().getStringExtra("translated");

            //Setting strings to EditText
            originTxt.setText(origin);
            translatedTxt.setText(translated);
        }
    }

    private void initViews() {
        originTxt = findViewById(R.id.originEditText);
        translatedTxt = findViewById(R.id.translatedEditText);

        updateBtn = findViewById(R.id.btnUpdateWords);

        myDB = new MyDataBaseHelper(UpdateActivity.this);
    }
}