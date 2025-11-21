package juego.progra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import java.util.logging.Logger;

/**
 * Gestor centralizado de música del juego.
 * Maneja la música del menú y del juego por separado.
 */
public class MusicManager {
    private static final Logger LOGGER = Logger.getLogger(MusicManager.class.getName());
    
    private static MusicManager instance;
    private Music menuMusic;
    private Music gameMusic;
    private Music currentMusic;
    
    /**
     * Constructor privado - Singleton pattern.
     */
    private MusicManager() {
        // Cargar música del menú
        try {
            FileHandle menuFile = Gdx.files.internal("music/menu.ogg");
            if (menuFile.exists()) {
                menuMusic = Gdx.audio.newMusic(menuFile);
                menuMusic.setLooping(true);
            } else {
                LOGGER.warning("Archivo de música del menú no encontrado: music/menu.ogg");
            }
        } catch (Exception e) {
            LOGGER.warning("Error cargando música del menú: " + e.getMessage());
            menuMusic = null;
        }
        
        // Cargar música del juego
        try {
            FileHandle gameFile = Gdx.files.internal("music/bg.ogg");
            if (gameFile.exists()) {
                gameMusic = Gdx.audio.newMusic(gameFile);
                gameMusic.setLooping(true);
            } else {
                LOGGER.warning("Archivo de música del juego no encontrado: music/bg.ogg");
            }
        } catch (Exception e) {
            LOGGER.warning("Error cargando música del juego: " + e.getMessage());
            gameMusic = null;
        }
    }
    
    /**
     * Obtiene la única instancia del gestor de música.
     */
    public static MusicManager getInstance() {
        if (instance == null) {
            instance = new MusicManager();
        }
        return instance;
    }
    
    /**
     * Reproduce la música del menú.
     */
    public void playMenuMusic() {
        stopAll();
        if (menuMusic != null) {
            currentMusic = menuMusic;
            menuMusic.setVolume(GameSettings.getInstance().getMusicVolume());
            menuMusic.play();
            LOGGER.info("Reproduciendo música del menú");
        }
    }
    
    /**
     * Reproduce la música del juego.
     */
    public void playGameMusic() {
        stopAll();
        if (gameMusic != null) {
            currentMusic = gameMusic;
            gameMusic.setVolume(GameSettings.getInstance().getMusicVolume());
            gameMusic.play();
            LOGGER.info("Reproduciendo música del juego");
        }
    }
    
    /**
     * Detiene toda la música.
     */
    public void stopAll() {
        if (menuMusic != null && menuMusic.isPlaying()) {
            menuMusic.stop();
        }
        if (gameMusic != null && gameMusic.isPlaying()) {
            gameMusic.stop();
        }
        currentMusic = null;
    }
    
    /**
     * Pausa la música actual.
     */
    public void pause() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.pause();
        }
    }
    
    /**
     * Reanuda la música actual.
     */
    public void resume() {
        if (currentMusic != null && !currentMusic.isPlaying()) {
            currentMusic.play();
        }
    }
    
    /**
     * Actualiza el volumen de toda la música.
     */
    public void updateVolume() {
        float volume = GameSettings.getInstance().getMusicVolume();
        if (menuMusic != null) {
            menuMusic.setVolume(volume);
        }
        if (gameMusic != null) {
            gameMusic.setVolume(volume);
        }
    }
    
    /**
     * Libera recursos.
     */
    public void dispose() {
        if (menuMusic != null) {
            menuMusic.dispose();
            menuMusic = null;
        }
        if (gameMusic != null) {
            gameMusic.dispose();
            gameMusic = null;
        }
        currentMusic = null;
        instance = null;
    }
}
