import java.awt.Rectangle;
import java.util.Random;

public class Inimigo {
    private int x, y, dx;
    private Random r = new Random();

    // Agora o inimigo sabe em qual fase está e fica mais rápido!
    public Inimigo(int fase){
        this.dx = 2 + fase; // Fase 1 = vel 3, Fase 2 = vel 4...
        respawn();
    }

    public void mover(){
        x += dx;
        if(x < 0 || x > 760)
            dx *= -1; // Inverte a direção ao bater na parede
    }

    public boolean deveAtirar(){
        // Chance de tiro baseada em probabilidade
        return r.nextInt(100) < 2;
    }

    public void respawn(){
        x = r.nextInt(700);
        y = r.nextInt(300); // Surge na metade superior da tela
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, 40, 40);
    }

    public int getX(){ return x; }
    public int getY(){ return y; }
}
