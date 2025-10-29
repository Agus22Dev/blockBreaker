package juego.progra;

/**
 * Bloque simple: se destruye con un solo golpe.
 * Hereda de AbstractBlock.
 */
public class SimpleBlock extends AbstractBlock {

    public SimpleBlock(int x, int y, int width, int height) {
        // pointValue = 10 puntos básicos
        super(x, y, width, height, 10);
    }

    @Override
    public boolean takeDamage() {
        if (!destroyed) {
            destroyed = true;
            System.out.println("💥 Bloque simple destruido (+10 puntos)");
            return true;
        }
        return false;
    }
}
