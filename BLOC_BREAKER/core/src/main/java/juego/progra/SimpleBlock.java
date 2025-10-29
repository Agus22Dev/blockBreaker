package juego.progra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;

/**
 * Implementación de un bloque simple que se destruye con un solo golpe.
 * Este es el tipo de bloque básico del juego original.
 */
public class SimpleBlock implements Destructible {
    protected int x, y, width, height;
    protected Color color;
    protected boolean destroyed;
    protected int pointValue;
    
    public SimpleBlock(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.destroyed = false;
        this.pointValue = 10; // Puntos básicos
        
        // Generar color aleatorio basado en posición (como en el código original)
        Random r = new Random(x + y);
        this.color = new Color(0.1f + r.nextFloat(1), r.nextFloat(1), r.nextFloat(1), 1.0f);
    }
    
    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            shape.setColor(color);
            shape.rect(x, y, width, height);
        }
    }
    
    @Override
    public boolean takeDamage() {
        if (!destroyed) {
            destroyed = true;
            return true; // Se destruye con un golpe
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
}