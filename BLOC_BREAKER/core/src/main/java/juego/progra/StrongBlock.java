package juego.progra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;

/**
 * Implementación de un bloque resistente que requiere múltiples golpes para ser destruido.
 * Cambia de color gradualmente según el daño recibido.
 */
public class StrongBlock implements Destructible {
    protected int x, y, width, height;
    protected Color[] colorStages;
    protected boolean destroyed;
    protected int maxHits;
    protected int currentHits;
    protected int pointValue;
    
    public StrongBlock(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.destroyed = false;
        this.maxHits = 3; // Requiere 3 golpes para destruirse
        this.currentHits = 0;
        this.pointValue = 30; // Más puntos por ser más difícil
        
        // Crear diferentes colores para cada estado de daño
        Random r = new Random(x + y);
        this.colorStages = new Color[maxHits];
        
        // Color inicial (más brillante)
        colorStages[0] = new Color(0.8f, 0.2f + r.nextFloat(0.5f), 0.2f + r.nextFloat(0.5f), 1.0f);
        // Color intermedio
        colorStages[1] = new Color(0.6f, 0.4f + r.nextFloat(0.3f), 0.4f + r.nextFloat(0.3f), 1.0f);
        // Color antes de destruirse (más oscuro)
        colorStages[2] = new Color(0.4f, 0.6f + r.nextFloat(0.2f), 0.6f + r.nextFloat(0.2f), 1.0f);
    }
    
    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            shape.setColor(getCurrentColor());
            shape.rect(x, y, width, height);
            
            // Dibujar borde para indicar que es un bloque especial
            shape.setColor(Color.WHITE);
            // Borde superior
            shape.rect(x, y + height - 2, width, 2);
            // Borde inferior
            shape.rect(x, y, width, 2);
            // Borde izquierdo
            shape.rect(x, y, 2, height);
            // Borde derecho
            shape.rect(x + width - 2, y, 2, height);
        }
    }
    
    private Color getCurrentColor() {
        if (currentHits >= maxHits) {
            return Color.GRAY; // No debería llegar aquí si está destruido
        }
        return colorStages[currentHits];
    }
    
    @Override
    public boolean takeDamage() {
        if (!destroyed) {
            currentHits++;
            if (currentHits >= maxHits) {
                destroyed = true;
                return true; // Finalmente destruido
            }
        }
        return false; // Aún no destruido
    }
    
    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public int getY() {
        return y;
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public int getPointValue() {
        return pointValue;
    }
    
    /**
     * Obtiene el número de golpes restantes para destruir el bloque
     * @return golpes restantes
     */
    public int getRemainingHits() {
        return Math.max(0, maxHits - currentHits);
    }
}