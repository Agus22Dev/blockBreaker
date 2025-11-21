package juego.progra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Pantalla de créditos del juego.
 * Muestra información sobre los creadores y permite volver al menú.
 */
public class CreditsScreen extends AbstractScreen {
    
    private float timeSinceLastInput = 0;
    private static final float INPUT_DELAY = 0.3f;
    
    public CreditsScreen(BlockBreakerGame game) {
        super(game);
    }
    
    @Override
    public void update(float delta) {
        timeSinceLastInput += delta;
        
        // Volver al menú con ESC o BACKSPACE
        if (timeSinceLastInput >= INPUT_DELAY) {
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || 
                Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) ||
                Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                ScreenManager.getInstance().setScreen(new MenuScreen(game));
                timeSinceLastInput = 0;
            }
        }
    }
    
    @Override
    public void render(SpriteBatch batch, ShapeRenderer shape, BitmapFont font) {
        // Fondo oscuro
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Decoración
        shape.begin(ShapeRenderer.ShapeType.Filled);
        
        // Efecto de estrellas parpadeantes
        for (int i = 0; i < 30; i++) {
            float alpha = (float) Math.sin(System.currentTimeMillis() * 0.002 + i * 0.5) * 0.5f + 0.5f;
            shape.setColor(new Color(1f, 1f, 1f, alpha));
            int x = (i * 73) % 800;
            int y = (i * 97) % 480;
            shape.circle(x, y, 2);
        }
        
        shape.end();
        
        // Texto
        batch.begin();
        
        // Título
        font.getData().setScale(3, 2.5f);
        font.setColor(Color.CYAN);
        font.draw(batch, "CREDITOS", 280, 420);
        
        // Información de créditos
        font.getData().setScale(2, 1.5f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Desarrollado por:", 250, 360);
        
        font.getData().setScale(1.5f, 1.2f);
        font.setColor(Color.YELLOW);
        font.draw(batch, "AGUSTIN IGNACIO", 250, 320);
        font.draw(batch, "SANDOVAL CARVALLO", 240, 295);
        
        font.draw(batch, "MIGUEL SUNIL ANTONIO", 210, 260);
        font.draw(batch, "SANTIBANEZ HIDALGO", 230, 235);
        
        font.draw(batch, "LEON SUHR", 310, 200);
        
        font.getData().setScale(1.8f, 1.4f);
        font.setColor(Color.WHITE);
        font.draw(batch, "Universidad:", 290, 150);
        
        font.getData().setScale(1.3f, 1.1f);
        font.setColor(Color.CYAN);
        font.draw(batch, "Pontificia Universidad", 230, 120);
        font.draw(batch, "Catolica de Valparaiso", 230, 95);
        
        font.getData().setScale(1.2f, 1f);
        font.setColor(new Color(0.7f, 0.7f, 0.7f, 1f));
        font.draw(batch, "Block Breaker - 2025", 270, 60);
        
        // Instrucciones para volver
        font.getData().setScale(1.5f, 1.2f);
        font.setColor(Color.GREEN);
        float pulse = (float) Math.sin(System.currentTimeMillis() * 0.003) * 0.3f + 0.7f;
        font.setColor(new Color(0, 1, 0, pulse));
        font.draw(batch, "Presiona ENTER para volver", 220, 40);
        
        batch.end();
    }
}
