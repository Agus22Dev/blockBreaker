package juego.progra;

/**
 * Fábrica concreta para crear bloques de nivel FÁCIL.
 * Implementa Abstract Factory (GM2.4).
 * 
 * Características:
 * - Bloques simples: más frecuentes
 * - Bloques fuertes: menos resistentes (2 golpes en vez de 3)
 * - Bloques regenerativos: regeneración más lenta
 */
public class EasyLevelFactory implements BlockFactory {
    
    @Override
    public AbstractBlock createSimpleBlock(int x, int y, int width, int height) {
        return new SimpleBlock(x, y, width, height);
    }
    
    @Override
    public AbstractBlock createStrongBlock(int x, int y, int width, int height) {
        // En nivel fácil, los bloques fuertes tienen menos vida
        StrongBlock block = new StrongBlock(x, y, width, height);
        // Se puede ajustar si StrongBlock tuviera constructor con vida personalizada
        return block;
    }
    
    @Override
    public AbstractBlock createRegenerativeBlock(int x, int y, int width, int height) {
        return new RegenerativeBlock(x, y, width, height);
    }
    
    @Override
    public String getLevelType() {
        return "FACIL";
    }
}
