package juego.progra;

import com.badlogic.gdx.graphics.Color;
import java.util.logging.Logger;

/**
 * Power-up que agranda temporalmente el paddle del jugador.
 * Se representa con color naranja y el símbolo "W".
 */
public class WidePaddlePowerUp extends PowerUp {
    
    private static final Logger LOGGER = Logger.getLogger(WidePaddlePowerUp.class.getName());
    
    public WidePaddlePowerUp(int x, int y) {
        super(
            x, 
            y, 
            GameConfig.POWERUP_WIDTH, 
            GameConfig.POWERUP_HEIGHT,
            new Color(1.0f, 0.6f, 0.2f, 1.0f), // Naranja
            GameConfig.WIDE_PADDLE_POWERUP_POINTS,
            "W"
        );
    }
    
    @Override
    public void applyEffect(BlockBreakerGame game) {
        game.widenPaddle();
        LOGGER.info("📏 ¡Paddle agrandado! (+" + getPointValue() + " puntos)");
    }
    
    @Override
    public String getDescription() {
        return "Paddle Ancho";
    }
}
