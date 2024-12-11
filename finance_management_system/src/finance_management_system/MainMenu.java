package finance_management_system; 
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.math.RoundingMode;

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Scale the background to fill the panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

public class MainMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel mainMenuPanel;
    private JPanel viewAccountPanel;
    private JPanel editAccountPanel;
    private JPanel annualReportPanel;
    private JLabel accountInfo; // To dynamically update the "View Account" information
    private JTable budgetTable;
    private JScrollPane scrollPane;
    private JPanel manageExpensesPanel;
    private JTextField nameField, ageField, occupationField, incomeField, fixedExpenseField;
    private int idUsers; 
    private double monthlySavingChecker; // will use this to check if monthlySaving-amount < 0 so we don't have to initlaize it in another panel by having to do the calculatons again i.e. Cheating 
    /**
     * Create the frame.
     */
    public MainMenu(int idUsers) {
    	this.idUsers=idUsers;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 720);

        // Main container using CardLayout
        contentPane = new JPanel(new CardLayout());
        setContentPane(contentPane);

        // Initialize Panels
        mainMenuPanel = createMainMenuPanel();
        viewAccountPanel = createViewAccountPanel();
        editAccountPanel = createEditAccountPanel();
        annualReportPanel = createAnnualReportPanel();
        manageExpensesPanel = createManageExpensesPanel();

        // Add Panels to Content Pane
        contentPane.add(mainMenuPanel, "Main Menu");
        contentPane.add(viewAccountPanel, "View Account");
        contentPane.add(editAccountPanel, "Edit Account");
        contentPane.add(annualReportPanel, "Annual Report");
        contentPane.add(manageExpensesPanel, "Manage Expenses");
    }



    // Create Main Menu Panel
    private JPanel createMainMenuPanel() {
        ImageIcon backgroundIcon = new ImageIcon("images\\background.jpg");
        BackgroundPanel panel = new BackgroundPanel(backgroundIcon.getImage());
        panel.setLayout(null);

        JLabel welcomeText = new JLabel("Welcome, ");
        welcomeText.setFont(new Font("Tahoma", Font.PLAIN, 22));
        welcomeText.setBounds(464, 85, 115, 39);
        panel.add(welcomeText);

        JLabel welcomeUSERNAME = new JLabel(MainMenuSQL.getUsername(idUsers));
        welcomeUSERNAME.setFont(new Font("Tahoma", Font.PLAIN, 22));
        welcomeUSERNAME.setBounds(568, 85, 245, 39);
        panel.add(welcomeUSERNAME);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> System.exit(0));
        logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        logoutBtn.setBounds(1112, 23, 115, 39);
        panel.add(logoutBtn);

        JButton viewAccountBtn = new JButton("View Account");
        viewAccountBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        viewAccountBtn.setBounds(464, 205, 349, 53);
        viewAccountBtn.addActionListener(e -> switchPanel("View Account"));
        panel.add(viewAccountBtn);

        JButton manageExpensesBtn = new JButton("Manage Income & Expenses");
        manageExpensesBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        manageExpensesBtn.setBounds(464, 385, 349, 53);
        manageExpensesBtn.addActionListener(e -> switchPanel("Manage Expenses"));
        panel.add(manageExpensesBtn);

        JButton annualReportBtn = new JButton("Monthly Report");
        annualReportBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        annualReportBtn.setBounds(464, 295, 349, 53);
        annualReportBtn.addActionListener(e -> switchPanel("Annual Report"));
        panel.add(annualReportBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        resetBtn.setBounds(464, 475, 349, 53);
        resetBtn.addActionListener(e -> resetAnnualReport());
        panel.add(resetBtn);

        return panel;
    }

    
    // Create View Account Panel
    private JPanel createViewAccountPanel() {
        // Try loading the background image
        Image backgroundImage = null;
        try {
            backgroundImage = new ImageIcon("images\\background.jpg").getImage();
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }
    
        // Set up the dynamic background panel
        BackgroundPanel panel = new BackgroundPanel(backgroundImage);
        panel.setLayout(null);
    
        // White highlight panel for account information
        JPanel textPanel = new JPanel();
        textPanel.setBackground(new Color(255, 255, 255, 200)); // White with transparency
        textPanel.setBounds(100, 100, 600, 200);
        textPanel.setLayout(null);
    
        // Dynamic account information label
        accountInfo = new JLabel("<html>Name: "+MainMenuSQL.getName(idUsers)+"<br>Age: "+MainMenuSQL.getAge(idUsers)+"<br>Occupation: "+MainMenuSQL.getOccupation(idUsers)+
        		"<br>Income: $"+MainMenuSQL.getAnnualIncome(idUsers)+"<br>Fixed Monthly Expense: $"+MainMenuSQL.getMonthlyExpense(idUsers)+"</html>");
        accountInfo.setFont(new Font("Tahoma", Font.PLAIN, 18));
        accountInfo.setBounds(10, 10, 580, 180);
        textPanel.add(accountInfo);
        panel.add(textPanel);
    
        // Edit Account button
        JButton editBtn = new JButton("Edit Account");
        editBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        editBtn.setBounds(100, 320, 200, 50);
        editBtn.addActionListener(e -> switchPanel("Edit Account"));
        panel.add(editBtn);
    
        // Back to Main Menu button
        JButton backBtn = new JButton("Back to Main Menu");
        backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        backBtn.setBounds(100, 400, 200, 50);
        backBtn.addActionListener(e -> switchPanel("Main Menu"));
        panel.add(backBtn);
    
        // Timer to update account information dynamically
        Timer timer = new Timer(1000, e -> {
            String name = nameField.getText();
            String ageText = ageField.getText();
            String occupation = occupationField.getText();
            String incomeText = incomeField.getText();
            String fixedExpenseText = fixedExpenseField.getText();
            int age = Integer.parseInt(ageText);
            // Convert income and fixed expenses to BigDecimal
            BigDecimal income = new BigDecimal(incomeText);
            BigDecimal fixedExpense = new BigDecimal(fixedExpenseText);
           
//            accountInfo.setText("<html>Name: " + name 
//                + "<br>Age: " + age 
//                + "<br>Occupation: " + occupation 
//                + "<br>Income: $" + income 
//                + "<br>Fixed Monthly Expense: $" + fixedExpense + "</html>");
        });
        timer.start();
    
        return panel;
    }
    
    

    // Create Edit Account Panel
    private JPanel createEditAccountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel editLabel = new JLabel("Edit Account Information:");
        editLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        editLabel.setBounds(100, 50, 300, 30);
        panel.add(editLabel);

        nameField = new JTextField(MainMenuSQL.getName(idUsers));
        nameField.setBounds(100, 100, 200, 30);
        panel.add(nameField);

        ageField = new JTextField( MainMenuSQL.getAge(idUsers).toString());
        ageField.setBounds(100, 150, 200, 30);
        panel.add(ageField);

        occupationField = new JTextField(MainMenuSQL.getOccupation(idUsers));
        occupationField.setBounds(100, 200, 200, 30);
        panel.add(occupationField);

        incomeField = new JTextField(  MainMenuSQL.getAnnualIncome(idUsers).toString() );
        incomeField.setBounds(100, 250, 200, 30);
        panel.add(incomeField);

        fixedExpenseField = new JTextField(MainMenuSQL.getMonthlyExpense(idUsers).toString());
        fixedExpenseField.setBounds(100, 300, 200, 30);
        panel.add(fixedExpenseField);

        JButton saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        saveBtn.setBounds(100, 340, 200, 50);
        saveBtn.addActionListener(e -> saveAccountInfo());
        panel.add(saveBtn);

        JButton backBtn = new JButton("Cancel");
        backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        backBtn.setBounds(100, 400, 200, 50);
        backBtn.addActionListener(e -> switchPanel("View Account"));
        panel.add(backBtn);

        JLabel backgroundLabel = new JLabel(new ImageIcon("images\\background.jpg"));
        backgroundLabel.setBounds(0, 0, 1280, 720); // Adjust dimensions to match your frame
        panel.add(backgroundLabel);

        return panel;
    }
    // Save edited account information
    private void saveAccountInfo() {
        String name = nameField.getText();
        String ageText = ageField.getText();
        String occupation = occupationField.getText();
        String incomeText = incomeField.getText();
        String fixedExpenseText = fixedExpenseField.getText();
        int age = Integer.parseInt(ageText);
        // Convert income and fixed expenses to BigDecimal
        BigDecimal income = new BigDecimal(incomeText);
        BigDecimal fixedExpense = new BigDecimal(fixedExpenseText);
        if(MainMenuSQL.updateInfoUser(idUsers, name, age, occupation, income, fixedExpense)==true) {
        //accountInfo.setText("<html>Name: " + name + "<br>Age: " + age + "<br>Occupation: " + occupation + "<br>Income: $" + income + "<br>Fixed Monthly Expense: $" + fixedExpense + "</html>");
        	accountInfo.setText("<html>Name: "+MainMenuSQL.getName(idUsers)+"<br>Age: "+MainMenuSQL.getAge(idUsers)+"<br>Occupation: "+MainMenuSQL.getOccupation(idUsers)+
    		"<br>Income: $"+MainMenuSQL.getAnnualIncome(idUsers)+"<br>Fixed Monthly Expense: $"+MainMenuSQL.getMonthlyExpense(idUsers)+"</html>");
        }
        else {
        	JOptionPane.showMessageDialog(null, "ERROR!");	
        }
        // Switch back to View Account panel
        switchPanel("View Account");
    }

    private JPanel createAnnualReportPanel() {
        // Set up the dynamic background panel
        ImageIcon backgroundIcon = new ImageIcon("images\\background.jpg");
        BackgroundPanel panel = new BackgroundPanel(backgroundIcon.getImage());
        panel.setLayout(null);
    
        // White highlight panel for the text and labels
        JPanel highlightPanel = new JPanel();
        highlightPanel.setBackground(new Color(255, 255, 255, 200)); // White with transparency
        highlightPanel.setBounds(50, 50, 1000, 600); // Adjust as needed
        highlightPanel.setLayout(null);
    
        // Recommendation text
        JLabel recommendationLabel = new JLabel("<html><b>Our recommendation is you should at least save 30% of your monthly income</b><br>Your status is:</html>");
        recommendationLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        recommendationLabel.setBounds(30, 10, 800, 60);
        highlightPanel.add(recommendationLabel);
    
        // Dynamic status label
        JLabel statusLabel = new JLabel();
        statusLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        statusLabel.setBounds(30, 80, 800, 30);
        highlightPanel.add(statusLabel);
        
     // Monthly Income Label
        JLabel savingsTargetLabel = new JLabel("Saving Target: $0");
        savingsTargetLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        savingsTargetLabel.setBounds(30, 120, 400, 30);
        highlightPanel.add(savingsTargetLabel);
    
        // Monthly Income Label
        JLabel monthlyIncomeLabel = new JLabel("Monthly Income: $0");
        monthlyIncomeLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        monthlyIncomeLabel.setBounds(30, 160, 400, 30);
        highlightPanel.add(monthlyIncomeLabel);
    
        // Monthly Expense Label
        JLabel monthlyExpenseLabel = new JLabel("Monthly Expense: $0");
        monthlyExpenseLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        monthlyExpenseLabel.setBounds(30, 200, 400, 30);
        highlightPanel.add(monthlyExpenseLabel);
    
        // Monthly Budget Label
        JLabel monthlyBudgetLabel = new JLabel("Monthly Budget: $0");
        monthlyBudgetLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        monthlyBudgetLabel.setBounds(30, 240, 400, 30);
        highlightPanel.add(monthlyBudgetLabel);
    
        // Define table columns and sample data
        String[] columnNames = {"Type", "Amount", "Description", "Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                // Return false to make all cells non-editable
                return false;
            }
        };// add to the cells are not editable 
       MainMenuSQL.loadBudgetTableData(tableModel, idUsers);
        budgetTable = new JTable(tableModel);
        budgetTable.setFont(new Font("Tahoma", Font.PLAIN, 16));
        budgetTable.setRowHeight(25);
        
        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(budgetTable);
        scrollPane.setBounds(30, 280, 900, 200);
        highlightPanel.add(scrollPane);
    
        // Add the highlight panel to the main background panel
        panel.add(highlightPanel);
    
        // Back Button
        JButton backBtn = new JButton("Back to Main Menu");
        backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
        backBtn.setBounds(30, 500, 200, 50);
        backBtn.addActionListener(e -> switchPanel("Main Menu"));
        highlightPanel.add(backBtn);
    
        // Update fields dynamically based on income and expense values
        Timer timer = new Timer(1000, e -> {
        	 double annualIncome = Double.parseDouble(MainMenuSQL.getAnnualIncome(idUsers).toString());
        	    double fixedExpense = Double.parseDouble(MainMenuSQL.getMonthlyExpense(idUsers).toString());
        	    	
        	    // Calculate Monthly Income and Expense
        	    double monthlyIncome = annualIncome / 12;
        	    double monthlyExpense = fixedExpense;

        	    double oneTimeIncomeTotal = 0;
                double oneTimeExpenseTotal = 0;

                // Adjust monthly income and expense
            // Iterate through budgetTable rows to calculate one-time entries
            for (int i = 0; i < budgetTable.getRowCount(); i++) {
                String type = budgetTable.getValueAt(i, 0).toString();
                double amount = Double.parseDouble(budgetTable.getValueAt(i, 1).toString());
                if (type.equals("One-time Income")) {
                	  //oneTimeIncomeTotal= oneTimeIncomeTotal.add(amount); // Add directly to monthly income
                	oneTimeIncomeTotal += amount;
                } else if (type.equals("One-time Expense")) {
                	//oneTimeExpenseTotal = oneTimeExpenseTotal.add(amount); // Add directly to monthly expense
                	oneTimeExpenseTotal += amount;
                }
            }
            monthlyIncome += oneTimeIncomeTotal;
            monthlyExpense += oneTimeExpenseTotal;
            double monthlySavings = monthlyIncome - monthlyExpense;
            double savingsTarget = 0.3 * monthlyIncome;
            monthlySavingChecker=monthlySavings;
            //System.out.println(annualIncome+"\t"+monthlyIncome+"\t"+monthlyExpense+"\t"+monthlySavings+"\t"+savingsTarget);
            // Update labels
            savingsTargetLabel.setText("Saving Target: $" + String.format("%.2f", savingsTarget));
            monthlyIncomeLabel.setText("Monthly Income: $" +  String.format("%.2f", monthlyIncome));
            monthlyExpenseLabel.setText("Monthly Expense: $" + String.format("%.2f",  monthlyExpense));
            monthlyBudgetLabel.setText("Monthly Budget: $" +String.format("%.2f",  monthlySavings));
    
            // Update status based on savings
            if (monthlySavings < savingsTarget) { // compare to checks where if less -1 equal 0 and greater 1 monthlySavings.compareTo(savingsTarget)== -1
                statusLabel.setText("<html><font color='red'>You have exceeded your monthly savings budget, please try to spend less</font></html>");
            } else {
                statusLabel.setText("<html><font color='green'>Great job! You are currently following our budget target for you.</font></html>");
            }
         
        });
        timer.start();
        return panel;
    }
    
    

