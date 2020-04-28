package mx.itesm.eko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FondoDinamico{
    private Objeto capa1;
    private Objeto capa2;
    private Objeto capa3;
    private Objeto capa4;
    private Objeto capaTiempo;
    private Objeto capaSol;


    private Texture texturaCapa1;
    private Texture texturaCapa2;
    private Texture texturaCapa3;
    private Texture texturaCapa4;
    private Texture texturaCapaTiempo;
    private Texture texturaCapaSol;
    public FondoDinamico(String assets,float x,float y) {
        texturaCapa1=new Texture("Fondos/fondo"+assets+"1.png");
        texturaCapa2=new Texture("Fondos/fondo"+assets+"2.png");
        texturaCapa3=new Texture("Fondos/fondo"+assets+"3.png");
        texturaCapa4=new Texture("Fondos/fondo"+assets+"4.png");
        texturaCapaTiempo=new Texture("Fondos/fondo"+assets+"Tiempo.png");
        texturaCapaSol=new Texture("Fondos/fondo"+assets+"Sol.png");
        capa1=new Objeto(texturaCapa1,x,0);
        capa2=new Objeto(texturaCapa2,x,0);
        capa3=new Objeto(texturaCapa3,x,0);
        capa4=new Objeto(texturaCapa4,x,0);
        capaTiempo=new Objeto(texturaCapaTiempo,0,y);
        capaSol=new Objeto(texturaCapaSol,0,y/2);
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
    public void renderCapaTiempo(SpriteBatch batch){
        capaTiempo.sprite.draw(batch);
    }
    public void renderCapaSol(SpriteBatch batch){
        capaSol.sprite.draw(batch);
    }



    public void moverCapas(float velocidad){
        capa1.sprite.setX(capa1.sprite.getX()+velocidad*0.5f);
        capa2.sprite.setX(capa2.sprite.getX()+velocidad*0.7f);
        capa3.sprite.setX(capa3.sprite.getX()+velocidad*0.8f);
        capa4.sprite.setX(capa4.sprite.getX()+velocidad);
    }

    public void moverCapasVertical(float velocidad){
        capaTiempo.sprite.setY(capaTiempo.sprite.getY()+velocidad);
        capaSol.sprite.setY(capaSol.sprite.getY()+velocidad*0.5f);
    }

    public float getWidth(){
        return capa1.sprite.getWidth();
    }

    public float getHeightTiempo(){
        return capaTiempo.sprite.getHeight();
    }

    public float getHeightSol(){return capaSol.sprite.getHeight();
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

    public void setYCapaTiempo(float y){
        capaTiempo.sprite.setY(y);
    }

    public void setYCapaSol(float y){
        capaSol.sprite.setY(y);
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

    public float getYCapaTiempo(){
        return capaTiempo.sprite.getY();
    }

    public float getYCapaSol(){
        return capaSol.sprite.getY();
    }
}
