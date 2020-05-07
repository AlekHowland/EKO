package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import mx.itesm.eko.musica.ControladorAudio;

public class PantallaInfo extends PantallaAbstracta {

    private final ControlJuego juego;

    // Elementos del Menu
    private Texture texturaFondo;
    private Stage escenaMenu;

    //Elementos del texto
    private Stage escenaCreditos;
    private logicaCreditos creditos;
    //private String texto;
    private String path = "Creditos.txt";
    private FileHandle handle = Gdx.files.internal(path);
    private String texto = handle.readString();

    // Audio
    private ControladorAudio audioInfo = new ControladorAudio();

    // Constructor
    public PantallaInfo(ControlJuego juego) {
        this.juego=juego;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return escenaMenu;
    }

    @Override
    public void show() {
        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        escenaCreditos = new Stage();

        creditos = new logicaCreditos(texto, TimeUnit.SECONDS.toMillis(50));

        creditos.setPosition(ANCHO/2, 0);
        creditos.setDy(30);

        escenaCreditos.addActor(creditos);

        //Dibujo logo
        Boton logo = new Boton("Fondos/fondoIntroBoton.png","Fondos/fondoIntroBoton.png");
        logo.setPosition(ANCHO/2-logo.getWidth()/2,ALTO*0.5f);

        //Boton de refreso al menú
        Boton botonInfo = new Boton("Botones/btnReturn.png","Botones/btnReturnP.png");
        botonInfo.setPosition(ANCHO*0.75f,ALTO*0.05f);

        botonInfo.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioInfo.setEfecto("Audios/efectoBoton.mp3");
                juego.setScreen(new PantallaMenu(juego));
            }
        });
        escenaMenu.addActor(botonInfo.getBtn());
        //escenaMenu.addActor(logo.getBtn());

        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!creditos.isAnimado()) {
            creditos.activarAnimacion();
        }

        escenaCreditos.act();
        escenaCreditos.draw();

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
        escenaCreditos.dispose();
    }

}
