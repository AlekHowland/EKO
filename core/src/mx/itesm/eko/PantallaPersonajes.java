package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class PantallaPersonajes extends Pantalla{
    private final Juego juego;
    private Texture texturaFondo;
    private String assets="Oso";
    //MENU
    private Stage escenaMenu; //botones...

    public PantallaPersonajes(Juego juego,String assets) {
        this.juego=juego;
        this.assets=assets;
    }

    @Override
    public void show() {
        texturaFondo=new Texture("select"+assets+".jpg");

        crearMenu();


    }

    private void crearMenu() {
        escenaMenu=new Stage(vista);
        //Boton Scores
        Boton btnDer=new Boton("btnSelDer.png","btnSelDerP.png");
        btnDer.setPosition(ANCHO-btnDer.getWidth(),ALTO/2-btnDer.getHeight()/2);
        btnDer.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(assets=="Oso")
                    juego.setScreen(new PantallaPersonajes(juego,"Tortuga"));
                else if(assets=="Tortuga")
                    juego.setScreen(new PantallaPersonajes(juego,"Elefante"));
                else if(assets=="Elefante")
                    juego.setScreen(new PantallaPersonajes(juego,"Oso"));
            }
        });
        escenaMenu.addActor(btnDer.getBtn());

        //Boton Izquierda
        Boton btnIzq=new Boton("btnSelIzq.png","btnSelIzqP.png");
        btnIzq.setPosition(0,ALTO/2-btnIzq.getHeight()/2);
        btnIzq.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
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
        Boton btnSelect=new Boton("btnSelect.png","btnSelectP.png");
        btnSelect.setPosition(ANCHO/2-btnSelect.getWidth()/2,ALTO*0.115f);
        btnSelect.getBtn().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
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
}
