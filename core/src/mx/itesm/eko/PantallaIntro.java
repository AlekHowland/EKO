package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import mx.itesm.eko.transiciones.TransicionPantalla;
import mx.itesm.eko.transiciones.efectoTransicion;

public class PantallaIntro extends PantallaAbstracta implements InputProcessor{

    private final ControlJuego juego;
    private float tiempoVisible = 2.5f;
    private Texture texturaLogo;
    private Sprite spriteLogo;
    final TransicionPantalla transicion = efectoTransicion.inicializacion(2.0f);


    @Override
    public InputProcessor getInputProcessor() {
        return null;
    }

    // Constructor
    public PantallaIntro(ControlJuego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaLogo = new Texture(Gdx.files.internal("Fondos/fondoIntro.jpg"));
        spriteLogo = new Sprite(texturaLogo);
        spriteLogo.setPosition(ANCHO/2-spriteLogo.getWidth()/2, ALTO/2-spriteLogo.getHeight()/2);
        escalarLogo();
        Gdx.input.setInputProcessor(this);
    }

    private void escalarLogo() {
        float factorCamara = ANCHO / ALTO;
        float factorPantalla = 1.0f*Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
        float escala = factorCamara / factorPantalla;
        spriteLogo.setScale(escala, 1);
    }

    @Override
    public void render(float delta) {

        borrarPantalla(0,0,0);

        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteLogo.draw(batch);
        batch.end();

        // Actualizar para cambiar pantalla
        tiempoVisible -= delta;
        if (tiempoVisible<=0) {
            juego.setScreen(new PantallaMenu(juego));
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaLogo.dispose();
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        juego.setScreen(new PantallaMenu(juego));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
