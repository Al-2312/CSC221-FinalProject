package finance_management_system;
import javax.swing.*;
import java.awt.*;

public class SignInSignUpPanel extends JPanel {

    public SignInSignUpPanel() {
        // Set up the main panel with CardLayout
        setLayout(new CardLayout());
        Color backgroundColor = Color.DARK_GRAY;
        Color textColor = Color.WHITE;
        Font font = new Font("Arial", Font.PLAIN, 20);

        // Create Sign In panel
        JPanel signInPanel = new JPanel();
        signInPanel.setLayout(new GridBagLayout());
        signInPanel.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 400, 10, 400);

        JLabel signInLabel = new JLabel("Sign In", SwingConstants.CENTER);
        signInLabel.setFont(font);
        signInLabel.setForeground(textColor);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(font);
        usernameLabel.setForeground(textColor);

        JTextField loginUsernameField = new JTextField(20);
        loginUsernameField.setFont(font);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(font);
        passwordLabel.setForeground(textColor);

        JPasswordField loginPasswordField = new JPasswordField(20);
        loginPasswordField.setFont(font);

        JButton loginButton = new JButton("Log In");
        loginButton.setFont(font);

        JButton switchToSignUp = new JButton("Go to Sign Up");
        switchToSignUp.setFont(font);

        // Add components to Sign In panel
        gbc.gridy = 0;
        gbc.gridx = 0;
        signInPanel.add(signInLabel, gbc);

        gbc.gridy++;
        signInPanel.add(usernameLabel, gbc);

        gbc.gridy++;
        signInPanel.add(loginUsernameField, gbc);

        gbc.gridy++;
        signInPanel.add(passwordLabel, gbc);

        gbc.gridy++;
        signInPanel.add(loginPasswordField, gbc);

        gbc.gridy++;
        signInPanel.add(loginButton, gbc);

        gbc.gridy++;
        signInPanel.add(switchToSignUp, gbc);

        // Create Sign Up panel
        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(new GridBagLayout());
        signUpPanel.setBackground(backgroundColor);

        JLabel signUpLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        signUpLabel.setFont(font);
        signUpLabel.setForeground(textColor);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(font);
        nameLabel.setForeground(textColor);

        JTextField nameField = new JTextField(20);
        nameField.setFont(font);

        JLabel usernameSignUpLabel = new JLabel("Username:");
        usernameSignUpLabel.setFont(font);
        usernameSignUpLabel.setForeground(textColor);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(font);

        JLabel passwordSignUpLabel = new JLabel("Password:");
        passwordSignUpLabel.setFont(font);
        passwordSignUpLabel.setForeground(textColor);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(font);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(font);
        ageLabel.setForeground(textColor);

        JTextField ageField = new JTextField(20);
        ageField.setFont(font);

        JLabel incomeLabel = new JLabel("Annual Income:");
        incomeLabel.setFont(font);
        incomeLabel.setForeground(textColor);

        JTextField incomeField = new JTextField(20);
        incomeField.setFont(font);

        JLabel occupationLabel = new JLabel("Occupation:");
        occupationLabel.setFont(font);
        occupationLabel.setForeground(textColor);

        JTextField occupationField = new JTextField(20);
        occupationField.setFont(font);

        JLabel expenseLabel = new JLabel("Fixed Monthly Expense:");
        expenseLabel.setFont(font);
        expenseLabel.setForeground(textColor);

        JTextField expenseField = new JTextField(20);
        expenseField.setFont(font);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(font);

        JButton switchToSignIn = new JButton("Go to Sign In");
        switchToSignIn.setFont(font);

        // Add components to Sign Up panel
        gbc.gridy = 0;
        gbc.gridx = 0;
        signUpPanel.add(signUpLabel, gbc);

        gbc.gridy++;
        signUpPanel.add(nameLabel, gbc);

        gbc.gridy++;
        signUpPanel.add(nameField, gbc);

        gbc.gridy++;
        signUpPanel.add(usernameSignUpLabel, gbc);

        gbc.gridy++;
        signUpPanel.add(usernameField, gbc);

        gbc.gridy++;
        signUpPanel.add(passwordSignUpLabel, gbc);

        gbc.gridy++;
        signUpPanel.add(passwordField, gbc);

        gbc.gridy++;
        signUpPanel.add(ageLabel, gbc);

        gbc.gridy++;
        signUpPanel.add(ageField, gbc);

        gbc.gridy++;
        signUpPanel.add(incomeLabel, gbc);

        gbc.gridy++;
        signUpPanel.add(incomeField, gbc);

        gbc.gridy++;
        signUpPanel.add(occupationLabel, gbc);

        gbc.gridy++;
        signUpPanel.add(occupationField, gbc);

        gbc.gridy++;
        signUpPanel.add(expenseLabel, gbc);

        gbc.gridy++;
        signUpPanel.add(expenseField, gbc);

        gbc.gridy++;
        signUpPanel.add(signUpButton, gbc);

        gbc.gridy++;
        signUpPanel.add(switchToSignIn, gbc);
        // Add panels to CardLayout
        add(signInPanel, "SignIn");
        add(signUpPanel, "SignUp");

        // Add action listeners for switching between panels
        switchToSignUp.addActionListener(e -> {
        	// added .setText to remove when changing pages
        	loginUsernameField.setText("");
            loginPasswordField.setText("");
            CardLayout cl = (CardLayout) getLayout();
            cl.show(this, "SignUp");
        });

        switchToSignIn.addActionListener(e -> {
        	usernameField.setText("");
            passwordField.setText("");
            incomeField.setText("");
            expenseField.setText("");
            occupationField.setText("");
            ageField.setText("");
            nameField.setText("");
            CardLayout cl = (CardLayout) getLayout();
            cl.show(this, "SignIn");
        });
        // added action listeners when login will use SqlLogin.Login and check if
        loginButton.addActionListener(e -> {
        	if(SqlLogin.Login(loginUsernameField,loginPasswordField)) {
        	JOptionPane.showMessageDialog(null, "Login successful!");
        	// TO DO 
        	//MAKE AN ANOTHER GUI FOR FINACNCE WHERE WE SEE our income,expenses, and budget,savings
        	
        	EventQueue.invokeLater(() -> {
                try {
                    MainMenu frame = new MainMenu(SqlLogin.getUserID(loginUsernameField, loginPasswordField));
                    
                    frame.setVisible(true);
                } catch (Exception s) {
                    s.printStackTrace();
                }
            });
        	
        	Window loginWindow = SwingUtilities.getWindowAncestor(loginButton); // Get the parent window of the button
            if (loginWindow != null) {
                loginWindow.dispose();
            }
        	
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "Invalid username or password.\nWant to sign up instead?");	
        	}
        });
        
        signUpButton.addActionListener(e -> {
        	if(SqlLogin.CheckUsername(usernameField)) {
        		JOptionPane.showMessageDialog(null, "Username is used login instead.");
        	}
        	else if(SqlLogin.AddUser(nameField,usernameField, passwordField,ageField,incomeField,occupationField,expenseField)) {
        		JOptionPane.showMessageDialog(null, "Sign Up successful!");	
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "Invalid password");
        	}
        	
        });
    }
}