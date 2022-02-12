package com.android.gdsc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity{

    int night_mode;
    SharedPreferences mypref;
    SharedPreferences.Editor myedit;
    TextView text_theme;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        text_theme = findViewById(R.id.settings_text);
        constraintLayout = findViewById(R.id.spinnerContainer);
        mypref = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        night_mode = mypref.getInt("Theme", 0);
        changeText();
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void openDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setSingleChoiceItems(R.array.theme, night_mode, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                night_mode = which;
                changeText();
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Button negbtn = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negbtn.setTextColor(Color.parseColor("#ff33b5e5"));
    }

    private  void changeText(){
        if(night_mode==0){
            text_theme.setText("System Default");
        }
        if(night_mode==1){
            text_theme.setText("Light");
        }
        if(night_mode==2){
            text_theme.setText("Dark");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        myedit = mypref.edit();
        if(night_mode==0){
            myedit.putInt("Theme", 0);
            myedit.apply();
            Log.i("Theme", "Default");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        if(night_mode==1){
            myedit.putInt("Theme", 1);
            myedit.apply();
            Log.i("Theme", "Light");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
        if(night_mode==2){
            myedit.putInt("Theme", 2);
            myedit.apply();
            Log.i("Theme", "Dark");
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }
}