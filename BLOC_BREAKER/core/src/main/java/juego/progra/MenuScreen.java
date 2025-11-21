package juego.progra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Pantalla del menú principal del juego.
 * Muestra las opciones: Inicio, Créditos, Opciones, Salir.
 */
public class MenuScreen extends AbstractScreen {
    
    private String[] menuOptions = {"INICIO", "CREDITOS", "OPCIONES", "SALIR"};
    private int selectedIndex = 0;
    private float timeSinceLastInput = 0;
    private static final float INPUT_DELAY = 0.15f; // Delay entre inputs
    private Texture logo;
    
    public MenuScreen(BlockBreakerGame game) {
        super(game);
    }
    
    @Override
    public void show() {
        selectedIndex = 0;
        // Cargar logo
        try {
            logo = new Texture(Gdx.files.internal("logo.png"));
        } catch (Exception e) {
            logo = null; // Si no existe el logo, seguir sin él
        }
        // Reproducir música del menú
        MusicManager.getInstance().playMenuMusic();
    }
    
    @Override
    public void update(float delta) {
        timeSinceLastInput += delta;
        
        // Navegación con teclas
        if (timeSinceLastInput >= INPUT_DELAY) {
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
            
            // Selección con Enter o Space
            if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                selectOption();
                timeSinceLastInput = 0;
            }
        }
    }
    
    @Override
    public void render(SpriteBatch batch, ShapeRenderer shape, BitmapFont font) {
        // Limpiar pantalla con color de fondo oscuro
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Dibujar texto y logo
        batch.begin();
        
        // Dibujar logo si existe
        if (logo != null) {
            // Logo más grande y mejor posicionado
            float logoWidth = 250;
            float logoHeight = 250;
            float logoX = (800 - logoWidth) / 2;
            float logoY = 210;
            batch.draw(logo, logoX, logoY, logoWidth, logoHeight);
        } else {
            // Si no hay logo, mostrar título de texto
            font.getData().setScale(3.5f, 2.5f);
            font.setColor(Color.CYAN);
            font.draw(batch, "BLOCK BREAKER", 200, 400);
        }
        
        // Opciones del menú - texto más pequeño y mejor espaciado
        font.getData().setScale(2f, 1.5f);
        int startY = 180;
        int spacing = 45;
        
        for (int i = 0; i < menuOptions.length; i++) {
            int y = startY - i * spacing;
            if (i == selectedIndex) {
                // Opción seleccionada
                font.setColor(Color.YELLOW);
                font.draw(batch, "> " + menuOptions[i] + " <", 310, y);
            } else {
                // Opciones no seleccionadas
                font.setColor(Color.WHITE);
                font.draw(batch, menuOptions[i], 330, y);
            }
        }
        
        // Instrucciones en la parte superior derecha - más discretas
        font.getData().setScale(0.9f, 0.8f);
        font.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
        font.draw(batch, "W/S o Flechas: Navegar", 560, 470);
        font.draw(batch, "ENTER: Seleccionar", 580, 455);
        
        batch.end();
    }
    
    @Override
    public void hide() {
        // Liberar recursos del logo cuando se sale de la pantalla
        if (logo != null) {
            logo.dispose();
            logo = null;
        }
    }
    
    /**
     * Ejecuta la acción de la opción seleccionada.
     */
    private void selectOption() {
        switch (selectedIndex) {
            case 0: // INICIO
                ScreenManager.getInstance().setScreen(new GameScreen(game));
                break;
            case 1: // CREDITOS
                ScreenManager.getInstance().setScreen(new CreditsScreen(game));
                break;
            case 2: // OPCIONES
                ScreenManager.getInstance().setScreen(new OptionsScreen(game));
                break;
            case 3: // SALIR
                Gdx.app.exit();
                break;
        }
    }
}