// Create Manage Expenses Panel
private JPanel createManageExpensesPanel() {
    ImageIcon backgroundIcon = new ImageIcon("images\\background.jpg");
    BackgroundPanel panel = new BackgroundPanel(backgroundIcon.getImage());
    panel.setLayout(null);

    // Panel for white highlight
    JPanel textPanel = new JPanel();
    textPanel.setBackground(new Color(255, 255, 255, 200)); // White background with transparency
    textPanel.setBounds(80, 10, 800, 400);
    textPanel.setLayout(null);

    JLabel titleLabel = new JLabel("Manage Expenses");
    titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
    titleLabel.setBounds(20, 20, 300, 30);
    textPanel.add(titleLabel);

    // Dropdown for Type
    JLabel typeLabel = new JLabel("Type:");
    typeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
    typeLabel.setBounds(20, 80, 100, 30);
    textPanel.add(typeLabel);

    String[] types = {"One-time Income", "One-time Expense", "Monthly Recurring Income", "Monthly Recurring Expense (Bills, Rents, Subscription etc.)"};
    JComboBox<String> typeDropdown = new JComboBox<>(types);
    typeDropdown.setFont(new Font("Tahoma", Font.PLAIN, 16));
    typeDropdown.setBounds(130, 80, 300, 30);
    textPanel.add(typeDropdown);

    // Field for Amount
    JLabel amountLabel = new JLabel("Amount:");
    amountLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
    amountLabel.setBounds(20, 130, 100, 30);
    textPanel.add(amountLabel);

    JTextField amountField = new JTextField();
    amountField.setFont(new Font("Tahoma", Font.PLAIN, 16));
    amountField.setBounds(130, 130, 200, 30);
    textPanel.add(amountField);

    // Input validation for integers in the Amount field
    amountField.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
            char c = evt.getKeyChar();
            if (!Character.isDigit(c) && c != '\b' && c != '.') { // Allow only digits, backspace, and '.'
                evt.consume(); // Ignore invalid input
                JOptionPane.showMessageDialog(panel, "Please enter only numbers in the Amount field.");
            }
        }
    });

    // Field for Description
    JLabel descriptionLabel = new JLabel("Description:");
    descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
    descriptionLabel.setBounds(20, 180, 100, 30);
    textPanel.add(descriptionLabel);

    JTextField descriptionField = new JTextField();
    descriptionField.setFont(new Font("Tahoma", Font.PLAIN, 16));
    descriptionField.setBounds(130, 180, 300, 30);
    textPanel.add(descriptionField);

    // Field for Date
    JLabel dateLabel = new JLabel("Date:");
    dateLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
    dateLabel.setBounds(20, 230, 100, 30);
    textPanel.add(dateLabel);

    JTextField dateField = new JTextField();
    dateField.setFont(new Font("Tahoma", Font.PLAIN, 16));
    dateField.setBounds(130, 230, 200, 30);
    textPanel.add(dateField);

    // Save Button
    JButton saveBtn = new JButton("Save");
    saveBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
    saveBtn.setBounds(20, 280, 150, 40);
    saveBtn.addActionListener(e -> {
        String type = (String) typeDropdown.getSelectedItem();
        String amountText = amountField.getText();
        String description = descriptionField.getText();
        String date = dateField.getText();
        boolean working =true;
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter a valid amount.");
            return;
        }
    
        double amount = Double.parseDouble(amountText);
        // Add One-Time Income to the budget table
        if (type.equals("One-time Income")) {
            DefaultTableModel model = (DefaultTableModel) budgetTable.getModel();
            model.addRow(new Object[]{type, new BigDecimal(amount) , description, date});
            if(MainMenuSQL.addIncome(idUsers, new BigDecimal(amount), description, date,type)==false) {
            	working= false;
            }
            else {working=true;}
            
        }
        // Add One-Time Expense to the budget table
        else if (type.equals("One-time Expense")) {
        	
            DefaultTableModel model = (DefaultTableModel) budgetTable.getModel();
            if(monthlySavingChecker-amount>=0) {
	            model.addRow(new Object[]{type, new BigDecimal(amount) , description, date});
	            if(MainMenuSQL.addExpense(idUsers, new BigDecimal(amount), description, date, type)==false) {
	            	working= false;
	            }
	            else {working=true; 
	            }
            }
            else { 
            	working=false;
            	JOptionPane.showMessageDialog(null, "Your expense is too large and will make your budget negative try again");
            }
            
        }
        // Add Monthly Recurring Income to the View Account incomeField
        else if (type.equals("Monthly Recurring Income")) {
            double annualIncome = Double.parseDouble(MainMenuSQL.getAnnualIncome(idUsers).toString());
            double totIncome = annualIncome +(amount * 12);
            incomeField.setText(String.valueOf(totIncome));
            if( MainMenuSQL.UpgradeAnnualIncome(idUsers, new BigDecimal(totIncome))== false) {
            	working=false;
            }
            else {working=true;
            saveAccountInfo();}
            
             
        }
        // Add Monthly Recurring Expense to the View Account fixedExpenseField
        else if (type.equals("Monthly Recurring Expense (Bills, Rents, Subscription etc.)")) {
            double currentExpense = Double.parseDouble(MainMenuSQL.getMonthlyExpense(idUsers).toString());
            fixedExpenseField.setText(String.valueOf(currentExpense + amount));
            if( MainMenuSQL.UpgradeAnnualIncome(idUsers, new BigDecimal(currentExpense+amount)) == false) {
            	working=false;
            }
            else {working=true;
            saveAccountInfo();}
            
        }
        
        // Clear the input fields
        amountField.setText("");
        descriptionField.setText("");
        dateField.setText("");
        if(working==true) {
        	JOptionPane.showMessageDialog(panel, "Entry added successfully!");
        }
        else { JOptionPane.showMessageDialog(null, "ERROR");}
    });
    
    textPanel.add(saveBtn);

    panel.add(textPanel);

    JButton backBtn = new JButton("Back to Main Menu");
    backBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
    backBtn.setBounds(80, 490, 200, 50);
    backBtn.addActionListener(e -> switchPanel("Main Menu"));
    panel.add(backBtn);

    return panel;
}

