package com.example.projectgekko_finalversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReadActivity extends AppCompatActivity {
    private CheckBox checkBoxFeeding, checkBoxToilet,checkBoxMolt,checkBoxOvulation;
    private TextView editTextWeight, editTextHeight, editTextComm;
    private DatabaseReference mDataBase;
    private String USER_KEY;
    private String dates = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Intent i = getIntent();
        USER_KEY = i.getStringExtra("key");
        init();
        dates = getIntent().getExtras().getString("date");
        setTitle(dates);


        checkBoxFeeding.setChecked(getIntent().getExtras().getBoolean("selectedCheckBoxFeeding"));
        checkBoxMolt.setChecked(getIntent().getExtras().getBoolean("selectedCheckBoxMolt"));
        checkBoxToilet.setChecked(getIntent().getExtras().getBoolean("selectedCheckBoxToilet"));
        checkBoxOvulation.setChecked(getIntent().getExtras().getBoolean("selectedCheckBoxOvulation"));
        editTextWeight.setText(getIntent().getExtras().getString("selectedEditTextWeight"));
        editTextHeight.setText(getIntent().getExtras().getString("selectedEditTextHeight"));
        editTextComm.setText(getIntent().getExtras().getString("selectedEditTextComm"));
    }
    private void init(){
        checkBoxFeeding = findViewById(R.id.checkBoxFeeding);
        checkBoxToilet = findViewById(R.id.checkBoxToilet);
        checkBoxMolt = findViewById(R.id.checkBoxMolt);
        checkBoxOvulation = findViewById(R.id.checkBoxOvulation);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextComm = findViewById(R.id.editTextComm);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }
}