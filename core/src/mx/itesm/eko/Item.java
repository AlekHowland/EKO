package mx.itesm.eko;

import com.badlogic.gdx.graphics.Texture;

public class Item extends Objeto {

    public Item(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void mover(float dy) {
        sprite.setY(sprite.getY() - dy);
    }

    public void moverHorizontal(float dx) {
        sprite.setX(sprite.getX() + dx);
    }

    public void moverVertical(float dy) {
        sprite.setY(sprite.getY() + dy);
    }
}
