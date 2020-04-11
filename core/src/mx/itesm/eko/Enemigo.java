package mx.itesm.eko;

import com.badlogic.gdx.graphics.Texture;

public class Enemigo extends Objeto {
    int tipoEnemigo;
    public Enemigo(Texture textura, float x, float y,int tipoEnemigo) {
        super(textura, x, y);
        this.tipoEnemigo=tipoEnemigo;
    }

    public void moverHorizontal(float dx){
        sprite.setX(sprite.getX()+dx);
    }
    public void moverVertical(float dy){
        sprite.setY(sprite.getY()+dy);
    }


    public void setPosition(float x, float y){
        sprite.setPosition(x,y);
    }

    public int getTipo(){
        return tipoEnemigo;
    }

}
