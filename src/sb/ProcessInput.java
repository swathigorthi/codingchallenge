package sb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProcessInput {

	public static void main(String[] args) {
		
		Processor p = new Processor();
		//Saving the group to user information int he processor's map
		processGroupInput(p);
	
		// saving the user to group information in the processor's queue
		processUserInput(p);
    	
	}

	private static void processGroupInput(Processor p) {
		Scanner in= null;
		try {
			in = new Scanner(new File("groups.txt"));
			while(in.hasNextLine()){
				p.addGroup(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(in != null){
				in.close();
			}
		}
	}
	
	private static void processUserInput(Processor p){
		//Starting a parallel thread that checks if each user in the processor's user map has a group or not and prints their id if not
		p.start();
		Scanner in= null;
		try {
			in = new Scanner(new File("users.txt"));
			while(in.hasNextLine()){
				p.addUser(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(in != null){
				in.close();
			}
		}
		// Set the flag on the processor, that all input has been processed. 
		p.processingDone();
	}

}
