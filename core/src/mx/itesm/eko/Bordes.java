package mx.itesm.eko;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Bordes {

    public static void crearBordes(World mundo) {
        Rectangle bordeSuperior = new Rectangle(0, PantallaAbstracta.ALTO * 0.2f,
                500, PantallaAbstracta.ALTO * 0.05f);
        Rectangle bordeInferior = new Rectangle(0, 0,
                500, PantallaAbstracta.ALTO * 0.05f);

        Array<Rectangle> arrRectangulo = new Array<>(2);
        arrRectangulo.add(bordeSuperior, bordeInferior);

        for (Rectangle borde : arrRectangulo) {
            Shape bordeDefinido = getRectangle(borde);

            BodyDef bd = new BodyDef();
            bd.position.set(borde.x, borde.y);
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = mundo.createBody(bd);
            body.createFixture(bordeDefinido, 1);

            bordeDefinido.dispose();
        }
    }

    private static PolygonShape getRectangle(Rectangle borde) {
        PolygonShape polygon = new PolygonShape();
        Vector2 tam = new Vector2(borde.x * 1f, borde.y * 1f);
        polygon.setAsBox(borde.width * 2, borde.height * 2, tam, 0f);
        return polygon;
    }

}
