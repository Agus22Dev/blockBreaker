package juego.progra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Implementación de BallAppearance para carita feliz.
 * Patrón Strategy - Estrategia concreta.
 */
public class HappyFaceBallAppearance implements BallAppearance {
    @Override
    public void draw(ShapeRenderer shape, int x, int y, int size) {
        // Cara amarilla
        shape.setColor(Color.YELLOW);
        shape.circle(x, y, size);
        
        // Ojos (dos círculos negros)
        shape.setColor(Color.BLACK);
        shape.circle(x - size/3, y + size/3, size/5);  // Ojo izquierdo
        shape.circle(x + size/3, y + size/3, size/5);  // Ojo derecho
        
        // Sonrisa (arco)
        shape.setColor(Color.BLACK);
        shape.arc(x, y - size/4, size * 0.6f, 200, 140);
    }
}
