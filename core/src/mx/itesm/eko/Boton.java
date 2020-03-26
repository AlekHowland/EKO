package mx.itesm.eko;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Boton {
    private ImageButton btn;
    public Texture texturaBtn;
    public Texture texturaBtnP;
    public Boton(String texturaString,String texturaPString) {

        Texture textura=new Texture(texturaString);
        Texture texturaPush=new Texture(texturaPString);

        TextureRegionDrawable trdBtn=new TextureRegionDrawable(new TextureRegion(textura));
        TextureRegionDrawable trdBtnP=new TextureRegionDrawable(new TextureRegion(texturaPush));
        btn=new ImageButton(trdBtn,trdBtnP);
    }


    public void setPosition(float x, float y){
        btn.setPosition(x,y);
    }

    public ImageButton getBtn(){
        return btn;
    }

    public float getHeight(){
        return btn.getHeight();
    }

    public float getWidth(){
        return btn.getWidth();
    }


}
