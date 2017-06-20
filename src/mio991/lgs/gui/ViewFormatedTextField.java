package mio991.lgs.gui;

import java.text.Format;

import javax.swing.JFormattedTextField;

public class ViewFormatedTextField extends JFormattedTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4747886251529722831L;
	private Object m_Model;
	
	public ViewFormatedTextField(Format format) {
		super(format);
	}

	/**
	 * @return the model
	 */
	public Object getModel() {
		return m_Model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Object model) {
		m_Model = model;
	}
	

}
