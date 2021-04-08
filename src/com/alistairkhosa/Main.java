package com.alistairkhosa;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    // class object
        Account transaction = new Account();
        double amount;
        boolean isTransact = true;
        boolean isValid;

        Scanner input = new Scanner(System.in);

        System.out.println("***** Welcome to Capital Bank ATM *****\n");

        while(isTransact){
            System.out.println("What would you like to do? \n1. Deposit \n2. Withdraw \n3. Check balance \n4. View Statement");
            System.out.print("Enter 1, 2, 3, 4 or 5 to Cancel: ");
            int option = input.nextInt();
            System.out.println();

            if(option == 1){
                isValid = true;
                while (isValid){
                    System.out.print("Enter Deposit amount: ");
                    amount = input.nextDouble();

                    if(amount > 0){
                        try{
                            System.out.println("Please wait while processing...");
                            Thread.sleep(2100);

                            // get the current date and time of the transaction
                            DateTimeFormatter now = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
                            LocalDateTime currentTime = LocalDateTime.now();
                            String time = now.format(currentTime);

                            transaction.deposit(amount,time);
                        } catch (InterruptedException e){
                            System.out.println(e.getMessage());
                        }

                        System.out.println("Would you like to do another transaction? \n1. Yes. \n2. No.");
                        System.out.print("Enter 1 or 2: ");
                        int isExit = input.nextInt();

                        if(isExit == 2)
                            isValid = false;
                    }
                    else{
                        System.out.println("Invalid amount! Make sure it is not 0.");
                    }
                }
            }
            else if(option == 2){
                isValid = true;
                while (isValid){
                    System.out.print("Enter Withdrawal amount: ");
                    amount = input.nextDouble();

                    if(amount > 0){

                        if(amount <= transaction.getAccountBalance()){

                            try{
                                System.out.println("Please wait while processing...");
                                Thread.sleep(2000);

                                // get the current date and time of the transaction
                                DateTimeFormatter now = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
                                LocalDateTime currentTime = LocalDateTime.now();
                                String time = now.format(currentTime);

                                transaction.withdraw(amount,time);
                            } catch (InterruptedException e){
                                System.out.println(e.getMessage());
                            }

                            System.out.println("Would you like to do another transaction? \n1. Yes. \n2. No.");
                            System.out.print("Enter 1 or 2: ");
                            int isExit = input.nextInt();

                            if(isExit == 2)
                                isValid = false;
                        }
                        else{
                            System.out.printf("You have insufficient funds. Your account balance is %s%n", transaction.getAccountBalance());
                            System.out.println();
                            isValid = false;
                        }
                    }
                    else{
                        System.out.println("Invalid amount! Make sure it is not 0.");
                    }
                }
            }
            else if(option == 3){
                transaction.viewBalance();
            }
            else if(option == 4){
                transaction.viewStatement();
            }
            else
                isTransact = false;
        }

    }
}

class Account{
    double accountBalance;
    int counter = 0;
    String[][] statement = new String[100][4];

    public Account(){
        accountBalance = 100;
    }

    public void deposit(double depositAmount, String time) {

        // set new account balance
        this.accountBalance += depositAmount;
        System.out.printf("You have a deposited %s%nYour new balance is %s%n", NumberFormat.getCurrencyInstance().format(depositAmount), NumberFormat.getCurrencyInstance().format(accountBalance));

        // store transactional history
        this.counter += 1;
        this.statement[counter - 1][0] = time;
        this.statement[counter - 1][1] = "Deposit\t";
        this.statement[counter - 1][2] = NumberFormat.getCurrencyInstance().format(depositAmount);
        this.statement[counter - 1][3] = NumberFormat.getCurrencyInstance().format(accountBalance);

    }

    public void withdraw(double withdrawalAmount, String time) {

        // set new account balance
        this.accountBalance -= withdrawalAmount;
        System.out.printf("You have a withdrew %s%nYour new balance is %s%n",NumberFormat.getCurrencyInstance().format(withdrawalAmount),NumberFormat.getCurrencyInstance().format(accountBalance));

        // store transactional history
        this.counter += 1;
        this.statement[counter - 1][0] = time;
        this.statement[counter - 1][1] = "Withdraw";
        this.statement[counter - 1][2] = NumberFormat.getCurrencyInstance().format(withdrawalAmount);
        this.statement[counter - 1][3] = NumberFormat.getCurrencyInstance().format(accountBalance);

    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void viewBalance() {
        System.out.println("Your account balance is: " + NumberFormat.getCurrencyInstance().format(accountBalance));
    }

    public void viewStatement() {
        if(counter == 0)
            System.out.println("You have no transactions.");
        else{
            System.out.printf("Date \t\t\t\t\tTransaction \tAmount \t\tBalance");
            for (int i = 0; i < counter; i++) {
                for (int j = 0; j < 4; j++) {
                    System.out.print(statement[i][j] + "\t\t");
                }
                System.out.println();
            }
            System.out.println("********** Closing balance: " + NumberFormat.getCurrencyInstance().format(accountBalance) + " **********");
        }
    }
}