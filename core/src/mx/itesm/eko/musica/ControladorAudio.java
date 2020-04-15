package mx.itesm.eko.musica;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class ControladorAudio
{

    private Music musica;
    private AssetManager manager = new AssetManager();

    public ControladorAudio() {
    }

    public void setMusica(String nombreArchivo, boolean loop, boolean play) {
        musica = manager.get(nombreArchivo);
        musica.setLooping(loop);
        if(play) musica.play();
    }

    public void modificarMusica(float volumen, float ecualizador, boolean loop){
        //Si se quiere dejar en el centro la ecualización
        //La variable ecualizador = 0.0f
        musica.setPan(ecualizador, volumen);
        musica.setLooping(loop);
    }

    public void stopMusica() {
        musica.stop();
    }

    public void setEfecto(String nombreArchivo) {
        Sound efecto = manager.get(nombreArchivo);
        efecto.play();
    }












}

