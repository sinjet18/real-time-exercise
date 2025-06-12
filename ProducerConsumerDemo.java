package Week10;

public class ProducerConsumerDemo {

    static class SharedData {
        private boolean dataReady = false;
        private String data;

        public synchronized void produce() {
            try {
                System.out.println("Producer: Preparing data...");
                Thread.sleep(1000);  // Production time
                data = "Hello from producer";
                dataReady = true;
                System.out.println("Producer: Data is ready.");
                notifyAll();  // Wake ALL waiting threads
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public synchronized void consume() {
            try {
                // Always re-check condition after wakeup
                while (!dataReady) {
                    System.out.println("Consumer: Waiting for data...");
                    wait();  // Releases lock and waits
                }
                System.out.println("Consumer: Received -> " + data);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        SharedData sharedData = new SharedData();

        // Consumer thread (starts first)
        Thread consumerThread = new Thread(() -> sharedData.consume());

        // Producer thread (delayed start)
        Thread producerThread = new Thread(() -> sharedData.produce());

        consumerThread.start();

        try {
            // Let consumer start first
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        producerThread.start();
    }
}