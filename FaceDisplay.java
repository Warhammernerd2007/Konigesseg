import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FaceDisplay extends JPanel implements ActionListener {
    private static final int FACE_COUNT = 3;
    private static final String[] TEXT_EXPLANATIONS = {
            "Event-driven programming responds to user actions.",
            "A button is a component that triggers an action when clicked.",
            "A canvas is an area where graphics can be drawn.",
            "AWT (Abstract Window Toolkit) is an older Java GUI toolkit.",
            "Swing is a more flexible GUI toolkit built on AWT.",
            "Java uses layout managers to arrange GUI components."
    };
    
    private int currentDisplay = 0;
    private boolean showingText = false;
    private final Random random;
    
    public FaceDisplay() {
        JButton toggleButton = new JButton("Change Display");
        toggleButton.addActionListener(this);
        
        setLayout(new BorderLayout());
        add(toggleButton, BorderLayout.SOUTH);
        
        random = new Random();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (showingText) {
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString(TEXT_EXPLANATIONS[currentDisplay], 50, 100);
        } else {
            drawFace(g, currentDisplay);
        }
    }
    
    private void drawFace(Graphics g, int faceType) {
        g.setColor(Color.YELLOW);
        g.fillOval(50, 50, 100, 100);
        
        g.setColor(Color.BLACK);
        g.fillOval(70, 80, 15, 15);
        g.fillOval(115, 80, 15, 15);
        
        if (faceType == 0) {
            g.drawArc(75, 110, 50, 20, 0, -180); // Smiling face
        } else if (faceType == 1) {
            g.drawLine(80, 120, 120, 120); // Neutral face
        } else {
            g.drawArc(75, 110, 50, 20, 0, 180); // Sad face
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (random.nextBoolean()) {
            showingText = true;
            currentDisplay = random.nextInt(TEXT_EXPLANATIONS.length);
        } else {
            showingText = false;
            currentDisplay = random.nextInt(FACE_COUNT);
        }
        repaint();
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Face Display");
        FaceDisplay panel = new FaceDisplay();
        
        frame.add(panel);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
