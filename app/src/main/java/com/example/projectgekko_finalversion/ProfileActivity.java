package com.example.projectgekko_finalversion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {



    ImageView imageView;
    Button button;
    private EditText mEditText;
    TextView currentDateTime;
    private final static String FILENAME = "sample.txt"; // имя файла
    Calendar dateAndTime= Calendar.getInstance();
    SharedPreferences sharedpreferences;
    private String USER_KEY;
    private DatabaseReference mDataBaseP;
    String pathData, date, editText;
    String mDate,mPath,mineedittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.profile_name);
        Intent i = getIntent();
        USER_KEY = i.getStringExtra("key");
        mEditText = findViewById(R.id.editText);
        imageView = findViewById(R.id.image);
        button = findViewById(R.id.buttonImage);
        currentDateTime = findViewById(R.id.currentDateTime);
        mDataBaseP = FirebaseDatabase.getInstance().getReference(USER_KEY).child("profile");
        sharedpreferences = getSharedPreferences("app_data", Context.MODE_PRIVATE);



        getDataFromDB();




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ProfileActivity.this)
                        .cropSquare()  			//Crop image(Optional), Check Customization for more option
                        // .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(400, 400)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        pathData = uri.toString();
        imageView.setImageURI(uri);
    }









    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {


        new DatePickerDialog(ProfileActivity.this, d,
                dateAndTime.get(Calendar.YEAR),

                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();

    }

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            SharedPreferences.Editor editor = sharedpreferences.edit();


            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);



            int year1 = dateAndTime.get(Calendar.YEAR);
            int month = dateAndTime.get(Calendar.MONTH)+1;
            int dayOfMonth1 = dateAndTime.get(Calendar.DAY_OF_MONTH);

            editor.putInt("year", year1).apply();
            editor.putInt("month", month).apply();
            editor.putInt("day", dayOfMonth1).apply();
            setInitialDateTime();
        }
    };

    private void setInitialDateTime() {
        int savedYear = sharedpreferences.getInt("year",0);
        int savedMonth = sharedpreferences.getInt("month",0);
        int savedDay = sharedpreferences.getInt("day",0);
        date = "Дата рождения: "+savedDay+"."+savedMonth+"."+savedYear;
        currentDateTime.setText(date);
    }

    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    UserProfile userProfile = ds.getValue(UserProfile.class);
                    assert userProfile !=null;
                    mineedittext = userProfile.mEditText;
                    mPath = userProfile.path;

                    mDate = userProfile.date;

                }
                if (mPath == null) mPath = "file:///storage/emulated/0/Android/data/com.example.projectgekko_finalversion/files/DCIM/IMG_20230226_120929175.jpg";
                String saved = mPath;
                Uri imageUri = Uri.parse(saved);
                imageView.setImageURI(imageUri);

                mEditText.setText(mineedittext);

                setInitialDateTime();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        mDataBaseP.addValueEventListener(vListener);

    }

    public void onClickSave(View view){
        String myDate = date;
        String myPath = pathData;
        String myEditText = mEditText.getText().toString();
        UserProfile userProfile = new UserProfile(myPath,myEditText,myDate);
        mDataBaseP.push().setValue(userProfile);
    }
}


