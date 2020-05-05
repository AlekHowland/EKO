package mx.itesm.eko.musica;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class ControladorAudio
{

    public Music musica;
    public AssetManager manager = new AssetManager();

    public ControladorAudio() {
    }

    public void setMusica(String nombreArchivo, boolean loop, boolean play) {

        manager.load(nombreArchivo, Music.class);
        manager.finishLoading();

        this.musica = manager.get(nombreArchivo);
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
        manager.load(nombreArchivo, Sound.class);
        manager.finishLoading();
        Sound efecto = manager.get(nombreArchivo);
        efecto.play();
    }

    public void disposeAudio(){
       manager.clear();
    }

    public void setVolumenMitad(){
        musica.setVolume(0.5f);
    }

    public void setVolumen100(){
        musica.setVolume(1);
    }

    public void setVolumen(float v){
        musica.setVolume(v);
    }

    public void playMusica(){
        musica.play();
    }

    public boolean isPlaying(){
        return musica.isPlaying();
    }













}

