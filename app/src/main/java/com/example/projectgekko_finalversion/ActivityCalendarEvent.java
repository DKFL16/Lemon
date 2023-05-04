package com.example.projectgekko_finalversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityCalendarEvent extends AppCompatActivity {
    private CheckBox checkBoxFeeding, checkBoxToilet,checkBoxMolt,checkBoxOvulation;
    private EditText editTextWeight, editTextHeight, editTextComm;
    private DatabaseReference mDataBase;
    private String USER_KEY;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event);
        Intent i = getIntent();
        USER_KEY = i.getStringExtra("key");
        init();
        String dates = getIntent().getExtras().getString("date");
        setTitle(dates);

    }
    private void init(){

        checkBoxFeeding = findViewById(R.id.checkBoxFeeding);
        checkBoxToilet = findViewById(R.id.checkBoxToilet);
        checkBoxMolt = findViewById(R.id.checkBoxMolt);
        checkBoxOvulation = findViewById(R.id.checkBoxOvulation);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextComm = findViewById(R.id.editTextComm);
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY).child("dates");
    }
    protected void onPause() {
        super.onPause();
        date = getIntent().getExtras().getString("date");
        String id = date;
        String mEditTextWeight = editTextWeight.getText().toString();
        String mEditTextHeight = editTextHeight.getText().toString();
        String mEditTextComm = editTextComm.getText().toString();
        String date = getIntent().getExtras().getString("date");
        Boolean mCheckBoxFeeding = checkBoxFeeding.isChecked();
        Boolean mCheckBoxToilet = checkBoxToilet.isChecked();
        Boolean mCheckBoxMolt = checkBoxMolt.isChecked();
        Boolean mCheckBoxOvulation = checkBoxOvulation.isChecked();

        User newUser = new User(id,mEditTextWeight,mEditTextHeight,mEditTextComm,mCheckBoxFeeding,mCheckBoxMolt,mCheckBoxToilet,mCheckBoxOvulation, date);
        mDataBase.push().setValue(newUser);
    }

}
