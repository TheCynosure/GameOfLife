import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

/**
 * Created by john_bachman on 12/15/15.
 */
public class ImageTools {

    private static float[] blurMatrix = {
            .111f, .111f, .111f,
            .111f, .111f, .111f,
            .111f, .111f, .111f
    };

    protected static BufferedImage blur(BufferedImage src) {
        BufferedImage returnImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        BufferedImageOp bufferedImageOp = new ConvolveOp(new Kernel(3, 3, blurMatrix));
        return bufferedImageOp.filter(src, returnImg);
    }

    @Deprecated
    protected static BufferedImage[] makeGif(String imageFolder) {
        File folder = new File(imageFolder);
        BufferedImage[] gifImages = new BufferedImage[folder.listFiles().length];
        for (int i = 0; i < gifImages.length; i++) {
            try {
                gifImages[i] = ImageIO.read(new File("Cell_Images/frame_" + i + "_delay-0.03s.gif"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gifImages;
    }

    @Deprecated
    protected static BufferedImage[] resize(BufferedImage[] imageList, int width, int height) {
        for(int i = 0; i < imageList.length; i++) {
            Image image = imageList[i].getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            g.drawImage(image, 0, 0, null);
            imageList[i] = bufferedImage;
        }
        return imageList;
    }
}
