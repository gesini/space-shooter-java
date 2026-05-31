import java.awt.Rectangle;

public class TiroInimigo {
    private int x, y;
    private boolean ativo = true;

    public TiroInimigo(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void mover(){
        y += 5;
        if(y > 600)
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
