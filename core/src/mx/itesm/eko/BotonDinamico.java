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
    private TextureRegion[][] texturaPartidaP;

    public BotonDinamico(Texture textura,int alto, int ancho,float x, float y) {
        this.x=x;
        this.y=y;
        TextureRegion region=new TextureRegion(textura);
        texturaPartidaP=region.split(ancho,alto);

    }


    public void setEstado(EstadoMovimiento estado){
        this.estado=estado;
    }

    public void cargarTexturasBtnPlay(){

        animacion=new Animation(0.15f,texturaPartidaP[0][0],texturaPartidaP[0][1],texturaPartidaP[0][2],texturaPartidaP[0][3],texturaPartidaP[0][4],texturaPartidaP[0][5]);

        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;
    }

    public void cargarTexturasBtnInfo(){

        animacion=new Animation(0.20f,texturaPartidaP[0][0],texturaPartidaP[0][1],texturaPartidaP[0][2],texturaPartidaP[0][3],texturaPartidaP[0][4],texturaPartidaP[0][5],texturaPartidaP[0][6]);

        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;
    }

    public void cargarItem(){

        animacion=new Animation(0.1f,texturaPartidaP[0][0],texturaPartidaP[0][1],texturaPartidaP[0][2],texturaPartidaP[0][3],texturaPartidaP[0][4],texturaPartidaP[0][5],texturaPartidaP[0][6],texturaPartidaP[0][7]);

        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;
    }

    public void cargarStart(){
        animacion = new Animation(0.5f,texturaPartidaP[0][0],texturaPartidaP[0][1]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;
    }

    public void cargarTexturasOso(){
        animacion=new Animation(0.30f,texturaPartidaP[0][0],texturaPartidaP[0][1],texturaPartidaP[0][2],texturaPartidaP[0][3]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;
    }

    public void cargarTexturasElefante(){
        animacion=new Animation(0.30f,texturaPartidaP[0][0],texturaPartidaP[0][1],texturaPartidaP[0][2],texturaPartidaP[0][3]);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;
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

    public void render(SpriteBatch batch, float x, float y) {
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
