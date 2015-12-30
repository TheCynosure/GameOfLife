import java.awt.*;

/**
 * Created by john_bachman on 12/15/15.
 */
public class Menu {
    private String[] menuItems;

    public void drawMenu(int width, int height, int x, int y, Graphics2D g2) {
        menuItems = new String[] {"For options press [ / ]", "To clear the screen [ c ]", "To add random cells [ r ]", "To enter this menu (or leave) [ esc ]", "To switch pen type [ p ]", "For normal pausing [ Space ]"};
        int fontSize = width / 20;
        g2.setFont(new Font("Arial", Font.BOLD, fontSize));
        for(int i = 0; i < menuItems.length; i++) {
            g2.drawString(menuItems[i], 10, i * (fontSize + 20) + fontSize);
        }
    }
}
