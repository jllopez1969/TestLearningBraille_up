package com.example.testlearningbraille;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFileTest {
      public boolean Create_path_and_file(Context context, String path, String name_file )
      {
          boolean bSuccess = true;

          //Path
          File dir = new File(Environment.getExternalStorageDirectory() + "/" + path);

          if (!dir.exists())
            if (!dir.mkdir()) {
                Toast.makeText(context, "Error creating directory", Toast.LENGTH_LONG).show();
                return false;
            }
            else {
                Toast.makeText(context, "Directory has been created", Toast.LENGTH_LONG).show();
            }

          // File
          File file = new File(dir,name_file);
          if (!file.exists()) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
                out.close();
                Toast.makeText(context,"File created",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(context,"File Not Created",Toast.LENGTH_LONG).show();
                bSuccess = false;
              }
            } else {
                Toast.makeText(context,"File already created, select another file name",Toast.LENGTH_LONG).show();
                bSuccess = false;
            }

          return bSuccess;
      }

    /** FORMA 1 DE ESCRITURA **/

        public void Write_Text_File (Context context, String path,  String name_file,String text){
            File dir = new File(  Environment.getExternalStorageDirectory() + "/" + path);
            File file = new File(dir, name_file);

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
                out.write(text);
                out.close();
            } catch (IOException e) {
                Toast.makeText(context,"Texto no añadido",Toast.LENGTH_LONG).show();
            }
        }
}





