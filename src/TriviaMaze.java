
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

public class TriviaMaze extends Application {

    public static void main(String[] args) throws FileNotFoundException {
    	PrintStream ans = new PrintStream(new File("Answers.txt"));
    	
    	try {
    		
			Connection conn = getConnection();
			MazeConnection myquery = new MazeConnection(conn);
			ArrayList<String> A = new ArrayList<String>(); 
			ArrayList<String> Q = new ArrayList<String>();
			ArrayList<String> I = new ArrayList<String>();
			
			myquery.findAll();
			I = myquery.findID();
			myquery.findAll();
			Q = myquery.findQuestions();
			myquery.findAll();
			A = myquery.findAnswers();
			MazeModel question = new MazeModel(Q,A,I); 
			
			int x = 0;
			while(x < A.size()) {
				ans.println("ID:" + I.get(x) + " " + A.get(x));
				x++;
			}
			
			
			launch(args);
		
			System.out.println();
			
		
			conn.close();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
    	
    	
    	
    	
    		
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MazeController controller = new MazeController();
        controller.buildView(primaryStage);
    }
    
  
	public static Connection getConnection() throws SQLException{
		Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}catch(InstantiationException e1) {
			e1.printStackTrace();
		}catch(IllegalAccessException e1) {
			e1.printStackTrace();
		}catch(ClassNotFoundException e1) {
			e1.printStackTrace(); 
		}
		
		String serverName = "127.0.0.1:3306";
		String mydatabase = "triviamaze";
		String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
		String user = "root";
		String password = "";
		con = DriverManager.getConnection(url, user, password);
		return con; 
	
	}
}


