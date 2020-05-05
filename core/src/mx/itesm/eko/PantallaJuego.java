package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

class PantallaJuego extends PantallaAbstracta {
    // Juego
    private final ControlJuego juego;
    private Random rnd = new Random();
    private final String assets;
    private EstadoJuego estadoJuego = EstadoJuego.JUGANDO;

    // Física
    private World mundoFisica;
    private Body bodyPersonaje;
    private static final float ANCHO_PERSONAJE = 20;

    // Texturas
    private Texture texturaPersonaje;
    private Texture texturaFondo;
    private Texture texturaEnemigo1;
    private Texture texturaEnemigo2;
    private Texture texturaEnemigo3;
    private Texture texturaPersonajeAbajo;
    private Texture texturaEnMov;


    // Personaje
    private Personaje personaje;
    private Enemigo enemigo;
    private Enemigo enemigo1;
    private Enemigo enemigo2;
    private Enemigo enemigo3;
    private BotonDinamico enemigoMov;

    //Fondo
    private FondoDinamico fondo1;
    private FondoDinamico fondo2;

    // Movimientos
    private Movimiento movimientoPersonaje = Movimiento.QUIETO;
    private Movimiento movimientoEnemigo = Movimiento.IZQUIERDA;
    private float timerPersonaje = 0;
    private float timerEnemigo = 0;
    private int pasoEnemigo = 10;
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

    // Constructor
    public PantallaJuego(ControlJuego juego, String assets) {
        this.juego = juego;
        this.assets = assets;
    }

    @Override
    public void show() {
        createMundoFisica();
        fisicaObjetos();
        crearParedes();
        cargarTexturas();
        createPersonaje();
        createEnemigo();
        createFondo();
        createMarcador();
        createParticulas();
        createVidas();

        texturaFondo=new Texture("Fondos/fondo"+assets+".jpg");

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void crearParedes() {
        Bordes.crearBordes(mundoFisica);
    }

    private void createFondo() {
        fondo1=new FondoDinamico(assets,0,0);
        fondo2=new FondoDinamico(assets,fondo1.getWidth(),fondo1.getHeightTiempo());
        texturaBotonPausa = new Texture("Botones/btnPausa.png");
        botonPausa = new Objeto(texturaBotonPausa, ANCHO*0.90f, ALTO*0.85f);
    }

    private void createVidas() {vidas = new Vidas(0,0.9f*ALTO,120,assets);
    }


    private void createParticulas() {
        sistemaParticulas=new ParticleEffect();
        sistemaParticulas.load(Gdx.files.internal("Particulas/particulas"+assets+".p"),Gdx.files.internal("Particulas"));
        Array<ParticleEmitter> emisores=sistemaParticulas.getEmitters();
        emisorParticulas=emisores.get(0);
        emisores.get(0).setPosition(ANCHO,ALTO/2);
        sistemaParticulas.start();
    }

    private void createMarcador() {
        marcador = new Marcador(ANCHO/2,0.95f*ALTO);
    }

    private void fisicaObjetos() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, ALTO * 0.15f);
        bodyPersonaje = mundoFisica.createBody(bodyDef);

        PolygonShape cajaFisica = new PolygonShape();
        cajaFisica.setAsBox(ANCHO_PERSONAJE, ANCHO_PERSONAJE * 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = cajaFisica;

        //Se hacen físicas diferentes para cada asset
        if (assets.equals("assetOso.png")){
            fixtureDef.density = 0.5f;
            fixtureDef.restitution = 0.1f;
        }
        else if (assets.equals("assetElefante.png")){
            fixtureDef.density = 0.7f;
            fixtureDef.restitution = 0.0f;
        }
        else if (assets.equals("assetTortuga.png")){
            fixtureDef.density = 0.3f;
            fixtureDef.restitution = 0.2f;
        }

        bodyPersonaje.createFixture(fixtureDef);
        cajaFisica.dispose();
        bodyPersonaje.setFixedRotation(true);
    }

