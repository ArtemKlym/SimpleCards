package com.example.simplecards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.example.simplecards.database.MyDataBaseHelper;
import com.example.simplecards.recycleradapter.CustomAdapter;

import java.util.ArrayList;

public class ShowCardsActivity extends AppCompatActivity {

    private MyDataBaseHelper myDB;
    private ArrayList<String> origin_word, translated_word;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cards);

        initViews();

        storeDataInArrays();

        //Set adapter to RecyclerView
        adapter = new CustomAdapter(ShowCardsActivity.this,origin_word,translated_word);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowCardsActivity.this));

    }

    //Show menu in ShowCardsActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.show_cards_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Get data from myDB and store it in arrays
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllWordsFromDB();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                origin_word.add(cursor.getString(1));
                translated_word.add(cursor.getString(2));
            }
        }
    }

    private void initViews() {
        recyclerView = findViewById(R.id.showCardsRecView);

        origin_word = new ArrayList<>();
        translated_word = new ArrayList<>();

        myDB = new MyDataBaseHelper(ShowCardsActivity.this);
    }
}