# space-shooter-java
Jogo de tiro 2D dinâmico desenvolvido em Java (Swing/AWT) aplicando conceitos de POO, Game Loop baseado em Timer, gerenciamento de entidades dinâmicas e detecção de colisões.

## 📝 Sobre o Projeto

O **PainelJogo** é o núcleo (Game Loop e engine gráfica) de um jogo de tiro 2D desenvolvido inteiramente em **Java nativo**, sem o uso de engines externas. A classe principal utiliza as bibliotecas `javax.swing` e `java.awt` para renderizar componentes gráficos em tempo real e gerenciar a física do jogo através de eventos de teclado.

### 🧠 Destaques Técnicos do Código:

*   **Game Loop com Renderização Ativa:** Utiliza um `javax.swing.Timer` configurado a cada 30ms (aproximadamente 33 FPS) para atualizar de forma síncrona a lógica de movimentação, IA dos inimigos e redesenhar a tela através do método `paintComponent`.
*   **Gerenciamento Dinâmico de Entidades:** Gerencia coleções (`ArrayList`) de múltiplos elementos em tempo real — como projéteis do jogador, tiros inimigos, obstáculos e itens coletáveis.
*   **Prevenção de Memory Leaks:** Implementa exclusão segura de objetos inativos na memória utilizando `Iterator` e o predicado `removeIf()`, garantindo que tiros que saíram da tela ou colidiram sejam destruídos corretamente para não sobrecarregar a JVM.
*   **Detecção de Colisões Bidimensional:** Utiliza a API geométrica do Java para mapear caixas de colisão (`Bounding Boxes` via `.getBounds()`) e calcular interseções matemáticas (`.intersects()`) entre os projéteis, o jogador, as estruturas estáticas (obstáculos) e os inimigos.
*   **Máquina de Estados de Fluxo:** Controla transições complexas de interface de usuário (UI) a partir da lógica do jogo, instanciando novas janelas como `MenuInicial`, `SelecaoFase` ou caixas de diálogo (`JOptionPane`) para loops de *Game Over* e progressão de nível.
