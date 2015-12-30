import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by john_bachman on 12/17/15.
 */
public class Options extends JPanel implements ActionListener {

    private JRadioButtonMenuItem bgOnrb, bgColorOnrb, gridOnrb;
    private Canvas canvas;
    private Colony colony;

    public Options(final Canvas canvas, Colony colony) {
        this.canvas = canvas;
        this.colony = colony;
        final JFrame frame = new JFrame("Options");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setBounds(0, 0, 200, 200);
        final JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Other Options");
        menuBar.add(menu);
        this.add(new JLabel("FPS:"));
        final JTextField textField = new JTextField(4);
        this.add(textField);
        //Background on radio button
        bgOnrb = new JRadioButtonMenuItem();
        bgOnrb.setText("Background Gif On");
        if(canvas.bgOn) {
            bgOnrb.setSelected(true);
        } else {
            bgOnrb.setSelected(false);
        }
        bgOnrb.addActionListener(this);

        //Adding radio buttons to the menu
        menu.add(bgOnrb);

        menu.addSeparator();

        bgColorOnrb = new JRadioButtonMenuItem();
        bgColorOnrb.setText("Opacity in colors");
        if(canvas.opacityOn) {
            bgColorOnrb.setSelected(true);
        } else {
            bgColorOnrb.setSelected(false);
        }
        bgColorOnrb.addActionListener(this);

        menu.add(bgColorOnrb);
        menu.addSeparator();

        gridOnrb = new JRadioButtonMenuItem();
        gridOnrb.setText("Grid On");
        if(colony.gridOn) {
            gridOnrb.setSelected(true);
        } else {
            gridOnrb.setSelected(false);
        }
        gridOnrb.addActionListener(this);
        menu.add(gridOnrb);

        final JButton button = new JButton("Change FPS");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = textField.getText();
                int fps = 10;
                if (text != null) {
                    try {
                        fps = Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Number, Dummy!");
                    }
                }
                if(fps <= 0) {
                    fps = 1;
                }
                canvas.changeTimerDelay(fps);
            }
        });
        this.add(button);

        this.add(menuBar);
        frame.add(this);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == bgOnrb) {
            if(canvas.bgOn) {
                canvas.bgOn = false;
            } else {
                canvas.bgOn = true;
            }
        }

        if(actionEvent.getSource() == bgColorOnrb) {
            if(canvas.opacityOn) {
                canvas.opacityOn = false;
            } else {
                canvas.opacityOn = true;
            }
        }

        if(actionEvent.getSource() == gridOnrb) {
            if(colony.gridOn) {
                colony.gridOn = false;
            } else {
                colony.gridOn = true;
            }
        }
    }
}
