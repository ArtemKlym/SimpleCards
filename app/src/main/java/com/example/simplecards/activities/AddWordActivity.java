package com.example.simplecards.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simplecards.R;
import com.example.simplecards.database.MyDataBaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.util.concurrent.Executor;

@SuppressWarnings("ALL")
public class AddWordActivity extends AppCompatActivity {

    private EditText originText, translatedText;
    private Spinner fromSpinner, toSpinner;
    private Button btnAdd, btnTranslate;
    private TextView translateTextView;
    private String[] fromLanguages = {"From","English","Spain","German","Ukrainian","Russian","Japanese","Mandarin","Hindi","Bengali","French","Italian","Polish"};
    private String[] toLanguages = {"TO","English","Spain","German","Ukrainian","Russian","Japanese","Mandarin","Hindi","Bengali","French","Italian","Polish"};

    int fromLangCode, toLangCode = 0;

    private MyDataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        initViews();

        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fromLangCode = getLangCode(fromLanguages[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //TODO: Create adapter for 1st Spinner
        ArrayAdapter fromAdapter = new ArrayAdapter(this,R.layout.spinner_item, fromLanguages);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);


        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toLangCode = getLangCode(toLanguages[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //TODO: Create adapter for 2nd Spinner
        ArrayAdapter toAdapter = new ArrayAdapter(this, R.layout.spinner_item, toLanguages);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(toAdapter);



        //TODO: Change content of editText in real time
        originText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                translateText(fromLangCode,toLangCode,editable);
            }
        });

        //TODO: Add to Database
        btnAdd.setOnClickListener(v ->{
            myDB.addRawToDataBase(originText.getText().toString().trim(),translatedText.getText().toString().trim());
        });
    }


    //TODO: Method that translate text
    private void translateText(int fromLangCode, int toLangCode, Editable editable){
        translateTextView.setText("Downloading Translate Modal...");
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions.Builder()
                .setSourceLanguage(fromLangCode)
                .setTargetLanguage(toLangCode)
                .build();

        FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().build();

        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                translateTextView.setText("");
                translator.translate(editable.toString()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        translatedText.setText(s.trim());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddWordActivity.this, "Fail to translate: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddWordActivity.this, "Fail to download model: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private int getLangCode(String s) {
        int languageCode = 0;
        switch(s) {
            case "English":
                languageCode = FirebaseTranslateLanguage.EN;break;
            case "Spain":
                languageCode = FirebaseTranslateLanguage.ES;break;
            case "German":
                languageCode = FirebaseTranslateLanguage.DE;break;
            case "Ukrainian":
                languageCode = FirebaseTranslateLanguage.UK;break;
            case "Russian":
                languageCode = FirebaseTranslateLanguage.RU;break;
            case "Japanese":
                languageCode = FirebaseTranslateLanguage.JA;break;
            case "Chinese":
                languageCode = FirebaseTranslateLanguage.ZH;break;
            case "Hindi":
                languageCode = FirebaseTranslateLanguage.HI;break;
            case "Bengali":
                languageCode = FirebaseTranslateLanguage.BN;break;
            case "French":
                languageCode = FirebaseTranslateLanguage.FR;break;
            case "Italian":
                languageCode = FirebaseTranslateLanguage.IT;break;
            case "Polish":
                languageCode = FirebaseTranslateLanguage.PL;break;
        }

        return languageCode;
    }

    private void initViews() {
        translateTextView = findViewById(R.id.txtViewLoad);

        originText = findViewById(R.id.edtTxtOriginalWord);
        translatedText = findViewById(R.id.edtTxtTranslatedWord);

        fromSpinner = findViewById(R.id.spinnerSelectLang1);
        toSpinner = findViewById(R.id.spinnerSelectLang2);

        btnAdd = findViewById(R.id.btnAddNewWord);
        myDB = new MyDataBaseHelper(AddWordActivity.this);
    }
}