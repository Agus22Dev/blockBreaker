package juego.progra;

/**
 * Interfaz Abstract Factory (GM2.4).
 * Define el contrato para fábricas que crean familias de bloques relacionados.
 * 
 * Patrón Abstract Factory:
 * - Proporciona una interfaz para crear familias de objetos relacionados
 * - Sin especificar sus clases concretas
 * - Cada fábrica concreta crea bloques con características específicas
 */
public interface BlockFactory {
    
    /**
     * Crea un bloque simple con las características de esta fábrica.
     * @param x posición X
     * @param y posición Y
     * @param width ancho del bloque
     * @param height alto del bloque
     * @return un nuevo SimpleBlock
     */
    AbstractBlock createSimpleBlock(int x, int y, int width, int height);
    
    /**
     * Crea un bloque fuerte con las características de esta fábrica.
     * @param x posición X
     * @param y posición Y
     * @param width ancho del bloque
     * @param height alto del bloque
     * @return un nuevo StrongBlock
     */
    AbstractBlock createStrongBlock(int x, int y, int width, int height);
    
    /**
     * Crea un bloque regenerativo con las características de esta fábrica.
     * @param x posición X
     * @param y posición Y
     * @param width ancho del bloque
     * @param height alto del bloque
     * @return un nuevo RegenerativeBlock
     */
    AbstractBlock createRegenerativeBlock(int x, int y, int width, int height);
    
    /**
     * Retorna el nombre del tipo de nivel que produce esta fábrica.
     * @return nombre del nivel
     */
    String getLevelType();
}
