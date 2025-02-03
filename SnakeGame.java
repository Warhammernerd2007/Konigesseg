import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int CELL_SIZE = 20;
    private static final int DELAY = 150;
    
    private int snakeX = WIDTH / 2, snakeY = HEIGHT / 2;
    private int foodX, foodY;
    private int energy = 0;
    private char direction = 'R';
    private Timer timer;
    private Random rand;
    
    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        
        rand = new Random();
        placeFood();
        
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    private void placeFood() {
        foodX = rand.nextInt(WIDTH);
        foodY = rand.nextInt(HEIGHT);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw food
        g.setColor(Color.RED);
        g.fillRect(foodX * CELL_SIZE, foodY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        
        // Draw snake
        g.setColor(Color.GREEN);
        g.fillRect(snakeX * CELL_SIZE, snakeY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
    
    private void move() {
        switch (direction) {
            case 'U': snakeY--; break;
            case 'D': snakeY++; break;
            case 'L': snakeX--; break;
            case 'R': snakeX++; break;
        }
        
        // Check collisions
        if (snakeX < 0 || snakeX >= WIDTH || snakeY < 0 || snakeY >= HEIGHT) {
            gameOver();
        }
        
        // Check food
        if (snakeX == foodX && snakeY == foodY) {
            energy++;
            placeFood();
        }
    }
    
    private void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Energy: " + energy);
        System.exit(0);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) direction = 'U';
        else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) direction = 'D';
        else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) direction = 'L';
        else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) direction = 'R';
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
