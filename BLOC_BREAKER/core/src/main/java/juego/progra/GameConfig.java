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
    public static final int PADDLE_STEP = 15;
    // Audio
    // Volumen por defecto para la música de fondo (0.0f - 1.0f)
    public static final float BG_MUSIC_VOLUME = 0.25f;
}
