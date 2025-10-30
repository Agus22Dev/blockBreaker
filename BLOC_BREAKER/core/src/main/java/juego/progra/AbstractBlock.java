package juego.progra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;

/**
 * Clase abstracta base para todos los tipos de bloques del juego.
 * Define el comportamiento común: posición, tamaño, color, dibujo y estado de destrucción.
 * Las subclases deben definir cómo reaccionan al recibir daño (takeDamage).
 */
public abstract class AbstractBlock implements Destructible {

    protected int x, y, width, height;
    protected Color color;
    protected boolean destroyed;
    protected int pointValue;

    public AbstractBlock(int x, int y, int width, int height, int pointValue) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.pointValue = pointValue;
        this.destroyed = false;

    // Color aleatorio (como en tu clase Block original)
    Random r = new Random(x + y);
    // nextFloat() no acepta argumentos; para escalar a un rango usamos multiplicación.
    // Usamos 0.1..1.0 para la componente R y 0..1 para G/B.
    this.color = new Color(0.1f + r.nextFloat() * 0.9f, r.nextFloat(), r.nextFloat(), 1f);
    }

    /**
     * Dibuja el bloque si no está destruido.
     */
    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            shape.setColor(color);
            shape.rect(x, y, width, height);
        }
    }

    /**
     * Indica si el bloque ya fue destruido.
     */
    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public int getX() { return x; }

    @Override
    public int getY() { return y; }

    @Override
    public int getWidth() { return width; }

    @Override
    public int getHeight() { return height; }

    @Override
    public int getPointValue() { return pointValue; }

    /**
     * Método abstracto que las subclases deben implementar.
     * Define cómo reacciona el bloque al recibir daño.
     *
     * @return true si el bloque fue destruido tras el golpe.
     */
    @Override
    public abstract boolean takeDamage();
}
