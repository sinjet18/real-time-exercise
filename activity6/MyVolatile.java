package activity6;

import java.util.Scanner;

class MyThread extends Thread {
    private volatile boolean running = true;

    public void run() {
        while (running) {
            System.out.println("Thread is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void shutdown() {
        running = false;
        this.interrupt();
    }
}

public class MyVolatile {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();

        new Scanner(System.in).nextLine(); // Wait for Enter key
        thread.shutdown();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

