package mx.itesm.eko.transiciones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class efectoTransicion implements TransicionPantalla
{
    private static final efectoTransicion instancia = new efectoTransicion();
    private float duracion;

    public static efectoTransicion inicializacion (float duracion){
        instancia.duracion = duracion;
        return instancia;
    }

    @Override
    public float getDuration() {
        return duracion;
    }

    @Override
    public void render(SpriteBatch batch, Texture pantallaActual, Texture pantallaSiguiente, float alpha) {
        float ancho = pantallaActual.getWidth();
        float alto = pantallaActual.getHeight();
        alpha = Interpolation.fade.apply(alpha);

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Batch para la pantalla actual
        batch.setColor(1, 1, 1, 1);
        batch.draw(pantallaActual, 0, 0, 0, 0, ancho, alto, 1, 1,
                0, 0, 0, pantallaActual.getWidth(), pantallaActual.getHeight(),
                false, true);

        // Batch para la pantalla siguiente
        batch.setColor(1, 1, 1, alpha);
        batch.draw(pantallaSiguiente, 0, 0, 0, 0, ancho, alto, 1, 1,
                0, 0, 0, pantallaSiguiente.getWidth(), pantallaSiguiente.getHeight(),
                false, true);

        batch.end();
    }
}
