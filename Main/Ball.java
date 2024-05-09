import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball extends Rectangle {
    int xVelocity;
    int yVelocity;
    int initialSpeed = 2;

    Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        xVelocity = initialSpeed;
        yVelocity = initialSpeed;
    }

    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {
        g.fillOval(x, y, width, height);
    }

    public void stop() {
        xVelocity = 0;
        yVelocity = 0;
    }

    public void start(int direction) {
        xVelocity = initialSpeed * direction;
        yVelocity = initialSpeed;
    }
}
