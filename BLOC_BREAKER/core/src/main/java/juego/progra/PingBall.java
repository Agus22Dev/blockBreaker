package juego.progra;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PingBall {
	    private int x;
	    private int y;
	    private int size;
	    private int xSpeed;
	    private int ySpeed;
	    private int normalXSpeed; // Velocidad normal
	    private int normalYSpeed; // Velocidad normal
	    private long slowEndTime = 0; // Tiempo en que termina el efecto de ralentización
	    private boolean estaQuieto;
	    private BallAppearance appearance; // Patrón Strategy - apariencia de la pelota
	    
	    public PingBall(int x, int y, int size, int xSpeed, int ySpeed, boolean iniciaQuieto) {
	        this.x = x;
	        this.y = y;
	        this.size = size;
	        this.xSpeed = xSpeed;
	        this.ySpeed = ySpeed;
	        this.normalXSpeed = xSpeed;
	        this.normalYSpeed = ySpeed;
	        estaQuieto = iniciaQuieto;
	        updateAppearance(); // Inicializar apariencia según configuración
	    }
	    
	    /**
	     * Actualiza la apariencia de la pelota según la configuración (Strategy Pattern).
	     */
	    public void updateAppearance() {
	        int appearanceIndex = GameSettings.getInstance().getBallAppearanceIndex();
	        switch (appearanceIndex) {
	            case 0:
	                appearance = new WhiteBallAppearance();
	                break;
	            case 1:
	                appearance = new HappyFaceBallAppearance();
	                break;
	            case 2:
	                appearance = new SoccerBallAppearance();
	                break;
	            default:
	                appearance = new WhiteBallAppearance();
	                break;
	        }
	    }
	    
	    public boolean estaQuieto() {
	    	return estaQuieto;
	    }
	    public void setEstaQuieto(boolean bb) {
	    	estaQuieto=bb;
	    }
	    public void setXY(int x, int y) {
	    	this.x = x;
	        this.y = y;
	    }
	    public int getY() {return y;}
	    
	    /**
	     * Activa el efecto de ralentización temporalmente.
	     */
	    public void activateSlow() {
	        slowEndTime = System.currentTimeMillis() + GameConfig.SLOW_BALL_DURATION;
	        xSpeed = normalXSpeed / 2;
	        ySpeed = normalYSpeed / 2;
	    }
	    
	    /**
	     * Verifica si el efecto de ralentización está activo.
	     */
	    public boolean isSlow() {
	        return System.currentTimeMillis() < slowEndTime;
	    }
	    
	    /**
	     * Actualiza el estado de la pelota (verifica si el efecto terminó).
	     */
	    private void updateEffects() {
	        if (slowEndTime > 0 && System.currentTimeMillis() >= slowEndTime) {
	            xSpeed = normalXSpeed * (xSpeed < 0 ? -1 : 1);
	            ySpeed = normalYSpeed * (ySpeed < 0 ? -1 : 1);
	            slowEndTime = 0;
	        }
	    }
	    
    public void draw(ShapeRenderer shape){
        // Usar el patrón Strategy para dibujar la apariencia actual
        if (appearance != null) {
            appearance.draw(shape, x, y, size);
        }
        
        // Overlay azul cuando está ralentizada
        if (isSlow()) {
            shape.setColor(new Color(0.3f, 0.3f, 1.0f, 0.3f)); // Azul semi-transparente
            shape.circle(x, y, size + 2);
        }
    }	    public void update() {
	    	if (estaQuieto) return;
	    	
	    	updateEffects(); // Actualizar efectos temporales
	    	
	        x += xSpeed;
	        y += ySpeed;
	        if (x-size < 0 || x+size > Gdx.graphics.getWidth()) {
	            xSpeed = -xSpeed;
	        }
	        if (y+size > Gdx.graphics.getHeight()) {
	            ySpeed = -ySpeed;
	        }
	    }
	    
	    public void checkCollision(Paddle paddle) {
	        if(collidesWith(paddle)){
	            ySpeed = -ySpeed;
	            
	            // Ajustar dirección horizontal según dónde golpee en el paddle
	            int ballCenter = x;
	            int paddleCenter = paddle.getX() + paddle.getWidth() / 2;
	            int relativeIntersectX = ballCenter - paddleCenter;
	            
	            // Normalizar la posición relativa (-1 a 1)
	            float normalizedRelativeIntersection = (float)relativeIntersectX / (paddle.getWidth() / 2);
	            
	            // Ajustar velocidad X basada en dónde golpeó
	            // Si golpea en el centro, mantiene la velocidad
	            // Si golpea en los bordes, aumenta la velocidad horizontal
	            int currentSpeedMagnitude = isSlow() ? normalXSpeed / 2 : normalXSpeed;
	            xSpeed = (int)(normalizedRelativeIntersection * currentSpeedMagnitude * 1.5f);
	            
	            // Asegurar una velocidad mínima horizontal
	            if (Math.abs(xSpeed) < 1) {
	                xSpeed = (xSpeed >= 0) ? 1 : -1;
	            }
	        }
	    }
	    private boolean collidesWith(Paddle pp) {

	    	boolean intersectaX = (pp.getX() + pp.getWidth() >= x-size) && (pp.getX() <= x+size);
	        boolean intersectaY = (pp.getY() + pp.getHeight() >= y-size) && (pp.getY() <= y+size);		
	    	return intersectaX && intersectaY;
	    }
	    
    public void checkCollision(Destructible destructible) {
        if(collidesWith(destructible)){
            ySpeed = - ySpeed;
            destructible.takeDamage();
        }
    }
    private boolean collidesWith(Destructible dd) {
    	boolean intersectaX = (dd.getX() + dd.getWidth() >= x-size) && (dd.getX() <= x+size);
        boolean intersectaY = (dd.getY() + dd.getHeight() >= y-size) && (dd.getY() <= y+size);		
    	return intersectaX && intersectaY;
    }	}
