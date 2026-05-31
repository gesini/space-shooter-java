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

        // MELHORIA: Inimigos agora recebem a fase para ajustar a velocidade criados dinamicamente
        int qtdInimigos = 5 + (fase * 5); // Fase 1 = 10 inimigos, Fase 2 = 15...
        for(int i = 0; i < qtdInimigos; i++) {
            inimigos.add(new Inimigo(fase));
        }

        // Configuração de Obstáculos básicos
        obs.add(new Obstaculo(200, 250, 150, 20));
        obs.add(new Obstaculo(450, 350, 150, 20));

        // Itens de Munição
        muni.add(new ItemMunicao(100, 400));
        muni.add(new ItemMunicao(650, 450));

        // Botão Menu formatado para não roubar o foco do teclado
        JButton voltar = new JButton("Menu");
        voltar.setBounds(680, 10, 100, 30);
        voltar.setFocusable(false);
        voltar.addActionListener(e -> {
            timer.stop();
            new MenuInicial();
            janela.dispose();
        });

        setLayout(null);
        add(voltar);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // MELHORIA: Ativando Anti-Aliasing (Deixa as bordas dos desenhos lisinhas, sem serrilhado)
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // HUD - Interface
        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, 800, 50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Nível: " + fase, 15, 28);
        g.drawString("Jogador: " + j.getNome(), 110, 28);
        g.drawString("Vida: " + j.getVida() + "%", 280, 28);
        g.drawString("Pontos: " + pontos, 430, 28);
        g.drawString("Munição: " + j.getBalas(), 580, 28);

        // Cenário de Fundo (Espaço escuro fica mais legal que cinza!)
        g.setColor(new Color(15, 15, 25));
        g.fillRect(0, 50, 800, 550);

        // Jogador (Nave Azul)
        g.setColor(new Color(50, 100, 255));
        g.fillRect(j.getX(), j.getY(), 40, 40);

        // Inimigos (Naves Vermelhas)
        g.setColor(new Color(255, 50, 50));
        for(Inimigo i : inimigos) {
            g.fillOval(i.getX(), i.getY(), 40, 40);
        }

        // Tiros do Jogador (Lasers Verdes)
        g.setColor(Color.GREEN);
        for(Tiro t : tiros) {
            g.fillRect(t.getX(), t.getY(), 4, 12);
        }

        // Tiros dos Inimigos (Lasers Vermelhos)
        g.setColor(Color.RED);
        for(TiroInimigo t : tirosInimigos) {
            g.fillRect(t.getX(), t.getY(), 4, 12);
        }

        // Obstáculos (Barreiras de Metal)
        g.setColor(new Color(100, 100, 110));
        for(Obstaculo o : obs) {
            g.fillRect(o.getX(), o.getY(), o.getW(), o.getH());
        }

        // Munição
        g.setColor(Color.YELLOW);
        for(ItemMunicao m : muni) {
            if(m.isAtivo()) {
                g.fillRect(m.getX(), m.getY(), 15, 15);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameOver || faseFinalizada) return;

        // Atualiza tiros do jogador e checa colisões
        for(Tiro t : tiros){
            t.mover();

            for(Obstaculo o : obs) {
                if(t.getBounds().intersects(o.getBounds())) t.setAtivo(false);
            }

            Iterator<Inimigo> it = inimigos.iterator();
            while(it.hasNext()){
                Inimigo i = it.next();
                if(t.getBounds().intersects(i.getBounds())){
                    pontos += 10; // Agora ganha 10 pontos por abate
                    it.remove();
                    t.setAtivo(false);
                    break;
                }
            }
        }
        tiros.removeIf(t -> !t.isAtivo());

        // Atualiza Inimigos e lógica de disparos
        for(Inimigo i : inimigos){
            i.mover();
            if(i.deveAtirar()){
                tirosInimigos.add(new TiroInimigo(i.getX() + 18, i.getY() + 40));
            }
        }

        // Atualiza tiros dos inimigos
        for(TiroInimigo t : tirosInimigos){
            t.mover();

            for(Obstaculo o : obs){
                if(t.getBounds().intersects(o.getBounds())) t.setAtivo(false);
            }

            if(t.isAtivo() && t.getBounds().intersects(j.getBounds())){
                j.tomarDano(15); // Dano balanceado para 15
                t.setAtivo(false);
            }
        }
        tirosInimigos.removeIf(t -> !t.isAtivo());

        // Coleta de munição
        for(ItemMunicao m : muni) {
            if(m.isAtivo() && j.getBounds().intersects(m.getBounds())){
                j.addBalas(8); // Dá 8 balas em vez de 5
                m.coletado();
            }
        }

        // Condição de Game Over
        if(j.getVida() <= 0){
            finalizarJogo("Game Over! Deseja tentar novamente?", false);
        }

        // Condição de Vitória
        if(inimigos.isEmpty()){
            finalizarJogo("Fase concluída com sucesso!", true);
        }

        repaint();
    }

    // MELHORIA: Método auxiliar para evitar código repetido no fim do jogo
    private void finalizarJogo(String mensagem, boolean venceu) {
        timer.stop();
        if(venceu) {
            JOptionPane.showMessageDialog(this, mensagem);
            new SelecaoFase(j.getNome(), fase + 1);
        } else {
            Object[] opcoes = {"Recomeçar", "Menu"};
            int escolha = JOptionPane.showOptionDialog(
                    this, mensagem, "Fim de Jogo",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, opcoes, opcoes[0]
            );
            if(escolha == 0){
                new JanelaJogo(j.getNome(), fase, faseLiberada);
            } else {
                new MenuInicial();
            }
        }
        janela.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e){
        int k = e.getKeyCode();
        // Movimentos ajustados para checar limites
        if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) j.mover(-12, 0);
        if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) j.mover(12, 0);
        if(k == KeyEvent.VK_W || k == KeyEvent.VK_UP) j.mover(0, -12);
        if(k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) j.mover(0, 12);

        if(k == KeyEvent.VK_SPACE && j.getBalas() > 0){
            tiros.add(new Tiro(j.getX() + 18, j.getY()));
            j.usarBala();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){}
    @Override
    public void keyTyped(KeyEvent e){}
}
