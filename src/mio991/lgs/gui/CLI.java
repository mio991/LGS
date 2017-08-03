package mio991.lgs.gui;

import java.io.*;
import java.util.*;
import mio991.math.*;

/**
 * A Command Line Interface for this linear system solver.
 * @author mio991
 *
 */
public class CLI {

	private static Matrix s_Matrix;
	private static Matrix s_Vector;
	private static LinearSystem s_LinearSystem;

	/**
	 * Runs a CLI for this linear system solver.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("########################################\n" + "#    Linear System Solver v0.4 CLI     #\n"
				+ "########################################\n" + "\n" + "For help type 'help'. ;-P");

		boolean runing = true;

		while (runing) {
			try {
				System.out.println("\nPlease type in a command to execute:");
				switch (scanner.nextLine().toLowerCase()) {
				case "help":
					System.out.println("\n" + "You can use these Commands:\n" + "\thelp\tthis help Text\n"
							+ "\tnew\tcreate a new Matrix, Vector or linear System, overrides the current one!\n"
							+ "\tview\tprint the Matrix or Vector"
							+ "\tload\tload a Matrix, Vector or linear System from the Filesystem\n"
							+ "\tsave\tsaves a Matrix, Vector or linear System to the Filesystem\n"
							+ "\texit\texit this Programm");
					break;
				case "new":
					System.out.println("Waht do you want to create? (MATRIX/vector/system)");
					switch (scanner.nextLine().toLowerCase()) {
					case "":
					case "matrix":
						System.out.println("Please enter the Dimensions for the new Matrix:");
						setMatrix(new Matrix(scanner.nextInt(), scanner.nextInt()));
						break;
					case "vector":
						System.out.println("Please enter the dimension of the Vector:");
						setVector(new Matrix(scanner.nextInt(), 1));
						break;
					case "system":
						if (getMatrix() == null) {
							System.out.println("There is no useable Matrix!");
							break;
						}
						if (getVector() == null) {
							System.out.println("There is no useable Vector!");
							break;
						}

						System.out.println("Creating new linear System from Matrix and Vector...");
						s_LinearSystem = new LinearSystem(getMatrix(), getVector());
						break;
					}
					break;
				case "view":
					System.out.println("What do you want to see? (MATRIX/vector)");
					switch (scanner.nextLine().toLowerCase()) {
					case "":
					case "matrix":
						System.out.println(s_Matrix.toString());
						break;
					case "vector":
						System.out.println(s_Vector.toString());
						break;
					}
					break;
				case "set":
					System.out.println("What do want to alter?(MATRIX/vector)");
					switch (scanner.nextLine().toLowerCase()) {
					case "":
					case "matrix":
						System.out.println("Which field do you want to alter? (k, l) = v");
						s_Matrix.set(scanner.nextInt() - 1, scanner.nextInt() - 1, scanner.nextDouble());
						break;
					case "vector":
						System.out.println("Which field do you want to alter? (i) = v");
						s_Vector.set(scanner.nextInt() - 1, 0, scanner.nextDouble());
						break;
					}
					break;
				case "solve":
					if (s_LinearSystem == null) {
						System.err.println("No linear System to solve!");
					}
					System.out.println(s_LinearSystem.calcSollution());
					break;
				case "save":
					System.out.println("What do you want to save? (MATRIX/vector/system)");
					String choice = scanner.nextLine().toLowerCase();
					try {
						System.out.println("Where do you want to save the File:");
						FileOutputStream output = new FileOutputStream(scanner.nextLine());
						switch (choice) {
						case "":
						case "matrix":
							getMatrix().save(output);
							break;
						case "vector":
							getVector().save(output);
							break;
						case "system":
							s_LinearSystem.save(output);
							break;
						}
						output.close();
					} catch (IOException e) {
						System.err.println(e.getLocalizedMessage());
					}
					break;
				case "load":
					System.out.println("What do you want to load? (MATRIX/vector/system)");
					switch (scanner.nextLine().toLowerCase()) {
					case "":
					case "matrix":
						System.out.println("Please enter the path to the .mat File:");

						try {
							FileInputStream input = new FileInputStream(scanner.nextLine());

							setMatrix(new Matrix(input));

							System.out.println(getMatrix().toString());

							input.close();
						} catch (IOException e) {
							System.out.println(e.getLocalizedMessage());
						}
						break;
					case "vector":
						System.out.println("Please enter the path to the .mat File:");

						try {
							FileInputStream input = new FileInputStream(scanner.nextLine());

							setVector(new Matrix(input));

							System.out.println(getVector().toString());

							input.close();
						} catch (IOException e) {
							System.err.println(e.getLocalizedMessage());
						}
						break;
					case "system":
						System.out.println("Please enter the path to the .lgs File:");

						try {
							FileInputStream input = new FileInputStream(scanner.nextLine());

							s_LinearSystem = new LinearSystem(input);

							setMatrix(s_LinearSystem.getCoefficients());
							setVector(s_LinearSystem.getConstants());

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
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
		}

		System.out.println("\nGoodbye!!!");

		scanner.close();
	}

	/**
	 * @return the s_Matrix
	 */
	private static Matrix getMatrix() {
		return s_Matrix;
	}

	/**
	 * @param s_Matrix
	 *            the s_Matrix to set
	 */
	private static void setMatrix(Matrix s_Matrix) {
		CLI.s_Matrix = s_Matrix;
	}

	/**
	 * @return the s_Vector
	 */
	private static Matrix getVector() {
		return s_Vector;
	}

	/**
	 * @param s_Vector
	 *            the s_Vector to set
	 */
	private static void setVector(Matrix s_Vector) {
		CLI.s_Vector = s_Vector;
	}
}
