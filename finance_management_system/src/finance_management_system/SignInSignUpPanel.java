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
        signInPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for precise alignment
        signInPanel.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 400, 10, 400); // Margins around components

        JLabel signInLabel = new JLabel("Sign In", SwingConstants.CENTER);
        signInLabel.setFont(font);
        signInLabel.setForeground(textColor);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(font);
        usernameLabel.setForeground(textColor);

        JTextField loginUsernameField = new JTextField(20); // Limit to 20 characters
        loginUsernameField.setFont(font);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(font);
        passwordLabel.setForeground(textColor);

        JPasswordField loginPasswordField = new JPasswordField(20); // Limit to 20 characters
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
        signUpPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for precise alignment
        signUpPanel.setBackground(backgroundColor);

        JLabel signUpLabel = new JLabel("Sign Up", SwingConstants.CENTER);
        signUpLabel.setFont(font);
        signUpLabel.setForeground(textColor);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(font);
        nameLabel.setForeground(textColor);

        JTextField nameField = new JTextField(20); // Limit to 20 characters
        nameField.setFont(font);

        JLabel usernameSignUpLabel = new JLabel("Username:");
        usernameSignUpLabel.setFont(font);
        usernameSignUpLabel.setForeground(textColor);

        JTextField usernameField = new JTextField(20); // Limit to 20 characters
        usernameField.setFont(font);

        JLabel passwordSignUpLabel = new JLabel("Password:");
        passwordSignUpLabel.setFont(font);
        passwordSignUpLabel.setForeground(textColor);

        JPasswordField passwordField = new JPasswordField(20); // Limit to 20 characters
        passwordField.setFont(font);

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
            CardLayout cl = (CardLayout) getLayout();
            cl.show(this, "SignIn");
        });
        // added action listeners when login will use SqlLogin.Login and check if
        loginButton.addActionListener(e -> {
        	if(SqlLogin.Login(loginUsernameField,loginPasswordField)) {
        	JOptionPane.showMessageDialog(null, "Login successful!");
        	// TO DO 
        	//MAKE AN ANOTHER GUI FOR FINACNCE WHERE WE SEE our income,expenses, and budget,savings
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "Invalid username or password.\nWant to sign up instead?");	
        	}
        });
        
        signUpButton.addActionListener(e -> {
        	if(SqlLogin.CheckUsername(usernameField)) {
        		JOptionPane.showMessageDialog(null, "Username is used login instead.");
        	}
        	else if(SqlLogin.AddUser(usernameField, passwordField)) {
        		JOptionPane.showMessageDialog(null, "Sign Up successful!");	
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "Invalid password");
        	}
        	
        });
    }
}