package mx.itesm.eko;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Personaje extends Objeto {

    public Personaje(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void mover(float dy){
        sprite.setY(sprite.getY()+dy);
    }
}
