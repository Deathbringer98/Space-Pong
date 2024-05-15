import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    static final int GAME_WIDTH = 800;
    static final int GAME_HEIGHT = 600;
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 10;
    static final int PADDLE_HEIGHT = 100;
    Paddle player1;
    Paddle player2;
    Ball ball;
    Timer timer;
    int player1Score = 0;
    int player2Score = 0;
    boolean playState = true;
    private Image backgroundImage;
    private SoundEffect scoreSound;  // Declaration of the SoundEffect object

    public GamePanel() {
        player1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        player2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), (GAME_HEIGHT / 2) - (BALL_DIAMETER / 2), BALL_DIAMETER, BALL_DIAMETER);
        loadImage();
        scoreSound = new SoundEffect("/sounds/score.wav");  // Initialize the sound effect

        this.setFocusable(true);
        this.addKeyListener(this);
        this.setPreferredSize(new java.awt.Dimension(GAME_WIDTH, GAME_HEIGHT));
        timer = new Timer(10, this);
        timer.start();
    }

    private void loadImage() {
        String filePath = "images/br.jpg";  // Relative path from the current working directory
        File file = new File(filePath);
        try {
            backgroundImage = ImageIO.read(file);
            if (backgroundImage != null) {
                System.out.println("Image loaded successfully: Width = " + backgroundImage.getWidth(null) + ", Height = " + backgroundImage.getHeight(null));
            } else {
                System.err.println("Failed to load the image.");
            }
        } catch (IOException e) {
            System.err.println("Exception while loading the image: " + e.getMessage());
            System.err.println("Failed to load image from path: " + filePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        drawScoreboard(g);
        drawCenterLine(g);
        player1.draw(g);
        player2.draw(g);
        ball.draw(g);
    }

    public void scoreUpdate(int player) {
        if (player == 1) {
            player1Score++;
        } else if (player == 2) {
            player2Score++;
        }
        scoreSound.play(); // Play sound on scoring
    }

    public void actionPerformed(ActionEvent e) {
        if (playState) {
            player1.move();
            player2.move();
            ball.move();
        }
        checkCollision();
        repaint();
    }

    public void checkCollision() {
        // Ball bounces off top & bottom window edges
        if (ball.y <= 0 || ball.y >= GAME_HEIGHT - ball.height) {
            ball.setYDirection(-ball.yVelocity); // Reverse the vertical direction using current velocity
        }
    
        // Ball bounces off paddles
        if (ball.intersects(player1) || ball.intersects(player2)) {
            ball.setXDirection(-ball.xVelocity); // Reverse the horizontal direction using current velocity
            ball.increaseSpeed(); // Increase speed on hitting a paddle
        }
    
        // Ball goes out of bounds
        if (ball.x <= 0) {
            scoreUpdate(2);
            resetGame(); // Handle game reset
        } else if (ball.x >= GAME_WIDTH - ball.width) {
            scoreUpdate(1);
            resetGame(); // Handle game reset
        }
    }
    
    private void resetGame() {
        playState = false;
        newPaddles();
        newBall();
        ball.resetSpeed(); // Reset speed when ball goes out of bounds
    }
    public void newPaddles() {
        player1.y = (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2);
        player2.y = (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2);
    }

    public void newBall() {
        ball.x = (GAME_WIDTH / 2) - (BALL_DIAMETER / 2);
        ball.y = (GAME_HEIGHT / 2) - (BALL_DIAMETER / 2);
        ball.resetSpeed();  // Reset to initial speeds
    }

    private void drawScoreboard(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 60));
        g.drawString(String.valueOf(player1Score), (GAME_WIDTH / 4), 50);
        g.drawString(String.valueOf(player2Score), (GAME_WIDTH / 4) * 3, 50);
    }

    private void drawCenterLine(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0);
        g2d.setStroke(dashed);
        g2d.setColor(Color.WHITE);
        g2d.drawLine(GAME_WIDTH / 2, 0, GAME_WIDTH / 2, GAME_HEIGHT);
    }

    public void keyPressed(KeyEvent e) {
        player1.keyPressed(e);
        player2.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !playState) {
            playState = true;  // Start moving the ball again
            ball.start(1);  // Restart ball with initial speed
        }
    }

    public void keyReleased(KeyEvent e) {
        player1.keyReleased(e);
        player2.keyReleased(e);
    }

    public void keyTyped(KeyEvent e) {}
}
