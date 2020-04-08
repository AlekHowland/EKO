package mx.itesm.eko;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Juego extends Game {

	// ShapeRenderer necesario para las transiciones
	public ShapeRenderer shapeRenderer;

	@Override
	public void create () {

		setScreen(new PantallaInfo(this));
	}

}
