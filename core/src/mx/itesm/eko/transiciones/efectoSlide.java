package mx.itesm.eko.transiciones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

public class efectoSlide implements TransicionPantalla
{
    public static final int IZQUIERDA = 1;
    public static final int DERECHA = 2;
    public static final int ARRIBA = 3;
    public static final int ABAJO = 4;
    private float duracion;
    private int direccion;
    private boolean slideOut;
    private Interpolation facilitador;
    private static final efectoSlide instancia = new efectoSlide();

    public static efectoSlide inicializacion (float duracion, int direccion, boolean slideOut,
                                              Interpolation facilitador)
    {
            instancia.duracion = duracion;
            instancia.direccion = direccion;
            instancia.slideOut = slideOut;
            instancia.facilitador = facilitador;
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
        float x = 0;
        float y = 0;

        if(facilitador != null) alpha = facilitador.apply(alpha);

        // Cálculo de la compensasción de la posición
        switch (direccion)
        {
            case IZQUIERDA:
                x = -ancho * alpha;
                if (!slideOut) x += ancho;
                break;

            case DERECHA:
                x = ancho * alpha;
                if (!slideOut) x -= ancho;
                break;

            case ARRIBA:
                y = alto * alpha;
                if (!slideOut) y -= alto;
                break;

            case ABAJO:
                y = -alto * alpha;
                if (!slideOut) y += alto;
                break;
        }

        // El dibujo depende del tipo de slide (in o out)
        Texture texturaBaja = slideOut ? pantallaSiguiente : pantallaActual;
        Texture texturaTope = slideOut ? pantallaActual : pantallaSiguiente;

        // Se dibujan ambas pantallas
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(texturaBaja, 0, 0, 0, 0, ancho, alto, 1, 1,
                0, 0, 0, pantallaActual.getWidth(), pantallaActual.getHeight(),
                false, true);

        batch.draw(texturaTope, 0, 0, 0, 0, ancho, alto, 1, 1,
                0, 0, 0, pantallaSiguiente.getWidth(), pantallaSiguiente.getHeight(),
                false, true);

        batch.end();


    }
}
