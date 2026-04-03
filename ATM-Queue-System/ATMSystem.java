import java.util.*;

class Customer {
    String name;
    int amount;

    Customer(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
}

public class ATMSystem {

    static Queue<Customer> normalQueue = new LinkedList<>();
    static Queue<Customer> priorityQueue = new LinkedList<>();
    static Stack<Customer> transactionHistory = new Stack<>();
    static int atmBalance = 20000;

    public static void addCustomer(String name, int amount, boolean isPriority) {
        Customer c = new Customer(name, amount);

        if (isPriority) {
            priorityQueue.add(c);
            System.out.println(name + " (Priority) added to queue");
        } else {
            normalQueue.add(c);
            System.out.println(name + " added to queue");
        }
    }

    
    public static void serveCustomer() {
        Customer c;

        if (!priorityQueue.isEmpty()) {
            c = priorityQueue.poll();
        } else if (!normalQueue.isEmpty()) {
            c = normalQueue.poll();
        } else {
            System.out.println("No customers in queue");
            return;
        }

        if (c.amount > atmBalance) {
            System.out.println(c.name + ": Insufficient ATM balance!");
        } else {
            atmBalance -= c.amount;
            transactionHistory.push(c);
            System.out.println(c.name + " withdrew ₹" + c.amount);
        }
    }

    public static void displayQueue() {
        System.out.println("\nPriority Queue:");
        for (Customer c : priorityQueue) {
            System.out.print(c.name + " → ");
        }

        System.out.println("\nNormal Queue:");
        for (Customer c : normalQueue) {
            System.out.print(c.name + " → ");
        }
        System.out.println();
    }

    public static void showBalance() {
        System.out.println("ATM Balance: ₹" + atmBalance);
    }

    public static void showHistory() {
        System.out.println("\nLast Transactions:");
        int count = 0;

        for (int i = transactionHistory.size() - 1; i >= 0 && count < 5; i--) {
            Customer c = transactionHistory.get(i);
            System.out.println(c.name + " withdrew ₹" + c.amount);
            count++;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- ATM MENU ---");
            System.out.println("1. Add Customer");
            System.out.println("2. Serve Customer");
            System.out.println("3. Display Queue");
            System.out.println("4. Show ATM Balance");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter withdrawal amount: ");
                    int amount = sc.nextInt();

                    System.out.print("Priority customer? (y/n): ");
                    char p = sc.next().charAt(0);

                    addCustomer(name, amount, p == 'y' || p == 'Y');
                    break;

                case 2:
                    serveCustomer();
                    break;

                case 3:
                    displayQueue();
                    break;

                case 4:
                    showBalance();
                    break;

                case 5:
                    showHistory();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}