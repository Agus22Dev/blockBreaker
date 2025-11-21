package juego.progra;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Pantalla del juego principal.
 * Contiene toda la lógica del juego Block Breaker.
 */
public class GameScreen extends AbstractScreen {
	private PingBall ball;
	private Paddle pad;
	private ArrayList<AbstractBlock> blocks = new ArrayList<>();
	private ArrayList<PowerUp> powerUps = new ArrayList<>();
	private int vidas;
	private int puntaje;
	private int nivel;
	private boolean paused = false;

	public GameScreen(BlockBreakerGame game) {
		super(game);
	}

    @Override
	public void show() {
		// Inicializar el juego
		nivel = 1;
		vidas = 3;
		puntaje = 0;
		
		crearBloques(2 + nivel);
		ball = new PingBall(Gdx.graphics.getWidth() / 2 - 10, 41, 10, GameConfig.BALL_SPEED_X, GameConfig.BALL_SPEED_Y, true);
		pad = new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10);

		// Reproducir música del juego
		MusicManager.getInstance().playGameMusic();
	}
	
	/**
	 * Inicializa los objetos del juego con las dimensiones actuales de la pantalla.
	 */
	private void initializeGameObjects() {
		crearBloques(2 + nivel);
		ball = new PingBall(Gdx.graphics.getWidth() / 2 - 10, 41, 10, GameConfig.BALL_SPEED_X, GameConfig.BALL_SPEED_Y, true);
		pad = new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10);
	}
	
	/**
	 * Genera los bloques del nivel según la resolución actual.
	 */
	public void crearBloques(int filas) {
		blocks.clear();
		powerUps.clear();
		
		int screenWidth = Gdx.graphics.getWidth();
		int screenHeight = Gdx.graphics.getHeight();
		
		// Calcular dimensiones de bloques para llenar toda la pantalla
		int numColumnas = 10; // Número fijo de columnas
		int spacing = 5; // Espaciado fijo en píxeles
		int totalSpacing = spacing * (numColumnas + 1); // Espacios entre bloques y bordes
		int blockWidth = (screenWidth - totalSpacing) / numColumnas;
		int blockHeight = screenHeight / 18; // Altura proporcional
		
		int y = screenHeight - spacing;

		for (int cont = 0; cont < filas; cont++) {
			y -= blockHeight + spacing;
			for (int col = 0; col < numColumnas; col++) {
				int x = spacing + col * (blockWidth + spacing);
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
	
	public void addLife() {
		vidas++;
	}
	
	public void slowDownBall() {
		ball.activateSlow();
	}
	
	public void widenPaddle() {
		pad.activateWide();
	}

	@Override
	public void update(float delta) {
		// Actualizar apariencia de la pelota si cambió en opciones
		ball.updateAppearance();
		
		// Pausar/despausar con ESC
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			paused = !paused;
		}
		
		// Si está pausado, permitir volver al menú con M
		if (paused) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
				ScreenManager.getInstance().setScreen(new MenuScreen(game));
				return;
			}
			return;
		}
		
		// Actualizar paddle
		pad.update();

		// Movimiento inicial de la bola
		if (ball.estaQuieto()) {
			ball.setXY(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11);
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) ball.setEstaQuieto(false);
		} else {
			ball.update();
		}

		// Verificar si la bola se va abajo
		if (ball.getY() < 0) {
			vidas--;
			ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11, 10, GameConfig.BALL_SPEED_X, GameConfig.BALL_SPEED_Y, true);
		}

		// Game over
		if (vidas <= 0) {
			vidas = 3;
			nivel = 1;
			puntaje = 0;
			initializeGameObjects();
		}

		// Siguiente nivel
		if (blocks.isEmpty()) {
			nivel++;
			crearBloques(2 + nivel);
			ball = new PingBall(pad.getX() + pad.getWidth() / 2 - 5, pad.getY() + pad.getHeight() + 11, 10, GameConfig.BALL_SPEED_X, GameConfig.BALL_SPEED_Y, true);
		}

		// Colisiones con bloques
		for (AbstractBlock b : blocks) {
			ball.checkCollision(b);
		}

		// Actualizar bloques destruidos y generar power-ups
		for (int i = 0; i < blocks.size(); i++) {
			AbstractBlock b = blocks.get(i);
			if (b.isDestroyed()) {
				puntaje += b.getPointValue();
				
				if (Math.random() < GameConfig.POWERUP_DROP_CHANCE) {
					createRandomPowerUp(b.getX() + b.getWidth() / 2 - GameConfig.getPowerUpWidth() / 2, 
					                   b.getY());
				}
				
				blocks.remove(i);
				i--;
			}
		}
		
		// Actualizar power-ups
		for (PowerUp p : powerUps) {
			p.update();
			
			if (!p.isDestroyed() && checkCollision(p, pad)) {
				p.applyEffect(this);
				puntaje += p.getPointValue();
				p.takeDamage();
			}
		}
		
		// Eliminar power-ups destruidos
		for (int i = 0; i < powerUps.size(); i++) {
			if (powerUps.get(i).isDestroyed()) {
				powerUps.remove(i);
				i--;
			}
		}

		// Colisión con el pad
		ball.checkCollision(pad);
	}
	
	private boolean checkCollision(PowerUp powerUp, Paddle paddle) {
		boolean intersectaX = (paddle.getX() + paddle.getWidth() >= powerUp.getX()) && 
		                      (paddle.getX() <= powerUp.getX() + powerUp.getWidth());
		boolean intersectaY = (paddle.getY() + paddle.getHeight() >= powerUp.getY()) && 
		                      (paddle.getY() <= powerUp.getY() + powerUp.getHeight());
		return intersectaX && intersectaY;
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer shape, BitmapFont font) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		shape.begin(ShapeRenderer.ShapeType.Filled);

		// Dibujar elementos del juego
		pad.draw(shape);
		
		for (AbstractBlock b : blocks) {
			b.draw(shape);
		}
		
		for (PowerUp p : powerUps) {
			p.draw(shape);
		}

		ball.draw(shape);

		shape.end();
		
		// Dibujar textos
		batch.begin();
		int screenWidth = Gdx.graphics.getWidth();
		font.getData().setScale(3, 2);
		font.setColor(Color.WHITE);
		font.draw(batch, "Puntos: " + puntaje, 10, 25);
		font.draw(batch, "Vidas : " + vidas, screenWidth - 200, 25);
		font.draw(batch, "Nivel : " + nivel, screenWidth / 2 - 60, 25);
		
		// Mostrar mensaje de pausa
		if (paused) {
			int centerX = Gdx.graphics.getWidth() / 2;
			int centerY = Gdx.graphics.getHeight() / 2;
			
			font.getData().setScale(4, 3);
			font.draw(batch, "PAUSA", centerX - 80, centerY + 50);
			font.getData().setScale(2, 1.5f);
			font.draw(batch, "ESC para continuar", centerX - 150, centerY);
			font.getData().setScale(1.8f, 1.4f);
			font.setColor(Color.YELLOW);
			font.draw(batch, "M para volver al menu", centerX - 150, centerY - 40);
			font.setColor(Color.WHITE);
		}
		
		batch.end();
	}
}
