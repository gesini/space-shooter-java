import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class PainelJogo extends JPanel implements ActionListener, KeyListener {

    private Jogador j;
    private ArrayList<Tiro> tiros = new ArrayList<>();
    private ArrayList<TiroInimigo> tirosInimigos = new ArrayList<>();
    private ArrayList<Inimigo> inimigos = new ArrayList<>();
    private ArrayList<Obstaculo> obs = new ArrayList<>();
    private ArrayList<ItemMunicao> muni = new ArrayList<>();

    private int pontos = 0;
    private int fase, faseLiberada;
    private Timer timer = new Timer(30, this);
    private JFrame janela;

    private boolean gameOver = false;
    private boolean faseFinalizada = false;

    public PainelJogo(String nome, int fase, int faseLiberada, JFrame janela) {
        this.fase = fase;
        this.faseLiberada = faseLiberada;
        this.janela = janela;

        j = new Jogador(nome);

        // 10 inimigos
        for(int i=0;i<10;i++)
            inimigos.add(new Inimigo());

        // obstáculos
        obs.add(new Obstaculo(200,200,150,20));
        obs.add(new Obstaculo(400,300,150,20));

        // munição
        muni.add(new ItemMunicao(100,400));
        muni.add(new ItemMunicao(600,450));

        // botão menu
        JButton voltar = new JButton("Menu");
        voltar.setBounds(680,10,100,30);
        voltar.setFocusable(false);
        voltar.addActionListener(e -> {
            new MenuInicial();
            janela.dispose();
        });

        setLayout(null);
        add(voltar);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        // inicia loop do jogo
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // HUD
        g.setColor(new Color(40,40,40));
        g.fillRect(0,0,800,50);

        g.setColor(Color.WHITE);
        g.drawString("Nome: "+j.getNome(),10,20);
        g.drawString("Vida: "+j.getVida(),150,20);
        g.drawString("Pontos: "+pontos,250,20);
        g.drawString("Balas: "+j.getBalas(),380,20);

        // cenário
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,50,800,550);

        // jogador
        g.setColor(Color.BLUE);
        g.fillRect(j.getX(),j.getY(),40,40);

        // inimigos
        g.setColor(Color.RED);
        for(Inimigo i:inimigos)
            g.fillOval(i.getX(),i.getY(),40,40);

        // tiros jogador
        g.setColor(Color.BLACK);
        for(Tiro t:tiros)
            g.fillRect(t.getX(),t.getY(),5,10);

        // tiros inimigos
        g.setColor(Color.ORANGE);
        for(TiroInimigo t:tirosInimigos)
            g.fillRect(t.getX(),t.getY(),5,10);

        // obstáculos
        g.setColor(new Color(120,70,20));
        for(Obstaculo o:obs)
            g.fillRect(o.getX(),o.getY(),o.getW(),o.getH());

        // munição
        g.setColor(Color.GREEN);
        for(ItemMunicao m:muni)
            if(m.isAtivo())
                g.fillRect(m.getX(),m.getY(),20,20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameOver || faseFinalizada)
            return;

        // tiros jogador
        for(Tiro t:tiros){
            t.mover();

            // colisão obstáculo
            for(Obstaculo o:obs)
                if(t.getBounds().intersects(o.getBounds()))
                    t.setAtivo(false);

            // colisão inimigo
            Iterator<Inimigo> it = inimigos.iterator();
            while(it.hasNext()){
                Inimigo i = it.next();
                if(t.getBounds().intersects(i.getBounds())){
                    pontos++;
                    it.remove();
                    t.setAtivo(false);
                    break;
                }
            }
        }
        tiros.removeIf(t -> !t.isAtivo());

        // inimigos
        for(Inimigo i:inimigos){
            i.mover();
            if(i.deveAtirar()){
                tirosInimigos.add(new TiroInimigo(i.getX()+20,i.getY()+40));
            }
        }

        // tiros inimigos
        for(TiroInimigo t:tirosInimigos){
            t.mover();

            // colisão obstáculos
            for(Obstaculo o:obs){
                if(t.getBounds().intersects(o.getBounds())){
                    t.setAtivo(false);
                }
            }

            // colisão jogador
            if(t.isAtivo() && t.getBounds().intersects(j.getBounds())){
                j.tomarDano(10);
                t.setAtivo(false);
            }
        }
        tirosInimigos.removeIf(t -> !t.isAtivo());

        // coletar munição
        for(ItemMunicao m:muni)
            if(m.isAtivo() && j.getBounds().intersects(m.getBounds())){
                j.addBalas(5);
                m.coletado();
            }

        // game over
        if(j.getVida() <= 0){
            gameOver = true;
            timer.stop();

            Object[] opcoes = {"Recomeçar", "Menu"};
            int escolha = JOptionPane.showOptionDialog(
                    this, "Game Over", "Fim de jogo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opcoes, opcoes[0]
            );

            if(escolha == 0){
                new JanelaJogo(j.getNome(), 1, 1);
            } else {
                new MenuInicial();
            }
            janela.dispose();
        }

        // passar fase
        if(inimigos.isEmpty()){
            faseFinalizada = true;
            timer.stop();

            JOptionPane.showMessageDialog(this, "Fase concluída!");
            new SelecaoFase(j.getNome(), fase+1);
            janela.dispose();
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e){
        int k = e.getKeyCode();

        // movimento
        if(k==KeyEvent.VK_A) j.mover(-10,0);
        if(k==KeyEvent.VK_D) j.mover(10,0);
        if(k==KeyEvent.VK_W) j.mover(0,-10);
        if(k==KeyEvent.VK_S) j.mover(0,10);

        // atirar
        if(k==KeyEvent.VK_SPACE && j.getBalas()>0){
            tiros.add(new Tiro(j.getX()+18,j.getY()));
            j.usarBala();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){}

    @Override
    public void keyTyped(KeyEvent e){}
}
