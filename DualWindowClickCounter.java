import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DualWindowClickCounter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClickCounterModel model = new ClickCounterModel();
            createWindow("Window 1", model);
            createWindow("Window 2", model);
        });
    }

    private static void createWindow(String title, ClickCounterModel model) {
        JFrame frame = new JFrame(title);
        JLabel label = new JLabel(String.valueOf(model.getCount()), SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        frame.add(label);

        // Update label whenever the model's value changes
        model.addObserver(count -> label.setText(String.valueOf(count)));

        // Mouse click event to update the model
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.increment();
            }
        });

        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

// The shared model for the click counter
class ClickCounterModel {
    private volatile double count = 0.0; // Shared count value
    private final Object lock = new Object(); // Lock for synchronization
    private CountObserver observer;

    public double getCount() {
        return count;
    }

    public void increment() {
        synchronized (lock) {
            count++;
            notifyObserver();
        }
    }

    public void addObserver(CountObserver observer) {
        this.observer = observer;
    }

    private void notifyObserver() {
        if (observer != null) {
            SwingUtilities.invokeLater(() -> observer.onCountChanged(count));
        }
    }
}

// Functional interface for observing count changes
@FunctionalInterface
interface CountObserver {
    void onCountChanged(double newCount);
}
