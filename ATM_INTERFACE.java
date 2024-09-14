// ATM_INTERFACE

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Class to represent a bank account with balance operations
class BankAccount {
    private double balance; // Current balance of the account

    // Constructor to initialize balance
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Method to get the current balance
    public double getBalance() {
        return balance;
    }

    // Method to deposit a specified amount
    public void deposit(double amount) {
        balance += amount;
    }

    // Method to withdraw a specified amount. Returns true if successful.
    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false; // Insufficient balance
        }
        balance -= amount;
        return true;
    }
}

// Class to represent a user with a name, pin, and bank account
class User {
    private String name; // Name of the user
    private int pin;     // Pin code for ATM access
    private BankAccount account; // User's bank account

    // Constructor to initialize user details
    public User(String name, int pin, double initialBalance) {
        this.name = name;
        this.pin = pin;
        this.account = new BankAccount(initialBalance);
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getPin() {
        return pin;
    }

    public BankAccount getAccount() {
        return account;
    }
}

// Class to simulate ATM operations
class ATM {
    private User currentUser; // Currently logged in user

    // Method to log in the user by checking pin
    public boolean login(User user, int enteredPin) {
        if (user.getPin() == enteredPin) {
            currentUser = user;
            return true;
        }
        return false;
    }

    // Method to log out the user
    public void logout() {
        currentUser = null;
    }

    // Method to deposit amount into the user's account
    public void deposit(double amount) {
        currentUser.getAccount().deposit(amount);
    }

    // Method to withdraw amount from the user's account
    public boolean withdraw(double amount) {
        return currentUser.getAccount().withdraw(amount);
    }

    // Method to check the current balance of the user's account
    public double checkBalance() {
        return currentUser.getAccount().getBalance();
    }

    // Get the currently logged-in user
    public User getCurrentUser() {
        return currentUser;
    }
}

// Main class for the ATM GUI interface
public class ATM_INTERFACE {

    // Default initial balance for users
    private static final double INITIAL_BALANCE = 1000.00;

    public static void main(String[] args) {
        // Create predefined users with initial balance
        User[] users = {
            new User("Dhananjay", 2345, INITIAL_BALANCE),
            new User("sameer", 1234, INITIAL_BALANCE),
            new User("Girish", 3456, INITIAL_BALANCE),
        };

        ATM atm = new ATM(); // Create ATM object

        // Create main window frame for the ATM interface
        JFrame frame = new JFrame("ATM Interface");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create login panel with gradient background
        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(173, 216, 230),
                        0, getHeight(), new Color(100, 149, 237));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        loginPanel.setLayout(new GridLayout(4, 1, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create labels and input fields for user selection and PIN entry
        JLabel userLabel = new JLabel("User Name:");
        JComboBox<String> userComboBox = new JComboBox<>();
        for (User user : users) {
            userComboBox.addItem(user.getName());
        }

        JLabel pinLabel = new JLabel("ATM PIN:");
        JPasswordField pinField = new JPasswordField();

        JButton loginButton = new JButton("Login"); // Login button
        setButtonStyle(loginButton); // Style the login button

        // Add components to the login panel
        loginPanel.add(userLabel);
        loginPanel.add(userComboBox);
        loginPanel.add(pinLabel);
        loginPanel.add(pinField);
        loginPanel.add(loginButton);

        frame.add(loginPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        // ActionListener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUser = (String) userComboBox.getSelectedItem();
                int enteredPin = Integer.parseInt(new String(pinField.getPassword()));

                // Check if entered PIN matches the selected user
                for (User user : users) {
                    if (user.getName().equals(selectedUser) && atm.login(user, enteredPin)) {
                        showMainPanel(frame, atm); // Show main menu if login is successful
                        return;
                    }
                }

                // Show error if login fails
                JOptionPane.showMessageDialog(frame,
                        "Invalid username or PIN. Please try again.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Method to display the main ATM menu after login
    private static void showMainPanel(JFrame frame, ATM atm) {
        frame.getContentPane().removeAll(); // Clear previous components

        // Create the main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(173, 216, 230),
                        0, getHeight(), new Color(100, 149, 237));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create buttons for checking balance, deposit, withdraw, and logout
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton logoutButton = new JButton("Logout");

        // Apply button styles
        setButtonStyle(checkBalanceButton);
        setButtonStyle(depositButton);
        setButtonStyle(withdrawButton);
        setButtonStyle(logoutButton);

        // Add buttons to the main panel
        mainPanel.add(checkBalanceButton);
        mainPanel.add(depositButton);
        mainPanel.add(withdrawButton);
        mainPanel.add(logoutButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();

        // ActionListener for the check balance button
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display current balance
                JOptionPane.showMessageDialog(frame,
                        "Your current balance is: $" + atm.checkBalance(),
                        "Balance Check", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // ActionListener for the deposit button
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter amount to deposit:");
                if (input != null) {
                    try {
                        double amount = Double.parseDouble(input);
                        if (amount <= 0) {
                            // Show error for invalid amount
                            JOptionPane.showMessageDialog(frame,
                                    "Please enter a valid deposit amount.",
                                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        } else {
                            atm.deposit(amount); // Deposit the amount
                            JOptionPane.showMessageDialog(frame,
                                    "Successfully deposited: $" + amount,
                                    "Deposit Successful", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        // Show error if input is not a valid number
                        JOptionPane.showMessageDialog(frame,
                                "Invalid input. Please enter a numeric value.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // ActionListener for the withdraw button
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:");
                if (input != null) {
                    try {
                        double amount = Double.parseDouble(input);
                        if (amount <= 0) {
                            // Show error for invalid amount
                            JOptionPane.showMessageDialog(frame,
                                    "Please enter a valid withdrawal amount.",
                                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        } else if (atm.withdraw(amount)) {
                            // Success message if withdrawal is successful
                            JOptionPane.showMessageDialog(frame,
                                    "Successfully withdrew: $" + amount,
                                    "Withdrawal Successful", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            // Show error if insufficient balancer
                            JOptionPane.showMessageDialog(frame,
                                    "Insufficient balance.",
                                    "Withdrawal Failed", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        // Show error if input is not a valid number
                        JOptionPane.showMessageDialog(frame,
                                "Invalid input. Please enter a numeric value.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // ActionListener for the logout button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atm.logout(); // Log out the current user
                frame.getContentPane().removeAll(); // Clear the main panel
                main(null); // Restart the application (show login screen again)
            }
        });
    }

    // Method to style buttons for consistency
    private static void setButtonStyle(JButton button) {
        button.setBackground(new Color(135, 206, 250)); // Set background color
        button.setForeground(Color.WHITE); // Set text color
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font style
        button.setFocusPainted(false); // Remove focus painting
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); // Add border to button
    }
}
