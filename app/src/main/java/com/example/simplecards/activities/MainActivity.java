package com.example.simplecards.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.simplecards.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

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
                if (isConnected()) {
                    startActivity(new Intent(MainActivity.this, AddWordActivity.class));
                } else {
                    showNoInternet();
                }
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
            if(isConnected()) {
                startActivity(new Intent(MainActivity.this,LearnCardsActivity.class));
            }else{
                showNoInternet();
            }
        });
    }


    private void showNoInternet() {
        Snackbar snackbar = Snackbar.make(bottomAppBar, R.string.no_connection, BaseTransientBottomBar.LENGTH_LONG);
        View view = snackbar.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(
                params.leftMargin+0,
                params.topMargin,
                params.rightMargin+0,
                params.bottomMargin+240
        );
        view.setLayoutParams(params);
        snackbar.show();
    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void initViews() {
        btnAdd = findViewById(R.id.btnAdd);
        btnStart = findViewById(R.id.btnStart);

        bottomAppBar = findViewById(R.id.bottomAppBarId);
    }
}