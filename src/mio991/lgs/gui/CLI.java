package mio991.lgs.gui;

import java.util.*;
import mio991.math.*;

public class CLI {
	
	private static Matrix s_Matrix;
	private static LGS s_linearSystem;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println(
				"########################################\n" +
				"#    Linear System Solver v0.4 CLI     #\n" + 
				"########################################\n" +
				"\n" +
				"For help type 'help'. ;-P"
				);
		
		boolean runing = true;
		
		while(runing)
		{
			System.out.println("\nPlease type in a command to execute:");
			switch(scanner.nextLine().toLowerCase())
			{
			case "help":
				System.out.println( "\n" +
						"You can use these Commands:\n"+
						"\thelp\tthis help Text\n" +
						"\texit\texit this Programm"
						);
				break;
			case "load":
				System.out.println("What do you want to load? (MATRIX/system)");
				switch(scanner.nextLine().toLowerCase())
				{
				case "":
				case "matrix":
					
					break;
				case "system":
					
					break;
				default:
					System.out.println("Sorry, I couldn't understand you.");
					break;
				}
				break;
			case "q":
			case "exit":
			case "quit":
				runing = false;
				break;
			default:
				System.out.println("Try 'help' for help.");
				break;
			}
		}
		
		System.out.println("\nGoodbye!!!");
		
		scanner.close();
	}
}
