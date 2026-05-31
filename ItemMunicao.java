import java.awt.Rectangle;

public class ItemMunicao {
    private int x,y;
    private boolean ativo = true;

    public ItemMunicao(int x,int y){
        this.x=x;
        this.y=y;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,20,20);
    }

    public void coletado(){
        ativo = false;
        x = -100;
    }

    public boolean isAtivo(){ return ativo; }
    public int getX(){ return x; }
    public int getY(){ return y; }
}
