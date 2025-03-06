import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Obstacle {
    private int x, y;
    private int width, height;
    private boolean destroyed;
    private BufferedImage obstacleImage;

    public Obstacle(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.width = 40;
        this.height = 80;
        this.destroyed = false;
        loadImage();
    }

    private void loadImage() {
        try {
            obstacleImage = ImageIO.read(new File("assets/bamboo.png"));
        } catch (IOException e) {
            System.err.println("Error loading obstacle image: " + e.getMessage());
        }
    }

    public void update() {
        x -= 5;
    }

    public void draw(Graphics g) {
        if (!destroyed) {
            g.drawImage(obstacleImage, x, y, width, height, null);
        }
    }

    public void destroy() {
        destroyed = true;
    }

    
    public boolean isDestroyed() {
        return destroyed;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public Rectangle getHitbox() {
        return new Rectangle(x+50, y, width, height);
    }

    public boolean isCollidingWith(Player player) {
        return getHitbox().intersects(player.getHitbox());
    }

    public boolean isAttackableBy(Player player) {
        return player.isAttacking() && getHitbox().intersects(player.getHitbox());
    }
}
