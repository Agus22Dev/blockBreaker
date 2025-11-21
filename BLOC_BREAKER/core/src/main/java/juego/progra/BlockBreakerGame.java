package juego.progra;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

/**
 * Contexto principal del juego Block Breaker.
 * Administra niveles, puntuación, vidas y el comportamiento de la pelota, el pad y los bloques.
 *
 * Utiliza las clases AbstractBlock, SimpleBlock y StrongBlock (GM1.4).
 */
public class BlockBreakerGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shape;
	private PingBall ball;
	private Paddle pad;
	// Música de fondo (se carga sólo si el archivo existe en assets/music/bg.ogg)
	private Music bgMusic;
	private ArrayList<AbstractBlock> blocks = new ArrayList<>();
	private ArrayList<PowerUp> powerUps = new ArrayList<>();
	private int vidas;
	private int puntaje;
	private int nivel;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(3, 2);
		nivel = 1;
		crearBloques(2 + nivel);

		shape = new ShapeRenderer();
		ball = new PingBall(Gdx.graphics.getWidth() / 2 - 10, 41, 10, GameConfig.BALL_SPEED_X, GameConfig.BALL_SPEED_Y, true);
		pad = new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10);

		// Intentar cargar música de fondo si el archivo existe en assets/music/bg.ogg
		try {
			FileHandle fh = Gdx.files.internal("music/bg.ogg");
			if (fh.exists()) {
				bgMusic = Gdx.audio.newMusic(fh);
				bgMusic.setLooping(true);
				bgMusic.setVolume(GameConfig.BG_MUSIC_VOLUME);
				bgMusic.play();
			}
		} catch (Throwable t) {
			// No bloquear si el backend/archivo no está disponible. Dejar sin música.
			bgMusic = null;
		}
		vidas = 3;
		puntaje = 0;
	}

	/**
	 * Genera los bloques del nivel.
	 */
	public void crearBloques(int filas) {
		blocks.clear();
		powerUps.clear(); // Limpiar power-ups al cambiar de nivel
		int blockWidth = 70;
		int blockHeight = 26;
		int y = Gdx.graphics.getHeight();

		for (int cont = 0; cont < filas; cont++) {
			y -= blockHeight + 10;
			for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
				// 60% simples, 30% fuertes, 10% regenerativos
				double random = Math.random();
				if (random < 0.1) {
					blocks.add(new RegenerativeBlock(x, y, blockWidth, blockHeight));
				} else if (random < 0.4) {
					blocks.add(new StrongBlock(x, y, blockWidth, blockHeight));
				} else {
					blocks.add(new SimpleBlock(x, y, blockWidth, blockHeight));
				}
			}
		}
	}
	
	/**
	 * Crea un power-up aleatorio en la posición dada.
	 */
	private void createRandomPowerUp(int x, int y) {
		double random = Math.random();
		if (random < 0.33) {
			powerUps.add(new ExtraLifePowerUp(x, y));
		} else if (random < 0.66) {
			powerUps.add(new SlowBallPowerUp(x, y));
		} else {
			powerUps.add(new WidePaddlePowerUp(x, y));
		}
	}
	
	/**
	 * Métodos públicos para aplicar efectos de power-ups.
	 */
	public void addLife() {
		vidas++;
	}
	
	public void slowDownBall() {
		ball.activateSlow();
	}
	
	public void widenPaddle() {
		pad.activateWide();
	}

	/**
	 * Dibuja los textos de interfaz.
	 */
	public void dibujaTextos() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		font.draw(batch, "Puntos: " + puntaje, 10, 25);
		font.draw(batch, "Vidas : " + vidas, Gdx.graphics.getWidth() - 200, 25);
		font.draw(batch, "Nivel : " + nivel, Gdx.graphics.getWidth() / 2 - 60, 25);
		batch.end();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.begin(ShapeRenderer.ShapeType.Filled);

		// Actualizar paddle
		pad.update();
		pad.draw(shape);

		// --- Movimiento inicial de la bola ---
		if (ball.estaQuieto()) {
			ball.setXY(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11);
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setEstaQuieto(false);
		} else {
			ball.update();
		}

		// --- Verificar si la bola se va abajo ---
		if (ball.getY() < 0) {
			vidas--;
			ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11, 10, GameConfig.BALL_SPEED_X, GameConfig.BALL_SPEED_Y, true);
		}

		// --- Game over ---
		if (vidas <= 0) {
			vidas = 3;
			nivel = 1;
			puntaje = 0;
			crearBloques(2 + nivel);
		}

		// --- Siguiente nivel ---
		if (blocks.isEmpty()) {
			nivel++;
			crearBloques(2 + nivel);
			ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11, 10, GameConfig.BALL_SPEED_X, GameConfig.BALL_SPEED_Y, true);
		}

		// --- Dibujar bloques y detectar colisiones ---
		for (AbstractBlock b : blocks) {
			b.draw(shape);
			ball.checkCollision(b);
		}

		// --- Actualizar bloques destruidos y generar power-ups ---
		for (int i = 0; i < blocks.size(); i++) {
			AbstractBlock b = blocks.get(i);
			if (b.isDestroyed()) {
				puntaje += b.getPointValue();
				
				// Probabilidad de generar power-up
				if (Math.random() < GameConfig.POWERUP_DROP_CHANCE) {
					createRandomPowerUp(b.getX() + b.getWidth() / 2 - GameConfig.POWERUP_WIDTH / 2, 
					                   b.getY());
				}
				
				blocks.remove(i);
				i--;
			}
		}
		
		// --- Actualizar y dibujar power-ups ---
		for (PowerUp p : powerUps) {
			p.update();
			p.draw(shape);
			
			// Verificar colisión con el paddle
			if (!p.isDestroyed() && checkCollision(p, pad)) {
				p.applyEffect(this);
				puntaje += p.getPointValue();
				p.takeDamage(); // Destruir el power-up
			}
		}
		
		// --- Eliminar power-ups destruidos ---
		for (int i = 0; i < powerUps.size(); i++) {
			if (powerUps.get(i).isDestroyed()) {
				powerUps.remove(i);
				i--;
			}
		}

		// --- Colisión con el pad ---
		ball.checkCollision(pad);
		ball.draw(shape);

		shape.end();
		dibujaTextos();
	}
	
	/**
	 * Verifica colisión entre un power-up y el paddle.
	 */
	private boolean checkCollision(PowerUp powerUp, Paddle paddle) {
		boolean intersectaX = (paddle.getX() + paddle.getWidth() >= powerUp.getX()) && 
		                      (paddle.getX() <= powerUp.getX() + powerUp.getWidth());
		boolean intersectaY = (paddle.getY() + paddle.getHeight() >= powerUp.getY()) && 
		                      (paddle.getY() <= powerUp.getY() + powerUp.getHeight());
		return intersectaX && intersectaY;
	}

	@Override
	public void dispose() {
		batch.dispose();
		shape.dispose();
		font.dispose();
		if (bgMusic != null) {
			bgMusic.stop();
			bgMusic.dispose();
			bgMusic = null;
		}
	}

	@Override
	public void pause() {
		if (bgMusic != null && bgMusic.isPlaying()) bgMusic.pause();
	}

	@Override
	public void resume() {
		if (bgMusic != null && !bgMusic.isPlaying()) bgMusic.play();
	}
}
