package com.example.testlearningbraille;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class config_exp extends AppCompatActivity {

/*    public void onBackPressed() {
        super.onBackPressed();

        finish();

    } */

    //************************************
    // Control Writing into file *.csv
    //********************************************
    String file_out ="";
    String path_braille = "Braille";

    int selected_test = 0;
    int Status_test = 0;
    int test_line =0;
  //  String file_out;
    String Selected_test;

    private TextView textView_file1;
    private TextView textView_file2;

    EditText edit_file;

    int sheet_selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_config_exp);

        textView_file1 = findViewById(R.id.textView14);
        textView_file2= findViewById(R.id.textView15);
        edit_file = findViewById(R.id.editTextTextPersonName);
        View Create_file = findViewById(R.id.imageView);

        Create_file.setVisibility(View.VISIBLE);

        // Entrada de datos externos de la pantalla de configuración
        Bundle datos = this.getIntent().getExtras();
        sheet_selection = datos.getInt("sheet_select");

        ImageButton backscreen = findViewById(R.id.backscreen);
        backscreen.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        //     finishActivity;
                        onBackPressed();
                    }});


        // Type test variables creation
        RadioButton pin_Onom = findViewById(R.id.Test_V_Onom);
        RadioButton pin_Control= findViewById(R.id.Test_Control);
        RadioButton pin_Number = findViewById(R.id.Test_V_Number);

        //Init selected_test
        pin_Control.setEnabled(true);
        pin_Onom.setEnabled(true);
        pin_Number.setEnabled(true);

        pin_Onom.setChecked(true);
        selected_test = 1;
        Selected_test = "_O_";

        pin_Control.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_test= 0;
                        Selected_test = "_C_";


                    }});
        pin_Onom.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_test = 1;
                        Selected_test = "_O_";

                    }});
        pin_Number.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_test = 2;
                        Selected_test = "_N_";
                    }});
    }

    public void onClickCreateFile(View v) {
        file_out = edit_file.getText().toString() + Selected_test ; // Add test name

        // Create file *.csv
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = simpleDateFormat.format(new Date());
        file_out = file_out +  currentDateandTime + ".csv"; // Add current time to file name

        WriteFileTest File = new WriteFileTest();
        boolean bSuccess = File.Create_path_and_file(config_exp.this, path_braille,file_out);
        if (!bSuccess)
            return;

        Intent intent = null;
        switch (sheet_selection) {
            case 1:
                intent = new Intent(this, Sheet_AE.class);
                break;
            case 2:
                intent = new Intent(this, Sheet_FJ.class);
                break;
            case 3:
                intent = new Intent(this, Sheet_KO.class);
                break;
            case 4:
                intent = new Intent(this, Sheet_PT.class);
                break;
            case 5:
                intent = new Intent(this, Sheet_UZ.class);
                break;
            case 6:
                intent = new Intent(this, Sheet_stressed_vowel.class);
                break;
            case 7:
                intent = new Intent(this, Sheet_punctuation_symbols.class);
                break;
        }
        intent.putExtra("selected_test", selected_test);
        intent.putExtra("file_out",file_out);
        startActivity(intent);
    }
}