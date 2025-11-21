package juego.progra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Implementación de BallAppearance para balón de fútbol.
 * Patrón Strategy - Estrategia concreta.
 */
public class SoccerBallAppearance implements BallAppearance {
    @Override
    public void draw(ShapeRenderer shape, int x, int y, int size) {
        // Pelota blanca base
        shape.setColor(Color.WHITE);
        shape.circle(x, y, size);
        
        // Pentágono negro en el centro (simulado con círculo)
        shape.setColor(Color.BLACK);
        shape.circle(x, y, size/3);
        
        // Hexágonos negros alrededor (simulados con círculos pequeños)
        shape.setColor(Color.BLACK);
        int numPatches = 6;
        for (int i = 0; i < numPatches; i++) {
            float angle = (float) (i * 2 * Math.PI / numPatches);
            float patchX = x + (float) Math.cos(angle) * size * 0.7f;
            float patchY = y + (float) Math.sin(angle) * size * 0.7f;
            shape.circle(patchX, patchY, size/5);
        }
    }
}