private void resetAnnualReport() {
    // Reset budget table by clearing all rows
    DefaultTableModel model = (DefaultTableModel) budgetTable.getModel();
    model.setRowCount(0);
    
    // Reset Monthly Income and Monthly Expense in the Annual Report
    double annualIncome = Double.parseDouble(incomeField.getText());
    double fixedExpense = Double.parseDouble(fixedExpenseField.getText());

    // Calculate Monthly Income and Expense
    double monthlyIncome = annualIncome / 12;
    double monthlyExpense = fixedExpense;

    if(MainMenuSQL.resetIncomeAndExpenses(idUsers)==false) {
    	// Display a confirmation message
    	   JOptionPane.showMessageDialog(this, "Annual Report has been reset.");
    }
    else {  JOptionPane.showMessageDialog(null, "ERROR.");}
    // Update the Timer logic in Annual Report dynamically
    Timer timer = new Timer(1000, e -> {
        double oneTimeIncomeTotal = 0;
        double oneTimeExpenseTotal = 0;

        // Adjust monthly income and expense
        double monthlySavings = monthlyIncome - monthlyExpense;
        double savingsTarget = 0.3 * monthlyIncome;

        // Update the labels in Annual Report Panel
        JLabel savingsTargetLabel = new JLabel("Monthly Income: $" + String.format("%.2f", savingsTarget));
        JLabel monthlyIncomeLabel = new JLabel("Monthly Income: $" + String.format("%.2f", monthlyIncome));
        JLabel monthlyExpenseLabel = new JLabel("Monthly Expense: $" + String.format("%.2f", monthlyExpense));
        JLabel monthlyBudgetLabel = new JLabel("Monthly Budget: $" + String.format("%.2f", monthlySavings));

        // Update Status
        JLabel statusLabel = new JLabel();
        if (monthlySavings < savingsTarget) {
            statusLabel.setText("<html><font color='red'>You have exceeded your monthly savings budget, please try to spend less</font></html>");
        } else {
            statusLabel.setText("<html><font color='green'>Great job! You are currently following our budget target for you.</font></html>");
        }
    });
    timer.start();
}



    // Helper method to switch between panels
    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) contentPane.getLayout();
        cl.show(contentPane, panelName);
    }
}
