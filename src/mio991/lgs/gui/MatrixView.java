package mio991.lgs.gui;

import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.text.*;

import mio991.math.Matrix;

public class MatrixView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 853596399033460281L;
	
	private boolean m_Editable = true;

	private Matrix m_Matrix;

	private static Format s_Format = new DecimalFormat();

	private static PropertyChangeListener s_ChangeListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent e) {
			if (e.getPropertyName() == "value" && e.getSource() instanceof ViewFormatedTextField) {
				ViewFormatedTextField tf = (ViewFormatedTextField) e.getSource();
				if (tf.getModel() instanceof MatrixCellModel && e.getNewValue() instanceof Number) {
					try {
						MatrixCellModel model = (MatrixCellModel) tf.getModel();
						double d = ((Number)e.getNewValue()).doubleValue();
						model.set(d);
					} catch (Exception ex) {
						System.err.println(ex.getMessage());
					}
				}
			}
		};
	};

	public MatrixView() {
		this.setLayout(new GridLayout(0, 1, 0, 0));
	}

	/**
	 * @return the matrix
	 */
	public Matrix getMatrix() {
		return m_Matrix;
	}

	/**
	 * @param matrix
	 *            the matrix to set
	 */
	public void setMatrix(Matrix matrix) {
		m_Matrix = matrix;

		this.removeAll();

		this.setLayout(new GridLayout(m_Matrix.getHeight(), m_Matrix.getWidth()));

		for (int k = 0; k < m_Matrix.getHeight(); k++) {
			for (int l = 0; l < m_Matrix.getWidth(); l++) {
				ViewFormatedTextField formattedTextField = new ViewFormatedTextField(s_Format);
				formattedTextField.setModel(new MatrixCellModel(m_Matrix, k, l));
				formattedTextField.addPropertyChangeListener(s_ChangeListener);
				formattedTextField.setValue(m_Matrix.get(k, l));
				formattedTextField.setHorizontalAlignment(JTextField.CENTER);
				formattedTextField.setEditable(this.isEditable());
				add(formattedTextField);
			}
		}

		this.doLayout();
	}

	/**
	 * Adds a column to the Matrix
	 */
	public void addColumn() {
		setMatrix(getMatrix().addColumn(new double[getMatrix().getHeight()]));
	}

	/**
	 * Adds a row to the Matrix
	 */
	public void addRow() {
		setMatrix(getMatrix().addRow(new double[getMatrix().getWidth()]));
	}
	
	public void removeRow()
	{
		setMatrix(getMatrix().removeLastRow());
	}
	
	public void removeColumn() {
		setMatrix(getMatrix().removeLastColumn());
	}
	
	public void setEditable(boolean t)
	{
		m_Editable = t;
		for (Component tf : getComponents()) {
			if(tf instanceof ViewFormatedTextField)
			{
				((ViewFormatedTextField)tf).setEditable(t);
			}
		}
	}

	public boolean isEditable() {
		return m_Editable;
	}
}
