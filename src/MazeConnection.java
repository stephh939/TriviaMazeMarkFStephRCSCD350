import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class MazeConnection {

	private static Connection conn = null; 
	
	//Connection to sqlite database
	public static Connection connector(){
			
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdvc:sqlite:C:\\Users\\Steph\\Documents\\CODE\\CSCD350\\TriviaMaze\\TriviaMazeMarkFStephRCSCD350\\src\\Database\\questionsDatabase.db");
			return conn; 		
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return conn; 
		}
			
	}
	
}
