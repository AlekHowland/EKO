package mx.itesm.eko;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import mx.itesm.eko.TransicionPantalla;


public class DireccionamientoPantallas implements ApplicationListener
{
    private boolean inicio;
    private PantallaAbstracta pantallaActual;
    private PantallaAbstracta pantallaSiguiente;
    private FrameBuffer actualFrameBuffer;
    private FrameBuffer siguienteFrameBuffer;
    private SpriteBatch batch;
    private float duracion;
    private TransicionPantalla transicionPantalla;

    public void setScreen(PantallaAbstracta pantalla, TransicionPantalla transicion){
        int ancho = Gdx.graphics.getWidth();
        int alto = Gdx.graphics.getHeight();

        if (!inicio) {
            actualFrameBuffer = new FrameBuffer(Format.RGBA8888, ancho, alto, false);
            siguienteFrameBuffer = new FrameBuffer(Format.RGBA8888, ancho, alto, false);
            batch = new SpriteBatch();
            inicio = true;
        }

        // Aquí es donde inicia la transición
        pantallaSiguiente = pantalla;
        pantallaSiguiente.show();       //Se activa la siguiente pantalla
        pantallaSiguiente.resize(ancho, alto);
        pantallaSiguiente.render(0);

        if (pantallaActual != null) pantallaActual.pause();
            pantallaSiguiente.pause();
            Gdx.input.setInputProcessor(null);
            this.transicionPantalla = transicionPantalla;
            duracion = 0;
    }

    @Override
    public void render() {
        // Se establece el límite del tiempo a 1/60 segundos
        float tiempo = Math.min(Gdx.graphics.getDeltaTime(), 1.0f/60.0f);

        if (pantallaSiguiente == null) {
            // No hay transición
            if(pantallaActual != null) pantallaActual.render(tiempo);
        } else {
            // Hay una transición
            float duracionTransicion = 0;
            if (transicionPantalla != null) {
                duracionTransicion = transicionPantalla.getDuration();
            }
          duracion = Math.min(duracion + tiempo, duracionTransicion);
          if (transicionPantalla == null || duracion >= duracionTransicion) {
              // Se acaba de terminar la transición o no hay
              if (pantallaActual != null) pantallaActual.hide();
              pantallaSiguiente.resume();

              // Se abilita el input para la siguiente pantalla

              /* SABER QUE ONDA CON ESTE METODO     */
              //Gdx.input.setInputProcessor(pantallaSiguiente.getInputProcessor());\\


              // Se hace el cambio de pantallas
              pantallaActual = pantallaSiguiente;
              pantallaSiguiente = null;
              transicionPantalla = null;
          } else {
              actualFrameBuffer.begin();
              if (pantallaActual != null) pantallaActual.render(tiempo);
              actualFrameBuffer.end();
              siguienteFrameBuffer.begin();
              pantallaSiguiente.render(tiempo);
              siguienteFrameBuffer.end();
              //  Efecto de transición
              float alpha = duracion / duracionTransicion;
              transicionPantalla.render(batch, actualFrameBuffer.getColorBufferTexture(),
                      siguienteFrameBuffer.getColorBufferTexture(), alpha);
          }
        }
    }

    @Override
    public void create() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void dispose() { }

}
