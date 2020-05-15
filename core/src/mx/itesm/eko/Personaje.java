package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Personaje extends Objeto {
    private Animation animacionCorriendo;
    private Animation animacionAgachado;
    private Animation animacionSaltando;
    public float timerAnimacion;
    private String assets;
    private TextureRegion[][] texturaPCorriendo;
    private TextureRegion[][] texturaPAgachado;
    private TextureRegion[][] texturaPSaltando;
    private float x, y;

    public Personaje(Texture textura, float x, float y, String assets) {
        super(textura, x, y);
        this.x = x;
        this.y = y;
        this.assets = assets;
        Texture texturaCorriendo = new Texture("Personajes/asset" + assets + "Corriendo.png");
        Texture texturaAgachado = new Texture("Personajes/asset" + assets + "Agachado.png");
        Texture texturaSaltando = new Texture("Personajes/asset" + assets + "Saltando.png");
        TextureRegion regionCorriendo = new TextureRegion(texturaCorriendo);
        TextureRegion regionAgachado = new TextureRegion(texturaAgachado);
        TextureRegion regionSaltando = new TextureRegion(texturaSaltando);
        switch (assets){
            case "Oso":
                texturaPCorriendo = regionCorriendo.split(300, 150);
                texturaPAgachado = regionAgachado.split(300, 94);
                texturaPSaltando= regionSaltando.split(300, 194);
                break;
            case "Elefante":
                texturaPCorriendo = regionCorriendo.split(250, 170);
                texturaPAgachado = regionCorriendo.split(392, 266);
                texturaPSaltando= regionCorriendo.split(392, 266);
                break;
        }

    }

    public void mover(float dy) {
        sprite.setY(sprite.getY() + dy);
    }

    public void cargarTexturas() {
        switch (assets){
            case "Oso":
                animacionCorriendo = new Animation(0.1f, texturaPCorriendo[0][0], texturaPCorriendo[0][1], texturaPCorriendo[0][2], texturaPCorriendo[0][3], texturaPCorriendo[0][4], texturaPCorriendo[0][5], texturaPCorriendo[0][6], texturaPCorriendo[0][7]);
                animacionAgachado = new Animation(0.1f, texturaPAgachado[0][0], texturaPAgachado[0][1], texturaPAgachado[0][2]);
                break;
            case "Elefante":
                animacionCorriendo = new Animation(0.075f, texturaPCorriendo[0][0], texturaPCorriendo[0][1], texturaPCorriendo[0][2], texturaPCorriendo[0][3], texturaPCorriendo[0][4], texturaPCorriendo[0][5], texturaPCorriendo[0][6]);
                animacionAgachado= new Animation(0.1f, texturaPCorriendo[0][0], texturaPCorriendo[0][1], texturaPCorriendo[0][2], texturaPCorriendo[0][3], texturaPCorriendo[0][4], texturaPCorriendo[0][5], texturaPCorriendo[0][6]);
                break;

        }
        animacionCorriendo.setPlayMode(Animation.PlayMode.LOOP);
        animacionAgachado.setPlayMode(Animation.PlayMode.LOOP);

        timerAnimacion = 0;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void renderCorrer(SpriteBatch batch) {
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animacionCorriendo.getKeyFrame(timerAnimacion);
        batch.draw(region, sprite.getX(), sprite.getY());
    }

    public void renderAgachar(SpriteBatch batch) {
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animacionAgachado.getKeyFrame(timerAnimacion);
        batch.draw(region,sprite.getX(), sprite.getY());
    }

    public void renderSaltar(SpriteBatch batch){

        switch (assets){
            case "Oso":
                if (timerAnimacion>=1.75f){
                    timerAnimacion=0;
                }
                animacionSaltando = new Animation(0.35f, texturaPSaltando[0][0], texturaPSaltando[0][1], texturaPSaltando[0][2], texturaPSaltando[0][3], texturaPSaltando[0][4]);
                break;
            case "Elefante":
                if (timerAnimacion>=1.75f){
                    timerAnimacion=0;
                }
                animacionSaltando = new Animation(0.1f, texturaPCorriendo[0][0], texturaPCorriendo[0][1], texturaPCorriendo[0][2], texturaPCorriendo[0][3], texturaPCorriendo[0][4], texturaPCorriendo[0][5], texturaPCorriendo[0][6]);
                break;
        }
        animacionSaltando.setPlayMode(Animation.PlayMode.NORMAL);
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion region = (TextureRegion) animacionSaltando.getKeyFrame(timerAnimacion);
        batch.draw(region,sprite.getX(), sprite.getY()-sprite.getY()/3);
    }
}
