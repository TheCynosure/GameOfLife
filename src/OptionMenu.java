import javax.swing.*;

/**
 * Created by john_bachman on 12/15/15.
 */
public class OptionMenu {

    @Deprecated
    public int getFps() {
        JTextField delay = new JTextField(2);
        JPanel OptionPane = new JPanel();
        OptionPane.add(new JLabel("Target Fps: "));
        int wantedFPS = 10;
        OptionPane.add(delay);
        int targetFps = JOptionPane.showConfirmDialog(null, OptionPane, "Target FPS", JOptionPane.OK_CANCEL_OPTION);
        if(targetFps == JOptionPane.OK_OPTION && (delay.getText() != null || delay.getText() != "")) {
            try {
                wantedFPS = Integer.parseInt(delay.getText());
            } catch(NumberFormatException e) {
                System.out.println("Can't parse that string");
            }
        }
        if(wantedFPS <= 0) {
            wantedFPS = 1;
        }
        return wantedFPS;
    }
}
