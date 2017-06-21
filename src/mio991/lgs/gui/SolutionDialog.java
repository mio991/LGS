package mio991.lgs.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import mio991.math.Matrix;

public class SolutionDialog extends JDialog {
	private MatrixView m_MatrixView;
	
	private SolutionDialog() {
		super();
		
		setTitle("Sollution");
		
		m_MatrixView = new MatrixView();
		getContentPane().add(m_MatrixView, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.add(panel_1, BorderLayout.NORTH);
		
		JButton btnAccept = new JButton("OK");
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		panel_1.add(btnAccept);
		btnAccept.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		m_MatrixView.setEditable(false);
	}

	private void close() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1008321997540489140L;
	
	private void setMatrix(Matrix val)
	{
		m_MatrixView.setMatrix(val);
	}
	
	public static void show(Matrix val)
	{
		SolutionDialog dialog = new SolutionDialog();
		
		dialog.setMatrix(val);
		
		dialog.pack();
		
		dialog.setSize(dialog.getPreferredSize());
		
		dialog.setVisible(true);
		
	}
}
