import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by john_bachman on 12/14/15.
 */
public class Canvas extends JPanel implements MouseListener, KeyListener, ComponentListener, MouseMotionListener {

    private Colony colony;
    private Timer timer;
    private BufferedImage pauseScreen;
    private Menu menu;
    public int fps = 10, currentFrame = 0;
    private String[] gifImages;
    private Image image;
    private boolean paused = false;

    public boolean bgOn = true, opacityOn = true;

    public Canvas() {
        int size = new File("Cell_Images").listFiles().length;
        gifImages = new String[size];
        for(int i = 0; i < size; i++) {
            gifImages[i] = "Cell_Images/frame_" + i + "_delay-0.03s.gif";
        }
        menu = new Menu();
        colony = new Colony();
        timer = new Timer((int)(1000/fps), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentFrame++;
                if(currentFrame >= gifImages.length) {
                    currentFrame = 0;
                }
                colony.step();
                repaint();
            }
        });
        timer.start();
        Audio audio = new Audio();
        audio.start();
    }

    private void callMenu() {
        menu.drawMenu(getWidth(), getHeight(), getX(), getY(), (Graphics2D) getGraphics());
    }

    protected void pause(boolean wasExit) {
        if(timer.isRunning()) {
            paused = true;
            timer.stop();
            while(timer.isRunning()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            createPauseScreen();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callMenu();
        } else if(!wasExit) {
            timer.start();
            paused = false;
        }
    }

    protected void createPauseScreen() {
        try {
            pauseScreen = new Robot().createScreenCapture(new Rectangle(getLocationOnScreen().x, getLocationOnScreen().y + getInsets().top, getWidth(), getHeight()));
            getGraphics().drawImage(ImageTools.blur(pauseScreen), 0, 0, null);
        } catch (AWTException e) {
            e.printStackTrace();
            getGraphics().drawImage(ImageTools.blur(pauseScreen), 0, 0, null);
        }
    }

    public void paintComponent(Graphics g) {
        if(!paused) {
            super.paintComponent(g);
            final Graphics2D g2 = (Graphics2D) g;
            if(bgOn) {
                image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            image = ImageIO.read(new File(gifImages[currentFrame])).getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                while (thread.isAlive()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                g2.drawImage(image, 0, 0, null);
            }
            colony.drawColony(g2, getWidth(), getHeight(), opacityOn);
        }
    }

    private void timerPause() {
        if(timer.isRunning()) {
            timer.stop();
            paused = false;
        } else {
            timer.start();
            paused = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        colony.placeCell(mouseEvent.getXOnScreen() - getLocationOnScreen().x, mouseEvent.getYOnScreen() - getLocationOnScreen().y + getInsets().top);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        pause(false);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        pause(true);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyChar() == 'c') {
            colony.initCells();
            colony.initLastGeneration();
            repaint();
        } else if(keyEvent.getKeyChar() == 'r') {
            colony.fillArray();
            repaint();
        } else if(keyEvent.getKeyCode() == 27) {
            pause(false);
        } else if(keyEvent.getKeyChar() == 'p') {
            colony.pen++;
            if(colony.pen >= 6) {
                colony.pen = 0;
            }
            repaint();
        } else if(keyEvent.getKeyChar() == '/') {
            Options options = new Options(this, colony);
        } else if(keyEvent.getKeyChar() == 32) {
            timerPause();
        }
    }

    public void changeTimerDelay(int fps) {
        timer.setDelay((int)(1000 / fps));
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        if(!timer.isRunning()) {
            createPauseScreen();
        }
    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {
        if(!timer.isRunning()) {
            createPauseScreen();
        }
    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        colony.placeCell(mouseEvent.getXOnScreen() - getLocationOnScreen().x + getInsets().left, mouseEvent.getYOnScreen() - getLocationOnScreen().y + getInsets().top);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent){

    }
}
