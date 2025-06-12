package week7;

import java.util.Random;

public class DeadlockNew implements Runnable {
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();
    private final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Thread myThread1 = new Thread(new DeadlockNew(), "thread-1");
        Thread myThread2 = new Thread(new DeadlockNew(), "thread-2");
        myThread1.start();
        myThread2.start();
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {

            synchronized (resource1) {
                synchronized (resource2) {
                    System.out.println("[" + Thread.currentThread().getName() +
                            "] Locked both resources safely");
                }
            }
            try { Thread.sleep(10); } catch (InterruptedException e) {}
        }
    }
}
