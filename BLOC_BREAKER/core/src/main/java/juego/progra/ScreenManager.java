package juego.progra;

/**
 * Gestor de pantallas del juego.
 * Maneja la transición entre diferentes pantallas (Menú, Juego, Créditos, etc.)
 * Patrón Singleton - solo existe una instancia del gestor de pantallas.
 */
public class ScreenManager {
    private static ScreenManager instance;
    private Screen currentScreen;
    
    private ScreenManager() {
        // Constructor privado para Singleton
    }
    
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }
    
    /**
     * Cambia a una nueva pantalla.
     * @param newScreen la pantalla a la que cambiar
     */
    public void setScreen(Screen newScreen) {
        if (currentScreen != null) {
            currentScreen.hide();
        }
        currentScreen = newScreen;
        if (currentScreen != null) {
            currentScreen.show();
        }
    }
    
    /**
     * Obtiene la pantalla actual.
     */
    public Screen getCurrentScreen() {
        return currentScreen;
    }
    
    /**
     * Libera recursos del gestor.
     */
    public void dispose() {
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
