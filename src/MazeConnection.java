import org.sqlite.SQLiteDataSource;
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

	public MazeConnection()throws SQLException{
		SQLiteDataSource ds = null;
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:questionsDatabase1.db");
			conn = ds.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void addQuestionToDatabase(String question, String answer, QuestionsTable table) {
		//TODO: Find the next ID available and then insert new question into the database
		//table.getItems().add(new Question(question, answer, id));
	}

	public void fillTable(QuestionsTable table) throws SQLException {
		String query = "select Questions.ID, Questions.Question, Answers.Answer\r\n" +
				"from Questions, Answers\r\n" +
				"where Questions.ID = Answers.ID\r\n" +
				"order by Questions.ID asc";

		statement = conn.createStatement();
		resultSet = statement.executeQuery(query);

		while (resultSet.next()) {
			Integer ID = Integer.parseInt(resultSet.getString(1));
			String question = resultSet.getString(2);
			String answer = resultSet.getString(3);
			table.getItems().add(new Question(question, answer, ID));
		}
	}

	public void findAll()throws SQLException{
		   String query = "select Questions.ID, Questions.Question, Answers.Answer\r\n" +
		   		"from Questions, Answers\r\n" +
		   		"where Questions.ID = Answers.ID\r\n" +
		   		"order by Questions.ID asc";

			   statement = conn.createStatement();
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

}
