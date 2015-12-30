import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Created by john_bachman on 12/17/15.
 */
public class BioFrame {
    public BioFrame() {
        JFrame frame = new JFrame("Biography of a Cell");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(0, 0, 900, 175);
        Panel panel = new Panel();
        JTextArea textArea = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(textArea);
        final String text = getText();
        textArea.setText(text);
        panel.add(jScrollPane);
        frame.add(panel);
        JButton button = new JButton("Read it to me");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] saythis = new String[]{"say", text};
                try {
                    Runtime.getRuntime().exec(saythis);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        panel.add(button);
        frame.setVisible(true);
    }

    public String getText() {
        int size = new File("Cell_Bio").listFiles().length;
        int random = (int)(Math.random() * size);
        String text = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Cell_Bio/bio" + random));
            String line = bufferedReader.readLine();
            while (line != null) {
                text += line + "\n";
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
