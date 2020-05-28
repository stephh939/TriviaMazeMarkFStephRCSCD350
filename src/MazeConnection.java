import org.sqlite.SQLiteDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MazeConnection {
	
	private Connection conn = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private String [][] questionsIDs;

	public MazeConnection() {
		questionsIDs = new String[5][5];
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

	public void findAll()throws SQLException {
	   String query = "select Questions.ID, Questions.Question, Answers.Answer\r\n" +
			"from Questions, Answers\r\n" +
			"where Questions.ID = Answers.ID\r\n" +
			"order by Questions.ID asc";

	   statement = conn.createStatement();
	   resultSet = statement.executeQuery(query);
	}

	private void fillQuestionIdArray() throws SQLException {
		//TODO: Fill the questionsIDs 2D array with random questions
	}

	protected String getQuestion(int xLoc, int yLoc) {
		String questionID = questionsIDs[xLoc][yLoc];
		//TODO: Create a select to be able to get the question for the dialog box
		return "Question";
	}

	public boolean checkAnswer(int xLoc, int yLoc, String answer) {
		String questionID = questionsIDs[xLoc][yLoc];
		//TODO: Create a select to find the answer and compare to what the user inputted
		return false;
	}
}
