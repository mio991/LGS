package mio991.lgs.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import mio991.math.LinearSystem;
import mio991.math.Matrix;

/**
 * The Main Window for the UI of the linear system solver.
 * @author mio991
 *
 */
public class Window {

	private JFrame frmLinearSystemSolver;
	private MatrixView m_MatrixView;
	private MatrixView m_VectorView;
	
	private LinearSystem m_LinearSystem = new LinearSystem(new Matrix(3), new Matrix(3, 1));
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frmLinearSystemSolver.setVisible(true);
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
	
	/**
	 * Opens a Linear System asking the user for the file.
	 */
	public void open()
	{
		JFileChooser fileChooser = new JFileChooser();
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			open(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	/**
	 * Opens a Linear System from the specified file.
	 * @param file The File to open.
	 */
	public void open(String file)
	{
		try {
			m_LinearSystem = new LinearSystem(new FileInputStream(file));
			m_MatrixView.setMatrix(m_LinearSystem.getCoefficients());
			m_VectorView.setMatrix(m_LinearSystem.getConstants());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Saves a Linear System asking the user for the file.
	 */
	public void save()
	{
		JFileChooser fileChooser = new JFileChooser();
		if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			save(fileChooser.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 * Saves a Linear System from the specified file.
	 * @param path The File to write.
	 */
	public void save(String path)
	{
		try {
			m_LinearSystem.save(new FileOutputStream(path));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLinearSystemSolver = new JFrame();
		frmLinearSystemSolver.setTitle("Linear System Solver");
		frmLinearSystemSolver.setBounds(100, 100, 500, 350);
		frmLinearSystemSolver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmLinearSystemSolver.setJMenuBar(menuBar);
		
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
				save();
			}
		});
		mnFile.add(mntmSave);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		frmLinearSystemSolver.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		frmLinearSystemSolver.getContentPane().add(panel_2, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{242, 242, 0};
		gbl_panel_2.rowHeights = new int[]{23, 23, 23, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JButton btnAddColumn = new JButton("Add Column");
		btnAddColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_MatrixView.addColumn();
				m_LinearSystem = new LinearSystem(m_MatrixView.getMatrix(), m_LinearSystem.getConstants());
			}
		});
		
		JButton btnAddRow = new JButton("Add Row");
		btnAddRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_MatrixView.addRow();
				m_VectorView.addRow();
				m_LinearSystem = new LinearSystem(m_MatrixView.getMatrix(), m_VectorView.getMatrix());
			}
		});
		GridBagConstraints gbc_btnAddRow = new GridBagConstraints();
		gbc_btnAddRow.fill = GridBagConstraints.BOTH;
		gbc_btnAddRow.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddRow.gridx = 0;
		gbc_btnAddRow.gridy = 0;
		panel_2.add(btnAddRow, gbc_btnAddRow);
		GridBagConstraints gbc_btnAddColumn = new GridBagConstraints();
		gbc_btnAddColumn.fill = GridBagConstraints.BOTH;
		gbc_btnAddColumn.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddColumn.gridx = 1;
		gbc_btnAddColumn.gridy = 0;
		panel_2.add(btnAddColumn, gbc_btnAddColumn);
		
		JButton btnRemoveRow = new JButton("Remove Row");
		btnRemoveRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_MatrixView.removeRow();
				m_VectorView.removeRow();
				m_LinearSystem = new LinearSystem(m_MatrixView.getMatrix(), m_VectorView.getMatrix());
			}
		});
		GridBagConstraints gbc_btnRemoveRow = new GridBagConstraints();
		gbc_btnRemoveRow.fill = GridBagConstraints.BOTH;
		gbc_btnRemoveRow.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveRow.gridx = 0;
		gbc_btnRemoveRow.gridy = 1;
		panel_2.add(btnRemoveRow, gbc_btnRemoveRow);
		
		JButton btnRemoveColumn = new JButton("Remove Column");
		btnRemoveColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_MatrixView.removeColumn();
				m_LinearSystem = new LinearSystem(m_MatrixView.getMatrix(), m_VectorView.getMatrix());
			}
		});
		GridBagConstraints gbc_btnRemoveColumn = new GridBagConstraints();
		gbc_btnRemoveColumn.fill = GridBagConstraints.BOTH;
		gbc_btnRemoveColumn.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemoveColumn.gridx = 1;
		gbc_btnRemoveColumn.gridy = 1;
		panel_2.add(btnRemoveColumn, gbc_btnRemoveColumn);
		
		JButton btnSolve = new JButton("Solve");
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					SolutionDialog.show(m_LinearSystem.calcSollution());
				}
				catch(IllegalArgumentException ex)
				{
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_btnSolve = new GridBagConstraints();
		gbc_btnSolve.gridwidth = 2;
		gbc_btnSolve.fill = GridBagConstraints.BOTH;
		gbc_btnSolve.gridx = 0;
		gbc_btnSolve.gridy = 2;
		panel_2.add(btnSolve, gbc_btnSolve);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.8);
		splitPane.setEnabled(false);
		frmLinearSystemSolver.getContentPane().add(splitPane);
		
		JPanel panelCoefficients = new JPanel();
		splitPane.setLeftComponent(panelCoefficients);
		panelCoefficients.setLayout(new BorderLayout(0, 0));
		
		JLabel lblCoefficients = new JLabel("Coefficients");
		panelCoefficients.add(lblCoefficients, BorderLayout.NORTH);
		
		m_MatrixView = new MatrixView();
		panelCoefficients.add(m_MatrixView);
		m_MatrixView.setMatrix(m_LinearSystem.getCoefficients());
		
		JPanel panelConstants = new JPanel();
		splitPane.setRightComponent(panelConstants);
		panelConstants.setLayout(new BorderLayout(0, 0));
		
		JLabel lblConstants = new JLabel("Constants");
		panelConstants.add(lblConstants, BorderLayout.NORTH);
		
		m_VectorView = new MatrixView();
		panelConstants.add(m_VectorView);
		m_VectorView.setMatrix(m_LinearSystem.getConstants());
	}

}
