import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Game extends JPanel implements Runnable {
    private Thread gameThread;
    private boolean running;
    private Player player;
    private List<Obstacle> obstacles;
    private Background background;
    private GameManager gameManager;
    private KeyboardInput keyboard;

    public Game() {
        setPreferredSize(new Dimension(800, 600));
        gameManager = new GameManager();
        player = new Player(50, 400);
        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(800, 500));
        background = new Background();
        keyboard = new KeyboardInput(player);

        addKeyListener(keyboard);
        setFocusable(true);
        gameManager.startGame();
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            if (gameManager.isPlaying()) {
                updateGame();
                checkCollisions();
            }
            repaint();
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        player.update();
        background.update();

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obs = obstacles.get(i);
            obs.update();
            if (obs.isDestroyed() || obs.getX() + obs.getWidth() < 0) {
                obstacles.remove(i);
                i--;
            }
        }

        if (Math.random() < 0.007) {
            obstacles.add(new Obstacle(800, 500));
        }
    }

    private void checkCollisions() {
        for (Obstacle obs : obstacles) {
            if (obs.isCollidingWith(player)) {
                if (obs.isAttackableBy(player)) {
                    obs.destroy();
                } else {
                    gameManager.gameOver();
                    running = false;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.draw(g, getWidth(), getHeight());
        player.draw(g);
        for (Obstacle obs : obstacles) {
            obs.draw(g);
        }

        if (gameManager.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("GAME OVER", getWidth() / 2 - 150, getHeight() / 2);
        }

        gameManager.drawHUD(g, player.getHealth());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("2D Game");
        Game game = new Game();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }
}
