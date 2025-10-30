package juego.progra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Bloque fuerte que requiere varios golpes para destruirse.
 * Cambia de color gradualmente según el daño recibido.
 */
public class StrongBlock extends AbstractBlock {

    private static final Logger LOGGER = Logger.getLogger(StrongBlock.class.getName());

    private int maxHits;
    private int currentHits;
    private Color[] colorStages;

    public StrongBlock(int x, int y, int width, int height) {
        // Llamada al constructor del padre: AbstractBlock
        super(x, y, width, height, GameConfig.STRONG_BLOCK_POINTS); // vale más puntos
        this.maxHits = 3;
        this.currentHits = 0;

        // Colores según el daño recibido
        Random r = new Random(x + y);
        this.colorStages = new Color[maxHits];
        colorStages[0] = new Color(0.8f, 0.2f + r.nextFloat() * 0.5f, 0.2f + r.nextFloat() * 0.5f, 1.0f);
        colorStages[1] = new Color(0.6f, 0.4f + r.nextFloat() * 0.3f, 0.4f + r.nextFloat() * 0.3f, 1.0f);
        colorStages[2] = new Color(0.4f, 0.6f + r.nextFloat() * 0.2f, 0.6f + r.nextFloat() * 0.2f, 1.0f);
    }

    @Override
    public boolean takeDamage() {
        if (!destroyed) {
            currentHits++;
            if (currentHits >= maxHits) {
                destroyed = true;
                LOGGER.info("💥 StrongBlock destruido (+" + getPointValue() + " puntos)");
                return true;
            } else {
                LOGGER.info("StrongBlock golpeado! HP restantes: " + getRemainingHits());
            }
        }
        return false;
    }

    /**
     * Dibuja el bloque con color según su nivel de daño.
     * Sobrescribe draw() del padre para personalizar su apariencia.
     */
    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            shape.setColor(getCurrentColor());
            shape.rect(x, y, width, height);

            // Borde blanco decorativo
            shape.setColor(Color.WHITE);
            shape.rect(x, y + height - 2, width, 2);  // superior
            shape.rect(x, y, width, 2);               // inferior
            shape.rect(x, y, 2, height);              // izquierda
            shape.rect(x + width - 2, y, 2, height);  // derecha
        }
    }

    private Color getCurrentColor() {
        if (currentHits >= maxHits) {
            return Color.GRAY;
        }
        return colorStages[Math.min(currentHits, colorStages.length - 1)];
    }

    public int getRemainingHits() {
        return Math.max(0, maxHits - currentHits);
    }
}

