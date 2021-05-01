import javax.swing.*;
import java.awt.*;

public class GameFrame {

    public GameFrame() {

        JFrame frame = new JFrame();
        Game game = new Game();

        frame.setTitle("Brick Game");
        frame.setSize(new Dimension(700,1000));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.pack();

        frame.setVisible(true);
    }
}
