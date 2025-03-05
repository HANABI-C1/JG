import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    private int x, y;
    private int width, height;
    private int yVelocity;
    private boolean isJumping;
    private boolean isAttacking;
    private int attackTimer;
    private int groundY;
    private int health;

    private BufferedImage run1, run2, jump, attack;
    private int animationFrame = 0;

    private final int GRAVITY = 2;
    private final int JUMP_STRENGTH = 30;
    private final int ATTACK_DURATION = 10;

    public Player(int startX, int groundY) {
        this.x = startX;
        this.groundY = groundY;
        this.y = groundY;
        this.width = 200;
        this.height = 200;
        this.isJumping = false;
        this.isAttacking = false;
        this.yVelocity = 0;
        this.attackTimer = 1;
        this.health = 3;

        loadImages();
    }

    private void loadImages() {
        try {
            run1 = ImageIO.read(new File("assets/player_run1.png"));
            run2 = ImageIO.read(new File("assets/player_run2.png"));
            jump = ImageIO.read(new File("assets/player_jump.png"));
            attack = ImageIO.read(new File("assets/player_attack.png"));
        } catch (IOException e) {
            System.err.println("Error loading player images: " + e.getMessage());
        }
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            yVelocity = -JUMP_STRENGTH;
        }
    }

    public void attack() {
        if (!isAttacking) {
            isAttacking = true;
            attackTimer = ATTACK_DURATION;
        }
    }

    public void update() {
        if (isAttacking) {
            attackTimer--;
            if (attackTimer <= 0) {
                isAttacking = false;
            }
        }

        if (isJumping) {
            y += yVelocity;
            yVelocity += GRAVITY;
            if (y >= groundY) {
                y = groundY;
                isJumping = false;
                yVelocity = 0;
            }
        }

        animationFrame = (animationFrame + 1) % 20;
    }

    public void draw(Graphics g) {
        BufferedImage currentImage;
        if (isAttacking) {
            currentImage = attack;
        } else if (isJumping) {
            currentImage = jump;
        } else {
            currentImage = (animationFrame < 10) ? run1 : run2;
        }
        g.drawImage(currentImage, x, y, width, height, null);
    }

   
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage() {
        health--;
        if (health <= 0) {
            health = 0;
        }
    }
}
