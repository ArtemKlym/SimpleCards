package com.example.simplecards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
                startActivity(new Intent(MainActivity.this,ShowCardsActivity.class));
                return true;
            }
        });

    }

    private void initViews() {
        btnAdd = findViewById(R.id.btnAdd);
        btnStart = findViewById(R.id.btnStart);

        bottomAppBar = findViewById(R.id.bottomAppBarId);
    }
}