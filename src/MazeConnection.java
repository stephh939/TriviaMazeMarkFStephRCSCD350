import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

//this class creates a connection to the triviamaze database in MySQL Workbench
//and will have three main methods that will return 3 different arrays with
//all of the IDs, Questions and Answers in the database

public class MazeConnection {


	private Connection conn = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	
	   
	public MazeConnection(Connection c)throws SQLException{
	       conn = c;
	       statement = conn.createStatement();
	   }
		
	public void findAll()throws SQLException{
		   String query = "select  questions.ID, question, Answer\n" +
	               "from questions, answers\n" +
	               "where questions.ID = answers.ID\n" +
	               "ORDER BY questions.ID asc";
		   
		   resultSet = statement.executeQuery(query);
		   
	   }
	   
	public ArrayList<String> findID()throws IOException, SQLException{
		   ArrayList<String> array = new ArrayList<String>(); 
		   
		   while(resultSet.next()) {
			   String ID = resultSet.getString(1);
			   array.add(ID);
		   }
		   return array;
	   }
	  
	public ArrayList<String> findQuestions()throws IOException, SQLException{
		   ArrayList<String> array = new ArrayList<String>(); 
		   
		   while(resultSet.next()) {
			   String question = resultSet.getString(2);
			   array.add(question);
		   }
		   return array;
	   }
	   
	public ArrayList<String> findAnswers() throws IOException, SQLException{
		   ArrayList<String> array = new ArrayList<String>(); 
		   
		   while(resultSet.next()) {
			   String answer = resultSet.getString(3);
			   array.add(answer);
		   }
		  
		   return array;
		   
	   }

	
	   
	
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
		

		
	   
	   
	   
	   
	
	
	
	
	
}
