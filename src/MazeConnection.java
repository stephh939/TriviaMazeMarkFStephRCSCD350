import org.sqlite.SQLiteDataSource;
import java.sql.*;

public class MazeConnection {
	
	private Connection conn = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private String [][] questionsIDs;

	public MazeConnection() {
		questionsIDs = new String[5][5];
		SQLiteDataSource ds;
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:questionsDatabase1.db");
			conn = ds.getConnection();
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void addQuestionToDatabase(String question, String answer, QuestionsTable table) throws SQLException {
		
		Integer id = null;
		String query = "select MAX(Questions.ID)\r\n" + 
				"from Answers, Questions";
		
		statement = conn.createStatement(); 
		resultSet = statement.executeQuery(query);
		
		while(resultSet.next()) {
			id = Integer.parseInt(resultSet.getString(1));	
			id = id +1; 
		}
		
		query = "Insert into Questions (ID, Question)" + "Values(?,?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1,id);
		ps.setString(2, question);
		ps.execute();
		
		query = "Insert into Answers (ID, Answer)" + "Values(?,?)";
		ps = conn.prepareStatement(query);
		ps.setInt(1,id);
		ps.setString(2, answer);
		ps.execute();
		
		
		
		
		if(id != null && question != null && answer != null) {
			table.getItems().add(new Question(question, answer, id));
		
		}
		
		
		//TODO: Find the next ID available and then insert new question into the database
		
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

	protected String getQuestion(int xLoc, int yLoc) throws SQLException {
		String questionID = questionsIDs[xLoc][yLoc];
		String q = null; 
		String query = "select Question\r\n" + 
				"from Questions\r\n" + 
				"where Questions.ID = " + questionID;
		
		statement = conn.createStatement(); 
		resultSet = statement.executeQuery(query);
		
		while(resultSet.next()) {
			q = resultSet.getString(1);
			return q; 
		}
		
		return q;
	}

	public boolean checkAnswer(int xLoc, int yLoc, String answer) throws SQLException {
		String questionID = questionsIDs[xLoc][yLoc];
		
		String query = "select Answer\r\n" + 
				"from Answers\r\n" + 
				"where Answers.ID = " + questionID + " and Answer = " + answer;
		
		statement = conn.createStatement();
		resultSet = statement.executeQuery(query);
		
		if(resultSet.next()) {
			return true; 
		}
		
		return false;
	}
}
