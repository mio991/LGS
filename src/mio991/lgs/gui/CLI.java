package mio991.lgs.gui;

import java.io.*;
import java.util.*;
import mio991.math.*;

public class CLI {
	
	private static Matrix s_Matrix;
	private static Matrix s_Vector;
	private static LinearSystem s_LinearSystem;

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
				System.out.println("What do you want to load? (MATRIX/vector/system)");
				switch(scanner.nextLine().toLowerCase())
				{
				case "":
				case "matrix":
					System.out.println("Please enter the path to the .mat File:");
					
					try {
						FileInputStream input = new FileInputStream(scanner.nextLine());
						
						s_Matrix = new Matrix(input);
						
						System.out.println(s_Matrix.toString());
						
						input.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					break;
				case "vector":
					System.out.println("Please enter the path to the .mat File:");
					
					try {
						FileInputStream input = new FileInputStream(scanner.nextLine());
						
						s_Vector = new Matrix(input);
						
						System.out.println(s_Vector.toString());
						
						input.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					break;
				case "system":
					System.out.println("Please enter the path to the .lgs File:");
					
					try {
						FileInputStream input = new FileInputStream(scanner.nextLine());
						
						s_LinearSystem = new LinearSystem(input);
						
						System.out.println(s_LinearSystem.toString());
						
						input.close();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
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
