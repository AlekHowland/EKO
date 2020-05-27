package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemigo extends Objeto {
    int tipoEnemigo;
    private String assets;

    //Animación
    private Animation animacionEnemigo;
    public float timerAnimacion;

    //Texturas
    private TextureRegion[][] texturaEnemigo;

    public Enemigo(Texture textura, float x, float y,int tipoEnemigo,String assets) {
        super(textura, x, y);
        this.tipoEnemigo=tipoEnemigo;
        this.assets=assets;

        Texture imagenEnemigo1 = new Texture("Enemigos/enemigo" + assets + "Animado"+tipoEnemigo+".png");
        TextureRegion regionEnemigo1 = new TextureRegion(imagenEnemigo1);
        switch (assets){
            case "Oso":
                switch (tipoEnemigo){
                    case 1:
                        texturaEnemigo = regionEnemigo1.split(110, 65);
                        break;
                    case 2:
                        texturaEnemigo = regionEnemigo1.split(336, 200);
                        break;
                    case 3:
                        texturaEnemigo = regionEnemigo1.split(200, 119);
                        break;
                }
                break;
            case "Elefante":
                switch (tipoEnemigo){
                    case 1:
                        texturaEnemigo = regionEnemigo1.split(110, 65);
                        break;
                    case 2:
                        texturaEnemigo = regionEnemigo1.split(218, 153);
                        break;
                    case 3:
                        texturaEnemigo = regionEnemigo1.split(200, 119);
                        break;
                }
                break;
            case "Tortuga":
                switch (tipoEnemigo){
                    case 1:
                        texturaEnemigo = regionEnemigo1.split(110, 65);
                        break;
                    case 2:
                        texturaEnemigo = regionEnemigo1.split(200, 119);
                        break;
                    case 3:
                        texturaEnemigo = regionEnemigo1.split(200, 119);
                        break;
                }
                break;
        }
    }

    public void moverHorizontal(float dx){
        sprite.setX(sprite.getX()+dx);
    }
    public void moverVertical(float dy){
        sprite.setY(sprite.getY()+dy);
    }


    public void setPosition(float x, float y){
        sprite.setPosition(x,y);
    }

    public int getTipo(){
        return tipoEnemigo;
    }

    public void cargarTexturas() {
        switch (assets){
            case "Oso":
                switch (tipoEnemigo){
                    case 1:
                        animacionEnemigo = new Animation(0.1f, texturaEnemigo[0][0], texturaEnemigo[0][1], texturaEnemigo[0][2]);
                        break;
                    case 2:
                        animacionEnemigo = new Animation(0.075f, texturaEnemigo[0][0]);
                        break;
                    case 3:
                        animacionEnemigo = new Animation(0.075f, texturaEnemigo[0][0]);
                        break;
                }
                break;
            case "Elefante":
                switch (tipoEnemigo){
                    case 1:
                        animacionEnemigo = new Animation(0.075f, texturaEnemigo[0][0], texturaEnemigo[0][1], texturaEnemigo[0][2]);
                        break;
                    case 2:
                        animacionEnemigo = new Animation(0.07f, texturaEnemigo[0][0],  texturaEnemigo[0][1], texturaEnemigo[0][2],
                                texturaEnemigo[0][3], texturaEnemigo[0][4], texturaEnemigo[0][5]);
                        break;
                    case 3:
                        animacionEnemigo = new Animation(0.075f, texturaEnemigo[0][0]);
                        break;

                }
                break;
            case "Tortuga":
                switch (tipoEnemigo){
                    case 1:
                        animacionEnemigo = new Animation(0.1f, texturaEnemigo[0][0], texturaEnemigo[0][1], texturaEnemigo[0][2]);
                        break;
                    case 2:
                        animacionEnemigo = new Animation(0.075f, texturaEnemigo[0][0]);
                        break;
                    case 3:
                        animacionEnemigo = new Animation(0.075f, texturaEnemigo[0][0]);
                        break;
                }
                break;
        }

        animacionEnemigo.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;
    }

    public void renderAnimacion(SpriteBatch batch) {
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animacionEnemigo.getKeyFrame(timerAnimacion);
        batch.draw(region, sprite.getX(), sprite.getY());
    }

}
