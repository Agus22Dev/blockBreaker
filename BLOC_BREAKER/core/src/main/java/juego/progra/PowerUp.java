package juego.progra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Clase abstracta base para todos los power-ups del juego.
 * Los power-ups son objetos destructibles que caen desde los bloques destruidos
 * y otorgan beneficios al jugador cuando son recogidos por el paddle.
 * 
 * Implementa Destructible para poder ser manejados de forma polimórfica.
 */
public abstract class PowerUp implements Destructible {
    
    protected int x, y;
    protected int width, height;
    protected int fallSpeed;
    protected Color color;
    protected boolean destroyed;
    protected int pointValue;
    protected String symbol; // Símbolo que se dibuja en el power-up
    
    /**
     * Constructor base para power-ups.
     * 
     * @param x posición inicial X
     * @param y posición inicial Y
     * @param width ancho del power-up
     * @param height alto del power-up
     * @param color color del power-up
     * @param pointValue puntos que otorga al recogerlo
     * @param symbol símbolo visual del power-up
     */
    public PowerUp(int x, int y, int width, int height, Color color, int pointValue, String symbol) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.pointValue = pointValue;
        this.symbol = symbol;
        this.fallSpeed = GameConfig.POWERUP_FALL_SPEED;
        this.destroyed = false;
    }
    
    /**
     * Actualiza la posición del power-up (cae hacia abajo).
     */
    public void update() {
        if (!destroyed) {
            y -= fallSpeed;
            
            // Se destruye si sale de la pantalla por abajo
            if (y + height < 0) {
                destroyed = true;
            }
        }
    }
    
    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            // Dibujar el fondo del power-up
            shape.setColor(color);
            shape.rect(x, y, width, height);
            
            // Dibujar borde blanco
            shape.setColor(Color.WHITE);
            shape.rect(x, y, width, 2); // borde superior
            shape.rect(x, y + height - 2, width, 2); // borde inferior
            shape.rect(x, y, 2, height); // borde izquierdo
            shape.rect(x + width - 2, y, 2, height); // borde derecho
            
            // Efecto de brillo pulsante
            float pulse = (float)(Math.sin(System.currentTimeMillis() * 0.005) * 0.3 + 0.7);
            shape.setColor(new Color(1f, 1f, 1f, pulse));
            shape.rect(x + 4, y + 4, width - 8, height - 8);
        }
    }
    
    @Override
    public boolean takeDamage() {
        // Los power-ups se destruyen inmediatamente al ser "recogidos"
        if (!destroyed) {
            destroyed = true;
            return true;
        }
        return false;
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
    
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Método abstracto que define el efecto del power-up.
     * Cada tipo de power-up debe implementar su efecto específico.
     * 
     * @param game referencia al juego para aplicar el efecto
     */
    public abstract void applyEffect(BlockBreakerGame game);
    
    /**
     * Retorna una descripción del power-up.
     * 
     * @return descripción del efecto
     */
    public abstract String getDescription();
}
