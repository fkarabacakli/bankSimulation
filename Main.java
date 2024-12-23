import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Account[] data = read_database("data.txt");
        while (true){
            System.out.printf("Welcome Bank%nLogin or Register: ");
            String answer = input.nextLine().strip().toLowerCase();
            if (answer.equals("login")){
                data = login(data, input);
                break;
            }
            else if(answer.equals("register")){
                data = register(input, data);
                break;
            }
            else if(answer.equals("exit")){
                break;
            }
            else{
                System.out.println("Invalid command");
            }
        }

        save_database(data,"data.txt");
        input.close(); 
    }

    public static Account[] login(Account[] data, Scanner input) {
        while (true) {
            System.out.printf("Enter username: ");
            String username = input.nextLine();

            System.out.printf("Enter password:");
            String password = input.nextLine();

            for (Account account : data) {
                if (account.getUserName().equals(username) && account.login(password)) {
                    System.out.println("Giriş yapıldı.");
                    bank(account, input);
                    return data;
                }
            }

            System.out.println("Giriş yapılamadı.");
            System.out.printf("Type 'exit' to quit or press enter to try again. => ");
            String exit = input.nextLine();
            if (exit.equals("exit")) {
                return data;
            }
        }
    }
    public static void bank(Account user, Scanner input){
        System.out.printf("%s Welcome to bank%n", user.getName());
        user.viewBalance();
        while(true){
            System.out.println("What would you like to do?");
            System.out.println("Type 'withdraw' for withdraw");
            System.out.println("Type 'deposit' to deposit");
            System.out.println("Type 'change' to change password");
            System.out.println("Type 'exit' to quit");
            String answer = input.nextLine();

            if (answer.equals("withdraw")) {
                System.out.print("Enter the amount to withdraw: ");
                double amount = input.nextDouble();
                input.nextLine();
                if (!user.withDraw(amount)) {
                    System.out.println("Withdrawal failed.");
                }
            }
            else if (answer.equals("deposit")) {
                System.out.print("Enter the amount to deposit: ");
                double amount = input.nextDouble();
                input.nextLine();
                if (!user.deposit(amount)) {
                    System.out.println("Deposit failed.");
                }
                else {
                    System.out.printf("Deposit successful.");
                }
            }
            else if (answer.equals("change")) {
                while (true){
                    System.out.print("Enter your current password: ");
                    String currentPassword = input.nextLine();
                    System.out.print("Enter your new password: ");
                    String newPassword = input.nextLine();
                    if (user.changePassword(currentPassword, newPassword)){
                        System.out.println("Password changed.");
                        break;
                    }
                    else{
                        System.out.println("Password could not changed.");
                        System.out.println("Would you like to try again? (y/n)");
                        String again = input.nextLine().toLowerCase();
                        if (!again.equals("y")){
                            break;
                        }
                    }
                }
            }
            else if (answer.equals("exit")) {
                System.out.println("Exiting the bank.");
                break;
            }
            else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static Account[] register(Scanner input, Account[] data){
        System.out.println("Register a new account:");

            while(true){
            System.out.print("First Name: ");
            String firstName = input.nextLine().strip();

            System.out.print("Last Name: ");
            String lastName = input.nextLine().strip();

            System.out.print("Username: ");
            String userName = input.nextLine().strip();

            System.out.print("Password: ");
            String password = input.nextLine();
            
            try {
                Account newAccount = new Account(firstName, lastName, userName, password, 0.0);

                Account[] updatedData = new Account[data.length + 1];
                System.arraycopy(data, 0, updatedData, 0, data.length);
                updatedData[data.length] = newAccount;
                data = updatedData;
                bank(newAccount, input);
                break;
            }
            catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return data;
    }

    public static int input_size(String source) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static Account[] read_database(String source) {
        String str;
        int i = 0;
        int count = input_size(source);

        Account[] output = new Account[count];

        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            while ((str = reader.readLine()) != null) {
                String[] data = str.split(",");
                Account user = new Account(data[0], data[1],data[2],data[3], Double.parseDouble(data[4]));
                output[i++] = user;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    public static void save_database(Account[] data, String source) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(source))) {
            for (Account account : data) {
                writer.write(account.toSave());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}