public class TestSleep {

    private static int current = 1;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        for (int i = 1; i <= 20; i++) {
            final int threadNumber = i;

            new Thread(() -> {
                synchronized (lock) {
                    while (threadNumber != current) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println("ONE (thread " + threadNumber + ")");
                    System.out.println("TWO (thread " + threadNumber + ")");
                    System.out.println("THREE (thread " + threadNumber + ")");
                    System.out.println("XXXXXXXXXX (thread " + threadNumber + ")");

                    current++;
                    lock.notifyAll();
                }
            }).start();
        }
    }
}
