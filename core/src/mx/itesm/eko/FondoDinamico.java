package mx.itesm.eko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FondoDinamico{
    private Objeto capa1;
    private Objeto capa2;
    private Objeto capa3;
    private Objeto capa4;

    private Texture texturaCapa1;
    private Texture texturaCapa2;
    private Texture texturaCapa3;
    private Texture texturaCapa4;
    public FondoDinamico(String assets,float x,float y) {
        texturaCapa1=new Texture("Fondos/fondo"+assets+"1.png");
        texturaCapa2=new Texture("Fondos/fondo"+assets+"2.png");
        texturaCapa3=new Texture("Fondos/fondo"+assets+"3.png");
        texturaCapa4=new Texture("Fondos/fondo"+assets+"4.png");
        capa1=new Objeto(texturaCapa1,x,y);
        capa2=new Objeto(texturaCapa2,x,y);
        capa3=new Objeto(texturaCapa3,x,y);
        capa4=new Objeto(texturaCapa4,x,y);
    }

    public void renderCapa1(SpriteBatch batch){
        capa1.sprite.draw(batch);
    }
    public void renderCapa2(SpriteBatch batch){
        capa2.sprite.draw(batch);
    }
    public void renderCapa3(SpriteBatch batch){
        capa3.sprite.draw(batch);
    }
    public void renderCapa4(SpriteBatch batch){
        capa4.sprite.draw(batch);
    }



    public void moverCapas(float velocidad){
        capa1.sprite.setX(capa1.sprite.getX()+velocidad*0.5f);
        capa2.sprite.setX(capa2.sprite.getX()+velocidad*0.7f);
        capa3.sprite.setX(capa3.sprite.getX()+velocidad*0.8f);
        capa4.sprite.setX(capa4.sprite.getX()+velocidad);
    }

    public float getWidth(){
        return capa1.sprite.getWidth();
    }

    public void setXCapa1(float x){
        capa1.sprite.setX(x);
    }

    public void setXCapa2(float x){
        capa2.sprite.setX(x);
    }

    public void setXCapa3(float x){
        capa3.sprite.setX(x);
    }

    public void setXCapa4(float x){
        capa4.sprite.setX(x);
    }

    public float getXCapa1(){
        return capa1.sprite.getX();
    }

    public float getXCapa2(){
        return capa2.sprite.getX();
    }

    public float getXCapa3(){
        return capa3.sprite.getX();
    }

    public float getXCapa4(){
        return capa4.sprite.getX();
    }
}
