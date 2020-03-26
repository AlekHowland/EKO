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
    //Personaje
    private Personaje personaje;
    private Enemigo enemigo;

    private Texture texturaPersonaje;
    private Texture texturaFondo;
    private Texture texturaEnemigo;

    private Movimiento movimientoPersonaje=Movimiento.QUIETO;
    private Movimiento movimientoEnemigo=Movimiento.IZQUIERDA;


    public PantallaJuego(Juego juego,String assets) {
        this.juego=juego;
        this.assets=assets;
    }

    @Override
    public void show() {
        cargarTexturas();
        createPersonaje();
        createEnemigo();


        texturaFondo=new Texture("fondo"+assets+".jpg");

        Gdx.input.setInputProcessor(new ProcesadorEntrada());


    }

    private void createPersonaje() {
        personaje =new Personaje(texturaPersonaje,0,ALTO*0.05f);
    }

    private void createEnemigo(){ enemigo=new Enemigo(texturaEnemigo,ANCHO,ALTO*0.05f);}

    private void cargarTexturas() {

        texturaPersonaje=new Texture("asset"+assets+".png");
        texturaEnemigo=new Texture("enemigo"+assets+".png");
    }



    @Override
    public void render(float delta) {

        //ACTUALIZACIONES (MOVER OBJETOS,COLISIONES.ETC)
        moverPersonaje();
        moverEnemigo();
        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo,0,0);
        personaje.render(batch);
        enemigo.render(batch);
        batch.end();
    }

    private void moverPersonaje() {
        switch (movimientoPersonaje){
            case ARRIBA:
                personaje.mover(10);
                break;
            case ABAJO:
                personaje.mover(-10);
                break;
            default:
                break;
        }
    }

    private void moverEnemigo(){
        switch (movimientoEnemigo){
            case IZQUIERDA:
                enemigo.mover(-10);
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
                //Derecha
                movimientoPersonaje=Movimiento.ARRIBA;
                if (v.y>=ALTO*0.85f && v.x>=ANCHO*0.9f){
                    juego.setScreen(new PantallaMenu(juego));
                }
            }
            else{
                //Izquierda
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

}
