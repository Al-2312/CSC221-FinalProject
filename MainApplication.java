import javax.swing.*;

public class MainApplication {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Sign In & Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800); // Increased size to accommodate 400px margins

        // Add the SignInSignUpPanel to the frame
        frame.add(new SignInSignUpPanel());

        // Set the frame to be visible
        frame.setVisible(true);
    }
}
