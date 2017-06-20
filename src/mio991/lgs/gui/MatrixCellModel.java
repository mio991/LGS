package mio991.lgs.gui;

import mio991.math.Matrix;

public class MatrixCellModel {
	private int m_Row;
	private int m_Column;
	private Matrix m_Matrix;
	
	public MatrixCellModel(Matrix matrix, int row, int column) {
		m_Matrix = matrix;
		m_Row = row;
		m_Column = column;
	}
	
	public double get()
	{
		return m_Matrix.get(m_Row, m_Column);
	}
	
	public void set(double val)
	{
		m_Matrix.set(m_Row, m_Column, val);
	}

}
