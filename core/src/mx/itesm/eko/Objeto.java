package mx.itesm.eko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Objeto {
    protected Sprite sprite;

    public Objeto(Texture textura, float x, float y){
        sprite=new Sprite(textura);
        sprite.setPosition(x,y);
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

    public void setTexture(Texture textura){
        float yt=sprite.getY();
        float xt=sprite.getX();
        sprite=new Sprite(textura);
        sprite.setPosition(xt,yt);
    }
}
