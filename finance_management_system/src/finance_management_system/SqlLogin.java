package finance_management_system;

import javax.swing.*;

import java.math.BigDecimal;
import java.sql.*;

public class SqlLogin {
	//This is for just login could also add the expensise 
	final static String DATABASE_URL = "jdbc:mysql://localhost:3306/main_schema";
	final static String USER_SQL ="root";
	final static String PASSWORD_SQL="csc221";
	// THis next strings will be the users table in main_schema
	final static String USER_TABLE="users";
//	public SqlLogin() {	
//	}
	// Our Goal is to find if the Username is in the database when signing up
	public static boolean CheckUsername( JTextField Username) { 
	String user= Username.getText();
	  if(user.isBlank()) {
		  return false;
	  }// if input is black or just spaces then it will not be added in the database 
	  
	  try {
		  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
		  PreparedStatement statement = connection.prepareStatement("SELECT * FROM "+USER_TABLE+" WHERE USERNAME = ?");
		  statement.setString(1,user);
		  ResultSet set = statement.executeQuery();
		  return set.next();// .next() will go to the first row if there is a row return true else return flase
	  }catch(SQLException e) {
		  e.printStackTrace();
	  }
	  return false;
	}// end CheckUsername
	
	// when user signs up it will insert it in the database; and say it it true
	//nameField,usernameField, passwordField,ageField,incomeField,occupationField,expenseField
	public static boolean AddUser(JTextField nameField,JTextField usernameField, JPasswordField passwordField,JTextField ageField ,JTextField incomeField ,JTextField occupationField ,JTextField expenseField) {
		String username = usernameField.getText();
		String password=  new String(passwordField.getPassword());// need this as it will turn the char array to string 
		String	name = nameField.getText();
		int age = Integer.parseInt(ageField.getText());
		BigDecimal income= new BigDecimal(incomeField.getText());
		String occupation = occupationField.getText();
		BigDecimal expense= new BigDecimal(expenseField.getText());
		if(password.isBlank()) {
			return false; // was not able to do this on CheckUsername so will be added here 
		}
		
		// if the checkusername is false then it means it is not added to database so we need to use ! to make it true
		if(!CheckUsername(usernameField)) {
			try {
				Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
				  PreparedStatement statement = connection.prepareStatement("INSERT INTO "+USER_TABLE+"( name, username, password, age, annual_income, occupation, monthly_expense) VALUES( ? , ? , ? , ? , ?, ? , ?)");
				  statement.setString(1,name);
				  statement.setString(2,username);
				  statement.setString(3,password);
				  statement.setInt(4, age);
				  statement.setBigDecimal(5, income);
				  statement.setString(6, occupation);
				  statement.setBigDecimal(7, expense);
				  statement.executeUpdate();
				  return true;
			}catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}//end AddUser
	
	// This will check the database for the username and login and if true will go change the login gui to the main gui
	public static boolean Login(JTextField usernameField, JPasswordField passwordField) {
		String username = usernameField.getText();
		String password=  new String(passwordField.getPassword());// need this as it will turn the char array to string 
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("SELECT * FROM "+USER_TABLE+" WHERE (username, password) = ( ? , ?)");
			  statement.setString(1,username);
			  statement.setString(2,password);
			  ResultSet set = statement.executeQuery();
			  return set.next();// .next() will go to the first row if there is a row return true else return flase
		  }catch(SQLException e) {
			  e.printStackTrace();
		  }
		  return false;  
	}//end login 
	public static int getUserID(JTextField usernameField,JPasswordField passwordField) {
		String username = usernameField.getText();
		String password=  new String(passwordField.getPassword());// need this as it will turn the char array to string 
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("SELECT idUsers FROM "+USER_TABLE+" WHERE (username, password) = ( ? , ?)");
			  statement.setString(1,username);
			  statement.setString(2,password);
			  ResultSet set = statement.executeQuery();
			  set.next();
			  return set.getInt("idUsers");
		}catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}//GetUserID
}
