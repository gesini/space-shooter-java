import javax.swing.*;
import java.awt.*;

public class MenuInicial extends JFrame {
    public MenuInicial() {
        setTitle("Strike");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("STRIKE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        JButton jogar = new JButton("Começar");
        jogar.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog("Digite seu nome:");
            if(nome != null && !nome.isEmpty()){
                new SelecaoFase(nome, 1);
                dispose();
            }
        });

        JButton sair = new JButton("Sair");
        sair.addActionListener(e -> System.exit(0));

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3,1,10,10));
        painel.add(titulo);
        painel.add(jogar);
        painel.add(sair);

        add(painel);
        setVisible(true);
    }
}
