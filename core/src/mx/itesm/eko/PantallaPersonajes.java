package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import mx.itesm.eko.musica.ControladorAudio;

public class PantallaPersonajes extends PantallaAbstracta {

    private final ControlJuego juego;
    private Texture texturaFondo;
    private String assets = "Oso";

    // Menu
    private Stage escenaMenu; //botones...

    // Audio
    private ControladorAudio audioPersonajes = new ControladorAudio();

    public PantallaPersonajes(ControlJuego juego, String assets) {
        this.juego=juego;
        this.assets=assets;
    }

    @Override
    public void show() {
        texturaFondo = new Texture("Personajes/select"+assets+".jpg");
        crearMenu();
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
                audioPersonajes.setEfecto("cambioPagina.mp3");
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
                audioPersonajes.setEfecto("cambioPagina.mp3");
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
                audioPersonajes.setEfecto("efectoPausa.mp3");
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

    @Override
    public InputProcessor getInputProcessor() {
        return escenaMenu;
    }
}
