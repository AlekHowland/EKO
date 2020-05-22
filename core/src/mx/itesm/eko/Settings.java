package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Settings {
    private FileHandle fileEscr = Gdx.files.local("Settings.txt");
    private FileHandle fileLect = Gdx.files.local("Settings.txt");




    public Settings(){

    }
    public void crearArchivo() {
        fileEscr.writeString("1",false);
    }


    public String leerArchivo() {
        String division=fileLect.readString();
        return division;
    }

    public void escribirArchivo(String string)  {
        fileEscr.writeString(string,false);
    }

    public boolean comprobarArchivo(){
        if (Gdx.files.local("Settings.txt").exists()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean musicaPrendida(){
        String string=leerArchivo();
        if (string.equals("1")){
            return true;
        }
        else {
            return false;
        }
    }


}
