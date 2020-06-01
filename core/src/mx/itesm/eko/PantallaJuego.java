package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.Random;
import mx.itesm.eko.musica.ControladorAudio;

class PantallaJuego extends PantallaAbstracta implements GestureDetector.GestureListener {
    // Juego
    private final ControlJuego juego;
    private Random rnd = new Random();
    private final String assets;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;

    // Texturas
    private Texture texturaPersonaje;
    private Texture texturaFondo;
    private Texture texturaEnemigo1;
    private Texture texturaEnemigo2;
    private Texture texturaEnemigo3;
    private Texture texturaPersonajeAbajo;
    private Texture texturaHuevo;


    // Personaje
    private Personaje personaje;
    private Enemigo enemigo;
    private Enemigo enemigo1;
    private Enemigo enemigo2;
    private Enemigo enemigo3;

    //Item
    private Item huevo;
    private int x;
    private int z;
    private boolean boolItem = false;

    //Fondo
    private FondoDinamico fondo1;
    private FondoDinamico fondo2;

    // Movimientos
    private Movimiento movimientoPersonaje = Movimiento.QUIETO;
    private Movimiento movimientoEnemigo = Movimiento.IZQUIERDA;
    private float timerPersonaje = 0;
    private float timerEnemigo = 0;
    private float timerItem = 0;
    private int pasoEnemigo = 10;
    private int pasoItem = 15;
    private float velocidadEnemigo;

    //Sistema de partículas
    private ParticleEffect sistemaParticulas;
    private ParticleEmitter emisorParticulas;

    // Marcador
    private Marcador marcador;
    private float score;

    // Vidas
    private Vidas vidas;

    // Pausa
    private EscenaPausa escenaPausa;
    private Objeto botonPausa;
    private Texture texturaBotonPausa;
    private float timerPausa=2;

    //Dificultad
    private final float MAXDIF = 15;
    private float dificultad = 0.005f;

    //Gestos
    private GestureDetector gestureDetector;
    private InputProcessor inputProcessor;
    private InputMultiplexer inputMultiplexer;

    // Constructor
    public PantallaJuego(ControlJuego juego, String assets) {
        this.juego = juego;
        this.assets = assets;
    }

    @Override
    public void show() {
        cargarTexturas();
        createPersonaje();
        createEnemigo();
        createFondo();
        createMarcador();
        createParticulas();
        createVidas();
        createItem();

        texturaFondo = new Texture("Fondos/fondo" + assets + ".jpg");

        //Gdx.input.setInputProcessor(new ProcesadorEntrada());
        gestureDetector = new GestureDetector(this);
        inputProcessor = new ProcesadorEntrada();
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
        //Vamos a atrapar la tecla de back
        //Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void createItem() {
        huevo = new Item(texturaHuevo, ANCHO, ALTO * 0.05f);
    }

    private void createFondo() {
        fondo1 = new FondoDinamico(assets, 0, 0);
        fondo2 = new FondoDinamico(assets, fondo1.getWidth(), fondo1.getHeightTiempo());
        texturaBotonPausa = new Texture("Botones/btnPausa.png");
        botonPausa = new Objeto(texturaBotonPausa, ANCHO * 0.90f, ALTO * 0.85f);
    }

    private void createVidas() {
        vidas = new Vidas(0, 0.9f * ALTO, 3, assets);
    }


    private void createParticulas() {
        sistemaParticulas = new ParticleEffect();
        sistemaParticulas.load(Gdx.files.internal("Particulas/particulas" + assets + ".p"), Gdx.files.internal("Particulas"));
        Array<ParticleEmitter> emisores = sistemaParticulas.getEmitters();
        emisorParticulas = emisores.get(0);
        emisores.get(0).setPosition(ANCHO, ALTO / 2);
        sistemaParticulas.start();
    }

    private void createMarcador() {
        marcador = new Marcador(ANCHO / 2, 0.95f * ALTO);
    }

    private void createPersonaje() {
        personaje = new Personaje(texturaPersonaje, 0, ALTO * 0.05f, assets);
        personaje.cargarTexturas();
    }

    private void createEnemigo() {
        enemigo1 = new Enemigo(texturaEnemigo1, ANCHO, ALTO * 0.05f, 1, assets);
        enemigo1.cargarTexturas();
        enemigo2 = new Enemigo(texturaEnemigo2, ANCHO, ALTO * 0.05f, 2, assets);
        enemigo2.cargarTexturas();
        enemigo3 = new Enemigo(texturaEnemigo3, ANCHO, ALTO * 0.05f, 3, assets);
        enemigo3.cargarTexturas();
        cambiarEnemigo();
    }

