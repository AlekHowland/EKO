package mx.itesm.eko;

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
    public String txt="Scores.txt";

    public float x,y;
    public Texto texto;

    public Scores(float x, float y){
        this.x=x;
        this.y=y;
        texto=new Texto("fuente.fnt");
    }
    public void crearArchivo() {
        FileWriter flwriter = null;
        try {
            flwriter = new FileWriter(txt);
            BufferedWriter bfwriter = new BufferedWriter(flwriter);
            bfwriter.write("");
            bfwriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (flwriter != null) {
                try {
                    flwriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList leerArchivo() throws FileNotFoundException, IOException {
        String cadena;
        ArrayList lista=new ArrayList();
        FileReader f = new FileReader(txt);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            lista.add(cadena);
        }
        b.close();
        return lista;
    }

    public void escribirArchivo(String score,String nombre) throws IOException {
        ArrayList arr=leerArchivo();
        File file = new File(txt);
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);
        if (arr.size()<2){
            br.write(score+"\n"+nombre);
        }
        else {
            br.write("\n" + score + "\n" + nombre);
        }
        br.close();
        fr.close();

    }

    public String getHighScore() throws IOException {
        String[] arr=ordenarScores();
        String[] scoresOrdenados;
        scoresOrdenados=arr[0].split("\n");
        String highScore=scoresOrdenados[1];
        return highScore;
    }

    public boolean comprobarArchivo(){
        File archivo = new File(txt);
        BufferedWriter bw;
        if(archivo.exists()) {
            return true;
        } else {
            return false;
        }
    }
    public String[] ordenarScores() throws IOException {
        String[] scores = new String[2];
        ArrayList arr=leerArchivo();
        String records="Score\n";
        String nombres="Date\n";
        for (int i=1;i<arr.size();i+=2){
            records+=(String) arr.get(i-1)+"\n";
            nombres+=(String) arr.get(i)+"\n";
        }
        scores[0]=records;
        scores[1]=nombres;
        String[] scoresOrdenados;
        String[] nombresOrdenados;

        scoresOrdenados=scores[0].split("\n");

        for(int i = 0; i<scoresOrdenados.length-1; i++) {
            for (int j = i + 1; j < scoresOrdenados.length; j++) {
                if (scoresOrdenados[i].compareTo(scoresOrdenados[j]) > 0) {
                    String temp = scoresOrdenados[i];
                    scoresOrdenados[i] = scoresOrdenados[j];
                    scoresOrdenados[j] = temp;
                }
            }
        }
        for(int i=0; i<scoresOrdenados.length/2; i++){
            String temp = scoresOrdenados[i];
            scoresOrdenados[i] = scoresOrdenados[scoresOrdenados.length -i -1];
            scoresOrdenados[scoresOrdenados.length -i -1] = temp;
        }

        for (int i=0;i<scoresOrdenados.length;i++){

        }
        scores[0]="";
        for (int i=0;i<scoresOrdenados.length;i++){
            scores[0]+=scoresOrdenados[i]+"\n";
        }
        System.out.println(scores[0]);
        System.out.println(scoresOrdenados[1]);

        return scores;
    }


    public void render(SpriteBatch batch) throws IOException {
        if (leerArchivo().size()>1) {
            String[] arr = ordenarScores();
            String scores = arr[0];
            String nombres = arr[1];
            texto.render(batch, scores, x, y);
            texto.render(batch, nombres, x * 2, y);
        }
    }


}
