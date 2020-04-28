package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class BotonDinamico {
    private Animation animacion;
    private float timerAnimacion;//frames en tiempos definidos
    private float x, y;
    private EstadoMovimiento estado=EstadoMovimiento.SUELTO;//QUIETO,CAMINANDO

    public BotonDinamico(Texture textura, Texture texturaP,int alto, int ancho,float x, float y) {
        this.x=x;
        this.y=y;
        TextureRegion region=new TextureRegion(textura);
        TextureRegion regionP=new TextureRegion(texturaP);

        TextureRegion[][] texturaPartida=region.split(ancho,alto);
        TextureRegion[][] texturaPartidaP=region.split(ancho,alto);

        animacion=new Animation(0.15f,texturaPartida[0][0],texturaPartida[0][1],texturaPartida[0][2],texturaPartida[0][3],texturaPartida[0][4],texturaPartida[0][5]);
        animacion=new Animation(0.15f,texturaPartidaP[0][0],texturaPartidaP[0][1],texturaPartidaP[0][2],texturaPartidaP[0][3],texturaPartidaP[0][4],texturaPartidaP[0][5]);

        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;
    }

    public void setEstado(EstadoMovimiento estado){
        this.estado=estado;
    }

    public void render(SpriteBatch batch) {
        if (estado==EstadoMovimiento.OPRIMIDO){
            timerAnimacion+= Gdx.graphics.getDeltaTime();
            TextureRegion regionP=(TextureRegion) animacion.getKeyFrame(timerAnimacion);
            batch.draw(regionP,x,y);
        }
        else {
            timerAnimacion+= Gdx.graphics.getDeltaTime();
            TextureRegion region=(TextureRegion) animacion.getKeyFrame(timerAnimacion);
            batch.draw(region,x,y);
        }

    }

    public enum EstadoMovimiento{
        SUELTO,
        OPRIMIDO
    }
}