    private void createMundoFisica() {
        Box2D.init();
        Vector2 gravedad = new Vector2(0, -3.5f);
        mundoFisica = new World(gravedad, true);
    }

    private void createPersonaje() {
        personaje = new Personaje(texturaPersonaje,0,ALTO*0.05f);
    }

    private void createEnemigo(){
        enemigo1 = new Enemigo(texturaEnemigo1,ANCHO,ALTO*0.05f,1);
        enemigo2 = new Enemigo(texturaEnemigo2,ANCHO,ALTO*0.05f,2);
        enemigo3 = new Enemigo(texturaEnemigo3,ANCHO,ALTO*0.05f,3);
        enemigoMov = new BotonDinamico(texturaEnMov, texturaEnMov,119, 200,ANCHO, ALTO);
        enemigoMov.cargarEnemigo1();
        cambiarEnemigo();
    }

    private void cargarTexturas() {
        texturaPersonaje = new Texture("Personajes/asset"+assets+".png");
        texturaEnemigo1 = new Texture("Enemigos/enemigo"+assets+"1.png");
        texturaEnemigo2 = new Texture("Enemigos/enemigo"+assets+"2.png");
        texturaEnemigo3 = new Texture("Enemigos/enemigo"+assets+"3.png");
        texturaPersonajeAbajo = new Texture("Personajes/asset"+assets+"Abajo.png");
        texturaEnMov = new Texture("Enemigos/enemigo"+assets+"Animado1.png");
    }

    @Override
    public void render(float delta) {
        //ACTUALIZACIONES (MOVER OBJETOS,COLISIONES.ETC)
        if(estadoJuego == EstadoJuego.JUGANDO) {
            actualizar(delta);
            moverEnemigo(delta);
            moverFondo(delta);
            probarColisiones();
        }

        float x = bodyPersonaje.getPosition().x - ANCHO_PERSONAJE;
        float y = bodyPersonaje.getPosition().y - ANCHO_PERSONAJE * 2;

        personaje.getSprite().setPosition(x, y);

        borrarPantalla(0,0,0);
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        moverPersonaje(delta);
        batch.draw(texturaFondo,0,0);
        renderFondo(batch);
        personaje.render(batch);
        enemigoMov.render(batch, enemigo.sprite.getX(), enemigo.sprite.getY());
        if(estadoJuego == EstadoJuego.JUGANDO){
            vidas.render(batch);
            marcador.render(batch);
            botonPausa.render(batch);
        }else if (estadoJuego == EstadoJuego.MUERTO){ marcador.render(batch);}

        sistemaParticulas.draw(batch);
        batch.end();
        if(estadoJuego == EstadoJuego.PAUSADO){
            escenaPausa.draw();
        }
        if(estadoJuego == EstadoJuego.MUERTO){
            juego.stopMusica();
            score = marcador.getScore();
            juego.setScreen(new PantallaMuerto(vista,batch,score,juego));
        }

        //La simulación física se actualiza
        mundoFisica.step(1/60f, 6, 2);

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
        fondo1.moverCapas(-delta*1000);
        fondo2.moverCapas(-delta*1000);
        fondo1.moverCapasVertical(-delta*100);
        fondo2.moverCapasVertical(-delta*100);

        if (fondo1.getYCapaTiempo()<-fondo1.getHeightTiempo()){
            fondo1.setYCapaTiempo(fondo2.getYCapaTiempo()+fondo2.getHeightTiempo());
        }
        if (fondo2.getYCapaTiempo()<-fondo2.getHeightTiempo()){
            fondo2.setYCapaTiempo(fondo1.getYCapaTiempo()+fondo1.getHeightTiempo());
        }

        if (fondo1.getYCapaSol()<-fondo1.getHeightSol()){
            fondo1.setYCapaSol(fondo2.getYCapaSol()+fondo2.getHeightSol());
        }
        if (fondo2.getYCapaSol()<-fondo2.getHeightSol()){
            fondo2.setYCapaSol(fondo1.getYCapaSol()+fondo1.getHeightSol());
        }




        if (fondo1.getXCapa1()<-fondo1.getWidth()){
            fondo1.setXCapa1(fondo2.getXCapa1()+fondo2.getWidth());
        }
        if (fondo2.getXCapa1()<-fondo2.getWidth()){
            fondo2.setXCapa1(fondo1.getXCapa1()+fondo1.getWidth());
        }

        if (fondo1.getXCapa2()<-fondo1.getWidth()){
            fondo1.setXCapa2(fondo2.getXCapa2()+fondo2.getWidth());
        }
        if (fondo2.getXCapa2()<-fondo2.getWidth()){
            fondo2.setXCapa2(fondo1.getXCapa2()+fondo1.getWidth());
        }

        if (fondo1.getXCapa3()<-fondo1.getWidth()){
            fondo1.setXCapa3(fondo2.getXCapa3()+fondo2.getWidth());
        }
        if (fondo2.getXCapa3()<-fondo2.getWidth()){
            fondo2.setXCapa3(fondo1.getXCapa3()+fondo1.getWidth());
        }

        if (fondo1.getXCapa4()<-fondo1.getWidth()){
            fondo1.setXCapa4(fondo2.getXCapa4()+fondo2.getWidth());
        }
        if (fondo2.getXCapa4()<-fondo2.getWidth()){
            fondo2.setXCapa4(fondo1.getXCapa4()+fondo1.getWidth());
        }
    }

