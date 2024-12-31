import javax.swing.*;

public class GameFrame extends JFrame {
    private static final Long serialVersionUID = 1L;

    GameFrame(){
        GameP p = new GameP();
        this.add(p);
        this.setTitle("SNAKE GAME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
