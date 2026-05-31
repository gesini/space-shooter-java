import java.awt.Rectangle;

public class Jogador {
    private int x = 350, y = 500;
    private int vida = 100;
    private int balas = 10;
    private String nome;

    public Jogador(String nome){
        this.nome = nome;
    }

    public void mover(int dx, int dy){
        x += dx;
        y += dy;

        if(x < 10) x = 10;
        if(x > 750) x = 750;
        if(y < 50) y = 50;
        if(y > 520) y = 520;
    }

    public void tomarDano(int d){
        vida -= d;
    }

    public void addBalas(int qtd){
        balas += qtd;
    }

    public void usarBala(){
        balas--;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,40,40);
    }

    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getVida(){ return vida; }
    public int getBalas(){ return balas; }
    public String getNome(){ return nome; }
}
