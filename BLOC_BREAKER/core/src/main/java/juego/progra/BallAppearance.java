package juego.progra;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Interfaz para el patrón Strategy (GM2.3).
 * Define el contrato para diferentes apariencias de la pelota.
 * 
 * Patrón Strategy:
 * - Permite cambiar el comportamiento (apariencia) de la pelota en tiempo de ejecución
 * - Encapsula diferentes algoritmos de dibujo
 * - El cliente (PingBall) puede cambiar entre estrategias sin modificar su código
 */
public interface BallAppearance {
    /**
     * Dibuja la pelota con la apariencia específica.
     * @param shape ShapeRenderer para dibujar
     * @param x Posición X de la pelota
     * @param y Posición Y de la pelota
     * @param size Tamaño de la pelota
     */
    void draw(ShapeRenderer shape, int x, int y, int size);
}
