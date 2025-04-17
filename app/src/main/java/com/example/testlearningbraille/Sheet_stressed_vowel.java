package com.example.testlearningbraille;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;

public class Sheet_stressed_vowel extends AppCompatActivity {

    /*********************************  ********************************************************

     Cycle of execution states

     *.csv File Creation          :  21-20-
     Development of the experiment:  0 - 1- 2 - 3 - 4- 5 - 6- 7 - 8- 9 - 0 ------
                                             (Even state = Training Period)
                                             (Odd state  = Testing Period)

     *********************************  **********************************************************/


    //*******************************
    // Double Click Control
    //*********************************************

    long mLastTime=System.currentTimeMillis();
    long mCurTime=System.currentTimeMillis();
    boolean flag_db = false;


    //************************************
    // Control Writing into file *.csv
    //********************************************
    String file_out ="";
    String path_braille = "Braille";

    // Test control variables
    boolean flag_sel = false;
    boolean flag_ok= false;
    boolean flag_elec = false;
    char  Symbol;
    char  Symbol_elec;
    int  Sc = 0;
    int  Er =0;
    int  test_line = 0;
    int selected_test = 0;
    int selected_mode =0;
    MediaPlayer mp;


    //*************************************************
    //  Encapsulation functions
    //*************************************************

    // Common Wait
    public void Wait( float time ) {

        try {
            //Retardo
            Thread.sleep((long) time*1000);
        } catch (Exception e) {

        }
    }




    public boolean Double_Click(char character){

        // Response disable
        boolean disable_button_resc= false;

        // Current time capture
        mCurTime= System.currentTimeMillis();

        if (flag_db & (mCurTime - mLastTime < 300)) {
            mCurTime = System.currentTimeMillis();
            mLastTime = System.currentTimeMillis();
            flag_db = false;

            // Active line test
            if ((Status_test == 1) | (Status_test == 3) | (Status_test == 5) | (Status_test == 7) | (Status_test == 9))
            {
                // Selected symbol on active test line
                if (flag_sel & flag_elec) {
                    flag_sel= false;
                    flag_elec = false;

                    // Succes , not success
                    if (Symbol == character) {
                        Sc++;
                        flag_ok = true;
                        Sound(20);
                        disable_button_resc= true;

                    } else {
                        Er++;
                        flag_ok = false;
                        Sound(21);
                    }
                    //  Cases
                    WriteFileTest File = new WriteFileTest();
                    switch (Sc + Er) {

                        case 1:
                            if (flag_ok) {

                                File.Write_Text_File( Sheet_stressed_vowel.this,path_braille,file_out, Symbol + ";" );  // Éxito
                            }else{
                                File.Write_Text_File( Sheet_stressed_vowel.this,path_braille,file_out,Symbol + "-"+ Symbol_elec +";"); // Error
                            }

                            break;
                        case 2:
                            if (flag_ok) {

                                File.Write_Text_File( Sheet_stressed_vowel.this,path_braille,file_out,Symbol + ";" );  // Éxito
                            }else{
                                File.Write_Text_File(Sheet_stressed_vowel.this, path_braille,file_out, Symbol + "-"+ Symbol_elec +";"); // Error
                            }
                            break;
                        case 3:
                            if (flag_ok) {

                                File.Write_Text_File(Sheet_stressed_vowel.this, path_braille,file_out,Symbol + ";");  // Éxito
                            }else{
                                File.Write_Text_File( Sheet_stressed_vowel.this,path_braille,file_out, Symbol + "-"+ Symbol_elec +";"); // Error
                            }
                            break;

                        case 4:
                            if (flag_ok) {

                                File.Write_Text_File(Sheet_stressed_vowel.this, path_braille,file_out,Symbol + ";" );  // Éxito
                            }else{
                                File.Write_Text_File( Sheet_stressed_vowel.this,path_braille,file_out, Symbol + "-"+ Symbol_elec +";"); // Error
                            }


                            break;
                        case 5:
                            if (Sc == 5)  {
                                if(flag_ok)

                                    File.Write_Text_File(Sheet_stressed_vowel.this,path_braille,file_out,Symbol + ";\n" ); //Final Test
                                secondLeft = 0;
                                On = false;
                                Activ_test = false;
                                textView2.setText("Fin del test");
                                Sound(36);

                            }else {
                                File.Write_Text_File( Sheet_stressed_vowel.this,path_braille,file_out, Symbol + "-"+ Symbol_elec +";\n"); // línea no completada


                                Sound(35);
                                secondLeft = 0;
                                // On=false;

                            }


                            break;

                    }

                }

            }

        }

        // Reset Double click
        flag_db = true;
        mLastTime= System.currentTimeMillis();
        return disable_button_resc;
    }
    // Function locutions

    public void Sound( int code) {


        switch (code) {
            case 1:

                mp = MediaPlayer.create(this, R.raw.a_pantera_rosa_acento);
                mp.start();
                break;

            case 2:

                mp = MediaPlayer.create(this, R.raw.e_maquina_de_escribir_acento);
                mp.start();
                break;

            case 3:

                mp = MediaPlayer.create(this, R.raw.i_campanadas_iglesia_acento);
                mp.start();
                break;

            case 4:

                mp = MediaPlayer.create(this, R.raw.o_odisea_encuentro_3_fase_acento);
                mp.start();
                break;

            case 5:

                mp = MediaPlayer.create(this, R.raw.u_lular_buho_acento);
                mp.start();
                break;
            case 6:

                mp = MediaPlayer.create(this, R.raw.acento);
                mp.start();
                break;

            case 11:

                mp = MediaPlayer.create(this, R.raw.alphabet_a);
                mp.start();
                break;

            case 12:

                mp = MediaPlayer.create(this, R.raw.alphabet_e);
                mp.start();
                break;

            case 13:

                mp = MediaPlayer.create(this, R.raw.alphabet_i);
                mp.start();
                break;

            case 14:

                mp = MediaPlayer.create(this, R.raw.alphabet_o);
                mp.start();
                break;

            case 15:

                mp = MediaPlayer.create(this, R.raw.alphabet_u);
                mp.start();
                break;

            case 20:

                mp = MediaPlayer.create(this, R.raw.loc_acierto);
                mp.start();
                break;

            case 21:

                mp = MediaPlayer.create(this, R.raw.loc_error);
                mp.start();
                break;

            case 22:

                mp = MediaPlayer.create(this, R.raw.loc_final_test);
                mp.start();
                break;


            case 23:

                mp = MediaPlayer.create(this, R.raw.sheet_stressed_vowels);
                mp.start();
                break;
            case 24:

                mp = MediaPlayer.create(this, R.raw.primer_entrenamiento);
                mp.start();
                break;

            case 25:

                mp = MediaPlayer.create(this, R.raw.primera_linea_test);
                mp.start();
                break;
            case 26:

                mp = MediaPlayer.create(this, R.raw.segundo_entrenamiento);
                mp.start();
                break;
            case 27:

                mp = MediaPlayer.create(this, R.raw.segunda_linea_test);
                mp.start();
                break;
            case 28:

                mp = MediaPlayer.create(this, R.raw.tercer_entrenamiento);
                mp.start();
                break;
            case 29:

                mp = MediaPlayer.create(this, R.raw.tercera_linea_test);
                mp.start();
                break;
            case 30:

                mp = MediaPlayer.create(this, R.raw.cuarto_entrenamiento);
                mp.start();
                break;
            case 31:

                mp = MediaPlayer.create(this, R.raw.cuarta_linea_test);
                mp.start();
                break;
            case 32:

                mp = MediaPlayer.create(this, R.raw.quinto_entrenamiento);
                mp.start();
                break;
            case 33:

                mp = MediaPlayer.create(this, R.raw.quinta_linea_test);
                mp.start();
                break;
            case 34:

                mp = MediaPlayer.create(this, R.raw.idprueba);
                mp.start();
                break;
            case 35:

                mp = MediaPlayer.create(this, R.raw.linea_er);
                mp.start();
                break;
            case 36:

                mp = MediaPlayer.create(this, R.raw.linea_sc);
                mp.start();
                break;

            case 40:

                mp = MediaPlayer.create(this, R.raw.numbers_as);
                mp.start();
                break;
            case 41:

                mp = MediaPlayer.create(this, R.raw.numbers_es);
                mp.start();
                break;
            case 42:

                mp = MediaPlayer.create(this, R.raw.numbers_is);
                mp.start();
                break;
            case 43:

                mp = MediaPlayer.create(this, R.raw.numbers_os);
                mp.start();
                break;
            case 44:

                mp = MediaPlayer.create(this, R.raw.numbers_us);
                mp.start();
                break;

        }


    }


