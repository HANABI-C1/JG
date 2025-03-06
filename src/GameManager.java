import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameManager {
    public enum State {
        MENU,
        PLAYING,
        GAMEOVER
    }

    private State gameState;
    private BufferedImage heartImage;

    public GameManager() {
        gameState = State.MENU; 
        loadImages();
    }
    //comming soon
    private void loadImages() {
        try {
            heartImage = ImageIO.read(new File("assets/heart.png"));
        } catch (IOException e) {
            System.err.println("Error loading heart image: " + e.getMessage());
        }
    }

    
    public void startGame() {
        gameState = State.PLAYING;
    }

    public void gameOver() {
        gameState = State.GAMEOVER;
    }

    public boolean isPlaying() {
        return gameState == State.PLAYING;
    }

    public boolean isGameOver() {
        return gameState == State.GAMEOVER;
    }
   // comming soon
    /**public void drawHUD(Graphics g, int health) {
        for (int i = 0; i < health; i++) {
            g.drawImage(heartImage, 10 + i * 30, 10, 20, 20, null);
        }
    }**/
}
