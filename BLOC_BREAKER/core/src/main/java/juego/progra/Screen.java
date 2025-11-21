package juego.progra;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Interfaz que define el comportamiento de una pantalla del juego.
 * Todas las pantallas (Menú, Juego, Créditos, Opciones) deben implementar esta interfaz.
 */
public interface Screen {
    
    /**
     * Se llama cuando esta pantalla se activa.
     */
    void show();
    
    /**
     * Se llama cada frame para actualizar la lógica de la pantalla.
     * @param delta tiempo transcurrido desde el último frame
     */
    void update(float delta);
    
    /**
     * Se llama cada frame para renderizar la pantalla.
     * @param batch para dibujar sprites y texto
     * @param shape para dibujar formas geométricas
     * @param font para dibujar texto
     */
    void render(SpriteBatch batch, ShapeRenderer shape, BitmapFont font);
    
    /**
     * Se llama cuando esta pantalla se desactiva.
     */
    void hide();
    
    /**
     * Se llama cuando se debe liberar recursos de esta pantalla.
     */
    void dispose();
}
