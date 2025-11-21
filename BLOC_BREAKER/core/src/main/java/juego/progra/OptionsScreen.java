package juego.progra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Pantalla de opciones del juego.
 * Permite configurar volumen de música y apariencia de la pelota.
 */
public class OptionsScreen extends AbstractScreen {
    
    private String[] menuOptions = {"VOLUMEN", "APARIENCIA PELOTA", "VOLVER"};
    private int selectedIndex = 0;
    private float timeSinceLastInput = 0;
    private static final float INPUT_DELAY = 0.15f;
    
    public OptionsScreen(BlockBreakerGame game) {
        super(game);
    }
    
    @Override
    public void show() {
        selectedIndex = 0;
    }
    
    @Override
    public void update(float delta) {
        timeSinceLastInput += delta;
        
        if (timeSinceLastInput >= INPUT_DELAY) {
            // Navegación vertical
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                selectedIndex--;
                if (selectedIndex < 0) selectedIndex = menuOptions.length - 1;
                timeSinceLastInput = 0;
            }
            
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                selectedIndex++;
                if (selectedIndex >= menuOptions.length) selectedIndex = 0;
                timeSinceLastInput = 0;
            }
            
            // Ajustes horizontales
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                adjustOption(-1);
                timeSinceLastInput = 0;
            }
            
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                adjustOption(1);
                timeSinceLastInput = 0;
            }
            
            // Selección
            if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                if (selectedIndex == 2) { // VOLVER
                    returnToMenu();
                }
                timeSinceLastInput = 0;
            }
            
            // ESC para volver
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                returnToMenu();
                timeSinceLastInput = 0;
            }
        }
    }
    
    /**
     * Ajusta la opción seleccionada.
     * @param direction -1 para decrementar, 1 para incrementar
     */
    private void adjustOption(int direction) {
        GameSettings settings = GameSettings.getInstance();
        
        switch (selectedIndex) {
            case 0: // VOLUMEN
                if (direction > 0) {
                    settings.increaseMusicVolume();
                } else {
                    settings.decreaseMusicVolume();
                }
                MusicManager.getInstance().updateVolume();
                break;
                
            case 1: // APARIENCIA PELOTA
                if (direction > 0) {
                    settings.nextBallAppearance();
                } else {
                    settings.previousBallAppearance();
                }
                break;
        }
    }
    
    /**
     * Vuelve al menú principal.
     */
    private void returnToMenu() {
        ScreenManager.getInstance().setScreen(new MenuScreen(game));
    }
    
    @Override
    public void render(SpriteBatch batch, ShapeRenderer shape, BitmapFont font) {
        // Fondo oscuro
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Decoración
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(0.2f, 0.3f, 0.5f, 0.3f));
        shape.rect(100, 100, 600, 300);
        shape.end();
        
        // Texto
        batch.begin();
        
        GameSettings settings = GameSettings.getInstance();
        
        // Título
        font.getData().setScale(3, 2.5f);
        font.setColor(Color.CYAN);
        font.draw(batch, "OPCIONES", 270, 420);
        
        // Opciones configurables
        font.getData().setScale(2, 1.5f);
        int startY = 320;
        int spacing = 90; // Aumentado de 70 a 90 para más espacio
        
        for (int i = 0; i < menuOptions.length; i++) {
            boolean isSelected = (i == selectedIndex);
            int y = startY - i * spacing;
            
            if (isSelected) {
                font.setColor(Color.YELLOW);
                font.draw(batch, ">", 150, y);
            } else {
                font.setColor(Color.WHITE);
            }
            
            // Dibujar nombre de la opción
            font.draw(batch, menuOptions[i], 190, y);
            
            // Dibujar valor de la opción
            if (i == 0) { // VOLUMEN
                int volumePercent = (int)(settings.getMusicVolume() * 100);
                font.setColor(isSelected ? Color.GREEN : Color.GRAY);
                font.draw(batch, "< " + volumePercent + "% >", 480, y);
            } else if (i == 1) { // APARIENCIA PELOTA
                font.setColor(isSelected ? Color.GREEN : Color.GRAY);
                font.draw(batch, "< " + settings.getBallAppearanceText() + " >", 480, y);
            }
        }
        
        // Instrucciones
        font.getData().setScale(1.2f, 1f);
        font.setColor(new Color(0.7f, 0.7f, 0.7f, 1));
        font.draw(batch, "Flechas o WASD: Navegar y Ajustar", 200, 80);
        font.draw(batch, "ENTER/ESC: Volver al menu", 220, 55);
        
        batch.end();
    }
}
