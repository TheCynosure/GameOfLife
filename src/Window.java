import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.io.BufferedReader;

/**
 * Created by john_bachman on 12/14/15.
 */
public class Window {
    public Window() {
        JFrame frame = new JFrame("Game Of Life - Jack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 400, 400);
        Canvas canvas = new Canvas();
        frame.add(canvas);
        frame.addKeyListener(canvas);
        frame.addMouseListener(canvas);
        frame.addComponentListener(canvas);
        frame.addMouseMotionListener(canvas);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //If it is a mac then set its name by changing the apple system property.
        if(System.getProperty("os.name").startsWith("Mac")) {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Jacks Life");
        }
        Window window = new Window();
    }
}
