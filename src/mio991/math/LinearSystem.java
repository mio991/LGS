package mio991.math;

import static mio991.math.Matrix.*;

import java.io.*;
import java.util.Scanner;

import mio991.io.UncloesableInputStream;

public class LinearSystem {
	Matrix m_Coefficients;
	Matrix m_Constants;
	
	/**
	 * Creates a linear system from a nxn coefficients Matrix and an nx1 constants Vector.
	 * @param coefficients the nxn coefficients Matrix
	 * @param constants the nx1 constants Vector
	 */
	public LinearSystem(Matrix coefficients, Matrix constants) {
		assert coefficients.getWidth() == constants.getHeight();
		assert coefficients.getHeight() == coefficients.getWidth();
		assert coefficients.det() != 0.0;
		
		m_Coefficients = coefficients;
		m_Constants = constants;
	}
	
	/**
	 * Calculate the solution Vector for the linear system
	 * @return the solution Vector.
	 */
	public Matrix calcSollution()
	{
		return multiply(m_Coefficients.inverse(), m_Constants);
	}

	/**
	 * Reads an linear System from the input Stream.
	 * 
	 * Expects the Coefficients to come first then the Constants.
	 * It's required, that the Coefficients Matrix and the Constants 
	 * Matrix are separated by a line break. Which is the <STRONG>ONLY</STRONG> one in the File.
	 *  
	 * @param input An {@link InputStream} providing the matrices for the linear system.
	 * @throws IOException 
	 */
	public LinearSystem(InputStream input) {
		input = new UncloesableInputStream(input);
		
		Scanner sc = new Scanner(input);
		
		String m1 = sc.nextLine();
		String m2 = sc.nextLine();
		
		sc.close();
		
		m_Coefficients = new Matrix(new ByteArrayInputStream(m1.getBytes()));
		m_Constants = new Matrix(new ByteArrayInputStream(m2.getBytes()));
		
		assert m_Coefficients.getWidth() == m_Coefficients.getHeight();
		assert m_Coefficients.getWidth() == m_Constants.getHeight();
		assert m_Coefficients.det() != 0;
	}
	
	/**
	 * Saves the linear System to the {@link OutputStream}.
	 * @param output The {@link OutputStream} to write to.
	 */
	public void save(OutputStream output)
	{
		m_Coefficients.save(output);
		m_Constants.save(output);
	}
	
	@Override
	public String toString() {
		return "Coefficients:\n" + m_Coefficients.toString() + "\nConstants\n" + m_Constants.toString();
	}
}