    //  Pattern notification ***

    public void Notification( String Text1, String Text2, int icono, long[] pattern)
    {
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

        //  int icono = R.mipmap.png1;
        Intent i = new Intent(Sheet_stressed_vowel.this, Message.class);
        PendingIntent pendingIntent;
     //   pendingIntent = PendingIntent.getActivity(Sheet_stressed_vowel.this, 0, i, 0);
        pendingIntent = PendingIntent.getActivity(Sheet_stressed_vowel.this, 0, i, PendingIntent.FLAG_IMMUTABLE);
        mBuilder = new NotificationCompat.Builder(getApplicationContext());
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(icono, 2);
        mBuilder.setContentTitle("Patrón: "+ Text1);
        mBuilder.setContentText(Text2);
        mBuilder.setVibrate(pattern);
        mBuilder.setAutoCancel(true);
        mNotifyMgr.notify(1, mBuilder.build());

    }


    //*************************************************
    //  TIMER
    //******************************************************************

    // Timer control
    boolean On = false;
    int secondLeft = 0;
    boolean Activ_test = false;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    private TextView textView_file1;
    private TextView textView_file2;


    // State & slot
    int Status_test = 0;
    int Time_slot = 120;
    boolean timerOn = false;

    Boolean  Tacton_trip = true;


    //New Timer Objects
    Timer timer = new Timer();
    Timer timerSlot1 = new Timer();


    // Overload TimeTask object

