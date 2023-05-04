package com.example.projectgekko_finalversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private String selectedDate, selectedEditTextWeight, selectedEditTextHeight, selectedEditTextComm, id;
    private Boolean selectedCheckBoxFeeding, selectedCheckBoxToilet, selectedCheckBoxMolt, selectedCheckBoxOvulation;
    private DatabaseReference mDataBase;
    private String USER_KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.calendar_name);
        Intent i = getIntent();
        USER_KEY = i.getStringExtra("key");
        if (USER_KEY == null) USER_KEY = "User";
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY).child("dates");

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {


            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                selectedDate = new StringBuilder().append(mMonth + 1)
                        .append("-").append(mDay).append("-").append(mYear)
                        .append(" ").toString();
                getDataFromDB();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra("date",selectedDate);
        intent.putExtra("key",USER_KEY);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityCalendarEvent.class);
        intent.putExtra("date", selectedDate);
        intent.putExtra("key",USER_KEY);
        startActivity(intent);
    }
    private void getDataFromDB(){
        selectedCheckBoxFeeding = false;
        selectedCheckBoxMolt = false;
        selectedCheckBoxToilet = false;
        selectedCheckBoxOvulation = false;
        selectedEditTextWeight = "";
        selectedEditTextHeight = "";
        selectedEditTextComm = "";
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    assert user != null;

                    if((user.id).equals(selectedDate)){
                        selectedCheckBoxFeeding = user.checkBoxFeeding;
                        selectedCheckBoxMolt = user.checkBoxMolt;
                        selectedCheckBoxToilet = user.checkBoxToilet;
                        selectedCheckBoxOvulation = user.checkBoxOvulation;
                        selectedEditTextWeight = user.editTextWeight;
                        selectedEditTextHeight = user.editTextHeight;
                        selectedEditTextComm = user.editTextComm;

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
       mDataBase.addValueEventListener(vListener);

    }

    public void onClickRead(View view) {
        Intent intent = new Intent(MainActivity.this, ReadActivity.class);
        intent.putExtra("selectedCheckBoxFeeding", selectedCheckBoxFeeding);
        intent.putExtra("selectedCheckBoxMolt", selectedCheckBoxMolt);
        intent.putExtra("selectedCheckBoxToilet", selectedCheckBoxToilet);
        intent.putExtra("selectedCheckBoxOvulation", selectedCheckBoxOvulation);
        intent.putExtra("selectedEditTextWeight", selectedEditTextWeight);
        intent.putExtra("selectedEditTextHeight", selectedEditTextHeight);
        intent.putExtra("selectedEditTextComm",selectedEditTextComm);
        intent.putExtra("date", selectedDate);
        intent.putExtra("key", USER_KEY);
        startActivity(intent);
    }
}