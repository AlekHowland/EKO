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
        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);




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
        Boton botonON= new Boton("Botones/botonON.png","Botones/botonONP.png");
        botonON.setPosition(ANCHO*0.55f,ALTO*0.45f);

        botonON.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.playMixer();
                juego.changeMusicaUsuario(true);


            }
        });
        escenaMenu.addActor(botonON.getBtn());

        //Boton off
        Boton botonOFF = new Boton("Botones/botonOff.png","Botones/botonOffP.png");
        botonOFF.setPosition(ANCHO*0.55f,ALTO*0.3f);

        botonOFF.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.stopMusica();
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.changeMusicaUsuario(false);

            }
        });
        escenaMenu.addActor(botonOFF.getBtn());


        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo,0,0);
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
