package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.eko.musica.ControladorAudio;

public class PantallaSettings extends PantallaAbstracta {

    private final ControlJuego juego;

    // Elementos del Menu
    private Texture texturaFondo;
    private Stage escenaMenu;

    private EstadoMusica estadoMusica;
    private Texture texturaON=new Texture("Botones/botonON.png");
    private Texture texturaOFF=new Texture("Botones/botonOff.png");
    private Objeto imagenBtnMusica;

    private Scores scores;

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
        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        if (settings.musicaPrendida()){
            imagenBtnMusica=new Objeto(texturaON,ANCHO*0.55f,ALTO*0.37f);
            estadoMusica=EstadoMusica.ON;
        }
        else {
            imagenBtnMusica=new Objeto(texturaOFF,ANCHO*0.55f,ALTO*0.37f);
            estadoMusica=EstadoMusica.OFF;
        }




        Boton botonInfo = new Boton("Botones/btnReturn.png","Botones/btnReturnP.png");
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
        Boton botonON= new Boton("Botones/botonMusica.png","Botones/botonMusicaP.png");
        botonON.setPosition(ANCHO*0.55f,ALTO*0.37f);

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
        Boton botonReset= new Boton("Botones/botonContinue.png","Botones/botonOffP.png");
        botonReset.setPosition(ANCHO*0.55f,ALTO*0.50f);

        botonReset.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");

                scores.crearArchivo();



            }
        });
        escenaMenu.addActor(botonReset.getBtn());


        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo,0,0);
        imagenBtnMusica.render(batch);
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

    private enum EstadoMusica {
        ON,
        OFF
    }

}
