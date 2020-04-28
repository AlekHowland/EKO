package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import mx.itesm.eko.musica.ControladorAudio;
import mx.itesm.eko.transiciones.TransicionPantalla;
import mx.itesm.eko.transiciones.efectoTransicion;

public class PantallaMenu extends PantallaAbstracta
{
    // Juego & Texturas
    private final ControlJuego juego;
    private Texture texturaFondo;
    private Texture texturaBtnPlay;
    private Texture texturaBtnPlayP;

    // Menu
    private Stage escenaMenu; //botones...
    private Scores scores;

    // Audio
    public final ControladorAudio audioMenu = new ControladorAudio();
    private boolean creado = false;


    //Botones
    private BotonDinamico botonPlay;

    //Sistema de partículas
    private ParticleEffect sistemaParticulas;
    private ParticleEmitter emisorParticulas;

    @Override
    public InputProcessor getInputProcessor() {
        return escenaMenu;
    }

    public PantallaMenu(ControlJuego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo=new Texture("Fondos/fondoMenu.jpg");
        texturaBtnPlay=new Texture("Botones/btnPlay.png");
        texturaBtnPlayP=new Texture("Botones/btnPlayP.png");
        crearBotones();
        crearMenu();
        createParticulas();
    }

    private void crearBotones() {
        botonPlay=new BotonDinamico(texturaBtnPlay,texturaBtnPlayP,332,331,ANCHO/2-texturaBtnPlay.getHeight()/2,ALTO*0.115f);
        botonPlay.cargarTexturasBtnPlay();
    }

    private void createParticulas() {
        sistemaParticulas=new ParticleEffect();
        sistemaParticulas.load(Gdx.files.internal("particulasMenu.p"),Gdx.files.internal(""));
        Array<ParticleEmitter> emisores=sistemaParticulas.getEmitters();
        emisorParticulas=emisores.get(0);
        emisores.get(0).setPosition(ANCHO,ALTO/2);
        sistemaParticulas.start();
    }

    private void crearMenu() {

        escenaMenu = new Stage(vista);
        //Scores
        scores=new Scores(ANCHO/2,ALTO-10);
        if (!scores.comprobarArchivo()){
            scores.crearArchivo();
        }
        //Música
        audioMenu.disposeAudio();
        audioMenu.setMusica("demoNatura2.mp3", true, true);
        audioMenu.setVolumen(0.6f);

        //Transición
        final TransicionPantalla transicion = efectoTransicion.inicializacion(2.0f);

        //Boton Jugar
        Boton botonJugar=new Boton("Botones/btnPlayTria.png","Botones/btnPlayTriaP.png");
        botonJugar.setPosition(ANCHO/2-botonJugar.getWidth()/2,ALTO*0.115f);
        botonJugar.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //audio.setEfecto("boton.mp3");
                audioMenu.setEfecto("efectoPausa.mp3");
                audioMenu.stopMusica();
                juego.setScreen(new PantallaPersonajes(juego,"Oso"), transicion);
            }
        });
        escenaMenu.addActor(botonJugar.getBtn());

        //Boton Informacion
        Boton botonInfo=new Boton("Botones/btnInfo.png","Botones/btnInfoP.png");
        botonInfo.setPosition(ANCHO*0.75f-botonInfo.getWidth()/2,ALTO*0.5f);
        botonInfo.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioMenu.setEfecto("efectoBoton.mp3");
                audioMenu.stopMusica();

                juego.setScreen(new PantallaInfo(juego));
            }
        });
        escenaMenu.addActor(botonInfo.getBtn());

        //Boton Scores
        Boton botonScores=new Boton("Botones/btnScore.png","Botones/btnScoreP.png");
        botonScores.setPosition(ANCHO*0.10f-botonScores.getWidth()/2,ALTO*0.5f);
        botonScores.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioMenu.setEfecto("efectoBoton.mp3");
                audioMenu.stopMusica();

                juego.setScreen(new PantallaScores(juego));
            }
        });
        escenaMenu.addActor(botonScores.getBtn());



        Gdx.input.setInputProcessor(escenaMenu);


    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        sistemaParticulas.update(delta);
        batch.begin();
        batch.draw(texturaFondo,0,0);
        sistemaParticulas.draw(batch);

        botonPlay.render(batch);



        batch.end();

        escenaMenu.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaFondo.dispose();
    }

}
