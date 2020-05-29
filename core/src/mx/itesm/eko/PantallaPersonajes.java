package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.eko.musica.ControladorAudio;

public class PantallaPersonajes extends PantallaAbstracta implements InputProcessor{

    private final ControlJuego juego;
    private Texture texturaFondo;
    private String assets = "Oso";
    private BotonDinamico personaje;
    private Texture texturaPersonaje;
    // Menu
    private Stage escenaMenu; //botones...

    // Audio
    //private ControladorAudio audioPersonajes = new ControladorAudio();

    public PantallaPersonajes(ControlJuego juego, String assets) {
        this.juego=juego;
        this.assets=assets;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Personajes/select"+assets+".jpg");
        texturaPersonaje=new Texture("Personajes/asset"+assets+"Dormido.png");
        switch (assets){
            case "Oso":
                personaje = new BotonDinamico(texturaPersonaje,  170, 462, ANCHO * 0.3f, ALTO * 0.3f);
                personaje.cargarTexturasOso();
                break;
            case "Elefante":
                personaje = new BotonDinamico(texturaPersonaje,  189, 406, ANCHO * 0.3f, ALTO * 0.3f);
                personaje.cargarTexturasElefante();
                break;
            case "Tortuga":
                personaje = new BotonDinamico(texturaPersonaje,  301, 532, ANCHO * 0.3f, ALTO * 0.3f);
                personaje.cargarTexturasElefante();
                break;
        }
        crearMenu();
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);



        //Boton cambio derecha
        Boton btnDer=new Boton("Botones/btnSelDer.png","Botones/btnSelDerP.png");
        btnDer.setPosition(ANCHO-btnDer.getWidth(),ALTO/2-btnDer.getHeight()/2);
        btnDer.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/cambioPagina.mp3");
                if(assets=="Oso")
                    juego.setScreen(new PantallaPersonajes(juego,"Tortuga"));
                else if(assets=="Tortuga")
                    juego.setScreen(new PantallaPersonajes(juego,"Elefante"));
                else if(assets=="Elefante")
                    juego.setScreen(new PantallaPersonajes(juego,"Oso"));
            }
        });
        escenaMenu.addActor(btnDer.getBtn());

        //Boton cambio izquierda
        Boton btnIzq=new Boton("Botones/btnSelIzq.png","Botones/btnSelIzqP.png");
        btnIzq.setPosition(0,ALTO/2-btnIzq.getHeight()/2);
        btnIzq.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/cambioPagina.mp3");
                if(assets=="Tortuga")
                    juego.setScreen(new PantallaPersonajes(juego,"Oso"));
                else if(assets=="Oso")
                    juego.setScreen(new PantallaPersonajes(juego,"Elefante"));
                else if(assets=="Elefante")
                    juego.setScreen(new PantallaPersonajes(juego,"Tortuga"));
            }
        });
        escenaMenu.addActor(btnIzq.getBtn());

        //Boton Select
        Boton btnSelect = new Boton("Botones/btnSelect.png","Botones/btnSelectP.png");
        btnSelect.setPosition(ANCHO/2-btnSelect.getWidth()/2,ALTO*0.115f);
        btnSelect.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setEfecto("Audios/efectoPlay.mp3");
                juego.setVolumen100();
                juego.setScreen(new PantallaJuego(juego,assets));

            }
        });
        escenaMenu.addActor(btnSelect.getBtn());


        Gdx.input.setInputProcessor(escenaMenu);


    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo,0,0);
        personaje.render(batch);
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
    public InputProcessor getInputProcessor() {
        return escenaMenu;
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
