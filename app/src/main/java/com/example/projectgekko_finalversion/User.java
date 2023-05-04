package com.example.projectgekko_finalversion;

public class User {
    public String id, editTextWeight, editTextHeight, editTextComm, dateSaved;
    public Boolean checkBoxFeeding,checkBoxMolt,checkBoxToilet,checkBoxOvulation;

    public User(){

    }

    public User(String id, String editTextWeight, String editTextHeight, String editTextComm, Boolean checkBoxFeeding,
                Boolean checkBoxMolt, Boolean checkBoxToilet, Boolean checkBoxOvulation, String dateSaved) {
        this.id = id;
        this.editTextWeight = editTextWeight;
        this.editTextHeight = editTextHeight;
        this.editTextComm = editTextComm;
        this.checkBoxFeeding = checkBoxFeeding;
        this.checkBoxMolt = checkBoxMolt;
        this.checkBoxToilet = checkBoxToilet;
        this.checkBoxOvulation = checkBoxOvulation;
        this.dateSaved = dateSaved;
    }
}
