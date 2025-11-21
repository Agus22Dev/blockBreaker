package juego.progra;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Clase abstracta base para pantallas del juego.
 * Implementa el patrón Template Method (GM2.2).
 * 
 * Template Method Pattern:
 * - Define el esqueleto del algoritmo de renderizado en renderScreen()
 * - Las subclases implementan los pasos específicos (update, render)
 * - Garantiza que todas las pantallas sigan el mismo flujo de ejecución
 */
public abstract class AbstractScreen implements Screen {
    
    protected BlockBreakerGame game;
    
    public AbstractScreen(BlockBreakerGame game) {
        this.game = game;
    }
    
    /**
     * TEMPLATE METHOD - Define el algoritmo general de renderizado de una pantalla.
     * Este método NO debe ser sobrescrito por las subclases.
     * 
     * Pasos del algoritmo:
     * 1. Actualizar lógica de la pantalla (update)
     * 2. Renderizar contenido visual (render)
     * 
     * @param batch SpriteBatch para dibujar sprites/texto
     * @param shape ShapeRenderer para dibujar formas
     * @param font BitmapFont para dibujar texto
     * @param delta Tiempo transcurrido desde el último frame
     */
    public final void renderScreen(SpriteBatch batch, ShapeRenderer shape, BitmapFont font, float delta) {
        // Paso 1: Actualizar lógica (paso abstracto - implementado por subclases)
        update(delta);
        
        // Paso 2: Renderizar gráficos (paso abstracto - implementado por subclases)
        render(batch, shape, font);
    }
    
    /**
     * Método abstracto: Actualiza la lógica de la pantalla.
     * Las subclases DEBEN implementar este método.
     * 
     * @param delta Tiempo en segundos desde el último frame
     */
    public abstract void update(float delta);
    
    /**
     * Método abstracto: Renderiza el contenido visual de la pantalla.
     * Las subclases DEBEN implementar este método.
     * 
     * @param batch SpriteBatch para dibujar sprites/texto
     * @param shape ShapeRenderer para dibujar formas
     * @param font BitmapFont para dibujar texto
     */
    public abstract void render(SpriteBatch batch, ShapeRenderer shape, BitmapFont font);
    
    @Override
    public void show() {
        // Por defecto no hace nada
    }
    
    @Override
    public void hide() {
        // Por defecto no hace nada
    }
    
    @Override
    public void dispose() {
        // Por defecto no hace nada
    }
}