    private void actualizar(float delta) {
        marcador.marcar(1);
        if (velocidadEnemigo<=25) {
            velocidadEnemigo = 15 + marcador.getScore() * 0.0005f;

        }
        sistemaParticulas.update(delta);

        if (vidas.getVidas()==0){
            estadoJuego=EstadoJuego.MUERTO;
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

            float x = bodyPersonaje.getPosition().x;
            float y = bodyPersonaje.getPosition().y;


            Vector3 v = new Vector3(screenX,screenY,0);
            camara.unproject(v);
            if(v.y >= ALTO/2 && !(v.y>=ALTO*0.85f && v.x>=ANCHO*0.9f)) {
                //audio.setEfecto("salto.mp3");
                //movimientoPersonaje = Movimiento.ARRIBA;
                bodyPersonaje.applyLinearImpulse(0, 180000, x, y, true);
            }
                //Detectar pausa
            if (v.y>=ALTO*0.85f && v.x>=ANCHO*0.9f){
                    estadoJuego = EstadoJuego.PAUSADO;
                    //audio.setEfecto("pausa.mp3");
                    if(escenaPausa == null){
                        juego.setVolumen(0.3f);
                        juego.setEfecto("Audios/efectoPausa.mp3");
                        escenaPausa = new EscenaPausa(vista, batch);
                    }
                }
            else  if(v.y < ALTO/2){
                //audio.setEfecto("agacharse.mp3");
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

            Texture texturaPausa = new Texture("Fondos/pantallaPausa.png");

            Image imgPausa = new Image(texturaPausa);
            imgPausa.setPosition(0,0);

            //Boton pantalla muerto
            Boton botonMenu = new Boton("Botones/btnReturn.png","Botones/btnReturnP.png");
            botonMenu.setPosition(ANCHO/3-botonMenu.getWidth()/2,ALTO*0.2f);
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
            Boton botonBack = new Boton("Botones/botonContinue.png","Botones/botonContinue.png");
            botonBack.setPosition(ANCHO/2+(botonBack.getWidth()/2)-70,ALTO*0.2f);
            botonBack.getBtn().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    //Cambia estado
                    estadoJuego = EstadoJuego.JUGANDO;
                    escenaPausa = null;
                    juego.setEfecto("Audios/efectoBoton.mp3");
                    juego.setVolumen100();
                    Gdx.input.setInputProcessor(new ProcesadorEntrada());

                }
            });

            this.addActor(imgPausa);
            this.addActor(botonBack.getBtn());
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
            enemigo.sprite.setX(ANCHO);
            vidas.restar(1);
        }
    }
}
