package mio991.math;

import java.io.*;
import java.util.Scanner;

/**
 * A linear system, composed of a Matrix and a column vector.
 * @author mio991
 *
 */
public class LinearSystem {
	Matrix m_Coefficients;
	Matrix m_Constants;
	
	/**
	 * Creates a linear system from a nxn coefficients Matrix and an nx1 constants Vector.
	 * @param coefficients the nxn coefficients Matrix
	 * @param constants the nx1 constants Vector
	 */
	public LinearSystem(Matrix coefficients, Matrix constants) {
		m_Coefficients = coefficients;
		m_Constants = constants;
	}
	
	/**
	 * Access to the Coefficients Matrix 
	 * @return the Coefficients Matrix of this System
	 */
	public Matrix getCoefficients()
	{
		return m_Coefficients;
	}
	
	/**
	 * Access to the Constants Vector
	 * @return the Constants Vector of this System
	 */
	public Matrix getConstants()
	{
		return m_Constants;
	}
	
	/**
	 * Calculate the solution Vector for the linear system
	 * @return the solution Vector.
	 */
	public Matrix calcSollution()
	{
		if(m_Coefficients.getHeight() != m_Constants.getHeight())
		{
			throw new IllegalArgumentException(Messages.getString("LinearSystem.0")); //$NON-NLS-1$
		}
		
		Matrix coefficents = new Matrix(m_Coefficients);
		Matrix constants = new Matrix(m_Constants);
		
		while(coefficents.getHeight() < coefficents.getWidth())
		{
			coefficents = coefficents.addRow(new double[coefficents.getWidth()]);
			constants = constants.addRow(new double[constants.getWidth()]);
		}
		
		return Matrix.gauss(coefficents ,constants);
	}

	/**
	 * Reads an linear System from the input Stream.
	 * 
	 * Expects the Coefficients to come first then the Constants.
	 * It's required, that the Coefficients Matrix and the Constants 
	 * Matrix are separated by a line break. Which is the <STRONG>ONLY</STRONG> one in the File.
	 *  
	 * @param input An InputStream providing the matrices for the linear system.
	 * @throws IOException 
	 */
	public LinearSystem(InputStream input) {		
		Scanner sc = new Scanner(input);
		
		String m1 = sc.nextLine();
		String m2 = sc.nextLine();
		
		sc.close();
		
		m_Coefficients = new Matrix(new ByteArrayInputStream(m1.getBytes()));
		m_Constants = new Matrix(new ByteArrayInputStream(m2.getBytes()));
	}
	
	/**
	 * Saves the linear System to the  OutputStream.
	 * @param output The OutputStream to write to.
	 * @throws IOException 
	 */
	public void save(OutputStream output) throws IOException
	{
		ByteArrayOutputStream o1 = new ByteArrayOutputStream();
		ByteArrayOutputStream o2 = new ByteArrayOutputStream();
		
		m_Coefficients.save(o1);
		m_Constants.save(o2);
		
		output.write(o1.toByteArray());
		output.write('\n');
		output.write(o2.toByteArray());
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof LinearSystem))
		{
			return false;
		}
		LinearSystem sys = (LinearSystem)obj;
		
		return this.m_Coefficients.equals(sys.m_Coefficients) && this.m_Constants.equals(sys.m_Constants);
	}
	
	@Override
	public String toString() {
		return Messages.getString("LinearSystem.1") + m_Coefficients.toString() + Messages.getString("LinearSystem.2") + m_Constants.toString(); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
