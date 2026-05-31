import java.awt.Rectangle;
import java.util.Random;

public class Inimigo {
    private int x, y, dx = 2;
    private Random r = new Random();

    public Inimigo(){
        respawn();
    }

    public void mover(){
        x += dx;
        if(x < 0 || x > 760)
            dx *= -1;
    }

    public boolean deveAtirar(){
        return r.nextInt(100) < 2;
    }

    public void respawn(){
        x = r.nextInt(700);
        y = r.nextInt(300);
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,40,40);
    }

    public int getX(){ return x; }
    public int getY(){ return y; }
}
