package juego.progra;

/**
 * Constantes de configuración del juego (valores simples y seguros para prototipo).
 */
public final class GameConfig {
    private GameConfig() {}

    // Puntuaciones
    public static final int SIMPLE_BLOCK_POINTS = 10;
    public static final int STRONG_BLOCK_POINTS = 30;
    public static final int REGENERATIVE_BLOCK_POINTS = 50;

    // Tiempos
    public static final long REGENERATIVE_DELAY_MS = 3000L;

    // Movimientos / UI
    // Paso de movimiento del paddle cuando se presionan las teclas izquierda/derecha
    public static final int PADDLE_STEP = 8;
    
    // Velocidades de la pelota
    public static final int BALL_SPEED_X = 4;
    public static final int BALL_SPEED_Y = 5;
    
    // Power-ups (se calcularán dinámicamente según resolución)
    // Estos son valores base para 800x480
    public static final int POWERUP_WIDTH_BASE = 30;
    public static final int POWERUP_HEIGHT_BASE = 20;
    public static final int POWERUP_FALL_SPEED = 2;
    public static final double POWERUP_DROP_CHANCE = 0.3; // 30% de probabilidad (aumentado para más diversión)
    
    /**
     * Calcula el ancho del power-up según la resolución actual.
     */
    public static int getPowerUpWidth() {
        return com.badlogic.gdx.Gdx.graphics.getWidth() * POWERUP_WIDTH_BASE / 800;
    }
    
    /**
     * Calcula la altura del power-up según la resolución actual.
     */
    public static int getPowerUpHeight() {
        return com.badlogic.gdx.Gdx.graphics.getHeight() * POWERUP_HEIGHT_BASE / 480;
    }
    
    // Puntos de power-ups
    public static final int EXTRA_LIFE_POWERUP_POINTS = 20;
    public static final int SLOW_BALL_POWERUP_POINTS = 15;
    public static final int WIDE_PADDLE_POWERUP_POINTS = 15;
    
    // Duraciones de efectos (en milisegundos)
    public static final long SLOW_BALL_DURATION = 5000L; // 5 segundos
    public static final long WIDE_PADDLE_DURATION = 8000L; // 8 segundos
    
    // Audio
    // Volumen por defecto para la música de fondo (0.0f - 1.0f)
    public static final float BG_MUSIC_VOLUME = 0.05f;
}
