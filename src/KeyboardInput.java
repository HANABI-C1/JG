import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardInput extends KeyAdapter {
    private Player player;

    public KeyboardInput(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_SPACE) {
            player.jump();
        }
        if (key == KeyEvent.VK_A || key == KeyEvent.VK_J) {
            player.attack();
        }
    }
}
