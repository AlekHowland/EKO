package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mx.itesm.eko.musica.ControladorAudio;

class PantallaMuerto extends PantallaAbstracta implements InputProcessor{

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
    private Scores scores;
    private Date date=new Date();
    private String fecha;
    private ArrayList arr;

    @Override
    public InputProcessor getInputProcessor() {
        return escenaMenu;
    }

    // Constructor
    public PantallaMuerto(Viewport vista, SpriteBatch batch, float score,ControlJuego juego) {
        this.batch=batch;
        this.score=score;
        this.juego=juego;
        finalScore= new Texto("Fonts/fuente.fnt");
    }


    @Override
    public void show() {
        texturaFondo = new Texture("Fondos/fondoGameOver.png");
        try {
            crearScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
        crearMenu();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearScores() throws IOException {
        scores = new Scores(ANCHO/2,ALTO-300);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        fecha = dateFormat.format(new Date());
        arr=scores.leerArchivo();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);




        Boton botonInfo = new Boton("Botones/botonContinue.png","Botones/botonContinueP.png");
        botonInfo.setPosition(ANCHO/2-botonInfo.getWidth()/2,ALTO*0.115f);

        botonInfo.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                audioInfo.setEfecto("Audios/efectoBoton.mp3");
                try {
                    scores.escribirArchivo(Integer.toString((int)score),fecha);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        finalScore.render(batch,Integer.toString((int)score),ANCHO/2,ALTO*0.4F);
        if (arr.size()>=2) {
            try {
                finalScore.render(batch, scores.getHighScore(), ANCHO / 2, ALTO*0.80f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            finalScore.render(batch, Integer.toString((int)score), ANCHO / 2, ALTO - 100);
        }

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
}