    private void cargarTexturas() {
        texturaPersonaje = new Texture("Personajes/asset" + assets + ".png");
        texturaEnemigo1 = new Texture("Enemigos/enemigo" + assets + "1.png");
        texturaEnemigo2 = new Texture("Enemigos/enemigo" + assets + "2.png");
        texturaEnemigo3 = new Texture("Enemigos/enemigo" + assets + "3.png");
        texturaPersonajeAbajo = new Texture("Personajes/asset" + assets + "Abajo.png");
        texturaHuevo = new Texture("Personajes/huevo.png");
    }

    @Override
    public void render(float delta) {
        //ACTUALIZACIONES (MOVER OBJETOS,COLISIONES.ETC)


        borrarPantalla(0, 0, 0);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();

        batch.draw(texturaFondo, 0, 0);
        renderFondo(batch);

        //Hitbox
        if (false) {
            personaje.render(batch);
            enemigo.render(batch);
            huevo.render(batch);
        }


        if (estadoJuego == EstadoJuego.JUGANDO) {
            actualizarSaltoPersonaje(delta);
            actualizar(delta);
            enemigo.renderAnimacion(batch);
            moverEnemigo(delta);
            moverFondo(delta);
            probarColisiones();
            hayItem();
            moverPersonaje(delta);
            if (boolItem) {
                huevo.renderAnimacion(batch);
                moverItem(delta);
                probarColisionesHuevo();
            }
        }


        if (estadoJuego == EstadoJuego.JUGANDO) {
            vidas.render(batch);
            marcador.render(batch);
            botonPausa.render(batch);
        } else if (estadoJuego == EstadoJuego.MUERTO) {
            marcador.render(batch);
        }

        sistemaParticulas.draw(batch);
        batch.end();
        if (estadoJuego == EstadoJuego.PAUSADO) {
            escenaPausa.draw();
            moverEnemigoPausa(delta);
        }
        if (estadoJuego == EstadoJuego.MUERTO) {
            juego.stopMusica();
            score = marcador.getScore();
            juego.setScreen(new PantallaMuerto(vista, batch, score, juego));
        }

        timerPausa+=delta;
        if(Gdx.input.isKeyPressed(Input.Keys.BACK) && timerPausa>0.5f){
            if(estadoJuego==EstadoJuego.JUGANDO){
                juego.setVolumen(0.3f);
                juego.setEfecto("Audios/efectoPausa.mp3");
                escenaPausa = new EscenaPausa(vista, batch);
                estadoJuego=EstadoJuego.PAUSADO;
            }
            else if (estadoJuego==EstadoJuego.PAUSADO){
                escenaPausa = null;
                juego.setVolumen100();
                juego.setEfecto("Audios/efectoBoton.mp3");
                Gdx.input.setInputProcessor(getInputProcessor());
                estadoJuego = EstadoJuego.JUGANDO;
            }
            timerPausa=0;
        }
    }

    private void moverEnemigoPausa(float delta) {
        float a = enemigo.sprite.getX();
        float b = enemigo.sprite.getY();
        enemigo.setPosition(a, b);
    }

    private void hayItem() {
        z = 0;
        x = marcador.getContador();
        if (x % 250 == 0) {
            z = (int) (Math.random() * 1000);

        } else {
            z = 20;
        }

        if (z > 850) {
            boolItem = true;
        }
    }

    private void moverItem(float delta) {

        huevo.moverHorizontal(-velocidadEnemigo * 0.5f);
        timerItem += delta;
        if (timerItem > 0.5) {
            timerItem = 0;
            timerItem = -pasoItem;
        }
        if (huevo.sprite.getY() <= 0) {
            timerItem = 0;
            pasoItem = 15;
        }
        if (huevo.sprite.getY() >= 0.25f * ALTO) {
            timerItem = 0;
            pasoItem = -15;
        }
        if (huevo.sprite.getX() >= ANCHO * 0.45f) {
            huevo.moverVertical(pasoItem);
        }

        if (huevo.sprite.getX() < -300) {
            huevo.sprite.setPosition(ANCHO, rnd.nextFloat() * ALTO / 4);
            boolItem = false;
        }
    }

    private void renderFondo(SpriteBatch batch) {
        fondo1.renderCapaTiempo(batch);
        fondo2.renderCapaTiempo(batch);
        fondo1.renderCapaSol(batch);
        fondo2.renderCapaSol(batch);

        fondo1.renderCapa1(batch);
        fondo2.renderCapa1(batch);
        fondo1.renderCapa2(batch);
        fondo2.renderCapa2(batch);
        fondo1.renderCapa3(batch);
        fondo2.renderCapa3(batch);
        fondo1.renderCapa4(batch);
        fondo2.renderCapa4(batch);
    }

