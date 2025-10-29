package juego.progra;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Interfaz que define el comportamiento de objetos destructibles en el juego.
 * Esta interfaz permite implementar diferentes tipos de bloques con 
 * distintos niveles de resistencia y comportamientos.
 */
public interface Destructible {
    
    /**
     * Dibuja el objeto destructible en pantalla
     * @param shape El ShapeRenderer usado para dibujar
     */
    void draw(ShapeRenderer shape);
    
    /**
     * Aplica daño al objeto destructible
     * @return true si el objeto fue destruido, false si aún resiste
     */
    boolean takeDamage();
    
    /**
     * Indica si el objeto ha sido completamente destruido
     * @return true si está destruido, false en caso contrario
     */
    boolean isDestroyed();
    
    /**
     * Obtiene la posición X del objeto
     * @return coordenada X
     */
    int getX();
    
    /**
     * Obtiene la posición Y del objeto
     * @return coordenada Y
     */
    int getY();
    
    /**
     * Obtiene el ancho del objeto
     * @return ancho en píxeles
     */
    int getWidth();
    
    /**
     * Obtiene la altura del objeto
     * @return altura en píxeles
     */
    int getHeight();
    
    /**
     * Obtiene los puntos que otorga este objeto al ser destruido
     * @return puntos de puntuación
     */
    int getPointValue();
}