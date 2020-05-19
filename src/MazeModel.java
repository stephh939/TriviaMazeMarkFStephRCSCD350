import java.io.*;
import java.sql.*;
import javax.swing.*;
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

	private int correct; 
	private int incorrect;
	private static ArrayList<String> Question = new ArrayList<String>();
	private static ArrayList<String> Answer = new ArrayList<String>();
	private static ArrayList<String> ID = new ArrayList<String>();
	
	public MazeModel(ArrayList<String> Q, ArrayList<String> A, ArrayList<String> I) {
		this.setQuestion(Q);
		this.setAnswer(A);
		this.setID(I);
	}
	

	/*
	public void interact() {
		Random r = new Random();
		int random = r.nextInt(40);
		Scanner kb = new Scanner(System.in);
		int c = this.getCorrect();
		int i = this.getIncorrect();
		int y = 0;
		
		while(!this.getQuestion().isEmpty() && y < this.getQuestion().size()) {
			System.out.println("ID Numer: " + this.getID().get(random) + ". " + this.getQuestion().get(random));
			String answer = kb.next();
			String a = answer.toLowerCase();
			 
			for(int x = 0; x < this.getAnswer().size(); x++) {
				if(this.getAnswer().get(random) != " " && a.compareTo(this.getAnswer().get(x)) == 0) {
					System.out.println("Correct, Opening Door");
					this.getID().remove(x);
					this.getID().add(x, " ");
					this.getQuestion().remove(x);
					this.getQuestion().add(x, " ");
					this.getAnswer().remove(x);
					this.getAnswer().add(x, " ");
					c++;
					setCorrect(c);
					return;
				}
			}
			
			if(c == 0) {
				i++;
				System.out.println("Incorrect, Sealing Door");
				setIncorrect(i);
				return;
			}
			
			y++;
		}
	}

	*/
	
	
	
	
	public static ArrayList<String> getQuestion() {
		return Question;
	}

	public void setQuestion(ArrayList<String> question) {
		Question = question;
	}

	public static ArrayList<String> getAnswer() {
		return Answer;
	}

	public void setAnswer(ArrayList<String> answer) {
		Answer = answer;
	}
	
	public static ArrayList<String> getID() {
		return ID;
	}

	public void setID(ArrayList<String> iD) {
		ID = iD;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getIncorrect() {
		return incorrect;
	}

	public void setIncorrect(int incorrect) {
		this.incorrect = incorrect;
	}

	

}
