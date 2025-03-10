class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    public void run() {
        for (int i = 1; i <= 1000; i++) {
            System.out.println(getName() + " is running: " + i);
            try {
                Thread.sleep(100); // Sleep for 100 milliseconds
            } catch (InterruptedException e) {
                System.out.println(getName() + " was interrupted.");
            }
        }
    }
}

public class ThreadPriorityDemo {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("Low Priority Thread");
        MyThread t2 = new MyThread("Medium Priority Thread");
        MyThread t3 = new MyThread("High Priority Thread");
        MyThread t4 = new MyThread("Priority 7 Thread");

        // Setting priorities
        t1.setPriority(Thread.MIN_PRIORITY); // Priority 1
        t2.setPriority(Thread.NORM_PRIORITY); // Priority 5
        t3.setPriority(Thread.MAX_PRIORITY); // Priority 10
        t4.setPriority(7); // Priority 7

        // Start threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

