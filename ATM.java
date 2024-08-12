public class ATM {

    public Account createAccount(int accountNumber, String ownerName, double initialBalance) {
        return new Account(accountNumber, ownerName, initialBalance);
    }


    // Public method to create a transaction
    public Transaction createTransaction(String type, Object param) {
        switch (type.toLowerCase()) {
            case "deposit":
                return new Deposit(1, (double) param);
            case "withdraw":
                return new Withdraw(1, (double) param);
            case "balanceinquiry":
                return new BalanceInquiry(1, (String) param);
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }
    }

    // Private nested Account class
    class Account {
        private int accountNumber;
        private String ownerName;
        private double balance;

        public Account(int accountNumber, String ownerName, double balance) {
            this.accountNumber = accountNumber;
            this.ownerName = ownerName;
            this.balance = balance;
        }

        public int getAccountNumber() {
            return accountNumber;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }
        
    }

    // Public abstract Transaction class
    public abstract class Transaction {
        protected int transactionId;

        public Transaction(int transactionId) {
            this.transactionId = transactionId;
        }

        public abstract double execute(Account account);
    }

    // Public Rollback interface
    public interface Rollback {
        double cancelTransaction(Account account);
    }

    // Private nested BalanceInquiry class
    private class BalanceInquiry extends Transaction {
        private String currencyType;

        public BalanceInquiry(int transactionId, String currencyType) {
            super(transactionId);
            this.currencyType = currencyType;
        }

        @Override
        public double execute(Account account) {
            if (currencyType.equals("U")) {
                return account.getBalance();
            } else if (currencyType.equals("E")) {
                return account.getBalance() * 0.88;
            } else {
                throw new UnsupportedOperationException("Unsupported currency type");
            }
        }
    }

    // Private nested Deposit class
    private class Deposit extends Transaction implements Rollback {
        private double amount;

        public Deposit(int transactionId, double amount) {
            super(transactionId);
            this.amount = amount;
        }

        @Override
        public double execute(Account account) {
            account.setBalance(account.getBalance() + amount);
            return account.getBalance();
        }

        @Override
        public double cancelTransaction(Account account) {
            account.setBalance(account.getBalance() - amount);
            return account.getBalance();
        }
    }

    // Private nested Withdraw class
    private class Withdraw extends Transaction implements Rollback {
        private double amount;

        public Withdraw(int transactionId, double amount) {
            super(transactionId);
            this.amount = amount;
        }

        @Override
        public double execute(Account account) {
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                return account.getBalance();
            } else {
                System.out.println("unallowed process try again");
                return account.getBalance();
            }
        }

        @Override
        public double cancelTransaction(Account account) {
            account.setBalance(account.getBalance() + amount);
            return account.getBalance();
        }
    }

    // Public method to get account balance
    public double getAccountBalance(Account account) {
        return account.getBalance();
    }

    // Public method to set account balance
    public void setAccountBalance(Account account, double balance) {
        account.setBalance(balance);
    }
}
