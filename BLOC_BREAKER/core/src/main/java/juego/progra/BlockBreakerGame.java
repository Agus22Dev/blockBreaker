package juego.progra;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Clase principal del juego Block Breaker.
 * Actúa como contenedor y gestor de pantallas (Menú, Juego, Créditos, Opciones).
 * La lógica del juego se encuentra en GameScreen.
 */
public class BlockBreakerGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shape;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(3, 2);
		shape = new ShapeRenderer();
		
		// Inicializar el gestor de música
		MusicManager.getInstance();
		
		// Iniciar con el menú principal
		ScreenManager.getInstance().setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Screen currentScreen = ScreenManager.getInstance().getCurrentScreen();
		if (currentScreen != null && currentScreen instanceof AbstractScreen) {
			// Usar el Template Method para renderizar la pantalla
			// Esto garantiza que todas las pantallas sigan el mismo flujo: update() -> render()
			camera.update();
			batch.setProjectionMatrix(camera.combined);
			((AbstractScreen) currentScreen).renderScreen(batch, shape, font, Gdx.graphics.getDeltaTime());
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		shape.dispose();
		font.dispose();
		MusicManager.getInstance().dispose();
		ScreenManager.getInstance().dispose();
	}

	@Override
	public void pause() {
		// Se puede agregar lógica de pausa global aquí si se necesita
	}

	@Override
	public void resume() {
		// Se puede agregar lógica de resume global aquí si se necesita
	}
}
