import java.util.InputMismatchException;
import java.util.Scanner;

class InvalidAmountException extends Exception {
    public InvalidAmountException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class BankAccount {
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountHolderName) {
        this.accountHolderName = accountHolderName;
        this.balance = 0.0;
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds. Your balance is: " + balance);
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }
}

public class OnlineBankSystem {
    public static void main(String[] args) {
        Scanner scanner = null;
        BankAccount account = null;

        try {
            scanner = new Scanner(System.in);
            System.out.println("Welcome to Online Bank Account Management System");
            System.out.print("Enter your name to create an account: ");
            String name = scanner.nextLine();
            account = new BankAccount(name);

            int choice;
            do {
                System.out.println("\n--- Menu ---");
                System.out.println("1. Deposit Money");
                System.out.println("2. Withdraw Money");
                System.out.println("3. View Balance");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); // clear invalid input
                    continue;
                }

                switch (choice) {
                    case 1:
                        try {
                            System.out.print("Enter amount to deposit: ");
                            double depositAmount = scanner.nextDouble();
                            account.deposit(depositAmount);
                            System.out.println("Deposit successful.");
                        } catch (InvalidAmountException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.nextLine();
                        }
                        break;

                    case 2:
                        try {
                            System.out.print("Enter amount to withdraw: ");
                            double withdrawAmount = scanner.nextDouble();
                            account.withdraw(withdrawAmount);
                            System.out.println("Withdrawal successful.");
                        } catch (InvalidAmountException | InsufficientFundsException e) {
                            System.out.println("Error: " + e.getMessage());
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.nextLine();
                        }
                        break;

                    case 3:
                        System.out.println("Account Holder: " + account.getAccountHolderName());
                        System.out.println("Current Balance: " + account.getBalance());
                        break;

                    case 4:
                        System.out.println("Thank you for using Online Bank Account Management System!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please choose between 1-4.");
                }
            } while (choice != 4);

        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("Scanner closed. Program terminated.");
        }
    }
}