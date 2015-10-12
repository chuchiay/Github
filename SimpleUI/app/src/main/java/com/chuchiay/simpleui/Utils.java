package com.chuchiay.simpleui;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by USER on 10/12/2015.
 */
public class Utils {
    public static void writeFile(Context context, String filename, String content){
        try{
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_APPEND);
            fos.write(content.getBytes());
            fos.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static String readFile(Context context, String filename){

        try {
            FileInputStream fin = context.openFileInput(filename);
            byte [] buffer = new byte[1024];
            fin.read(buffer,0,buffer.length);
            fin.close();
            return new String(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