    TimerTask task = new TimerTask() {

        @Override
        public void run() {

            //Timer Thread
            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {

                @Override
                public void run() {


                    // TODO Auto-generated method stub

                    if (On & Activ_test) {
                        if(secondLeft>0) {
                            secondLeft--;
                        }
                        textView.setText("" + secondLeft);
                        if ((secondLeft < 1) & On) {

                            //textView2.setText("Fin del test");
                            if (Sc < 5 & (Status_test == 1|Status_test == 3|Status_test == 5|Status_test == 7|Status_test == 9))
                            {
                                //  WriteFileTest File = new WriteFileTest();
                                //  File.Write_Text_File(Sheet_stressed_vowel.this,path_braille,file_out,"Lin_err" + test_line +"\n");

                            }
                            secondLeft = Time_slot;
                            On = false;

                            //Cambio de estado Test

                            switch (Status_test) {
                                case 0:
                                    Status_test = 1;
                                    //textView3.setText(Status_test);
                                    break;
                                case 1:
                                    Status_test = 2;
                                    //textView3.setText(Status_test);
                                    break;
                                case 2:
                                    Status_test = 3;
                                    //textView3.setText(Status_test);
                                    break;
                                case 3:
                                    Status_test = 4;
                                    //textView3.setText(Status_test);
                                    break;
                                case 4:
                                    Status_test = 5;
                                    //textView3.setText(Status_test);
                                    break;
                                case 5:
                                    Status_test = 6;
                                    // textView3.setText(Status_test);
                                    break;
                                case 6:
                                    Status_test = 7;
                                    //textView3.setText(Status_test);
                                    break;
                                case 7:
                                    Status_test = 8;
                                    //textView3.setText(Status_test);
                                    break;
                                case 8:
                                    Status_test = 9;
                                    //textView3.setText(Status_test);
                                    break;
                                case 9:
                                    Status_test = 0;
                                    //textView3.setText(Status_test);
                                    break;

                            }

                        }
                    }
                }
            });
        }
    };

    // Flags trip vibrates
    boolean Symbol_aa = false;
    boolean Symbol_ee= false;
    boolean Symbol_ii= false;
    boolean Symbol_oo= false;
    boolean Symbol_uu= false;



    int count = 0;
    TimerTask task1 = new TimerTask() {
        @Override
        public void run() {

            // TODO Auto-generated method stub
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    //******  Symbol á  *****

                    if (Symbol_aa) {
                        count++;
                    }

                    // Training case
                    if (Symbol_aa & ((Status_test==0)||(Status_test==2)||(Status_test==4) || (Status_test==6)||(Status_test==8))) {

                        switch (selected_test) {
                            case 1:
                                    if (count == 4) {
                                        Notification("AA", "Tema - Pantera Rosa", R.mipmap.png1, Pattern.pattern("AA"));
                                        Tacton_trip = true;
                                        count = 0;
                                        Symbol_aa = false;
                                    }


                                break;
                            case 2:

                                    // Number list phrase
                                    if (count == 2) {
                                        Sound(40);
                                    }


                                    // Coded Pattern of number list phrase
                                    if (count == 4) {
                                        Notification("AA", "1-2-3-5-6", R.mipmap.png1, Pattern.pattern_number("AA"));
                                        Tacton_trip = true;
                                        count = 0;
                                        Symbol_aa = false;
                                    }


                                break;
                        }

                    }
                    // Experiment case

                    if ( (Symbol_aa) & ((Status_test==1)||(Status_test==3)||(Status_test==5) || (Status_test==7)||(Status_test==9)) )
                    {
                        switch (selected_test) {
                            case 1:
                                Notification("AA", "Tema - Pantera Rosa", R.mipmap.png1, Pattern.pattern("AA"));
                                Tacton_trip = true;
                                count = 0;
                                Symbol_aa = false;
                                break;
                            case 2 :
                                Notification("AA", "1-2-3-5-6", R.mipmap.png1, Pattern.pattern_number("AA"));
                                Tacton_trip = true;
                                count = 0;
                                Symbol_aa = false;
                                break;

                        }
                    }

                    //***** Symbol é ******
                    if (Symbol_ee) {
                        count++;
                    }
                    // Training case
                    if (Symbol_ee & ((Status_test==0)||(Status_test==2)||(Status_test==4) || (Status_test==6)||(Status_test==8))) {

                        switch (selected_test) {
                            case 1:
                                    if (count == 6) {
                                        Notification("EA", "Máquina de Escribir", R.mipmap.png2, Pattern.pattern("EA"));
                                        Tacton_trip = true;
                                        count = 0;
                                        Symbol_ee = false;
                                    }

                                break;
                            case 2:

                                    // Number list phrase
                                    if (count == 2) {
                                        Sound(41);
                                    }

                                    // Coded Pattern of number list phrase
                                    if (count == 4) {
                                        Notification("EA", "2-3-4-6", R.mipmap.png2, Pattern.pattern_number("EA"));
                                        Tacton_trip = true;
                                        count = 0;
                                        Symbol_ee = false;
                                    }
                                break;
                        }

                    }
                    // Experiment case

                    if ((Symbol_ee) & ((Status_test==1)||(Status_test==3)||(Status_test==5) || (Status_test==7)||(Status_test==9)) )
                    {
                        switch (selected_test) {
                            case 1:
                                Notification("EA", "Máquina de Escribir", R.mipmap.png2, Pattern.pattern("EA"));
                                Tacton_trip = true;
                                count = 0;
                                Symbol_ee = false;
                                break;
                            case 2 :
                                Notification("EA", "2-3-4-6", R.mipmap.png2, Pattern.pattern_number("EA"));
                                Tacton_trip = true;
                                count = 0;
                                Symbol_ee = false;
                                break;

                        }
                    }


                    // *******  Symbol í ****

                    if (Symbol_ii) {
                        count++;
                    }
                    // Training case
                    if (Symbol_ii & ((Status_test==0)||(Status_test==2)||(Status_test==4) || (Status_test==6)||(Status_test==8))) {

                        switch (selected_test) {
                            case 1:
                                    if (count == 6) {
                                        Notification("IA", "Sonido de campana", R.mipmap.png2, Pattern.pattern("IA"));
                                        Tacton_trip = true;
                                        count = 0;
                                        Symbol_ii = false;
                                    }
                                break;
                            case 2:

                                    // Number list phrase
                                    if (count == 2) {
                                        Sound(42);
                                    }

                                    // Coded Pattern of number list phrase
                                    if (count == 4) {
                                        Notification("IA", "3-4", R.mipmap.png2, Pattern.pattern_number("IA"));
                                        Tacton_trip = true;
                                        count = 0;
                                        Symbol_ii = false;
                                    }

                                break;
                        }

                    }
                    // Experiment case

                    if ((Symbol_ii) & ((Status_test==1)||(Status_test==3)||(Status_test==5) || (Status_test==7)||(Status_test==9)) )
                    {
                        switch (selected_test) {
                            case 1:
                                Notification("IA", "Sonido de campana", R.mipmap.png2, Pattern.pattern("IA"));
                                Tacton_trip = true;
                                count = 0;
                                Symbol_ii = false;
                                break;
                            case 2 :
                                Notification("IA", "3-4", R.mipmap.png2, Pattern.pattern_number("IA"));
                                Tacton_trip = true;
                                count = 0;
                                Symbol_ii = false;
                                break;

                        }
                    }

                    //******  Symbol ó *********
                    if (Symbol_oo) {
                        count++;
                    }
                    // Training case
                    if (Symbol_oo &((Status_test==0)||(Status_test==2)||(Status_test==4) || (Status_test==6)||(Status_test==8))) {

                        switch (selected_test) {
                            case 1:
                                if (count == 4) {
                                    Notification("OA", "Encuentros en la tercera fase", R.mipmap.png4, Pattern.pattern("OA"));
                                    Tacton_trip = true;
                                    count = 0;
                                    Symbol_oo = false;
                                }
                                break;
                            case 2:

                                // Number list phrase
                                if (count == 2) {
                                    Sound(43);
                                }
                                if (count == 4) {
                                    // Coded Pattern of number list phrase
                                    Notification("OA", "3-4-6", R.mipmap.png4, Pattern.pattern_number("OA"));
                                    Tacton_trip = true;
                                    count = 0;
                                    Symbol_oo = false;
                                }
                                break;
                        }

                    }
                    // Experiment case

                    if ((Symbol_oo) & ((Status_test==1)||(Status_test==3)||(Status_test==5) || (Status_test==7)||(Status_test==9)) )
                    {
                        switch (selected_test) {
                            case 1:
                                Notification("OA", "Encuentros en la tercera fase", R.mipmap.png4, Pattern.pattern("OA"));
                                Tacton_trip = true;
                                count = 0;
                                Symbol_oo = false;
                                break;
                            case 2 :
                                Notification("OA", "3-4-6", R.mipmap.png4, Pattern.pattern_number("OA"));
                                Tacton_trip = true;
                                count = 0;
                                Symbol_oo = false;
                                break;

                        }
                    }


                    // ****** Symbol ú ******
                    if (Symbol_uu) {
                        count++;
                    }
                    // Training case
                    if (Symbol_uu & ((Status_test==0)||(Status_test==2)||(Status_test==4) || (Status_test==6)||(Status_test==8))) {

                        switch (selected_test) {
                            case 1:
                                    if (count == 4) {
                                        Notification("UA", "Ulular de Buho", R.mipmap.png5, Pattern.pattern("UA"));
                                        Tacton_trip = true;
                                        count = 0;
                                        Symbol_uu = false;
                                    }
                                break;
                            case 2:

                                    // Number list phrase
                                    if (count == 2) {
                                        Sound(44);
                                    }

                                    // Coded Pattern of number list phrase
                                    if (count == 4) {
                                        Notification("UA", "2-3-4-5-6", R.mipmap.png5, Pattern.pattern_number("UA"));
                                        Tacton_trip = true;
                                        count = 0;
                                        Symbol_uu = false;
                                    }

                                break;
                        }

                    }
                    // Experiment case

                    if ((Symbol_uu) & ((Status_test==1)||(Status_test==3)||(Status_test==5) || (Status_test==7)||(Status_test==9)) )
                    {
                        switch (selected_test) {
                            case 1:
                                Notification("UA", "Ulular de Buho", R.mipmap.png5, Pattern.pattern("UA"));
                                Tacton_trip = true;
                                count = 0;
                                Symbol_uu = false;
                                break;
                            case 2 :
                                Notification("UA", "2-3-4-5-6", R.mipmap.png5, Pattern.pattern_number("UA"));
                                Tacton_trip = true;
                                count = 0;
                                Symbol_uu = false;
                                break;

                        }
                    }



                } // end run

            });
        }
    };




    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

    boolean button_pressed = false;
