package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

    //Personaje
    private Texture texturaPersonaje=new Texture("Personajes/assetOso.png");
    private Personaje personaje;
    private Personaje elefante= new Personaje(texturaPersonaje, -ANCHO*0.3f, 0, "Elefante");
    private Personaje oso= new Personaje(texturaPersonaje, -ANCHO*0.3f, 0, "Oso");
    private Personaje tortuga= new Personaje(texturaPersonaje, -ANCHO*0.3f, 0, "Tortuga");

    // Audio
    private ControladorAudio audioInfo = new ControladorAudio();

    // Constructor
    public PantallaInfo(ControlJuego juego) {
        this.juego=juego;
    }

    //Imagen créditos
    public Texture texturaCreditos=new Texture("Creditos/imagenCreditos.png");
    public Objeto creditosImagen=new Objeto(texturaCreditos,0,-texturaCreditos.getHeight());


    @Override
    public void show() {
        crearMenu();
        oso.cargarTexturas();
        elefante.cargarTexturas();
        tortuga.cargarTexturas();
        personaje=oso;
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);

        escenaCreditos = new Stage();

        creditos = new logicaCreditos(texto, TimeUnit.SECONDS.toMillis(500));

        creditos.setPosition(ANCHO/2, 0);
        creditos.setDy(60);

        escenaCreditos.addActor(creditos);

        //Dibujo logo
        Boton logo = new Boton("Fondos/fondoIntroBoton.png","Fondos/fondoIntroBoton.png");
        logo.setPosition(ANCHO/2-logo.getWidth()/2,ALTO*0.5f);



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
        moverPersonaje();
        moverImagen();
        batch.begin();
        creditosImagen.render(batch);
        personaje.renderCorrer(batch);
        batch.end();
        escenaCreditos.act();
        escenaCreditos.draw();

        escenaMenu.draw();
    }

    private void moverImagen() {
        creditosImagen.sprite.setY(creditosImagen.sprite.getY()+10);
        if (creditosImagen.sprite.getY()>=ALTO){
            creditosImagen.sprite.setY(-creditosImagen.sprite.getHeight());
        }
    }

    private void moverPersonaje() {
        personaje.moverX(5);
        if (personaje.sprite.getX()>=ANCHO*1.3){
            personaje.sprite.setX(-ANCHO*0.3f);
            switch (personaje.getAssets()){
                case "Oso":
                    personaje=elefante;
                    break;
                case "Elefante":
                    personaje=tortuga;
                    break;
                case "Tortuga":
                    personaje=oso;
                    break;
            }
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
        escenaCreditos.dispose();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return new ProcesadorEntrada();
    }

    private class ProcesadorEntrada implements InputProcessor {

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
            audioInfo.setEfecto("Audios/efectoBoton.mp3");
            juego.setScreen(new PantallaMenu(juego));
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

}
