package sb;import org.apache.commons.cli.*;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;
import org.apache.commons.cli.*;

public class ProcessInput {

	public static void main(String[] args) {
		Options options = new Options();
       Option input = new Option("u", "users", true, "input users file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("g", "groups", true, "input groups file path");
        output.setRequired(true);
        options.addOption(output);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
        }

        String usersFilePath = cmd.getOptionValue("users");
        String groupsFilePath = cmd.getOptionValue("groups");
		
		Processor p = new Processor();
		//Saving the group to user information int he processor's map
		processGroupInput(p, groupsFilePath);
	
		// saving the user to group information in the processor's queue
		processUserInput(p, usersFilePath);
    	
	}

	private static void processGroupInput(Processor p, String groupsFilePath) {
		Scanner in= null;
		try {
//			in = new Scanner(new File("groups.txt"));
			in = new Scanner(new File(groupsFilePath));
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
	
	private static void processUserInput(Processor p, String usersFilePath){
		//Starting a parallel thread that checks if each user in the processor's user map has a group or not and prints their id if not
		p.start();
		Scanner in= null;
		try {
//			in = new Scanner(new File("users.txt"));
			in = new Scanner(new File(usersFilePath));
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
