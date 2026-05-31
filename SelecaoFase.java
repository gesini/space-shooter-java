import javax.swing.*;
import java.awt.*;

public class SelecaoFase extends JFrame {
    public SelecaoFase(String nome, int faseLiberada) {
        setTitle("Seleção de Fases");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel jogador = new JLabel("Jogador: " + nome, SwingConstants.CENTER);

        JButton f1 = new JButton("Fase 1");
        JButton f2 = new JButton("Fase 2");

        f2.setEnabled(faseLiberada >= 2);

        f1.addActionListener(e -> {
            new JanelaJogo(nome,1,faseLiberada);
            dispose();
        });

        f2.addActionListener(e -> {
            new JanelaJogo(nome,2,faseLiberada);
            dispose();
        });

        JButton menu = new JButton("Menu");
        menu.addActionListener(e -> {
            new MenuInicial();
            dispose();
        });

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4,1,10,10));
        painel.add(jogador);
        painel.add(f1);
        painel.add(f2);
        painel.add(menu);

        add(painel);
        setVisible(true);
    }
}
