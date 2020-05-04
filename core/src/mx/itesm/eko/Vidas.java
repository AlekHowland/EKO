package mx.itesm.eko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Vidas {
    private int vidas;

    private float x,y;

    private Texto texto;

    private String assets;

    private Objeto imagenVida;

    private Texture texturaImagen;

    //Constructor
    public Vidas(float x, float y,int vidas,String assets){
        this.vidas=vidas;
        this.x=x;
        this.y=y;
        this.assets=assets;
        texto=new Texto("Fonts/fuente.fnt"); //Fuente
        texturaImagen=new Texture("Personajes/vida"+assets+".png");
        imagenVida=new Objeto(texturaImagen,x+0.8f,y*0.95f);
    }


    //Agrega puntos
    public void sumar(int numVidas){
        this.vidas+=numVidas;
    }

    public void restar(int numVidas){
        this.vidas-=numVidas;
    }

    public void render(SpriteBatch batch){
        String mensaje="x"+vidas;
        imagenVida.render(batch);
        texto.render(batch,mensaje,x+imagenVida.sprite.getWidth()*1.5f,y);
    }

    public float getWidth(){
        return texto.getWidth();
    }

    public float getHeight(){
        return texto.getHeight();
    }

    public int getVidas(){
        return vidas;
    }
}
