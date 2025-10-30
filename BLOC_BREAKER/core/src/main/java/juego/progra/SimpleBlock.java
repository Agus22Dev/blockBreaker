package juego.progra;

import java.util.logging.Logger;

/**
 * Bloque simple: se destruye con un solo golpe.
 * Hereda de AbstractBlock.
 */
public class SimpleBlock extends AbstractBlock {

    private static final Logger LOGGER = Logger.getLogger(SimpleBlock.class.getName());

    public SimpleBlock(int x, int y, int width, int height) {
        // pointValue = 10 puntos básicos
        super(x, y, width, height, GameConfig.SIMPLE_BLOCK_POINTS);
    }

    @Override
    public boolean takeDamage() {
        if (!destroyed) {
            destroyed = true;
            LOGGER.info("💥 Bloque simple destruido (+" + getPointValue() + " puntos)");
            return true;
        }
        return false;
    }
}
