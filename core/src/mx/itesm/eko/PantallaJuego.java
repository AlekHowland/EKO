package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

class PantallaJuego extends PantallaAbstracta {
    private final Juego juego;
    private Random rnd = new Random();
    private final String assets;

    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;

    private Personaje personaje;
    private Enemigo enemigo;

    private Texture texturaPersonaje;
    private Texture texturaFondo;
    private Texture texturaEnemigo;
    private Texture texturaPersonajeAbajo;

    private Movimiento movimientoPersonaje = Movimiento.QUIETO;
    private Movimiento movimientoEnemigo = Movimiento.IZQUIERDA;
    private float timerPersonaje = 0;
    private float timerEnemigo = 0;
    private int pasoEnemigo = 5;

    private Marcador marcador;

    //Pausa
    private EscenaPausa escenaPausa;

    public PantallaJuego(Juego juego,String assets) {
        this.juego = juego;
        this.assets = assets;
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

    private void createMarcador() {marcador = new Marcador(ANCHO/2,0.95f*ALTO);}

    private void createPersonaje() {personaje = new Personaje(texturaPersonaje,0,ALTO*0.05f);}

    private void createEnemigo(){enemigo=new Enemigo(texturaEnemigo,ANCHO,ALTO*0.05f,1);}

    private void cargarTexturas() {
        texturaPersonaje = new Texture("asset"+assets+".png");
        texturaEnemigo = new Texture("enemigo"+assets+".png");
        texturaPersonajeAbajo = new Texture("asset"+assets+"Abajo.png");
    }



    @Override
    public void render(float delta) {

        //ACTUALIZACIONES (MOVER OBJETOS,COLISIONES.ETC)
        if(estadoJuego == EstadoJuego.JUGANDO) {
            actualizar(delta);
            moverEnemigo(delta);
            probarColisiones();
        }

        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        moverPersonaje(delta);
        batch.draw(texturaFondo,0,0);
        personaje.render(batch);
        enemigo.render(batch);
        marcador.render(batch);

        batch.end();
        if(estadoJuego == EstadoJuego.PAUSADO){
            escenaPausa.draw();
        }
    }

    private void actualizar(float delta) {
        marcador.marcar(1);
    }

    private void moverPersonaje(float delta) {
        timerPersonaje = delta;
        switch (movimientoPersonaje){
            case ARRIBA:

                if (personaje.sprite.getY()<0.35*ALTO){
                    personaje.mover(20);
                    personaje.setTexture(texturaPersonaje);


                }
                break;
            case ABAJO:
                personaje.setTexture(texturaPersonajeAbajo);
                break;
            case QUIETO:
                personaje.setTexture(texturaPersonaje);
                if(personaje.sprite.getY()>0.05f*ALTO)
                    personaje.mover(-20);

                break;
            default:
                break;
        }
    }

    private void moverEnemigo(float delta){
        switch (movimientoEnemigo){
            case IZQUIERDA:
                enemigo.moverHorizontal(-9);
                timerEnemigo += delta;
                if (timerEnemigo > 0.5){
                    timerEnemigo = 0;
                    pasoEnemigo =- pasoEnemigo;
                }
                enemigo.moverVertical(pasoEnemigo);
                if(enemigo.sprite.getX()<-300) {
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

            Vector3 v = new Vector3(screenX,screenY,0);
            camara.unproject(v);
            if(v.y >= ALTO/2 && !(v.y>=ALTO*0.85f && v.x>=ANCHO*0.9f)) {
                movimientoPersonaje = Movimiento.ARRIBA;
            }
                //Detectar pausa
            if (v.y>=ALTO*0.85f && v.x>=ANCHO*0.9f){
                    estadoJuego = EstadoJuego.PAUSADO;
                    if(escenaPausa == null){
                        escenaPausa = new EscenaPausa(vista, batch);
                    }
                }

            else  if(v.y < ALTO/2){
                movimientoPersonaje = Movimiento.ABAJO;
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            movimientoPersonaje = Movimiento.QUIETO;
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

    //Clase Pausa (Ventana que se muestra cuando el usuario pausa la app)
    class EscenaPausa extends Stage
    {


        public EscenaPausa(Viewport vista, SpriteBatch batch){
            super(vista, batch);

            Texture texturaPausa = new Texture("pantallaPausa.png");

            Image imgPausa = new Image(texturaPausa);
            imgPausa.setPosition(0,0);

            //Boton regresar a Menu
            Boton botonMenu = new Boton("btnReturn.png","btnReturnP.png");
            botonMenu.setPosition(ANCHO/3-botonMenu.getWidth()/2,ALTO*0.2f);
            botonMenu.getBtn().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaMenu(juego));
                }
            });

            //Boton regresar a juego
            Boton botonBack = new Boton("btnReturn.png","btnReturnP.png");
            botonBack.setPosition(ANCHO/2+botonBack.getWidth()/2,ALTO*0.2f);
            botonBack.getBtn().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    //Cambia estado
                    estadoJuego = EstadoJuego.JUGANDO;
                    escenaPausa = null;

                }
            });

            this.addActor(imgPausa);
            this.addActor(botonBack.getBtn());
            this.addActor(botonMenu.getBtn());

            Gdx.input.setInputProcessor(this);
        }
    }

    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        MUERTO
    }

    private void probarColisiones() {
        Rectangle rectPersonaje = personaje.sprite.getBoundingRectangle();
        Rectangle rectEnemigo = enemigo.sprite.getBoundingRectangle();
        if(rectPersonaje.overlaps(rectEnemigo)){
            enemigo.sprite.setPosition(ANCHO,rnd.nextFloat()*ALTO/4);
        }
    }
}
