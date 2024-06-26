import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball {
    double x, y; // Use double for position to handle fractional moves
    int width = 20, height = 20;
    double xVelocity = 10, yVelocity = 10; // Use double for velocity

    public Ball(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g) {
        g.fillOval((int)x, (int)y, width, height); // Cast to int for drawing
    }

    public void stop() {
        xVelocity = 0;
        yVelocity = 0;
    }

    public void start(int direction) {
        xVelocity = direction;
        yVelocity = direction;
    }

    public boolean intersects(Paddle paddle) {
        return new Rectangle(x, y, width, height).intersects(new Rectangle(paddle.x, paddle.y, paddle.width, paddle.height));
    }

    public void setXDirection(int newDirection) {
        xVelocity = newDirection * Math.abs(xVelocity);
    }

    public void setYDirection(int newDirection) {
        yVelocity = newDirection * Math.abs(yVelocity);
    }

    public void increaseSpeed() {
        xVelocity *= 2.1;
        yVelocity *= 2.1;
    }

    public void resetSpeed() {
        xVelocity = 10;
        yVelocity = 10;
    }
}
