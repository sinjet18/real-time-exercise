package week8;

public class SynchronizationComparison {
    public static void main(String[] args) {
        int numThreads = 10;
        long start, end;

        // Normal thread without sync
        start = System.nanoTime();  // using nano cuz want to prevent system time interrupt
        NormalThread[] normalThreads = new NormalThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            normalThreads[i] = new NormalThread();
            normalThreads[i].start();
        }
        for (NormalThread thread : normalThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = System.nanoTime();
        double normalTime = (end - start) / 1e9;

        // Sync Thread
        start = System.nanoTime();
        SynchronizedThread[] syncThreads = new SynchronizedThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            syncThreads[i] = new SynchronizedThread();
            syncThreads[i].start();
        }
        for (SynchronizedThread thread : syncThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = System.nanoTime();
        double syncTime = (end - start) / 1e9;

        // Output
        System.out.printf("Normal thread = %.8f seconds\n", normalTime);
        System.out.printf("Synchronized thread = %.8f seconds\n", syncTime);
    }
}

//method & parameter
class NormalThread extends Thread {
    private static int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            count++; // race condition occur in this line
        }
    }
}

class SynchronizedThread extends Thread {
    private static int count = 0;
    private static final Object lock = new Object();

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            synchronized (lock) {
                count++;
            }
        }
    }
}
