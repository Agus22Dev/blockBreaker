package juego.progra;

import com.badlogic.gdx.graphics.Color;
import java.util.logging.Logger;

/**
 * Power-up que otorga una vida extra al jugador.
 * Se representa con color verde y el símbolo "+".
 */
public class ExtraLifePowerUp extends PowerUp {
    
    private static final Logger LOGGER = Logger.getLogger(ExtraLifePowerUp.class.getName());
    
    public ExtraLifePowerUp(int x, int y) {
        super(
            x, 
            y, 
            GameConfig.getPowerUpWidth(), 
            GameConfig.getPowerUpHeight(),
            new Color(0.2f, 0.8f, 0.2f, 1.0f), // Verde
            GameConfig.EXTRA_LIFE_POWERUP_POINTS,
            "+"
        );
    }
    
    @Override
    public void applyEffect(GameScreen gameScreen) {
        gameScreen.addLife();
        LOGGER.info("💚 ¡Vida extra obtenida! (+" + getPointValue() + " puntos)");
    }
    
    @Override
    public String getDescription() {
        return "Vida Extra (+1)";
    }
}
