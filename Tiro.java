import java.awt.Rectangle;

public class Tiro {
    private int x, y;
    private boolean ativo = true;

    public Tiro(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void mover(){
        y -= 8;
        if(y < 0)
            ativo = false;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,5,10);
    }

    public int getX(){ return x; }
    public int getY(){ return y; }
    public boolean isAtivo(){ return ativo; }
    public void setAtivo(boolean a){ ativo = a; }
}
