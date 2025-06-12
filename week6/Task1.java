package week6;

public class Task1 {
    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) {
            final int number = i;
            new Thread(() -> {
                for (int j = 1; j <= 3; j++) {
                    System.out.println(Thread.currentThread().getName() + ": " + number + " * " + j + " = " + (number * j));
                }
            }).start();
        }
    }
}
