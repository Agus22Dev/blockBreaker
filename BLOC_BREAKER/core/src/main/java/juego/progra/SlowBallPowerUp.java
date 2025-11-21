package juego.progra;

import com.badlogic.gdx.graphics.Color;
import java.util.logging.Logger;

/**
 * Power-up que ralentiza temporalmente la velocidad de la pelota.
 * Se representa con color azul claro y el símbolo "S".
 */
public class SlowBallPowerUp extends PowerUp {
    
    private static final Logger LOGGER = Logger.getLogger(SlowBallPowerUp.class.getName());
    
    public SlowBallPowerUp(int x, int y) {
        super(
            x, 
            y, 
            GameConfig.POWERUP_WIDTH, 
            GameConfig.POWERUP_HEIGHT,
            new Color(0.3f, 0.6f, 1.0f, 1.0f), // Azul claro
            GameConfig.SLOW_BALL_POWERUP_POINTS,
            "S"
        );
    }
    
    @Override
    public void applyEffect(BlockBreakerGame game) {
        game.slowDownBall();
        LOGGER.info("🐌 ¡Pelota ralentizada! (+" + getPointValue() + " puntos)");
    }
    
    @Override
    public String getDescription() {
        return "Ralentizar Pelota";
    }
}
