package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.eko.musica.ControladorAudio;

public class PantallaSettings extends PantallaAbstracta implements InputProcessor{

    private final ControlJuego juego;

    // Elementos del Menu
    private Texture texturaFondo;
    private Stage escenaMenu;

    private EstadoMusica estadoMusica;
    private Texture texturaON=new Texture("Botones/botonON.png");
    private Texture texturaOFF=new Texture("Botones/botonOff.png");
    private Objeto imagenBtnMusica;

    //Scores
    private Scores scores;
    private Texture texturaContinue=new Texture("Settings/continue.png");
    private Objeto objContinue;
    private Settings settings;



    @Override
    public InputProcessor getInputProcessor() {
        return escenaMenu;
    }

    // Constructor
    public PantallaSettings(ControlJuego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoSettings.png");
        scores=new Scores(ANCHO/2,ALTO-10);
        settings=new Settings();
        objContinue=new Objeto(texturaContinue,ANCHO,ALTO);
        crearMenu();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        if (settings.musicaPrendida()){
            imagenBtnMusica=new Objeto(texturaON,ANCHO*0.55f,ALTO*0.55f);
            estadoMusica=EstadoMusica.ON;
        }
        else {
            imagenBtnMusica=new Objeto(texturaOFF,ANCHO*0.55f,ALTO*0.55f);
            estadoMusica=EstadoMusica.OFF;
        }

        Boton botonInfo = new Boton("Botones/botonReturnSinFondo.png","Botones/botonReturnSinFondoP.png");
        botonInfo.setPosition(ANCHO/2-botonInfo.getWidth()/2,ALTO*0.115f);

        botonInfo.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.setScreen(new PantallaMenu(juego));
            }
        });
        escenaMenu.addActor(botonInfo.getBtn());

        //Boton on
        final Boton botonON= new Boton("Botones/botonMusica.png","Botones/botonMusicaP.png");
        botonON.setPosition(ANCHO*0.55f,ALTO*0.55f);

        botonON.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (estadoMusica == EstadoMusica.OFF) {
                    super.clicked(event, x, y);
                    juego.setEfecto("Audios/efectoBoton.mp3");
                    juego.playMixer();
                    juego.changeMusicaUsuario(true);
                    imagenBtnMusica.setTexture(texturaON);
                    estadoMusica=EstadoMusica.ON;
                    settings.escribirArchivo("1");
                }
                else if(estadoMusica==EstadoMusica.ON){
                    super.clicked(event, x, y);
                    juego.stopMusica();
                    juego.setEfecto("Audios/efectoBoton.mp3");
                    juego.changeMusicaUsuario(false);
                    imagenBtnMusica.setTexture(texturaOFF);
                    estadoMusica=EstadoMusica.OFF;
                    settings.escribirArchivo("0");
                }

            }
        });
        escenaMenu.addActor(botonON.getBtn());


        //Boton reset

        final Boton botonResetYes= new Boton("Settings/yes.png","Settings/yesP.png");
        botonResetYes.setPosition(ANCHO,ALTO);
        final Boton botonResetNo= new Boton("Settings/no.png","Settings/noP.png");
        botonResetNo.setPosition(ANCHO,ALTO);
        final Boton botonReset= new Boton("Settings/botonReset.png","Settings/botonResetP.png");
        botonReset.setPosition(ANCHO/2-botonReset.getBtn().getWidth()/2,ALTO*0.3f);

        botonResetYes.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                scores.crearArchivo();
                botonResetYes.setPosition(ANCHO,ALTO);
                botonResetNo.setPosition(ANCHO,ALTO);
                botonReset.setPosition(ANCHO/2-botonReset.getBtn().getWidth()/2,ALTO*0.3f);
                objContinue.sprite.setPosition(ANCHO,ALTO);

            }
        });

        botonResetNo.getBtn().addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                botonResetYes.setPosition(ANCHO,ALTO);
                botonResetNo.setPosition(ANCHO,ALTO);
                botonReset.setPosition(ANCHO/2-botonReset.getBtn().getWidth()/2,ALTO*0.3f);
                objContinue.sprite.setPosition(ANCHO,ALTO);
            }
        });

        botonReset.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                botonResetYes.setPosition(ANCHO*0.25f,ALTO*0.3f);
                botonResetNo.setPosition(ANCHO*0.5f,ALTO*0.3f);
                botonReset.setPosition(ANCHO,ALTO);
                objContinue.sprite.setPosition(ANCHO/2-objContinue.sprite.getWidth()/2,ALTO*0.4f);

            }
        });

        escenaMenu.addActor(botonResetYes.getBtn());
        escenaMenu.addActor(botonReset.getBtn());
        escenaMenu.addActor(botonResetNo.getBtn());

        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo,0,0);
        imagenBtnMusica.render(batch);
        objContinue.render(batch);
        batch.end();

        escenaMenu.draw();
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
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
        texturaFondo.dispose();
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

    private enum EstadoMusica {
        ON,
        OFF
    }



}
