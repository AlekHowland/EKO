package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

class PantallaJuego extends Pantalla {
    private final Juego juego;
    private Random rnd = new Random();
    private final String assets;

    private EstadoJuego estadoJuego=EstadoJuego.JUGANDO;

    private Personaje personaje;
    private Enemigo enemigo;

    private Texture texturaPersonaje;
    private Texture texturaFondo;
    private Texture texturaEnemigo;
    private Texture texturaPersonajeAbajo;

    private Movimiento movimientoPersonaje=Movimiento.QUIETO;
    private Movimiento movimientoEnemigo=Movimiento.IZQUIERDA;
    private float timerPersonaje=0;
    private float timerEnemigo=0;
    private int pasoEnemigo=7;

    private Marcador marcador;


    public PantallaJuego(Juego juego,String assets) {
        this.juego=juego;
        this.assets=assets;
    }

    @Override
    public void show() {
        cargarTexturas();
        createPersonaje();
        createEnemigo();
        createMarcador();


        texturaFondo=new Texture("fondo"+assets+".jpg");

        Gdx.input.setInputProcessor(new ProcesadorEntrada());


    }

    private void createMarcador() {marcador=new Marcador(ANCHO/2,0.95f*ALTO);}

    private void createPersonaje() {
        personaje =new Personaje(texturaPersonaje,0,ALTO*0.05f);
    }

    private void createEnemigo(){ enemigo=new Enemigo(texturaEnemigo,ANCHO,ALTO*0.05f);}

    private void cargarTexturas() {

        texturaPersonaje=new Texture("asset"+assets+".png");
        texturaEnemigo=new Texture("enemigo"+assets+".png");
        texturaPersonajeAbajo=new Texture("asset"+assets+"Abajo.png");
    }



    @Override
    public void render(float delta) {

        //ACTUALIZACIONES (MOVER OBJETOS,COLISIONES.ETC)
        if(estadoJuego==EstadoJuego.JUGANDO) {
            actualizar(delta);
        }

        moverEnemigo(delta);
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        moverPersonaje(delta);
        batch.draw(texturaFondo,0,0);
        personaje.render(batch);
        enemigo.render(batch);
        marcador.render(batch);


        batch.end();
    }

    private void actualizar(float delta) {
        marcador.marcar(1);
    }

    private void moverPersonaje(float delta) {
        timerPersonaje=delta;
        switch (movimientoPersonaje){
            case ARRIBA:

                if (personaje.sprite.getY()<0.35*ALTO){
                    personaje.mover(20);
                    personaje.setTexture(texturaPersonaje);

                }
                break;
            case ABAJO:
                personaje.setTexture(texturaPersonajeAbajo);
            case QUIETO:
                personaje.sprite.setY(0.05f*ALTO);

                break;
            default:
                break;
        }
    }

    private void moverEnemigo(float delta){
        switch (movimientoEnemigo){
            case IZQUIERDA:
                enemigo.moverHorizontal(-7);
                timerEnemigo+=delta;
                if (timerEnemigo>0.5){
                    timerEnemigo=0;
                    pasoEnemigo=-pasoEnemigo;
                }
                enemigo.moverVertical(pasoEnemigo);
                if(enemigo.sprite.getX()<-100) {
                    enemigo.sprite.setPosition(ANCHO, rnd.nextFloat()*ALTO/4);
                }
                break;
            default:
                break;


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

    }

    private class ProcesadorEntrada implements InputProcessor {


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

            Vector3 v =new Vector3(screenX,screenY,0);
            camara.unproject(v);
            if(v.y>=ALTO/2){
                movimientoPersonaje=Movimiento.ARRIBA;
                if (v.y>=ALTO*0.85f && v.x>=ANCHO*0.9f){
                    juego.setScreen(new PantallaMenu(juego));
                }
            }
            else{
                movimientoPersonaje=Movimiento.ABAJO;
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            movimientoPersonaje=Movimiento.QUIETO;
            return true;
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
    //Movimiento
    private enum Movimiento{
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA,
        QUIETO
    }

    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        MUERTO
    }
}
