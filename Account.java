public class Account {
    private final String firstName;
    private final String lastName;
    private final String userName;
    private  String password;
    private  double balance;

    public Account(String firstName, String lastName, String userName, String password, double balance){
        
        if (firstName.strip().length() < 2 || lastName.strip().length() < 2 || userName.strip().length()< 2) {
            throw new IllegalArgumentException("All fields except password must be at least 2 characters long.");
        }
        
        if(password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long.");
        }

        if (balance < 0) {
            throw new IllegalArgumentException("Balance has to be greater than zero");
        }

        this.firstName = firstName.strip();
        this.lastName = lastName.strip();
        this.userName = userName.strip();
        this.password = password;
        this.balance = balance;
    }

    public Boolean withDraw(double value){
        if (value < balance){
            balance -= value;
            System.out.printf("%s New balance is %.2f%n", userName,balance);
            return true;
        }
        System.out.printf("Balance: %.2f, you can not withDraw %.2f%n", balance, value);
        return false;
    }

    public Boolean deposit(double value){
        if (value > 0){
            balance += value;
            System.out.printf("%s New balance is %.2f%n", userName,balance);
            return true;
        }
        return false;
    }
    
    public boolean login(String userPassword){
        if (password.equals(userPassword)){
            return true;
        }
        return false;
    }

    public boolean changePassword(String oldPassword,String newPassword){
        if (newPassword.strip().length() < 6 || !login(oldPassword)){
            return false;
        }
        password = newPassword;
        return true;
    }
    
    public void viewBalance(){
        System.out.printf("Balance: %.2f%n", balance);
    }

    public String getName(){
        return firstName + " " + lastName;
    }

    public String getUserName(){
        return userName;
    }

    public String toSave(){
        return firstName + "," + lastName + "," + userName + "," + password + "," + balance;
    }
}