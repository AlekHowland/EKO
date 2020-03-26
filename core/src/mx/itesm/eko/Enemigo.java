package mx.itesm.eko;

import com.badlogic.gdx.graphics.Texture;

public class Enemigo extends Objeto {

    public Enemigo(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void mover(float dx){
        sprite.setX(sprite.getX()+dx);
    }


    public void setPosition(float x, float y){
    sprite.setPosition(x,y);
    }
}