    private void moverFondo(float delta) {
        fondo1.moverCapas(-velocidadEnemigo);
        fondo2.moverCapas(-velocidadEnemigo);
        fondo1.moverCapasVertical(-delta * 100);
        fondo2.moverCapasVertical(-delta * 100);

        if (fondo1.getYCapaTiempo() < -fondo1.getHeightTiempo()) {
            fondo1.setYCapaTiempo(fondo2.getYCapaTiempo() + fondo2.getHeightTiempo());
        }
        if (fondo2.getYCapaTiempo() < -fondo2.getHeightTiempo()) {
            fondo2.setYCapaTiempo(fondo1.getYCapaTiempo() + fondo1.getHeightTiempo());
        }

        if (fondo1.getYCapaSol() < -fondo1.getHeightSol()) {
            fondo1.setYCapaSol(fondo2.getYCapaSol() + fondo2.getHeightSol());
        }
        if (fondo2.getYCapaSol() < -fondo2.getHeightSol()) {
            fondo2.setYCapaSol(fondo1.getYCapaSol() + fondo1.getHeightSol());
        }


        if (fondo1.getXCapa1() < -fondo1.getWidth()) {
            fondo1.setXCapa1(fondo2.getXCapa1() + fondo2.getWidth());
        }
        if (fondo2.getXCapa1() < -fondo2.getWidth()) {
            fondo2.setXCapa1(fondo1.getXCapa1() + fondo1.getWidth());
        }

        if (fondo1.getXCapa2() < -fondo1.getWidth()) {
            fondo1.setXCapa2(fondo2.getXCapa2() + fondo2.getWidth());
        }
        if (fondo2.getXCapa2() < -fondo2.getWidth()) {
            fondo2.setXCapa2(fondo1.getXCapa2() + fondo1.getWidth());
        }

        if (fondo1.getXCapa3() < -fondo1.getWidth()) {
            fondo1.setXCapa3(fondo2.getXCapa3() + fondo2.getWidth());
        }
        if (fondo2.getXCapa3() < -fondo2.getWidth()) {
            fondo2.setXCapa3(fondo1.getXCapa3() + fondo1.getWidth());
        }

        if (fondo1.getXCapa4() < -fondo1.getWidth()) {
            fondo1.setXCapa4(fondo2.getXCapa4() + fondo2.getWidth());
        }
        if (fondo2.getXCapa4() < -fondo2.getWidth()) {
            fondo2.setXCapa4(fondo1.getXCapa4() + fondo1.getWidth());
        }
    }

    private void actualizar(float delta) {
        marcador.marcar(1);
        marcador.contar(1);
        if (velocidadEnemigo <= MAXDIF) {
            velocidadEnemigo = 15 + marcador.getScore() * dificultad;

        }
        sistemaParticulas.update(delta);

        if (vidas.getVidas() == 0) {
            estadoJuego = EstadoJuego.MUERTO;
        }
    }

    private void moverPersonaje(float delta) {
        timerPersonaje += delta;

        switch (movimientoPersonaje) {
            case ABAJO:
                personaje.renderAgachar(batch);
                personaje.setTexture(texturaPersonajeAbajo);
                if (timerPersonaje >= 0.7f) {
                    movimientoPersonaje = Movimiento.QUIETO;
                }
                break;
            case QUIETO:
                if (personaje.sprite.getY() >= ALTO * 0.11f) {
                    personaje.setTexture(texturaPersonajeAbajo);
                    if (personaje.timerAnimacion >= 1) {
                        personaje.timerAnimacion = 0;
                    }
                    personaje.renderSaltar(batch);
                } else {
                    personaje.setTexture(texturaPersonaje);

                    personaje.renderCorrer(batch);
                }

                break;
            default:
                break;
        }
    }

    private void actualizarSaltoPersonaje(float delta) {

        personaje.actualizarSalto(delta);
    }

    private void moverEnemigo(float delta) {
        switch (enemigo.getTipo()) {
            case 1:
                enemigo.moverHorizontal(-velocidadEnemigo);
                timerEnemigo += delta;
                if (timerEnemigo > 0.5) {
                    timerEnemigo = 0;
                    pasoEnemigo = -pasoEnemigo;
                }
                if (enemigo.sprite.getY() <= 0) {
                    timerEnemigo = 0;
                    pasoEnemigo = 10;
                }
                if (enemigo.sprite.getY() >= 0.35f * ALTO) {
                    timerEnemigo = 0;
                    pasoEnemigo = -10;
                }
                if (enemigo.sprite.getX() >= ANCHO * 0.6f) {
                    enemigo.moverVertical(pasoEnemigo);
                }

                if (enemigo.sprite.getX() < -300) {
                    cambiarEnemigo();
                    enemigo.sprite.setPosition(ANCHO, rnd.nextFloat() * ALTO / 4);
                }
                break;
            case 2:
                enemigo.sprite.setY(-0.03f * ALTO);
                enemigo.moverHorizontal(-velocidadEnemigo);
                if (enemigo.sprite.getX() < -300) {
                    cambiarEnemigo();
                    enemigo.sprite.setPosition(ANCHO, rnd.nextFloat() * ALTO / 4);
                }
                break;
            case 3:
                enemigo.sprite.setY(0.2f * ALTO);
                enemigo.moverHorizontal(-velocidadEnemigo);
                if (enemigo.sprite.getX() < -300) {
                    cambiarEnemigo();
                    enemigo.sprite.setPosition(ANCHO, rnd.nextFloat() * ALTO / 4);
                }
                break;
            default:
                break;

        }
    }

