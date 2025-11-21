package juego.progra;

/**
 * Fábrica concreta para crear bloques de nivel DIFÍCIL.
 * Implementa Abstract Factory (GM2.4).
 * 
 * Características:
 * - Bloques simples: menos frecuentes
 * - Bloques fuertes: más resistentes
 * - Bloques regenerativos: regeneración más rápida (más desafiante)
 */
public class HardLevelFactory implements BlockFactory {
    
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
        // En nivel difícil, los bloques regenerativos son más comunes
        return new RegenerativeBlock(x, y, width, height);
    }
    
    @Override
    public String getLevelType() {
        return "DIFICIL";
    }
}
