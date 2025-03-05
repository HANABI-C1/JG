import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Background {
    private BufferedImage[] backgrounds;
    private int currentIndex;
    private int nextIndex;
    private float alpha;
    private boolean transitioning;
    private int frameCount;

    public Background() {
        backgrounds = new BufferedImage[3];
        loadImages();
        currentIndex = 0;
        nextIndex = 1;
        alpha = 0.0f;
        transitioning = false;
        frameCount = 0;
    }

    private void loadImages() {
        try {
            backgrounds[0] = ImageIO.read(new File("assets/background1.png"));
            backgrounds[1] = ImageIO.read(new File("assets/background2.png"));
            backgrounds[2] = ImageIO.read(new File("assets/background3.png"));
        } catch (IOException e) {
            System.err.println("Error loading background images: " + e.getMessage());
        }
    }

    public void update() {
        frameCount++;
        if (frameCount >= 600) {
            transitioning = true;
            frameCount = 0;
            nextIndex = (currentIndex + 1) % backgrounds.length;
            alpha = 0.0f;
        }

        if (transitioning) {
            alpha += 0.01f;
            if (alpha >= 1.0f) {
                alpha = 1.0f;
                currentIndex = nextIndex;
                transitioning = false;
            }
        }
    }

    public void draw(Graphics g, int width, int height) {
        g.drawImage(backgrounds[currentIndex], 0, 0, width, height, null);
        if (transitioning) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.drawImage(backgrounds[nextIndex], 0, 0, width, height, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
