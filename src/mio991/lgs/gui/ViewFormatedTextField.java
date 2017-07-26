package mio991.lgs.gui;

import java.text.Format;

import javax.swing.JFormattedTextField;

/**
 * An extension of the basic {@link JFormattedTextField} to hold a Model for use in event handlers.
 * @author mio991
 *
 */
public class ViewFormatedTextField extends JFormattedTextField {

	private static final long serialVersionUID = 4747886251529722831L;
	private Object m_Model;
	
	/**
	 * @see javax.swing.JFormattedTextField#JFormattedTextField(Format format)
	 */
	public ViewFormatedTextField(Format format) {
		super(format);
	}

	/**
	 * Returns the model currently used.
	 */
	public Object getModel() {
		return m_Model;
	}

	/**
	 * Set the currently used model.
	 * 
	 * @param The new Model to use.
	 */
	public void setModel(Object model) {
		m_Model = model;
	}
	

}
