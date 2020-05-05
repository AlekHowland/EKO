package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.eko.musica.ControladorAudio;

public class PantallaHowToPlay extends PantallaAbstracta {

    private final ControlJuego juego;

    // Elementos del Menu
    private Texture texturaFondo;
    private Stage escenaMenu;
    private int numero = 1;


    @Override
    public InputProcessor getInputProcessor() {
        return escenaMenu;
    }

    // Constructor
    public PantallaHowToPlay(ControlJuego juego, int numero) {
        this.juego=juego;
        this.numero = numero;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoHowToPlay"+ numero +".png");
        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);




        Boton botonNext = new Boton("Botones/botonNext.png","Botones/botonNext.png");
        botonNext.setPosition(ANCHO*0.65f,ALTO*0.69f);

        botonNext.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.setEfecto("Audios/cambioPagina.mp3");
                if(numero == 1)
                    juego.setScreen(new PantallaHowToPlay(juego,2));
                else if(numero == 2)
                    juego.setScreen(new PantallaHowToPlay(juego,3));
                else if(numero == 3)
                    juego.setScreen(new PantallaHowToPlay(juego,1));
            }
        });
        escenaMenu.addActor(botonNext.getBtn());

        //Boton Exit
        Boton botonExit = new Boton("Botones/botonExit.png","Botones/botonExit.png");
        botonExit.setPosition(ANCHO*0.65f,ALTO*0.57f);
        botonExit.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoBoton.mp3");
                juego.setScreen(new PantallaMenu(juego));
            }
        });
        escenaMenu.addActor(botonExit.getBtn());

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
