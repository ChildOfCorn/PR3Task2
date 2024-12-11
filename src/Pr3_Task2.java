import java.util.LinkedList;
import java.util.Queue;

class CustomerQueue {
    private final Queue<String> customers = new LinkedList<>();

    public CustomerQueue() {
        for (int i = 1; i <= 10; i++) {
            customers.add("Клієнт " + i);
        }
    }

    public synchronized String serveCustomer(String cashierName) {
        if (!customers.isEmpty()) {
            String customer = customers.poll();
            System.out.println(cashierName + " обслуговує " + customer);
            return customer;
        }
        return null;
    }
}

class SynchronizedCashier extends Thread {
    private final CustomerQueue customerQueue;
    private final String cashierName;

    public SynchronizedCashier(CustomerQueue queue, String name) {
        this.customerQueue = queue;
        this.cashierName = name;
    }

    @Override
    public void run() {
        String customer;
        while ((customer = customerQueue.serveCustomer(cashierName)) != null) {
            try {
                Thread.sleep((long) (Math.random() * 2000)); // Simulate service time
            } catch (InterruptedException e) {
                System.out.println(cashierName + " було перервано.");
            }
            System.out.println(cashierName + " закінчив обслуговування " + customer);
        }
        System.out.println(cashierName + " закінчив роботу (більше немає клієнтів).");
    }
}

public class Pr3_Task2 {
    public static void main(String[] args) {
        CustomerQueue queue = new CustomerQueue();

        // Creating multiple cashier threads
        Thread cashier1 = new SynchronizedCashier(queue, "Касир 1");
        Thread cashier2 = new SynchronizedCashier(queue, "Касир 2");
        Thread cashier3 = new SynchronizedCashier(queue, "Касир 3");

        // Starting the cashier threads
        cashier1.start();
        cashier2.start();
        cashier3.start();
    }
}