    private void cambiarEnemigo() {
        switch ((int) (Math.floor(Math.random() * 3 + 1))) {
            case 1:
                enemigo = enemigo1;
                break;
            case 2:
                enemigo = enemigo2;
                break;
            case 3:
                enemigo = enemigo3;
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
        return gestureDetector;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        Vector3 touchPos = new Vector3(x, y, 0);
        camara.unproject(touchPos);
        if (touchPos.y >= ALTO * 0.85f && touchPos.x >= ANCHO * 0.9f) {
            estadoJuego = EstadoJuego.PAUSADO;
            if (escenaPausa == null) {
                juego.setVolumen(0.3f);
                juego.setEfecto("Audios/efectoPausa.mp3");
                escenaPausa = new EscenaPausa(vista, batch);
            }
        }
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (velocityY < 0) {
            personaje.saltar();
            movimientoPersonaje = Movimiento.QUIETO;
        }
        if (velocityY > 0 && !(personaje.estaSaltando())) {
            timerPersonaje = 0;
            movimientoPersonaje = Movimiento.ABAJO;

        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    //@Override
    //public InputProcessor getInputProcessor() {
    //    return new InputProcessor() {
    //    };
    //}

    //@Override
    //public boolean touchDown(float x, float y, int pointer, int button) {
    //    return false;
    //}


    //@Override
    //public InputProcessor getInputProcessor() {
    //    return null;
    //}


    //Clase Pausa (Ventana que se muestra cuando el usuario pausa la app)
    class EscenaPausa extends Stage {
        public EscenaPausa(final Viewport vista, final SpriteBatch batch) {
            super(vista, batch);

            Texture texturaPausa = new Texture("Fondos/pantallaPausa.png");

            Image imgPausa = new Image(texturaPausa);
            imgPausa.setPosition(0, 0);

            //Boton pantalla muerto
            Boton botonMenu = new Boton("Botones/btnMenuFondo.png", "Botones/btnMenuFondoP.png");
            botonMenu.setPosition(ANCHO / 3 - botonMenu.getWidth() / 2, ALTO * 0.2f);
            botonMenu.getBtn().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.stopMusica();
                    juego.setEfecto("Audios/efectoBoton.mp3");
                    estadoJuego = estadoJuego.MUERTO;


                }
            });

            //Boton regresar a juego
            Boton botonBack = new Boton("Botones/botonContinue.png", "Botones/botonContinueP.png");
            botonBack.setPosition(ANCHO / 2 + (botonBack.getWidth() / 2) - 70, ALTO * 0.2f);
            botonBack.getBtn().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    //Cambia estado
                    estadoJuego = EstadoJuego.JUGANDO;
                    escenaPausa = null;
                    juego.setEfecto("Audios/efectoBoton.mp3");
                    juego.setVolumen100();
                    //Gdx.input.setInputProcessor(new ProcesadorEntrada());
                    Gdx.input.setInputProcessor(getInputProcessor());

                }
            });

            this.addActor(imgPausa);
            this.addActor(botonBack.getBtn());
            this.addActor(botonMenu.getBtn());

            //Gdx.input.setInputProcessor(this);
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
    private enum Movimiento {
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA,
        QUIETO
    }

    private void regresarHuevo() {
        huevo.sprite.setY(ALTO);
        huevo.sprite.setX(ANCHO);
    }

    private void probarColisionesHuevo() {
        Rectangle rectItem = huevo.sprite.getBoundingRectangle();
        Rectangle rectPersonaje = personaje.sprite.getBoundingRectangle();
        if (rectPersonaje.overlaps(rectItem)) {
            //huevo.sprite.setY(-100);
            juego.setEfecto("Audios/efectoItem.mp3");
            vidas.sumar(1);
            marcador.resetContador();
            huevo.sprite.setX(ANCHO);
            boolItem = false;
        }
    }

    // Colisiones de enemigos
    private void probarColisiones() {
        Rectangle rectPersonaje = personaje.sprite.getBoundingRectangle();
        Rectangle rectEnemigo = enemigo.sprite.getBoundingRectangle();
        if (rectPersonaje.overlaps(rectEnemigo)) {
            juego.setEfecto("Audios/efectoGolpe" + assets + ".mp3");
            cambiarEnemigo();
            enemigo.sprite.setX(ANCHO*2);
            vidas.restar(1);
        }

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
}
