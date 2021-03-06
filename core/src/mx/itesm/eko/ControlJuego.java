package mx.itesm.eko;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import mx.itesm.eko.musica.ControladorAudio;
import mx.itesm.eko.transiciones.TransicionPantalla;

public class ControlJuego implements ApplicationListener, InputProcessor
{
    private boolean inicio;
    private PantallaAbstracta pantallaActual;
    private PantallaAbstracta pantallaSiguiente;
    private FrameBuffer actualFrameBuffer;
    private FrameBuffer siguienteFrameBuffer;
    private SpriteBatch batch;
    private float duracion;
    private TransicionPantalla transicionPantalla;
    private ControladorAudio mixer;
    private final boolean bloquearBack = true;
    private final int BACK = 4;
    private boolean musicaUsuario = true;

    public void setScreen(PantallaAbstracta pantalla){
        setScreen(pantalla, null);
    }

    public void setScreen(PantallaAbstracta pantalla, TransicionPantalla transicion){
        int ancho = Gdx.graphics.getWidth();
        int alto = Gdx.graphics.getHeight();

        if (!inicio) {
            actualFrameBuffer = new FrameBuffer(Format.RGBA8888, ancho, alto, false);
            siguienteFrameBuffer = new FrameBuffer(Format.RGBA8888, ancho, alto, false);
            batch = new SpriteBatch();
            inicio = true;
        }
        // Aquí es donde inicia la transición
        pantallaSiguiente = pantalla;
        pantallaSiguiente.show();       //Se activa la siguiente pantalla
        pantallaSiguiente.resize(ancho, alto);
        pantallaSiguiente.render(0);
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
        if (pantallaActual != null) pantallaActual.pause();
        pantallaSiguiente.pause();
        this.transicionPantalla = transicion;
        duracion = 0;
    }


    @Override
    public void render() {
        // Se establece el límite del tiempo a 1/60 segundos
        float tiempoDelta = Math.min(Gdx.graphics.getDeltaTime(), 1.0f/60.0f);

        if (pantallaSiguiente == null) {
            // No hay transición
            if(pantallaActual != null) pantallaActual.render(tiempoDelta);
        } else {
            // Hay una transición
            float duracionTransicion = 0;
            if (transicionPantalla != null)
                duracionTransicion = transicionPantalla.getDuration();
            // Se actualiza el proceso de la transición en curso
            duracion = Math.min(duracion + tiempoDelta, duracionTransicion);
            if (transicionPantalla == null || duracion >= duracionTransicion) {
              // Se acaba de terminar la transición o no hay
              if (pantallaActual != null) pantallaActual.hide();
              pantallaSiguiente.resume();
              // Se devuelve el input a la siguiente pantalla
              Gdx.input.setInputProcessor(pantallaSiguiente.getInputProcessor());
              // Se hace el cambio de pantallas
              pantallaActual = pantallaSiguiente;
              pantallaSiguiente = null;
              transicionPantalla = null;
            } else {
                //Gdx.input.setCatchKey(BACK, bloquearBack);
                // Se hace el render de las pantallas a los buffers
                actualFrameBuffer.begin();
                if (pantallaActual != null) pantallaActual.render(tiempoDelta);
                actualFrameBuffer.end();

                siguienteFrameBuffer.begin();
                Gdx.input.setCatchKey(BACK, bloquearBack);
                pantallaSiguiente.render(tiempoDelta);
                Gdx.input.setCatchKey(BACK, bloquearBack);
                siguienteFrameBuffer.end();
                //  Efecto de transición
                float alpha = duracion / duracionTransicion;
                try {
                    transicionPantalla.render(batch, actualFrameBuffer.getColorBufferTexture(),
                            siguienteFrameBuffer.getColorBufferTexture(), alpha);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void resize(int ancho, int altura) {
        if(pantallaActual != null) pantallaActual.resize(ancho, altura);
        if(pantallaSiguiente != null) pantallaSiguiente.resize(ancho, altura);
    }

    @Override
    public void pause() {
        if(pantallaActual != null) pantallaActual.pause();
    }

    @Override
    public void resume() {
        if(pantallaActual != null) pantallaActual.resume();
    }

    // Esto se hace con el fin de informar a las pantallas de los eventos
    // Permite también que las pantallas y los FrameBuffers se elminen cuando
    // no son necesarios
    @Override
    public void dispose() {
        if(pantallaActual != null) pantallaActual.hide();
        if(pantallaSiguiente != null) pantallaSiguiente.hide();
        if(inicio){
            actualFrameBuffer.dispose();
            pantallaActual = null;
            siguienteFrameBuffer.dispose();
            pantallaSiguiente = null;
            batch.dispose();
            inicio = false;
        }
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchKey(Input.Keys.BACK,true);
    }

    //Creacion y metodos de la mixer

    public void setMixer(ControladorAudio mixer){
        this.mixer = mixer;
    }

    public void setMusica(String nombreArchivo, boolean loop, boolean play) {

        mixer.manager.load(nombreArchivo, Music.class);
        mixer.manager.finishLoading();

        this.mixer.musica = mixer.manager.get(nombreArchivo);
        mixer.musica.setLooping(loop);
        if(play) mixer.musica.play();
    }

    public void stopMusica() {
        mixer.musica.stop();
    }

    public void setEfecto(String nombreArchivo) {
        mixer.manager.load(nombreArchivo, Sound.class);
        mixer.manager.finishLoading();
        Sound efecto = mixer.manager.get(nombreArchivo);
        efecto.play();
    }

    public void setVolumen100(){
        mixer.musica.setVolume(1);
    }

    public void setVolumen(float v){
        mixer.musica.setVolume(v);
    }

    public void playMixer(){
        mixer.musica.play();
    }

    public void changeMusicaUsuario(boolean f){
        this.musicaUsuario = f;
    }

    public boolean getMusicaUsaurio(){
        return this.musicaUsuario;
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
