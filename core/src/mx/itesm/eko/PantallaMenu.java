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
    private Texture texturaBtnInfo;

    // Menu
    private Stage escenaMenu; //botones...
    private Scores scores;


    //Botones
    private BotonDinamico botonPlay;
    private BotonDinamico botonInfo;


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
        texturaFondo=new Texture("Fondos/fondoMenu.png");
        texturaBtnPlay=new Texture("Botones/btnPlay.png");
        texturaBtnInfo=new Texture("Botones/btnInfoDina.png");

        crearBotones();
        crearMenu();
        createParticulas();
    }

    private void crearBotones() {
        botonPlay = new BotonDinamico(texturaBtnPlay,texturaBtnPlay,332,331,ANCHO/2-(texturaBtnPlay.getHeight()/2)+10,ALTO*0.21f);
        botonPlay.cargarTexturasBtnPlay();
    }

    private void createParticulas() {
        sistemaParticulas=new ParticleEffect();
        sistemaParticulas.load(Gdx.files.internal("Particulas/particulasMenu.p"),Gdx.files.internal("Particulas"));
        Array<ParticleEmitter> emisores = sistemaParticulas.getEmitters();
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
            if(juego.getMusicaUsaurio() == true){
                juego.setMusica("Audios/demoNatura.mp3", true, true);
                juego.setVolumen(0.8f);
            }



        //Transición
        final TransicionPantalla transicion = efectoTransicion.inicializacion(2.0f);

        //Boton Jugar
        Boton botonJugar = new Boton("Botones/btnPlayTria.png","Botones/btnPlayTria.png");
        botonJugar.setPosition(ANCHO/2-(botonJugar.getWidth()/2)+10,ALTO*0.21f);
        botonJugar.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoPlay.mp3");
                juego.stopMusica();
                if(juego.getMusicaUsaurio() == true){
                    juego.setMusica("Audios/expectationOfAJourney.mp3", true, true);
                }
                juego.setScreen(new PantallaPersonajes(juego,"Oso"), transicion);
            }
        });
        escenaMenu.addActor(botonJugar.getBtn());


        //Boton Scores
        Boton botonScores = new Boton("Botones/trofeo.png","Botones/trofeo.png");
        botonScores.setPosition(ANCHO*0.18f-botonScores.getWidth()/2,ALTO*0.21f);
        botonScores.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.setScreen(new PantallaScores(juego), transicion);
            }
        });
        escenaMenu.addActor(botonScores.getBtn());

        //Boton How To Play
        Boton botonHowToPlay = new Boton("Botones/botonHowToPlay.png","Botones/botonHowToPlay.png");
        botonHowToPlay.setPosition(ANCHO*0.71f,ALTO*0.599f);
        botonHowToPlay.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.setScreen(new PantallaHowToPlay(juego,1));
            }
        });
        escenaMenu.addActor(botonHowToPlay.getBtn());

        //Boton Settings
        Boton botonSettings = new Boton("Botones/botonSettings.png","Botones/botonSettings.png");
        botonSettings.setPosition(ANCHO*0.74f,ALTO*0.41f);
        botonSettings.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.setScreen(new PantallaSettings(juego));
            }
        });
        escenaMenu.addActor(botonSettings.getBtn());

        //Boton Credits
        Boton botonCredits = new Boton("Botones/botonCredits.png","Botones/botonCredits.png");
        botonCredits.setPosition(ANCHO*0.72f,ALTO*0.21f);
        botonCredits.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.setScreen(new PantallaInfo(juego));
            }
        });
        escenaMenu.addActor(botonCredits.getBtn());


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
