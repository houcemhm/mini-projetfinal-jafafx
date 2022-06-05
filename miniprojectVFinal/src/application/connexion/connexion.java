package application.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class connexion {
	static String URL="jdbc:mysql://localhost:3306/projetjj";
	static String db_name="root";
	static String db_pwd="";
	public static Connection con  = null;   
    static Statement  stmt  = null;
    
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			 con = DriverManager.getConnection(URL, db_name, db_pwd);
			System.out.println("Connection successfully established with database. . .");

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	public static Connection getCon() {
		return con;
	}
public static void main(String args[]) {
	connexion.getCon();

}
}