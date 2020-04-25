package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.itesm.eko.musica.ControladorAudio;

class PantallaMuerto extends PantallaAbstracta{

    private final ControlJuego juego;

    // Elementos del Menu
    private Texture texturaFondo;
    private Stage escenaMenu;
    private SpriteBatch batch;

    // Audio
    private ControladorAudio audioInfo = new ControladorAudio();

    //Score
    private float score;
    private Texto finalScore;

    @Override
    public InputProcessor getInputProcessor() {
        return escenaMenu;
    }

    // Constructor
    public PantallaMuerto(Viewport vista, SpriteBatch batch, float score,ControlJuego juego) {
        this.batch=batch;
        this.score=score;
        this.juego=juego;
        finalScore= new Texto("fuente.fnt");
    }


    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoGameOver.png");
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
                audioInfo.setEfecto("efectoBoton.mp3");
                juego.setScreen(new PantallaMenu(juego));
            }
        });
        escenaMenu.addActor(botonInfo.getBtn());

        Gdx.input.setInputProcessor(escenaMenu);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo,0,0);
        finalScore.render(batch,Integer.toString((int)score),ANCHO/2,ALTO/2);

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
