import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.util.*;
import java.lang.String;


/*
 All logic of the interaction of data happens here ie. the business logic
 Here we will...
 1) Connect, manipulate, and pull from the database
 2) An array that has every door's assigned question id and the status of that door (needs to be maintained for save/load)
 	//method to update the view class of the doors. Listeners and observers here 
*/

public class MazeModel {

	private Connection conn = null; 
	private Statement statement = null; 
	private ResultSet resultSet = null; 
	
	public MazeModel() {
		
	}
	
	
	public MazeModel(Connection c)throws SQLException{
		conn = c; 
		statement = conn.createStatement();
	}
	
	public void findQuestions()throws IOException, SQLException{
		
		String query = "SELECT QuestionOptions.Question_ID, QuestionOptions.Question_Choice, QuestionAnswers.Answer\r\n" + 
				"from QuestionOptions, QuestionAnswers\r\n" + 
				"Where QuestionOptions.Question_ID = QuestionAnswers.Question_ID\r\n" + 
				"Order By QuestionOptions.Question_ID asc";
		
		resultSet = statement.executeQuery(query);
		
	}
	
	public void printQuestions() throws IOException, SQLException{
		System.out.println("****** Query 0 ******");
		System.out.println(); 
		System.out.println("Question");
		
		while(resultSet.next()) {
			String question = resultSet.getString(1);
		}
		System.out.println();
	}
	
	
	
	
	
	
	
	
	
	

}
