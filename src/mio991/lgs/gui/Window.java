package mio991.lgs.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import mio991.math.Matrix;

public class Window {

	private JFrame frame;
	private MatrixView m_MatrixView;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}
	
	public void open()
	{
		JFileChooser fileChooser = new JFileChooser();
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			open(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	public void open(String file)
	{
		try {
			m_MatrixView.setMatrix(new Matrix(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void save()
	{
		JFileChooser fileChooser = new JFileChooser();
		if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			save(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	public void save(String path)
	{
		try {
			m_MatrixView.getMatrix().save(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntOpen = new JMenuItem("Open");
		mntOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				open();
			}
		});
		mnFile.add(mntOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		mnFile.add(mntmSave);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		m_MatrixView = new MatrixView();
		frame.getContentPane().add(m_MatrixView, BorderLayout.CENTER);
		
		MatrixView m_VectorView = new MatrixView();
		frame.getContentPane().add(m_VectorView, BorderLayout.EAST);
	}

}
