package juego.progra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Gestor centralizado de música del juego.
 * Maneja la música del menú y del juego por separado.
 * Soporta cola de reproducción para música del juego.
 */
public class MusicManager {
    private static final Logger LOGGER = Logger.getLogger(MusicManager.class.getName());
    
    private static MusicManager instance;
    private Music menuMusic;
    private List<Music> gameMusicPlaylist; // Cola de canciones del juego
    private int currentTrackIndex; // Índice de la canción actual
    private Music currentMusic;
    private boolean isPlayingGameMusic; // Para saber si estamos reproduciendo música del juego
    
    /**
     * Constructor privado - Singleton pattern.
     */
    private MusicManager() {
        gameMusicPlaylist = new ArrayList<>();
        currentTrackIndex = 0;
        isPlayingGameMusic = false;
        
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
        
        // Cargar todas las canciones del juego en una playlist
        loadGamePlaylist();
    }
    
    /**
     * Carga todas las canciones disponibles en la carpeta music/ para el juego.
     */
    private void loadGamePlaylist() {
        // Buscar archivos de música en la carpeta music/
        // Puedes agregar más canciones aquí
        String[] songFiles = {
            "music/bg.ogg",
            "music/game1.ogg",
            "music/game2.ogg",
            "music/game3.ogg"
            // Agrega más canciones aquí si las tienes
        };
        
        for (String songPath : songFiles) {
            try {
                FileHandle gameFile = Gdx.files.internal(songPath);
                if (gameFile.exists()) {
                    Music music = Gdx.audio.newMusic(gameFile);
                    music.setLooping(false); // No repetir individualmente
                    
                    // Listener para pasar a la siguiente canción cuando termine
                    music.setOnCompletionListener(new Music.OnCompletionListener() {
                        @Override
                        public void onCompletion(Music music) {
                            playNextTrack();
                        }
                    });
                    
                    gameMusicPlaylist.add(music);
                    LOGGER.info("Canción cargada: " + songPath);
                } else {
                    LOGGER.info("Archivo de música no encontrado (se omite): " + songPath);
                }
            } catch (Exception e) {
                LOGGER.warning("Error cargando música del juego (" + songPath + "): " + e.getMessage());
            }
        }
        
        if (gameMusicPlaylist.isEmpty()) {
            LOGGER.warning("No se cargaron canciones para el juego");
        } else {
            LOGGER.info("Playlist del juego cargada con " + gameMusicPlaylist.size() + " canciones");
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
     * Reproduce la música del juego (inicia con la primera canción de la playlist).
     */
    public void playGameMusic() {
        stopAll();
        isPlayingGameMusic = true;
        
        if (!gameMusicPlaylist.isEmpty()) {
            currentTrackIndex = 0;
            playCurrentTrack();
        } else {
            LOGGER.warning("No hay canciones en la playlist del juego");
        }
    }
    
    /**
     * Reproduce la canción actual de la playlist.
     */
    private void playCurrentTrack() {
        if (!gameMusicPlaylist.isEmpty() && currentTrackIndex < gameMusicPlaylist.size()) {
            currentMusic = gameMusicPlaylist.get(currentTrackIndex);
            currentMusic.setVolume(GameSettings.getInstance().getMusicVolume());
            currentMusic.play();
            LOGGER.info("Reproduciendo canción " + (currentTrackIndex + 1) + "/" + gameMusicPlaylist.size());
        }
    }
    
    /**
     * Pasa a la siguiente canción en la playlist.
     */
    private void playNextTrack() {
        if (!isPlayingGameMusic || gameMusicPlaylist.isEmpty()) {
            return;
        }
        
        currentTrackIndex++;
        
        // Si llegamos al final, volver al inicio (loop de la playlist)
        if (currentTrackIndex >= gameMusicPlaylist.size()) {
            currentTrackIndex = 0;
        }
        
        playCurrentTrack();
    }
    
    /**
     * Detiene toda la música.
     */
    public void stopAll() {
        isPlayingGameMusic = false;
        
        if (menuMusic != null && menuMusic.isPlaying()) {
            menuMusic.stop();
        }
        
        // Detener todas las canciones de la playlist
        for (Music music : gameMusicPlaylist) {
            if (music.isPlaying()) {
                music.stop();
            }
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
        
        // Actualizar volumen de todas las canciones de la playlist
        for (Music music : gameMusicPlaylist) {
            music.setVolume(volume);
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
        
        // Liberar todas las canciones de la playlist
        for (Music music : gameMusicPlaylist) {
            music.dispose();
        }
        gameMusicPlaylist.clear();
        
        currentMusic = null;
        instance = null;
    }
}
