package com.example.simplecards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.simplecards.database.MyDataBaseHelper;
import com.example.simplecards.swipeadpter.SwipeAdapter;
import com.yalantis.library.Koloda;

import java.util.ArrayList;

public class LearnCardsActivity extends AppCompatActivity {

    private SwipeAdapter swipeAdapter;
    private ArrayList<String> origin,translated;
    private MyDataBaseHelper myDB;
    Koloda koloda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_cards);

        initItems();

        storeDataInArrays();

        //Initialize adaptor & set it to koloda
        swipeAdapter = new SwipeAdapter(this,origin,translated);
        koloda.setAdapter(swipeAdapter);

    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllWordsFromDB();

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                origin.add(cursor.getString(1));
                translated.add(cursor.getString(2));
            }
        }
    }

    private void initItems() {
        koloda = findViewById(R.id.koloda);

        origin = new ArrayList<>();
        translated = new ArrayList<>();

        myDB = new MyDataBaseHelper(LearnCardsActivity.this);
    }
}