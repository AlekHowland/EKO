package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.eko.musica.ControladorAudio;

/**
 * Representa el comportamiento genérico de cualquier pantalla que forma
 * parte del juego
 */
public abstract class PantallaAbstracta implements Screen
{
    // Atributos disponibles en todas las clases del proyecto
    public static final float ANCHO = 1280;
    public static final float ALTO = 720;

    // Atributos disponibles solo en las subclases
    // Todas las pantallas tienen una cámara y una vista
    protected OrthographicCamera camara;
    protected Viewport vista;

    // Todas las pantallas dibujan algo :)
    protected SpriteBatch batch;

    // Constructor, inicializa los objetos camara, vista, batch
    public PantallaAbstracta() {
        // Crea la cámara con las dimensiones del mundo
        camara = new OrthographicCamera(ANCHO, ALTO);

        // En el centro de la pantalla. (x,y) de la cámara en el centro geométrico
        camara.position.set(ANCHO / 2, ALTO / 2, 0);
        camara.update();

        // La vista que escala los elementos gráficos
        vista = new StretchViewport(ANCHO, ALTO, camara);

        // El objeto que administra los trazos gráficos
        batch = new SpriteBatch();
    }



    // Borra la pantalla con fondo negro
    protected void borrarPantalla() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // Borra la pantalla con el color RGB (r,g,b)
    protected void borrarPantalla(float r, float g, float b) {
        Gdx.gl.glClearColor(r,g,b,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // Notifica a la vista, que el tamaño de la pantalla física ha cambiado
    @Override
    public void resize(int width, int height) {
        vista.update(width, height);
    }

    @Override
    public void hide() {
        // Libera los recursos asignados por cada pantalla
        // Las subclases están obligadas a sobrescribir el método dispose()
        dispose();
    }

    // Código necesario para la transición de pantallas
    public abstract InputProcessor getInputProcessor();
    protected ControlJuego pantalla;
    public PantallaAbstracta (ControlJuego pantalla){
        this.pantalla = pantalla;
    }

}