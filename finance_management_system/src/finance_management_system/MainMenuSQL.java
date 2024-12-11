package finance_management_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.math.BigDecimal;
import java.sql.*;

public class MainMenuSQL {
	final static String DATABASE_URL = "jdbc:mysql://localhost:3306/main_schema";
	final static String USER_SQL ="root";
	final static String PASSWORD_SQL="csc221";
	// THis next strings will be the users table in main_schema
	final static String USER_TABLE="users";
	final static String INCOME_TABLE="income";
	final static String EXPENSES_TABLE="expensies";
	final static String SAVINGS_TABLE="savings";
	//this will get the id for users form MainMenuSQL 
	
	public static String getUsername(int idUsers) {
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("SELECT username FROM "+USER_TABLE+" WHERE idUsers = "+idUsers);
			  ResultSet set = statement.executeQuery();
			  set.next();
			  return set.getString("username");
		}catch(SQLException e) {
			e.printStackTrace();
			return "NULL";
		}
	}// get username
	
	public static String getName(int idUsers) {
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("SELECT name FROM "+USER_TABLE+" WHERE idUsers = "+idUsers);
			  ResultSet set = statement.executeQuery();
			  set.next();
			  return set.getString("name");
		}catch(SQLException e) {
			e.printStackTrace();
			return "NULL";
		}
	}// getName
	
	public static Integer getAge(int idUsers) {
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("SELECT age FROM "+USER_TABLE+" WHERE idUsers = "+idUsers);
			  ResultSet set = statement.executeQuery();
			  set.next();
			  return set.getInt("age");
		}catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public static String getOccupation(int idUsers) {
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("SELECT occupation FROM "+USER_TABLE+" WHERE idUsers = "+idUsers);
			  ResultSet set = statement.executeQuery();
			  set.next();
			  return set.getString("occupation");
		}catch(SQLException e) {
			e.printStackTrace();
			return "NULL";
		}
	}//end getOccupation
	
	public static BigDecimal getAnnualIncome(int idUsers) {
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("SELECT annual_income FROM "+USER_TABLE+" WHERE idUsers = "+idUsers);
			  ResultSet set = statement.executeQuery();
			  set.next();
			  return set.getBigDecimal("annual_income");
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}//end annual_income
	
	public static boolean UpgradeAnnualIncome(int idUsers, BigDecimal newIncome) {
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("UPDATE "+USER_TABLE+" Set annual_income = ? WHERE (idUsers = "+idUsers+" )" );
			  statement.setBigDecimal(1,newIncome);
			  int rowsAffected = statement.executeUpdate();
			  return rowsAffected>0;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}//end annual_income
	
	public static BigDecimal getMonthlyExpense(int idUsers) {
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("SELECT monthly_expense FROM "+USER_TABLE+" WHERE idUsers = "+idUsers);
			  ResultSet set = statement.executeQuery();
			  set.next();
			  return set.getBigDecimal("monthly_expense");
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}//end expense
	}//end expense 
	
	public static boolean UpgradeMonthlyExpense(int idUsers, BigDecimal newExpense) {
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("UPDATE "+USER_TABLE+" Set monthly_expense = ? WHERE (idUsers = "+idUsers+" )" );
			  statement.setBigDecimal(1,newExpense);
			  int rowsAffected = statement.executeUpdate();
			  return rowsAffected>0;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}//end annual_income
	
	public static boolean updateInfoUser(int idUsers,String name, int age,String occupation, BigDecimal income,BigDecimal fixedExpense) {
		try {
			  Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  //UPDATE `main_schema`.`users` SET `age` = '22', `annual_income` = '150000.00', `occupation` = 'Developer', `monthly_expense` = '3000', `name` = 'Allan Yunayev' WHERE (`idUsers` = '1');
			  PreparedStatement statement = connection.prepareStatement("UPDATE "+USER_TABLE+" Set name = ?, age = ?, occupation =  ?, annual_income = ?, monthly_expense =? WHERE (idUsers = "+idUsers+" )");
			  statement.setString(1,name);
			  statement.setInt(2,age);
			  statement.setString(3,occupation);
			  statement.setBigDecimal(4, income);
			  statement.setBigDecimal(5, fixedExpense);
			  int rowsAffected = statement.executeUpdate();
			  return rowsAffected>0;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}//end expense
	}//emd updateinfousers
	
	public static boolean resetIncomeAndExpenses(int idUsers) { // if false then it is good
		 int rowsAffected,Affected;
		try {
			Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
		//DELETE FROM `main_schema`.`income` WHERE (`idUser` = '1');
			PreparedStatement statement = connection.prepareStatement("DELETE FROM "+INCOME_TABLE+" WHERE ( idUser = "+idUsers+")");
			  rowsAffected = statement.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			return true;
		}//end expense
		
		try {
			Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
		//DELETE FROM `main_schema`.`income` WHERE (`idUser` = '1');
			PreparedStatement statement = connection.prepareStatement("DELETE FROM "+EXPENSES_TABLE+" WHERE ( idUser = "+idUsers+")");
			 Affected = statement.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
			return true;
		}//end expense
		
		return rowsAffected>0 && Affected>0; // we want false
	}//end reset 
	
	public static boolean addIncome(int idUsers, BigDecimal amount, String description, String date,String type ) { //implementing a date system would take to much time.
		try {
			Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("INSERT INTO "+INCOME_TABLE+"(idUser,amount,description,date,type_of ) VALUES( ?, ?, ? , ?, ? )");
			  statement.setInt(1,idUsers);
			  statement.setBigDecimal(2,amount);
			  statement.setString(3,description);
			  statement.setString(4,date);
			  statement.setString(5, type);
			  statement.executeUpdate();
			  return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}// end addIncome
	
	public static boolean addExpense(int idUsers, BigDecimal amount, String description, String date, String type ) { //implementing a date system would take to much time.
		try {
			Connection connection= DriverManager.getConnection( DATABASE_URL,USER_SQL,PASSWORD_SQL);
			  PreparedStatement statement = connection.prepareStatement("INSERT INTO "+EXPENSES_TABLE+"(idUser,amount,description,date,type_of ) VALUES( ?, ?, ? , ?, ? )");
			  statement.setInt(1,idUsers);
			  statement.setBigDecimal(2,amount);
			  statement.setString(3,description);
			  statement.setString(4,date);
			  statement.setString(5, type);
			  statement.executeUpdate();
			  return true;
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}// end addIncome
	public static void loadBudgetTableData(DefaultTableModel tableModel,int idUsers) {
		try  {
			Connection connection = DriverManager.getConnection(DATABASE_URL, USER_SQL, PASSWORD_SQL);
	        PreparedStatement statement = connection.prepareStatement("SELECT 'Income' AS type, type_of ,amount, description, date FROM income WHERE idUser = ? "
	        		+ "UNION  SELECT 'Expense' AS type,  type_of ,amount, description, date FROM expensies WHERE idUser= ?");
	        statement.setInt(1, idUsers); 
	        statement.setInt(2, idUsers); 

	        ResultSet resultSet = statement.executeQuery();
	        //tableModel.setRowCount(0); // Clear the table
	        while (resultSet.next()) {
	            tableModel.addRow(new Object[]{
	            	resultSet.getString("type_of"),
	                resultSet.getBigDecimal("amount"),
	                resultSet.getString("description"),
	                resultSet.getString("date")
	            });
	        }
	    }catch (SQLException e) {
	        e.printStackTrace();
	    }
	}// end load 
	
}//end MainMenuSQL
