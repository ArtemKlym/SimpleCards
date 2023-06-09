package com.example.simplecards.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.simplecards.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private Button btnStart;
    private BottomAppBar bottomAppBar;
    FloatingActionButton btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        //Inflate the menu resource file
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,bottomAppBar.getMenu());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddWordActivity.class));
            }
        });

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_cards_ic:
                        startActivity(new Intent(MainActivity.this,ShowCardsActivity.class));
                        break;
                }
                return true;
            }
        });

        btnStart.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this,LearnCardsActivity.class));
        });
    }

    private void initViews() {
        btnAdd = findViewById(R.id.btnAdd);
        btnStart = findViewById(R.id.btnStart);

        bottomAppBar = findViewById(R.id.bottomAppBarId);
    }
}