package juego.progra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle {
    private int x = 20;
    private int y = 20;
    private int width = 100;
    private int height = 10;
    private int normalWidth = 100; // Ancho normal del paddle
    private int wideWidth = 150;   // Ancho cuando está agrandado
    private long wideEndTime = 0;  // Tiempo en que termina el efecto de agrandamiento
    
    
    public Paddle(int x, int y, int ancho, int alto) {
    	this.x = x;
    	this.y= y;
    	width = ancho;
    	normalWidth = ancho;
    	wideWidth = (int)(ancho * 1.5);
    	height = alto;
    }
     
    public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	
	/**
	 * Activa el efecto de paddle ancho temporalmente.
	 */
	public void activateWide() {
	    wideEndTime = System.currentTimeMillis() + GameConfig.WIDE_PADDLE_DURATION;
	    
	    // Ajustar posición si al agrandarse quedaría fuera del borde
	    if (x + wideWidth > Gdx.graphics.getWidth()) {
	        x = Gdx.graphics.getWidth() - wideWidth;
	    }
	    
	    width = wideWidth;
	}
	
	/**
	 * Verifica si el efecto de paddle ancho está activo.
	 */
	public boolean isWide() {
	    return System.currentTimeMillis() < wideEndTime;
	}
	
	/**
	 * Actualiza el estado del paddle (verifica si el efecto terminó).
	 */
	public void update() {
	    if (wideEndTime > 0 && System.currentTimeMillis() >= wideEndTime) {
	        // Ajustar posición antes de reducir el tamaño para evitar quedar fuera
	        if (x + normalWidth > Gdx.graphics.getWidth()) {
	            x = Gdx.graphics.getWidth() - normalWidth;
	        }
	        width = normalWidth;
	        wideEndTime = 0;
	    }
	}

	public void draw(ShapeRenderer shape){
        // Color diferente cuando está agrandado
        if (isWide()) {
            shape.setColor(new Color(0.0f, 0.8f, 1.0f, 1.0f)); // Azul cyan
        } else {
            shape.setColor(Color.BLUE);
        }
        
        int x2 = x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x2 = x - GameConfig.PADDLE_STEP;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x2 = x + GameConfig.PADDLE_STEP;
        
        if (x2 > 0 && x2+width < Gdx.graphics.getWidth()) {
            x = x2;
        }
        shape.rect(x, y, width, height);
    }
    
    
}
