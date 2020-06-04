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
    private Texture texturaStart;

    // Menu
    private Stage escenaMenu; //botones...
    private Scores scores;


    //Botones
    private BotonDinamico botonPlay;
    private BotonDinamico botonInfo;
    private Boton botonScores;
    private BotonDinamico start;
    float timerBoton=0;


    //Sistema de partículas
    private ParticleEffect sistemaParticulas;
    private ParticleEmitter emisorParticulas;

    private Settings settings=new Settings();
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
        texturaStart=new Texture("Botones/pressToStart.png");


        crearBotones();
        crearMenu();
        createParticulas();
    }

    private void crearBotones() {
        botonPlay = new BotonDinamico(texturaBtnPlay,332,331,ANCHO/2-(texturaBtnPlay.getHeight()/2)+10,ALTO*0.21f);
        botonPlay.cargarTexturasBtnPlay();
        start = new BotonDinamico(texturaStart,23,287,ANCHO/2-(texturaBtnPlay.getHeight()/2)+40,ALTO*0.135f);
        start.cargarStart();
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

        if (!settings.comprobarArchivo()){
            settings.crearArchivo();
        }

        //Música
        if(juego.getMusicaUsaurio() == true){
            juego.setMusica("Audios/demoNatura.mp3", true, true);
        }

        if (!settings.musicaPrendida()){
            juego.stopMusica();
            juego.changeMusicaUsuario(false);
        }

        //Transición
        final TransicionPantalla transicion = efectoTransicion.inicializacion(2.0f);

        //Boton Jugar
        Boton botonJugar = new Boton("Botones/btnPlayTria.png","Botones/btnPlayTriaP.png");
        botonJugar.setPosition(ANCHO/2-(botonJugar.getWidth()/2)+10,ALTO*0.10f);
        botonJugar.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoPlay.mp3");
                juego.stopMusica();
                if(juego.getMusicaUsaurio() == true){
                    juego.setMusica("Audios/Expectation of a Journey.mp3", true, true);
                }
                juego.setScreen(new PantallaPersonajes(juego,"Oso"), transicion, true);
            }
        });
        escenaMenu.addActor(botonJugar.getBtn());


        //Boton Scores
        botonScores = new Boton("Botones/trofeo.png","Botones/trofeoP.png");
        botonScores.setPosition(ANCHO*0.18f-botonScores.getWidth()/2,ALTO*0.21f);
        botonScores.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.setScreen(new PantallaScores(juego), transicion, true);
            }
        });
        escenaMenu.addActor(botonScores.getBtn());

        //Boton How To Play
        Boton botonHowToPlay = new Boton("Botones/botonHowToPlay.png","Botones/botonHowToPlayP.png");
        botonHowToPlay.setPosition(ANCHO*0.71f,ALTO*0.599f);
        botonHowToPlay.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.setScreen(new PantallaHowToPlay(juego,1), transicion, true);
            }
        });
        escenaMenu.addActor(botonHowToPlay.getBtn());

        //Boton Settings
        Boton botonSettings = new Boton("Botones/botonSettings.png","Botones/botonSettingsP.png");
        botonSettings.setPosition(ANCHO*0.74f,ALTO*0.41f);
        botonSettings.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.setScreen(new PantallaSettings(juego), transicion, true);
            }
        });
        escenaMenu.addActor(botonSettings.getBtn());

        //Boton Credits
        Boton botonCredits = new Boton("Botones/botonCredits.png","Botones/botonCreditsP.png");
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
        moverBoton();
        batch.draw(texturaFondo,0,0);
        sistemaParticulas.draw(batch);

        botonPlay.render(batch);
        start.render(batch);

        batch.end();

        escenaMenu.draw();
    }

    private void moverBoton() {
        timerBoton+=0.001;
        if (timerBoton<=0.1) {
            botonScores.setPosition(botonScores.getBtn().getX()+0.07f, botonScores.getBtn().getY() + 0.1f);
        }
        else if(timerBoton<=0.2){
            botonScores.setPosition(botonScores.getBtn().getX()-0.07f, botonScores.getBtn().getY() - 0.1f);
        }
        else {
            timerBoton=0;
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
        texturaFondo.dispose();
    }

}
