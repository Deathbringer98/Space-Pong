import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle {
    int id;
    int yVelocity;
    int speed = 10;

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
        this.id = id;
    }

    public void keyPressed(KeyEvent e) {
        if (id == 1) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                setYDirection(-speed);
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                setYDirection(speed);
            }
        } else if (id == 2) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                setYDirection(-speed);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                setYDirection(speed);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if ((id == 1 && (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S)) ||
            (id == 2 && (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN))) {
            setYDirection(0);
        }
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move() {
        int newY = y + yVelocity;  // Proposed new y position
        int lowerBound = GamePanel.GAME_HEIGHT - height;  // Calculate the lower boundary dynamically

        if (newY >= 0 && newY <= lowerBound) {
            y = newY;  // Update y position only if within bounds
        }
    }

    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);
    }
}
