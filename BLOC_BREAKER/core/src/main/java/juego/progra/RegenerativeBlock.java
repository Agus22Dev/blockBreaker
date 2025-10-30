package juego.progra;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Implementación de un bloque especial que se regenera después de un tiempo.
 * Requiere múltiples golpes rápidos para ser destruido permanentemente.
 */
public class RegenerativeBlock extends AbstractBlock {
    // Campos específicos del comportamiento regenerativo
    protected Color originalColor;
    protected long lastHitTime;
    protected long regenerationDelay; // tiempo en ms para regenerarse
    protected boolean wasHit;
    
    public RegenerativeBlock(int x, int y, int width, int height) {
        // Puntuación alta por ser un bloque difícil
        super(x, y, width, height, 50);
        this.regenerationDelay = 3000; // 3 segundos para regenerarse
        this.wasHit = false;
        this.lastHitTime = 0;

        // Color dorado para indicar que es especial
        this.originalColor = new Color(1.0f, 0.8f, 0.0f, 1.0f); // Dorado
        // Establecer el color base heredado
        this.color = this.originalColor;
    }
    
    @Override
    public void draw(ShapeRenderer shape) {
        // Actualizar estado antes de dibujar
        updateRegenerationStatus();
        
        if (!destroyed) {
            Color currentColor = getCurrentColor();
            shape.setColor(currentColor);
            shape.rect(x, y, width, height);
            
            // Dibujar patrón especial para indicar que es regenerativo
            shape.setColor(Color.YELLOW);
            // Dibujar puntos en el centro
            int centerX = x + width / 2;
            int centerY = y + height / 2;
            shape.circle(centerX - 8, centerY, 2);
            shape.circle(centerX, centerY, 2);
            shape.circle(centerX + 8, centerY, 2);
            
            // Borde pulsante
            shape.setColor(Color.WHITE);
            float pulse = (float)(Math.sin(System.currentTimeMillis() * 0.01) * 0.5 + 0.5);
            shape.setColor(new Color(1.0f, 1.0f, 1.0f, pulse));
            // Borde superior e inferior
            shape.rect(x, y + height - 1, width, 1);
            shape.rect(x, y, width, 1);
            // Borde izquierdo y derecho
            shape.rect(x, y, 1, height);
            shape.rect(x + width - 1, y, 1, height);
        }
    }
    
    private Color getCurrentColor() {
        if (wasHit) {
            // Color más oscuro cuando ha sido golpeado
            long timeSinceHit = System.currentTimeMillis() - lastHitTime;
            float progress = (float) timeSinceHit / regenerationDelay;
            progress = Math.min(1.0f, progress);
            
            // Interpolar entre color dañado y color original
            Color damagedColor = new Color(0.8f, 0.2f, 0.2f, 1.0f); // Rojo
            return new Color(
                damagedColor.r + (originalColor.r - damagedColor.r) * progress,
                damagedColor.g + (originalColor.g - damagedColor.g) * progress,
                damagedColor.b + (originalColor.b - damagedColor.b) * progress,
                1.0f
            );
        }
        return originalColor;
    }
    
    private void updateRegenerationStatus() {
        if (wasHit && !destroyed) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastHitTime >= regenerationDelay) {
                // Se regenera - resetear estado
                wasHit = false;
                lastHitTime = 0;
            }
        }
    }
    
    @Override
    public boolean takeDamage() {
        long currentTime = System.currentTimeMillis();
        
        if (wasHit) {
            // Si ya fue golpeado y recibe otro golpe rápido, se destruye
            if (currentTime - lastHitTime < regenerationDelay) {
                this.destroyed = true;
                System.out.println("💥 RegenerativeBlock destruido (+" + getPointValue() + " puntos)");
                return true;
            } else {
                // Ha pasado mucho tiempo, se considera el primer golpe again
                wasHit = true;
                lastHitTime = currentTime;
                return false;
            }
        } else {
            // Primer golpe
            wasHit = true;
            lastHitTime = currentTime;
            return false;
        }
    }
    
    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public int getY() {
        return y;
    }
    
    @Override
    public int getWidth() {
        return width;
    }
    
    @Override
    public int getHeight() {
        return height;
    }
    
    @Override
    public int getPointValue() {
        return pointValue;
    }
}