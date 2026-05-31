import java.awt.Rectangle;
import java.util.Random;

public class Alvo {
    private int x, y;
    private Random r = new Random();

    public Alvo() {
        gerar();
    }

    public void gerar() {
        x = r.nextInt(740);
        y = r.nextInt(300);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
