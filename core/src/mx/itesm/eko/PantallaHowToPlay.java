package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.eko.musica.ControladorAudio;

public class PantallaHowToPlay extends PantallaAbstracta{

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
        Boton botonNext = new Boton("Botones/botonNext.png","Botones/botonNextP.png");
        /*.setPosition(ANCHO*0.65f,ALTO*0.69f);
        if(numero == 1 || numero == 2 || numero == 3){
            botonNext.setPosition(ANCHO*0.65f,ALTO*0.69f);
        }else if(numero == 4 ){
            botonNext.setPosition(ANCHO/2-botonNext.getWidth()+170,ALTO*0.4f);
        }else{*/
            botonNext.setPosition(ANCHO-botonNext.getWidth()- 50,10);
        //}
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
                    juego.setScreen(new PantallaHowToPlay(juego,4));
                else if(numero == 4)
                    juego.setScreen(new PantallaHowToPlay(juego,5));
                else if(numero == 5)
                    juego.setScreen(new PantallaHowToPlay(juego,6));
                else if(numero == 6)
                    juego.setScreen(new PantallaHowToPlay(juego,1));
            }
        });
        escenaMenu.addActor(botonNext.getBtn());

        //Boton Exit
        Boton botonExit = new Boton("Botones/botonExit.png","Botones/botonExitP.png");
        /*if(numero == 1 || numero == 2 || numero == 3){
            botonExit.setPosition(ANCHO*0.65f,ALTO*0.57f);
        }else if(numero == 4 ){
            botonExit.setPosition(ANCHO/2-botonExit.getWidth()+170,ALTO*0.3f);
        }else{*/
            botonExit.setPosition(35,8);
        //}
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

    }
}
