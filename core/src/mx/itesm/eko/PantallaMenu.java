package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.eko.musica.ControladorAudio;
import mx.itesm.eko.transiciones.TransicionPantalla;
import mx.itesm.eko.transiciones.efectoTransicion;

public class PantallaMenu extends PantallaAbstracta
{
    // Juego & Texturas
    private final ControlJuego juego;
    private Texture texturaFondo;

    // Menu
    private Stage escenaMenu; //botones...

    // Audio
    private ControladorAudio audio = new ControladorAudio();

    @Override
    public InputProcessor getInputProcessor() {
        return escenaMenu;
    }

    public PantallaMenu(ControlJuego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        texturaFondo=new Texture("fondo.jpg");
        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        //Música
        audio.setMusica("musicaMenu.mp3", true, true);

        //Transición
        final TransicionPantalla transicion = efectoTransicion.inicializacion(2.0f);

        //Boton Jugar
        Boton botonJugar=new Boton("btnJugar.png","btnJugarP.png");
        botonJugar.setPosition(ANCHO/2-botonJugar.getWidth()/2,ALTO*0.115f);
        botonJugar.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audio.setEfecto("boton.mp3");
                juego.setScreen(new PantallaPersonajes(juego,"Oso"), transicion);
            }
        });
        escenaMenu.addActor(botonJugar.getBtn());

        //Boton Informacion
        Boton botonInfo=new Boton("btnInfo.png","btnInfoP.png");
        botonInfo.setPosition(ANCHO*0.75f-botonInfo.getWidth()/2,ALTO*0.5f);
        botonInfo.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaInfo(juego));
            }
        });
        escenaMenu.addActor(botonInfo.getBtn());

        //Boton Scores
        Boton botonScores=new Boton("btnScore.png","btnScoreP.png");
        botonScores.setPosition(ANCHO*0.10f-botonScores.getWidth()/2,ALTO*0.5f);
        botonScores.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
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
