import javax.swing.*;

public class GameFrame extends JFrame {

    GameFrame(){
        GamePanel panel=new GamePanel();
        this.add(panel);
        this.setTitle("SNAKE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);  //to make our window appear in the middle of the computer screen
    }
}
