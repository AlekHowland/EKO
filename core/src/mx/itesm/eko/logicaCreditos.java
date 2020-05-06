package mx.itesm.eko;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.lang.String;

public class logicaCreditos extends Actor {

    private final String texto;

    //Animacion
    private final long duracionAnimacion;
    private boolean estaAnimado = false;
    private long inicioAnimacion;
    private float dx;
    private float dy;
    private BitmapFont font = new BitmapFont();

    //Constructor
    public logicaCreditos(String texto, long duracionAnimacion) {
        this.texto = texto;
        this.duracionAnimacion = duracionAnimacion;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public void activarAnimacion() {
        estaAnimado = true;
        inicioAnimacion = System.currentTimeMillis();
    }

    public boolean isAnimado() {
        return estaAnimado;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if(esDesechable()) {
            dispose();
            return;
        }

        if(estaAnimado) {

            float tiempoTranscurrido = System.currentTimeMillis() - inicioAnimacion;

            font.draw(batch, texto, getX() + dx * tiempoTranscurrido / 1000f,
                    getY() + dy * tiempoTranscurrido / 1000f);
        }
    }

    private boolean esDesechable() {
        return ((inicioAnimacion + duracionAnimacion) < System.currentTimeMillis());
    }

    private void dispose() {
        font.dispose();
        remove();
    }
}
