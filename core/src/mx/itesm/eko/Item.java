package mx.itesm.eko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item extends Objeto {

    private BotonDinamico animacion;
    private Texture texturaItem;

    public Item(Texture textura, float x, float y) {
        super(textura, x, y);
        texturaItem=new Texture("Personajes/huevoAnimado.png");
        animacion=new BotonDinamico(texturaItem,99,108,sprite.getX(),sprite.getY());
        animacion.cargarItem();
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

    public void renderAnimacion(SpriteBatch batch){
        animacion.render(batch,sprite.getX(),sprite.getY());
    }
}
