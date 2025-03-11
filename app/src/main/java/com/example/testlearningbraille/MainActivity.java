package com.example.testlearningbraille;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;



public class MainActivity extends AppCompatActivity {
    int sheet_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        ImageButton  Sheet_AE = findViewById(R.id.imageButton1);
        ImageButton  Sheet_FJ = findViewById(R.id.imageButton2);
        ImageButton  Sheet_KO = findViewById(R.id.imageButton3);
        ImageButton  Sheet_PT = findViewById(R.id.imageButton4);
        ImageButton  Sheet_UZ = findViewById(R.id.imageButton5);
        ImageButton  Sheet_stressed_vowel = findViewById(R.id.imageButton6);
        ImageButton Sheet_punctuation_symbols= findViewById(R.id.imageButton7);

        Intent intent0 = new Intent(this,config_exp.class);

        // start locution
        MediaPlayer mp;
        mp = MediaPlayer.create(this, R.raw.seleccion);
        mp.start();

        // wait locution
        voice  wait = new voice();
        wait.start();

     Sheet_AE.setOnClickListener(
             new View.OnClickListener() {
                 public void onClick(View view) {
                     if (wait.stop) {
                         sheet_select = 1;
                         intent0.putExtra("sheet_select", sheet_select);
                         startActivity(intent0);
                     }
                 }
             });
     Sheet_FJ.setOnClickListener(
             new View.OnClickListener() {
                 public void onClick(View view) {
                     if (wait.stop) {
                         sheet_select = 2;
                         intent0.putExtra("sheet_select", sheet_select);
                         startActivity(intent0);
                     }
                 }
             });
     Sheet_KO.setOnClickListener(
             new View.OnClickListener() {
                 public void onClick(View view) {
                     sheet_select = 3;
                     intent0.putExtra("sheet_select", sheet_select);
                     startActivity(intent0);
                 }
             });

     Sheet_PT.setOnClickListener(
             new View.OnClickListener() {
                 public void onClick(View view) {
                     if (wait.stop) {
                         sheet_select = 4;
                         intent0.putExtra("sheet_select", sheet_select);
                         startActivity(intent0);
                     }
                 }
             });

     Sheet_UZ.setOnClickListener(
             new View.OnClickListener() {
                 public void onClick(View view) {
                     if (wait.stop) {
                         sheet_select = 5;
                         intent0.putExtra("sheet_select", sheet_select);
                         startActivity(intent0);
                     }
                 }
             });

     Sheet_stressed_vowel.setOnClickListener(
             new View.OnClickListener() {
                 public void onClick(View view) {
                     if (wait.stop) {
                         sheet_select = 6;
                         intent0.putExtra("sheet_select", sheet_select);
                         startActivity(intent0);
                     }
                 }
             });

     Sheet_punctuation_symbols.setOnClickListener(
             new View.OnClickListener() {
                 public void onClick(View view) {
                     if (wait.stop) {
                         sheet_select = 7;
                         intent0.putExtra("sheet_select", sheet_select);
                         startActivity(intent0);
                     }
                 }
             });
    }
    }