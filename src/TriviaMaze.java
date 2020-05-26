
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;

import org.sqlite.SQLiteDataSource;

import javafx.application.Application;
import javafx.stage.Stage;

public class TriviaMaze extends Application {

    public static void main(String[] args) throws FileNotFoundException {
    	PrintStream ans = new PrintStream(new File("Answers.txt"));
    	SQLiteDataSource ds = null;
    	
    	try {
    		ds = new SQLiteDataSource();
    		ds.setUrl("jdbc:sqlite:questionsDatabase1.db");
    	}catch(Exception e) {
    		e.printStackTrace();
    		System.exit(0);
    	}
    	
    	
    	try {
    		
			Connection conn = ds.getConnection();
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
    
  
}


