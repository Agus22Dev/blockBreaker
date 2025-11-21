package juego.progra;

/**
 * Fábrica concreta para crear bloques de nivel NORMAL.
 * Implementa Abstract Factory (GM2.4).
 * 
 * Características:
 * - Bloques simples: frecuencia estándar
 * - Bloques fuertes: resistencia normal (3 golpes)
 * - Bloques regenerativos: regeneración estándar
 */
public class NormalLevelFactory implements BlockFactory {
    
    @Override
    public AbstractBlock createSimpleBlock(int x, int y, int width, int height) {
        return new SimpleBlock(x, y, width, height);
    }
    
    @Override
    public AbstractBlock createStrongBlock(int x, int y, int width, int height) {
        return new StrongBlock(x, y, width, height);
    }
    
    @Override
    public AbstractBlock createRegenerativeBlock(int x, int y, int width, int height) {
        return new RegenerativeBlock(x, y, width, height);
    }
    
    @Override
    public String getLevelType() {
        return "NORMAL";
    }
}
