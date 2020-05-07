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

public class Scores {
    private FileHandle fileEscr = Gdx.files.local("Scores.txt");
    private FileHandle fileLect = Gdx.files.internal("Scores.txt");

    public float x,y;
    public Texto texto;

    public Scores(float x, float y){
        this.x=x;
        this.y=y;
        texto=new Texto("Fonts/fuente.fnt");
    }
    public void crearArchivo() {
        fileEscr.writeString("0\n-\n0\n-\n0\n-\n0\n-\n0\n-\n0\n-",false);
    }


    public ArrayList leerArchivo() throws FileNotFoundException, IOException {
        String[] division=fileLect.readString().split("\n");
        ArrayList lista=new ArrayList();
        int i=0;
        while(lista.size()!=division.length){
            lista.add(division[i]);
            i++;
        }
        return lista;
    }

    public void escribirArchivo(String score,String nombre) throws IOException {
        ArrayList arr=leerArchivo();
        if (arr.size()<2){
            fileEscr.writeString(score+"\n"+nombre,true);
        }
        else {
            fileEscr.writeString("\n" + score + "\n" + nombre,true);
        }

    }

    public String getHighScore() throws IOException {
        String[] arr=ordenarScores();
        String[] scoresString;
        scoresString=arr[0].split("\n");
        String highScore=scoresString[1];
        return highScore;
    }

    public boolean comprobarArchivo(){
        if (Gdx.files.local("Scores.txt").exists()){
            return true;
        }
        else {
            return false;
        }
    }
    public String[] ordenarScores() throws IOException {
        String[] scores = new String[3];
        ArrayList arr=leerArchivo();
        String records="";
        String nombres="";
        for (int i=1;i<arr.size();i+=2){
            records+=(String) arr.get(i-1)+"\n";
            nombres+=(String) arr.get(i)+"\n";
        }
        scores[0]=records;
        scores[1]=nombres;
        String[] scoresString;
        String[] nombresString;

        scoresString=scores[0].split("\n");
        nombresString=scores[1].split("\n");

        float[] scoresFloat= new float[scoresString.length];

        String[] nombresOrdenados= new String[scoresString.length];

        for (int i = 0; i<scoresString.length; i++){
            scoresFloat[i]=Float.parseFloat(scoresString[i]);
        }
        Arrays.sort(scoresFloat);


        for (int i=0;i<scoresFloat.length;i++){
            for (int j=0;j<scoresFloat.length;j++){
                if (Float.parseFloat(scoresString[j])==scoresFloat[i]){
                    nombresOrdenados[i]=nombresString[j];
                }
            }
        }



        scores[0]="Score\n";

        for (int i=scoresFloat.length-1;i>=scoresFloat.length-5;i--){
            scores[0]+=(int)scoresFloat[i]+"\n";
        }
        scores[2]="\n";
        for (int i=1;i<=5;i++){
            scores[2]+=i+"\n";
        }

        scores[1]="Date\n";
        for (int i=nombresOrdenados.length-1;i>=nombresOrdenados.length-5;i--){
            scores[1]+=nombresOrdenados[i]+"\n";
        }
        return scores;
    }


    public void render(SpriteBatch batch) throws IOException {
        if (leerArchivo().size()>1) {
            String[] arr = ordenarScores();
            texto.render(batch, arr[0], x, y);
            texto.render(batch, arr[2], x*0.5f, y);
            texto.render(batch, arr[1], x * 2, y);
        }

    }


}
