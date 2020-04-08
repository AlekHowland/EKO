package mx.itesm.eko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface TransicionPantalla
{
    public float getDuration();

    public void render(SpriteBatch batch, Texture pantallaActual, Texture pantallaSiguiente,
                       float alpha);
}
