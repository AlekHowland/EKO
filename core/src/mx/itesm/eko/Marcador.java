package mx.itesm.eko;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Marcador {
    private int score;

    private float x,y;

    private Texto texto;

    //Constructor
    public Marcador(float x, float y){
        this.x=x;
        this.y=y;
        score=0;
        texto=new Texto("fuente.fnt"); //Fuente
    }

    public void reset(){
        score=0;
    }

    //Agrega puntos
    public void marcar(int puntos){
        this.score+=puntos;
    }

    public void render(SpriteBatch batch){
        String mensaje=""+score;
        texto.render(batch,"score",x,y);
        texto.render(batch,mensaje,x,y-0.075f*y);
    }

    public float getWidth(){
        return texto.getWidth();
    }

    public float getHeight(){
        return texto.getHeight();
    }

    public int getScore(){
        return score;
    }
}
