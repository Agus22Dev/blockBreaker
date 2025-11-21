package juego.progra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Implementación de BallAppearance para pelota blanca estándar.
 * Patrón Strategy - Estrategia concreta.
 */
public class WhiteBallAppearance implements BallAppearance {
    @Override
    public void draw(ShapeRenderer shape, int x, int y, int size) {
        shape.setColor(Color.WHITE);
        shape.circle(x, y, size);
    }
}
