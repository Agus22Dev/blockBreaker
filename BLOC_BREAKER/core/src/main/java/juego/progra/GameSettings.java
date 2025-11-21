package juego.progra;

/**
 * Singleton que gestiona la configuración global del juego.
 * Almacena preferencias como volumen, resolución, etc.
 * 
 * Patrón Singleton (GM2.1):
 * - Solo existe una instancia en toda la aplicación
 * - Proporciona acceso global a las configuraciones
 * - Constructor privado para prevenir instanciación externa
 */
public class GameSettings {
    private static GameSettings instance;
    
    // Configuraciones
    private float musicVolume;
    private int ballAppearanceIndex; // 0 = blanca, 1 = carita feliz, 2 = balón de fútbol
    
    /**
     * Constructor privado - Singleton pattern.
     * Inicializa valores por defecto.
     */
    private GameSettings() {
        musicVolume = GameConfig.BG_MUSIC_VOLUME;
        ballAppearanceIndex = 0; // Por defecto blanca
    }
    
    /**
     * Obtiene la única instancia de GameSettings.
     * @return instancia singleton
     */
    public static GameSettings getInstance() {
        if (instance == null) {
            instance = new GameSettings();
        }
        return instance;
    }
    
    // Getters y Setters con encapsulamiento
    
    public float getMusicVolume() {
        return musicVolume;
    }
    
    public void setMusicVolume(float volume) {
        // Validar que esté entre 0 y 1
        this.musicVolume = Math.max(0f, Math.min(1f, volume));
    }
    
    public void increaseMusicVolume() {
        setMusicVolume(musicVolume + 0.05f);
    }
    
    public void decreaseMusicVolume() {
        setMusicVolume(musicVolume - 0.05f);
    }
    
    public int getBallAppearanceIndex() {
        return ballAppearanceIndex;
    }
    
    public void nextBallAppearance() {
        ballAppearanceIndex = (ballAppearanceIndex + 1) % 3;
    }
    
    public void previousBallAppearance() {
        ballAppearanceIndex--;
        if (ballAppearanceIndex < 0) {
            ballAppearanceIndex = 2;
        }
    }
    
    public String getBallAppearanceText() {
        switch (ballAppearanceIndex) {
            case 0: return "Blanca";
            case 1: return "Carita Feliz";
            case 2: return "Balon Futbol";
            default: return "Blanca";
        }
    }
}
