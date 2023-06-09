package com.example.simplecards.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.simplecards.R;
import com.example.simplecards.database.MyDataBaseHelper;
import com.example.simplecards.swipeadpter.SwipeAdapter;
import com.yalantis.library.Koloda;
import com.yalantis.library.KolodaListener;

import java.util.ArrayList;

public class LearnCardsActivity extends AppCompatActivity {

    private SwipeAdapter swipeAdapter;
    private ArrayList<String> origin,translated;
    private MyDataBaseHelper myDB;

    private RelativeLayout showTextTop, showTextBottom;
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

        koloda.setKolodaListener(new KolodaListener() {

            @Override
            public void onNewTopCard(int i) {

            }

            @Override
            public void onCardDrag(int i, @NonNull View view, float v) {
                showTextBottom.setVisibility(View.VISIBLE);
                showTextTop.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCardSwipedLeft(int i) {
                String originSwipe = origin.get(i+1);
                String translateSwipe = translated.get(i+1);

                swipeAdapter.addSwiped(originSwipe,translateSwipe);
                swipeAdapter.notifyDataSetChanged();

                showTextBottom.setVisibility(View.GONE);
                showTextTop.setVisibility(View.GONE);
            }

            @Override
            public void onCardSwipedRight(int i) {
                showTextBottom.setVisibility(View.GONE);
                showTextTop.setVisibility(View.GONE);
            }

            @Override
            public void onClickRight(int i) {

            }

            @Override
            public void onClickLeft(int i) {

            }

            @Override
            public void onCardSingleTap(int i) {
                int tap = 0;
                if(showTextBottom.getVisibility() != View.VISIBLE && showTextTop.getVisibility() != View.VISIBLE && tap % 2==0){
                    showTextBottom.setVisibility(View.VISIBLE);
                    showTextTop.setVisibility(View.VISIBLE);
                    tap++;
                }else{
                    showTextBottom.setVisibility(View.GONE);
                    showTextTop.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCardDoubleTap(int i) {

            }

            @Override
            public void onCardLongPress(int i) {

            }

            @Override
            public void onEmptyDeck() {

            }
        });


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

        showTextBottom = findViewById(R.id.rlShowClueBottom);
        showTextTop = findViewById(R.id.rlShowClueTop);

        origin = new ArrayList<>();
        translated = new ArrayList<>();

        myDB = new MyDataBaseHelper(LearnCardsActivity.this);
    }
}