package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Random;

class PantallaJuego extends PantallaAbstracta {
    // Juego
    private final ControlJuego juego;
    private Random rnd = new Random();
    private final String assets;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;

    // Personaje
    private Personaje personaje;
    private Enemigo enemigo;
    private Enemigo enemigo1;
    private Enemigo enemigo2;
    private Enemigo enemigo3;

    // Texturas
    private Texture texturaPersonaje;
    private Texture texturaFondo;
    private Texture texturaEnemigo1;
    private Texture texturaEnemigo2;
    private Texture texturaEnemigo3;
    private Texture texturaPersonajeAbajo;

    // Movimientos
    private Movimiento movimientoPersonaje = Movimiento.QUIETO;
    private Movimiento movimientoEnemigo = Movimiento.IZQUIERDA;
    private float timerPersonaje = 0;
    private float timerEnemigo = 0;
    private int pasoEnemigo = 10;
    private float velocidadEnemigo;

    // Marcador
    private Marcador marcador;

    // Pausa
    private EscenaPausa escenaPausa;

    // Muerto
    private EscenaMuerto escenaMuerto;


    public PantallaJuego(ControlJuego juego, String assets) {
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

    private void createMarcador() {
        marcador = new Marcador(ANCHO/2,0.95f*ALTO);
    }

    private void createPersonaje() {
        personaje = new Personaje(texturaPersonaje,0,ALTO*0.05f);
    }

    private void createEnemigo(){
        enemigo1=new Enemigo(texturaEnemigo1,ANCHO,ALTO*0.05f,1);
        enemigo2=new Enemigo(texturaEnemigo2,ANCHO,ALTO*0.05f,2);
        enemigo3=new Enemigo(texturaEnemigo3,ANCHO,ALTO*0.05f,3);
        cambiarEnemigo();
    }

    private void cargarTexturas() {
        texturaPersonaje = new Texture("asset"+assets+".png");
        texturaEnemigo1 = new Texture("enemigo"+assets+"1.png");
        texturaEnemigo2 = new Texture("enemigo"+assets+"2.png");
        texturaEnemigo3 = new Texture("enemigo"+assets+"3.png");
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
        if(estadoJuego == EstadoJuego.JUGANDO){
            marcador.render(batch);
        }else if (estadoJuego == EstadoJuego.MUERTO){ marcador.render(batch);}

        batch.end();
        if(estadoJuego == EstadoJuego.PAUSADO){
            escenaPausa.draw();
        }
        if(estadoJuego == EstadoJuego.MUERTO){
            escenaMuerto.draw();
        }
    }

    private void actualizar(float delta) {
        marcador.marcar(1);
        if (velocidadEnemigo<=25) {
            velocidadEnemigo = 15 + marcador.getScore() * 0.0005f;

        }
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

    private void moverEnemigo(float delta) {
        switch (enemigo.getTipo()){
            case 1:
                enemigo.moverHorizontal(-velocidadEnemigo);
                timerEnemigo += delta;
                if (timerEnemigo > 0.5){
                    timerEnemigo = 0;
                    pasoEnemigo =- pasoEnemigo;
                }
                if (enemigo.sprite.getY()<=0){
                    timerEnemigo = 0;
                    pasoEnemigo =10;
                }
                if (enemigo.sprite.getY()>=0.35f*ALTO){
                    timerEnemigo = 0;
                    pasoEnemigo =-10;
                }
                if (enemigo.sprite.getX()>=ANCHO*0.6f) {
                    enemigo.moverVertical(pasoEnemigo);
                }

                if(enemigo.sprite.getX()<-300) {
                    cambiarEnemigo();
                    enemigo.sprite.setPosition(ANCHO, rnd.nextFloat()*ALTO/4);
                }
                break;
            case 2:
                enemigo.sprite.setY(-0.05f*ALTO);
                enemigo.moverHorizontal(-velocidadEnemigo);
                if(enemigo.sprite.getX()<-300) {
                    cambiarEnemigo();
                    enemigo.sprite.setPosition(ANCHO, rnd.nextFloat()*ALTO/4);
                }
                break;
            case 3:
                enemigo.sprite.setY(0.3f*ALTO);
                enemigo.moverHorizontal(-velocidadEnemigo);
                if(enemigo.sprite.getX()<-300) {
                    cambiarEnemigo();
                    enemigo.sprite.setPosition(ANCHO, rnd.nextFloat()*ALTO/4);
                }
                break;
            default:
                break;

        }
    }

    private void cambiarEnemigo() {
        switch ((int)(Math.floor(Math.random()*3+1))) {
            case 1:
                enemigo=enemigo1;
                break;
            case 2:
                enemigo=enemigo2;
                break;
            case 3:
                enemigo=enemigo3;
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

    @Override
    public InputProcessor getInputProcessor() {
        return new ProcesadorEntrada();
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

    //Clase Pausa (Ventana que se muestra cuando el usuario pausa la app)
    class EscenaPausa extends Stage
    {
        public EscenaPausa(final Viewport vista, final SpriteBatch batch) {
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
                    estadoJuego = estadoJuego.MUERTO;
                    if(escenaMuerto == null) {
                        escenaMuerto = new EscenaMuerto(vista, batch);
                    }

                    //juego.setScreen(new PantallaMenu(juego));
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
    //Clase Muerto (Ventana que se muestra cuando el usuario muere o sale)
    class EscenaMuerto extends Stage
    {
        public EscenaMuerto(Viewport vista, SpriteBatch batch) {
            super(vista, batch);

            Texture texturaMuerto = new Texture("calaverita.png");

            Image imgMuerto = new Image(texturaMuerto);
            imgMuerto.setPosition(0,0);


            //Boton regresar a Menu
            Boton botonMenu = new Boton("btnReturn.png","btnReturnP.png");
            botonMenu.setPosition(ANCHO/2-botonMenu.getWidth()/2,ALTO*0.2f);
            botonMenu.getBtn().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaMenu(juego));
                }
            });


            this.addActor(imgMuerto);
            this.addActor(botonMenu.getBtn());

            Gdx.input.setInputProcessor(this);
        }
    }

    // Estado del Juego
    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        MUERTO
    }

    // Movimiento
    private enum Movimiento{
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA,
        QUIETO
    }

    // Colisiones de enemigos
    private void probarColisiones() {
        Rectangle rectPersonaje = personaje.sprite.getBoundingRectangle();
        Rectangle rectEnemigo = enemigo.sprite.getBoundingRectangle();
        if(rectPersonaje.overlaps(rectEnemigo)){
            cambiarEnemigo();
            enemigo.sprite.setX(ANCHO);        }
    }
}
