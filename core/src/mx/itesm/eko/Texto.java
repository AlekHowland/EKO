package mx.itesm.eko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class Texto {
    private BitmapFont font;
    private float anchoTexto;
    private float altoTexto;

    public Texto(String archivo){
        font = new BitmapFont(Gdx.files.internal(archivo)); //Archivo es .fnt
    }

    public void render(SpriteBatch batch, String mensaje, float x, float y) {
        GlyphLayout glyph= new GlyphLayout();
        glyph.setText(font,mensaje);
        anchoTexto=glyph.width;
        altoTexto=glyph.height;
        font.draw(batch,glyph,x-anchoTexto/2,y);
    }

    public float getWidth(){
        return anchoTexto;
    }

    public float getHeight(){
        return altoTexto;
    }
}