//************************************************************
//                 Create Function
//**********************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_sheet_stressed_vowel);



        // Timer Display
        textView = findViewById(R.id.textView10);
        textView1 = findViewById(R.id.textView11);
        textView2 = findViewById(R.id.textView12);
        textView3 = findViewById(R.id.textView13);

/*
        textView_file1 = findViewById(R.id.textView14);
        textView_file2= findViewById(R.id.textView15);
        EditText edit_file = findViewById(R.id.editTextTextPersonName);



        textView_file1.setVisibility(View.INVISIBLE);
        textView_file2.setVisibility(View.INVISIBLE);
        edit_file.setVisibility(View.INVISIBLE);

 */




// Graphic objects of symbols

        ImageButton button1 = findViewById(R.id.imageButton1);
        ImageButton button2 = findViewById(R.id.imageButton2);
        ImageButton button3 = findViewById(R.id.imageButton3);
        ImageButton button4 = findViewById(R.id.imageButton4);
        ImageButton button5 = findViewById(R.id.imageButton5);
        ImageButton button6 = findViewById(R.id.imageButton6);
        ImageButton button7 = findViewById(R.id.imageButton7);
        ImageButton button8 = findViewById(R.id.imageButton8);
        ImageButton button9 = findViewById(R.id.imageButton9);
        ImageButton button10 = findViewById(R.id.imageButton10);
        ImageButton button11 = findViewById(R.id.imageButton11);
        ImageButton button12 = findViewById(R.id.imageButton12);
        ImageButton button13 = findViewById(R.id.imageButton13);
        ImageButton button14 = findViewById(R.id.imageButton14);
        ImageButton button15 = findViewById(R.id.imageButton15);
        ImageButton button16 = findViewById(R.id.imageButton16);
        ImageButton button17 = findViewById(R.id.imageButton17);
        ImageButton button18 = findViewById(R.id.imageButton18);
        ImageButton button19 = findViewById(R.id.imageButton19);
        ImageButton button20 = findViewById(R.id.imageButton20);
        ImageButton button21 = findViewById(R.id.imageButton21);
        ImageButton button22 = findViewById(R.id.imageButton22);
        ImageButton button23 = findViewById(R.id.imageButton23);
        ImageButton button24 = findViewById(R.id.imageButton24);
        ImageButton button25 = findViewById(R.id.imageButton25);
        ImageButton button26 = findViewById(R.id.imageButton26);
        ImageButton button27 = findViewById(R.id.imageButton27);
        ImageButton button28 = findViewById(R.id.imageButton28);
        ImageButton button29 = findViewById(R.id.imageButton29);
        ImageButton button30 = findViewById(R.id.imageButton30);


        ImageButton button61 = findViewById(R.id.imageButton61);
        ImageButton button62 = findViewById(R.id.imageButton62);
        ImageButton button63 = findViewById(R.id.imageButton63);
        ImageButton button64 = findViewById(R.id.imageButton64);
        ImageButton button65 = findViewById(R.id.imageButton65);

        ImageButton button40 = findViewById(R.id.imageButton40);
        ImageButton button41 = findViewById(R.id.imageButton41);
        ImageButton button42 = findViewById(R.id.imageButton42);
        ImageButton button43 = findViewById(R.id.imageButton43);
        ImageButton button44 = findViewById(R.id.imageButton44);
        ImageButton button45 = findViewById(R.id.imageButton45);
        ImageButton button46 = findViewById(R.id.imageButton46);
        ImageButton button47 = findViewById(R.id.imageButton47);
        ImageButton button48 = findViewById(R.id.imageButton48);
        ImageButton button49 = findViewById(R.id.imageButton49);
        ImageButton button50 = findViewById(R.id.imageButton50);
        ImageButton button51 = findViewById(R.id.imageButton51);


        // Entrada de datos externos de la pantalla de configuración

        Bundle datos = this.getIntent().getExtras();
        selected_test = datos.getInt("selected_test");
        file_out=  datos.getString("file_out");


        // Back screen

        ImageButton backscreen = findViewById(R.id.backscreen);
        backscreen.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        //     finishActivity;
                        onBackPressed();
                    }});


/*
        // *.cvs file creation button
        View Create_file = findViewById(R.id.imageView);
        Create_file.setVisibility(View.INVISIBLE);

        //Selection  mode program

      RadioButton pin_mode_work= findViewById(R.id.Chek);
        RadioButton pin_mode_check= findViewById(R.id.Test);


        // Init selected_mode
       pin_mode_check.setEnabled(true);
        pin_mode_work.setEnabled(true);

        pin_mode_work.setChecked(true);
        selected_mode = 1;


        pin_mode_check.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_mode = 0;

                    }});

        pin_mode_work.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_mode= 1;

                    }});



        // Selection type test variables

        RadioButton pin_Onom = findViewById(R.id.Test_V_Onom);
        RadioButton pin_Control= findViewById(R.id.Test_Control);
        RadioButton pin_Number = findViewById(R.id.Test_V_Number);

        //Init selected_test
        pin_Control.setEnabled(true);
        pin_Onom.setEnabled(true);
        pin_Number.setEnabled(true);

        pin_Onom.setChecked(true);
        selected_test = 1;

        pin_Control.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_test= 0;

                    }});
        pin_Onom.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_test = 1;

                    }});
        pin_Number.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        selected_test = 2;
                    }});

 */


        boolean disable_button_resc= false;


        //Declaration of Test Start graphical variable

        ImageButton Start_test = findViewById(R.id.Start_test);

        //Test Interrupt graphical variable declaration
        ImageButton Stop_test= findViewById(R.id.Stop_test);


        // Enable the vibration option

        Vibrator vibrator;
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        // Enable write permission on disk

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer y escribir!");
        }


        // Start threads timer&control
        Stop_test.setVisibility(View.INVISIBLE);
        timer.schedule(task, 1000, 1000);
        timerSlot1.schedule(task1,1000,1000);



        Sound(23);


        // Start test
