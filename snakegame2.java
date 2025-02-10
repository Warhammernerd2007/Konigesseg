import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int CELL_SIZE = 20;
    private static final int INITIAL_DELAY = 150;
    
    private LinkedList<Point> snake;
    private Point food;
    private char direction = 'R';
    private Timer timer;
    private Random rand;
    private int delay;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        
        rand = new Random();
        delay = INITIAL_DELAY;
        snake = new LinkedList<>();
        snake.add(new Point(WIDTH / 2, HEIGHT / 2));
        placeFood();
        
        timer = new Timer(delay, this);
        timer.start();
    }
    
    private void placeFood() {
        int x, y;
        do {
            x = rand.nextInt(WIDTH);
            y = rand.nextInt(HEIGHT);
        } while (snake.contains(new Point(x, y)));
        food = new Point(x, y);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw food
        g.setColor(Color.RED);
        g.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        
        // Draw snake
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
    
    private void move() {
        Point head = snake.getFirst();
        Point newHead = switch (direction) {
            case 'U' -> new Point(head.x, head.y - 1);
            case 'D' -> new Point(head.x, head.y + 1);
            case 'L' -> new Point(head.x - 1, head.y);
            case 'R' -> new Point(head.x + 1, head.y);
            default -> head;
        };
        
        // Check collisions
        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT || snake.contains(newHead)) {
            gameOver();
            return;
        }
        
        // Check food
        if (newHead.equals(food)) {
            placeFood();
        } else {
            snake.removeLast();
        }
        
        snake.addFirst(newHead);
    }
    
    private void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Score: " + (snake.size() - 1));
        System.exit(0);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && direction != 'D') direction = 'U';
        else if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && direction != 'U') direction = 'D';
        else if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && direction != 'R') direction = 'L';
        else if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && direction != 'L') direction = 'R';
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
