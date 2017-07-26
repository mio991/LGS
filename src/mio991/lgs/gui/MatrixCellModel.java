package mio991.lgs.gui;

import mio991.math.Matrix;

/**
 * A Model of a single field or cell in a matrix.
 * To use with the {@link ViewFormatedTextField}.
 * @author mio991
 *
 */
public class MatrixCellModel {
	private int m_Row;
	private int m_Column;
	private Matrix m_Matrix;
	
	/**
	 * Construct a Model of a single Matrix-field
	 * @param matrix The Matrix containing the field.
	 * @param row The row in which the field is.
	 * @param column The column in which the field is. 
	 */
	public MatrixCellModel(Matrix matrix, int row, int column) {
		m_Matrix = matrix;
		m_Row = row;
		m_Column = column;
	}

	/**
	 * Returns the current Value of the Field
	 */
	public double get()
	{
		return m_Matrix.get(m_Row, m_Column);
	}
	
	/**
	 * Set the value of the Field.
	 */
	public void set(double val)
	{
		m_Matrix.set(m_Row, m_Column, val);
	}

}