/*
        Create_file.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        if (Status_test == 20) {
                            Create_file.setVisibility(View.INVISIBLE);
                            edit_file.setVisibility(View.INVISIBLE);
                            textView_file2.setText(edit_file.getText());
                            textView_file2.setVisibility(View.VISIBLE);
                            textView_file1.setVisibility(View.VISIBLE);
                            Status_test = 0;
                            test_line=0;

                            file_out = edit_file.getText().toString(); // Add test name

                            // Create file *.csv

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            String currentDateandTime = simpleDateFormat.format(new Date());
                            file_out = file_out +  currentDateandTime + ".csv"; // Add current time to file name

                            WriteFileTest File = new WriteFileTest();
                            File.Create_path_and_file(Sheet_stressed_vowel.this, path_braille,file_out);


                        }

                    }});

 */



        Stop_test.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        //Interrupción test - Botón emergencia
                        Start_test.setVisibility(View.VISIBLE);
                        Start_test.setEnabled(true);
                        Stop_test.setVisibility((View.INVISIBLE));
                        Stop_test.setEnabled(false);
                        secondLeft=0;


                    }});


        Start_test.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        switch (Status_test) {
/*
                            case 21:
                                textView_file1.setVisibility(View.VISIBLE);
                                edit_file.setVisibility(View.VISIBLE);
                                Create_file.setVisibility(View.VISIBLE);
                                Status_test = 20;
                                secondLeft = 0;
                                Sound(34);
                                //Mode programme

                                if  (selected_mode == 0)  // Check mode
                                {
                                    pin_mode_check.setEnabled(true);
                                    pin_mode_work.setEnabled(false);
                                }else
                                {
                                    pin_mode_check.setEnabled(false);
                                    pin_mode_work.setEnabled(true);
                                }

                                // Selected test
                                if (selected_mode == 0){
                                    switch(selected_test){
                                        case 0:

                                            pin_Control.setEnabled(true);
                                            pin_Onom.setEnabled(false);
                                            pin_Number.setEnabled(false);
                                            break;


                                        case 1:

                                            pin_Control.setEnabled(false);
                                            pin_Onom.setEnabled(true);
                                            pin_Number.setEnabled(false);
                                            break;


                                        case 2:

                                            pin_Control.setEnabled(false);
                                            pin_Onom.setEnabled(false);
                                            pin_Number.setEnabled(true);
                                            break;


                                    }}



                                break;

 */


                            case 0:
                                On = true;
                                Activ_test = true;
                                textView2.setText("Entrenamiento nº1");
                                secondLeft = Time_slot;
                                Start_test.setEnabled(false);
                                Stop_test.setVisibility(View.VISIBLE);
                                Stop_test.setEnabled(true);

                                button40.setVisibility(View.VISIBLE);
                                button41.setVisibility(View.VISIBLE);
                                button50.setVisibility(View.INVISIBLE);
                                button51.setVisibility(View.INVISIBLE);

                                button26.setImageResource(R.drawable.png40);
                                button27.setImageResource(R.drawable.png43);
                                button28.setImageResource(R.drawable.png44);
                                button29.setImageResource(R.drawable.png41);
                                button30.setImageResource(R.drawable.png42);

                                Sound(24);
                                break;
                            case 1:
                                On = true;
                                textView2.setText("Test Línea 1ª");
                                button61.setEnabled(true);
                                button62.setEnabled(true);
                                button63.setEnabled(true);
                                button64.setEnabled(true);
                                button65.setEnabled(true);
                                Sc = 0;
                                Er = 0;
                                flag_sel = false;
                                secondLeft = Time_slot;
                                test_line = test_line + 1;
                                // test_line = 1;
                                Start_test.setEnabled(false);
                                Stop_test.setVisibility(View.VISIBLE);
                                Stop_test.setEnabled(true);

                                button40.setVisibility(View.INVISIBLE);
                                button41.setVisibility(View.INVISIBLE);
                                button42.setVisibility(View.VISIBLE);
                                button43.setVisibility(View.VISIBLE);

                                button6.setImageResource(R.drawable.png0);
                                button7.setImageResource(R.drawable.png0);
                                button8.setImageResource(R.drawable.png0);
                                button9.setImageResource(R.drawable.png0);
                                button10.setImageResource(R.drawable.png0);

                                if (Activ_test) {
                                    WriteFileTest File = new WriteFileTest();
                                   // File.Write_Text_File(Sheet_stressed_vowel.this,path_braille,file_out,"Line "+ test_line+";");
                                }

                                Sound(25);

                                break;

                            case 2:
                                On = true;
                                textView2.setText("Entrenamiento nº2");
                                secondLeft = Time_slot;
                                Start_test.setEnabled(false);
                                Stop_test.setVisibility(View.VISIBLE);
                                Stop_test.setEnabled(true);

                                button40.setVisibility(View.VISIBLE);
                                button41.setVisibility(View.VISIBLE);
                                button42.setVisibility(View.INVISIBLE);
                                button43.setVisibility(View.INVISIBLE);

                                button6.setImageResource(R.drawable.png41);
                                button7.setImageResource(R.drawable.png44);
                                button8.setImageResource(R.drawable.png42);
                                button9.setImageResource(R.drawable.png40);
                                button10.setImageResource(R.drawable.png43);

                                Sound(26);
                                break;

                            case 3:
                                On = true;
                                textView2.setText("Test Línea 2ª");
                                button61.setEnabled(true);
                                button62.setEnabled(true);
                                button63.setEnabled(true);
                                button64.setEnabled(true);
                                button65.setEnabled(true);
                                Sc = 0;
                                Er = 0;
                                flag_sel = false;
                                secondLeft = Time_slot;
                                Start_test.setEnabled(false);
                                Stop_test.setVisibility(View.VISIBLE);
                                Stop_test.setEnabled(true);
                                test_line = test_line + 1;

                                button40.setVisibility(View.INVISIBLE);
                                button41.setVisibility(View.INVISIBLE);
                                button44.setVisibility(View.VISIBLE);
                                button45.setVisibility(View.VISIBLE);

                                button11.setImageResource(R.drawable.png0);
                                button12.setImageResource(R.drawable.png0);
                                button13.setImageResource(R.drawable.png0);
                                button14.setImageResource(R.drawable.png0);
                                button15.setImageResource(R.drawable.png0);

                                // test_line = 2;
                                if (Activ_test) {
                                    WriteFileTest File2 = new WriteFileTest();
                                   // File2.Write_Text_File(Sheet_stressed_vowel.this,path_braille,file_out,"Line " + test_line + ";" );
                                }

                                Sound(27);
                                break;
                            case 4:
                                On = true;
                                textView2.setText("Entrenamiento nº3");
                                secondLeft = Time_slot;

                                Start_test.setEnabled(false);
                                Stop_test.setVisibility(View.VISIBLE);
                                Stop_test.setEnabled(true);

                                button40.setVisibility(View.VISIBLE);
                                button41.setVisibility(View.VISIBLE);
                                button44.setVisibility(View.INVISIBLE);
                                button45.setVisibility(View.INVISIBLE);

                                button11.setImageResource(R.drawable.png43);
                                button12.setImageResource(R.drawable.png40);
                                button13.setImageResource(R.drawable.png42);
                                button14.setImageResource(R.drawable.png41);
                                button15.setImageResource(R.drawable.png44);

                                Sound(28);
                                break;
                            case 5:
                                On = true;
                                textView2.setText("Test Línea 3ª");
                                button61.setEnabled(true);
                                button62.setEnabled(true);
                                button63.setEnabled(true);
                                button64.setEnabled(true);
                                button65.setEnabled(true);
                                Sc = 0;
                                Er = 0;
                                flag_sel = false;
                                secondLeft = Time_slot;
                                test_line = test_line +1;
                                //test_line = 3;
                                Start_test.setEnabled(false);
                                Stop_test.setVisibility(View.VISIBLE);
                                Stop_test.setEnabled(true);

                                button40.setVisibility(View.INVISIBLE);
                                button41.setVisibility(View.INVISIBLE);
                                button46.setVisibility(View.VISIBLE);
                                button47.setVisibility(View.VISIBLE);

                                button16.setImageResource(R.drawable.png0);
                                button17.setImageResource(R.drawable.png0);
                                button18.setImageResource(R.drawable.png0);
                                button19.setImageResource(R.drawable.png0);
                                button20.setImageResource(R.drawable.png0);

                                if (Activ_test) {
                                    WriteFileTest File3 = new WriteFileTest();
                                   // File3.Write_Text_File(Sheet_stressed_vowel.this,path_braille,file_out,"Line " + test_line +";");
                                }

                                Sound(29);
                                break;
                            case 6:
                                On = true;
                                textView2.setText("Entrenamiento nº4");
                                secondLeft = Time_slot;
                                secondLeft = Time_slot;
                                Start_test.setEnabled(false);
                                Stop_test.setVisibility(View.VISIBLE);
                                Stop_test.setEnabled(true);

                                button40.setVisibility(View.VISIBLE);
                                button41.setVisibility(View.VISIBLE);
                                button46.setVisibility(View.INVISIBLE);
                                button47.setVisibility(View.INVISIBLE);

                                button16.setImageResource(R.drawable.png44);
                                button17.setImageResource(R.drawable.png41);
                                button18.setImageResource(R.drawable.png43);
                                button19.setImageResource(R.drawable.png42);
                                button20.setImageResource(R.drawable.png40);

                                Sound(30);
                                break;
                            case 7:
                                On = true;
                                textView2.setText("Test Línea 4ª");
                                button61.setEnabled(true);
                                button62.setEnabled(true);
                                button63.setEnabled(true);
                                button64.setEnabled(true);
                                button65.setEnabled(true);
                                Sc = 0;
                                Er = 0;
                                flag_sel = false;
                                secondLeft = Time_slot;
                                test_line = test_line +1;
                                //test_line = 4;
                                Start_test.setEnabled(false);
                                Stop_test.setVisibility(View.VISIBLE);
                                Stop_test.setEnabled(true);

                                button40.setVisibility(View.INVISIBLE);
                                button41.setVisibility(View.INVISIBLE);
                                button48.setVisibility(View.VISIBLE);
                                button49.setVisibility(View.VISIBLE);

                                button21.setImageResource(R.drawable.png0);
                                button22.setImageResource(R.drawable.png0);
                                button23.setImageResource(R.drawable.png0);
                                button24.setImageResource(R.drawable.png0);
                                button25.setImageResource(R.drawable.png0);

                                if (Activ_test) {
                                    WriteFileTest File4 = new WriteFileTest();
                                  //  File4.Write_Text_File(Sheet_stressed_vowel.this,path_braille,file_out,"Line " + test_line + ";");
                                }

                                Sound(31);
                                break;
                            case 8:
                                On = true;
                                textView2.setText("Entrenamiento nº5");
                                flag_sel = false;
                                secondLeft = Time_slot;
                                secondLeft = Time_slot;
                                Start_test.setEnabled(false);
                                Stop_test.setVisibility(View.VISIBLE);
                                Stop_test.setEnabled(true);

                                button40.setVisibility(View.VISIBLE);
                                button41.setVisibility(View.VISIBLE);
                                button48.setVisibility(View.INVISIBLE);
                                button49.setVisibility(View.INVISIBLE);

                                button21.setImageResource(R.drawable.png42);
                                button22.setImageResource(R.drawable.png43);
                                button23.setImageResource(R.drawable.png40);
                                button24.setImageResource(R.drawable.png44);
                                button25.setImageResource(R.drawable.png42);

                                Sound(32);
                                break;

                            case 9:
                                On = true;
                                textView2.setText("Test Linea 5º");
                                button61.setEnabled(true);
                                button62.setEnabled(true);
                                button63.setEnabled(true);
                                button64.setEnabled(true);
                                button65.setEnabled(true);
                                Sc = 0;
                                Er = 0;
                                secondLeft = Time_slot;
                                test_line = test_line  + 1;
                                //test_line = 5;
                                Start_test.setEnabled(false);
                                Stop_test.setVisibility(View.VISIBLE);
                                Stop_test.setEnabled(true);

                                button40.setVisibility(View.INVISIBLE);
                                button41.setVisibility(View.INVISIBLE);
                                button50.setVisibility(View.VISIBLE);
                                button51.setVisibility(View.VISIBLE);

                                button26.setImageResource(R.drawable.png0);
                                button27.setImageResource(R.drawable.png0);
                                button28.setImageResource(R.drawable.png0);
                                button29.setImageResource(R.drawable.png0);
                                button30.setImageResource(R.drawable.png0);

                                if (Activ_test) {
                                    WriteFileTest File5 = new WriteFileTest();
                                  //  File5.Write_Text_File(Sheet_stressed_vowel.this,path_braille,file_out,"Line " + test_line + ";");
                                }

                                Sound(33);
                                break;

                        }
                    }});


        //****************************************************
        // Answers Column

        //  Symbol "A"

        button61.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        if ((Status_test == 1)|(Status_test == 3)|(Status_test == 5)|(Status_test == 7)|(Status_test == 9))
                        {
                            if (secondLeft>0) {
                                if ( Double_Click('A'))
                                {
                                    button61.setEnabled(false);
                                }
                                Symbol_elec = 'A';

                            }
                        }

                    }

                });




        button61.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (((Status_test == 1) | (Status_test == 3) | (Status_test == 5) | (Status_test == 7) | (Status_test == 9))) {
                    Sound(11);
                    flag_elec = true;
                }
                return false;
            }
        });

        //  Symbol "E"

        button62.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        if ((Status_test == 1)|(Status_test == 3)|(Status_test == 5)|(Status_test == 7)|(Status_test == 9))
                        {
                            if (secondLeft>0) {
                                if (Double_Click('E'))
                                {
                                    button62.setEnabled(false);
                                }
                                Symbol_elec = 'E';
                            }

                        }
                    }
                });

        button62.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if ((Status_test == 1)|(Status_test == 3)|(Status_test == 5)|(Status_test == 7)|(Status_test == 9))
                {
                    Sound(12);
                    flag_elec= true;
                }
                return false;
            }
        });

        //  Symbol "I"

        button63.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        if ((Status_test == 1)|(Status_test == 3)|(Status_test == 5)|(Status_test == 7)|(Status_test == 9))
                        {
                            if (secondLeft>0) {
                                if (Double_Click('I'))
                                {
                                    button63.setEnabled(false);
                                }
                                Symbol_elec = 'I';
                            }
                        }
                    }
                });
        button63.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if ((Status_test == 1)|(Status_test == 3)|(Status_test == 5)|(Status_test == 7)|(Status_test == 9))
                {
                    Sound(13);
                    flag_elec= true;
                }
                return false;
            }
        });

        //  Symbol "O"

        button64.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        if ((Status_test == 1)|(Status_test == 3)|(Status_test == 5)|(Status_test == 7)|(Status_test == 9))
                        {
                            if (secondLeft>0) {
                                if ( Double_Click('O'))
                                {
                                    button64.setEnabled(false);
                                }
                                Symbol_elec = 'O';
                            }
                        }
                    }
                });
        button64.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if ((Status_test == 1)|(Status_test == 3)|(Status_test == 5)|(Status_test == 7)|(Status_test == 9))
                {
                    Sound(14);
                    flag_elec= true;
                }
                return false;
            }
        });

        //  Symbol "U"

        button65.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {

                        if ((Status_test == 1)|(Status_test == 3)|(Status_test == 5)|(Status_test == 7)|(Status_test == 9))
                        {
                            if (secondLeft>0) {
                                if (Double_Click('U'))
                                {
                                    button65.setEnabled(false);
                                }
                                Symbol_elec = 'U';
                            }
                        }
                    }
                });
        button65.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                if ((Status_test == 1)|(Status_test == 3)|(Status_test == 5)|(Status_test == 7)|(Status_test == 9))
                {
                    Sound(15);
                    flag_elec= true;
                }
                return false;
            }
        });

        //**************************************************************************************
        //  Training Line
        //**************************************************************************************
        //  Symbol "á"

        button1.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {


                        if ((secondLeft > 0) & ((Status_test == 2) | (Status_test == 4) | (Status_test == 6) | (Status_test == 8) | (Status_test == 0))) {

                            switch (selected_test) {
                                case 0:
                                    //Symbol Locution
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_aa = true;
                                        Symbol_ee = false;
                                        Symbol_ii = false;
                                        Symbol_oo = false;
                                        Symbol_uu = false;
                                        count = 0;
                                        if (Symbol_aa) {
                                            Sound(11);
                                        }
                                    }
                                    break;

                                case 1:
                                    //Symbol Locution + onomatopoeid sound
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_aa = true;
                                        Symbol_ee = false;
                                        Symbol_ii = false;
                                        Symbol_oo = false;
                                        Symbol_uu = false;
                                        count = 0;
                                        if (Symbol_aa) {
                                            Sound(1);
                                        }
                                    }

                                    break;

                                case 2:

                                    // Numbers list
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_aa = true;
                                        Symbol_ee = false;
                                        Symbol_ii = false;
                                        Symbol_oo = false;
                                        Symbol_uu = false;
                                        count = 0;
                                        if (Symbol_aa) {
                                            Sound(40);
                                        }
                                    }

                                    break;

                                default:
                                    throw new IllegalStateException("Unexpected value: " + selected_test);
                            }
                        }
                        return false;
                    }});


        //Symbol "é"


        button2.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {

                        if ((secondLeft>0) & ((Status_test == 2) | (Status_test == 4) | (Status_test == 6) | (Status_test == 8) | (Status_test == 0)))
                        {

                            switch (selected_test) {
                                case 0:
                                    //Symbol Locution
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_ee = true;
                                        Symbol_aa = false;
                                        Symbol_ii = false;
                                        Symbol_oo = false;
                                        Symbol_uu = false;
                                        count = 0;
                                        if (Symbol_ee) {
                                            Sound(12);
                                        }
                                    }
                                    break;

                                case 1:
                                    //Locución symbol + onomatopoeid sound
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_ee = true;
                                        Symbol_aa = false;
                                        Symbol_ii = false;
                                        Symbol_oo = false;
                                        Symbol_uu = false;
                                        count = 0;
                                        if (Symbol_ee) {
                                            Sound(2);
                                        }
                                    }

                                      break;

                                case 2:

                                    // Numbers list
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_ee= true;
                                    Symbol_aa = false;
                                    Symbol_ii= false;
                                    Symbol_oo= false;
                                    Symbol_uu = false;
                                    count = 0;
                                    if(Symbol_ee) {
                                        Sound(41);
                                    }
                                    }
                                    break;

                            }
                        }

                        return false;
                    }});
        //Symbol "í"

        button3.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {

                        if ((secondLeft>0) &((Status_test == 2) | (Status_test == 4) | (Status_test == 6) | (Status_test == 8) | (Status_test == 0))) {

                            switch (selected_test) {
                                case 0:
                                    //Symbol Locution
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_ii = true;
                                        Symbol_ee = false;
                                        Symbol_aa = false;
                                        Symbol_oo = false;
                                        Symbol_uu = false;
                                        count = 0;
                                        if (Symbol_ii) {
                                            Sound(13);
                                        }
                                    }
                                    break;

                                case 1:
                                    //Symbol Locution + onomatopoeid sound
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_ii = true;
                                        Symbol_ee = false;
                                        Symbol_aa = false;
                                        Symbol_oo = false;
                                        Symbol_uu = false;
                                        count = 0;
                                        if (Symbol_ii) {
                                            Sound(3);
                                        }
                                    }

                                   break;

                                case 2:

                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_ii = true;
                                        Symbol_ee = false;
                                        Symbol_aa = false;
                                        Symbol_oo = false;
                                        Symbol_uu = false;
                                        count = 0;
                                        if (Symbol_ii) {
                                            Sound(42);
                                        }
                                    }

                                    break;

                            }


                        }
                        return false;
                    }});

        // Symbol "ó"

        button4.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v)
                    {
                        if ((secondLeft>0) &((Status_test == 2) | (Status_test == 4) | (Status_test == 6) | (Status_test == 8) | (Status_test == 0))) {
                            switch (selected_test) {
                                case 0:
                                    //Symbol Locution
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_oo = true;
                                        Symbol_ee = false;
                                        Symbol_aa = false;
                                        Symbol_uu = false;
                                        Symbol_ii = false;
                                        count = 0;
                                        if (Symbol_oo) {
                                            Sound(14);
                                        }
                                    }
                                    break;

                                case 1:
                                    //Symbol Locution  + onomatopoeid sound
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_oo = true;
                                        Symbol_ee = false;
                                        Symbol_aa = false;
                                        Symbol_uu = false;
                                        Symbol_ii = false;
                                        count = 0;
                                        if (Symbol_oo) {
                                            Sound(4);
                                        }
                                    }
                                    break;

                                case 2:
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_oo = true;
                                        Symbol_ee = false;
                                        Symbol_aa = false;
                                        Symbol_uu = false;
                                        Symbol_ii = false;
                                        count = 0;
                                        if (Symbol_oo) {
                                            Sound(43);
                                        }
                                    }
                                    break;

                            }

                        }
                        return false;
                    }});
        //Symbol "ú"

        button5.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v)
                    {
                        if ((secondLeft>0) &((Status_test == 2) | (Status_test == 4) | (Status_test == 6) | (Status_test == 8) | (Status_test == 0))) {
                            switch (selected_test) {
                                case 0:
                                    //Symbol Locution
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_uu = true;
                                        Symbol_ee = false;
                                        Symbol_aa = false;
                                        Symbol_oo = false;
                                        Symbol_ii = false;
                                        count = 0;
                                        if (Symbol_uu) {
                                            Sound(15);
                                        }
                                    }
                                    break;

                                case 1:
                                    //Symbol Locution  + onomatopoeid sound
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_uu = true;
                                        Symbol_ee = false;
                                        Symbol_aa = false;
                                        Symbol_oo = false;
                                        Symbol_ii = false;
                                        count = 0;
                                        if (Symbol_uu) {
                                            Sound(5);
                                        }
                                    }

                                    break;

                                case 2:
                                    // Numbers list
                                    if (mp != null && !mp.isPlaying()) {

                                        Symbol_uu = true;
                                        Symbol_ee = false;
                                        Symbol_aa = false;
                                        Symbol_oo = false;
                                        Symbol_ii = false;
                                        count = 0;
                                        if (Symbol_uu) {
                                            Sound(44);
                                        }
                                    }
                                   break;
                            }
                        }
                        return false;
                    }});

        // *******************************************************************
        // Test on first line
        //********************************************************************
        //Symbol "é"


        button6.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 1) & (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_ee = true;
                            Symbol = 'E';
                            flag_sel= true;
                        }
                        return false;
                    }});

        //Symbol "ú"


        button7.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 1) & (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_uu = true;
                            Symbol = 'U';
                            flag_sel= true;
                        }
                        return false;
                    }});
        //Symbol "í"

        button8.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 1)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_ii = true;
                            Symbol = 'I';
                            flag_sel= true;
                        }
                        return false;
                    }});

        // Symbol "á"


        button9.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 1)& (secondLeft>0)) {
                            Tacton_trip = false;
                           Symbol_aa = true;
                            Symbol = 'A';
                            flag_sel= true;
                        }
                        return false;

                    }});

        // Symbol "ó"

        button10.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 1)&& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_oo = true;
                            Symbol = 'O';
                            flag_sel= true;
                        }
                        return false;

                    }});

        //****************************************************************
        // Test on second line
        //****************************************************************

        //Symbol "ó"


        button11.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 3) & (secondLeft>0)){
                            Tacton_trip = false;
                            Symbol_oo = true;
                            Symbol = 'O';
                            flag_sel= true;
                        }
                        return false;

                    }});


        //Symbol "á"

        button12.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 3) & (secondLeft>0)) {
                            Tacton_trip = false;
                          Symbol_aa = true;
                            Symbol= 'A';
                            flag_sel= true;
                        }
                        return false;
                    }});
        //Symbol "í"

        button13.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 3)& (secondLeft>0)) {
                            Tacton_trip = false;
                           Symbol_ii = true;
                            Symbol = 'I';
                            flag_sel= true;
                        }
                        return false;
                    }});

        // Symbol "é"

        button14.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 3)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_ee = true;
                            Symbol = 'E';
                            flag_sel= true;
                        }

                        return false;
                    }});

        //Symbol "ú"

        button15.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 3)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_uu = true;
                            Symbol = 'U';
                            flag_sel= true;
                        }
                        return false;
                    }});


        //**********************************************************
        // Test on third line
        //**********************************************************

        //Symbol "ú"

        button16.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 5)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_uu = true;
                            Symbol = 'U';
                            flag_sel= true;
                        }
                        return false;
                    }});

        //Symbol "é"

        button17.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 5)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_ee = true;
                            Symbol = 'E';
                            flag_sel= true;
                        }
                        return false;
                    }});

        //Symbol "ó"

        button18.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 5)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_oo = true;
                            Symbol = 'O';
                            flag_sel= true;
                        }
                        return false;
                    }});


        // Symbol "í"

        button19.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 5)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_ii = true;
                            Symbol = 'I';
                            flag_sel= true;
                        }
                        return false;
                    }});

        //Symbol "á"

        button20.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 5)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_aa = true;
                            Symbol= 'A';
                            flag_sel= true;
                        }
                        return false;
                    }});


        //****************************************************
        // Test on fourth line
        //****************************************************

        //Symbol "í"

        button21.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 7)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_ii = true;
                            Symbol = 'I';
                            flag_sel= true;
                        }
                        return false;
                    }});

        //Symbol "ó"

        button22.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 7)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_oo = true;
                            Symbol = 'O';
                            flag_sel= true;
                        }
                        return false;
                    }});

        //Symbol "á"

        button23.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 7)& (secondLeft>0)) {
                            Tacton_trip = false;
                           Symbol_aa = true;
                            Symbol= 'A';
                            flag_sel= true;
                        }
                        return false;
                    }});

        // Symbol "ú"


        button24.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 7)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_uu = true;
                            Symbol = 'U';
                            flag_sel= true;
                        }
                        return false;
                    }});




        //Symbol "é"

        button25.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 7)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_ee = true;
                            Symbol = 'E';
                            flag_sel= true;
                        }
                        return false;
                    }});


        //*****************************************************
        // Test on fifth line
        //******************************************************

         //Symbol "á"
        button26.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 9)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_aa = true;
                            Symbol= 'A';
                            flag_sel= true;
                        }
                        return false;
                    }});

        //Symbol "ó"

        button27.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((secondLeft>0) & (Status_test == 9)) {
                            Tacton_trip = false;
                            Symbol_oo = true;
                            Symbol = 'O';
                            flag_sel= true;
                        }
                        return false;
                    }});

        //Symbol "ú"

        button28.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 9) & (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_uu = true;
                            Symbol = 'U';
                            flag_sel= true;
                        }
                        return false;
                    }});

        // Symbol "é"

        button29.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 9)& (secondLeft>0)) {
                            Tacton_trip = false;
                            Symbol_ee = true;
                            Symbol = 'E';
                            flag_sel= true;
                        }
                        return false;

                    }});

        //Symbol "í"

        button30.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        if ((Status_test == 9)& (secondLeft>0)) {
                            Tacton_trip = false;
                           Symbol_ii = true;
                            Symbol = 'I';
                            flag_sel= true;
                        }
                        return false;
                    }});

    }
}