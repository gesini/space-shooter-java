import java.awt.Rectangle;

public class Obstaculo {
    private int x,y,w,h;

    public Obstaculo(int x,int y,int w,int h){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,w,h);
    }

    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getW(){ return w; }
    public int getH(){ return h; }
}
