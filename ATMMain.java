import java.util.Scanner;

public class ATMMain {
    public static void main(String[] args) {
        ATM atm = new ATM();
        ATM.Account account = null;
        ATM.Transaction lastTransaction = null;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Press 1 to enter account details");
            System.out.println("Press 2 to deposit");
            System.out.println("Press 3 to withdraw");
            System.out.println("Press 4 to show current balance");
            System.out.println("Press 5 to cancel last transaction");
            System.out.println("Press 6 to exit");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.println("Enter account number:");
                    int accountNumber = scanner.nextInt();
                    System.out.println("Enter owner name:");
                    String ownerName = scanner.next();
                    System.out.println("Enter initial balance:");
                    double initialBalance = scanner.nextDouble();
                    account = atm.createAccount(accountNumber, ownerName, initialBalance);
                    break;
                case 2:
                    if (account == null) {
                        System.out.println("Please enter account details first.");
                        break;
                    }
                    System.out.println("Enter amount to deposit:");
                    double depositAmount = scanner.nextDouble();
                    lastTransaction = atm.createTransaction("deposit", depositAmount);
                    double newBalance = lastTransaction.execute(account);
                    System.out.println("New balance: " + newBalance);
                    break;
                case 3:
                    if (account == null) {
                        System.out.println("Please enter account details first.");
                        break;
                    }
                    System.out.println("Enter amount to withdraw:");
                    double withdrawAmount = scanner.nextDouble();
                    lastTransaction = atm.createTransaction("withdraw", withdrawAmount);
                    newBalance = lastTransaction.execute(account);
                    System.out.println("Current balance: " + newBalance);
                    break;
                case 4:
                    if (account == null) {
                        System.out.println("Please enter account details first.");
                        break;
                    }
                    System.out.println("Enter currency type (U for USD, E for EUR):");
                    String currencyType = scanner.next().toUpperCase();
                    lastTransaction = atm.createTransaction("balanceinquiry", currencyType);
                    double balance = lastTransaction.execute(account);
                    System.out.println("Current balance in " + currencyType + ": " + balance);
                    break;
                case 5:
                    if (account == null || lastTransaction == null) {
                        System.out.println("Please enter account details and perform a transaction first.");
                        break;
                    }
                    if (lastTransaction instanceof ATM.Rollback) {
                        double cancelledBalance = ((ATM.Rollback) lastTransaction).cancelTransaction(account);
                        System.out.println("Transaction cancelled. New balance: " + cancelledBalance);
                    } else {
                        System.out.println("Last transaction cannot be cancelled.");
                    }
                    lastTransaction = null;
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
