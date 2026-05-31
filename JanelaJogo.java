import javax.swing.*;

public class JanelaJogo extends JFrame {
    public JanelaJogo(String nome, int fase, int faseLiberada) {
        setTitle("Jogo");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new PainelJogo(nome, fase, faseLiberada, this));
        setVisible(true);
    }
}
