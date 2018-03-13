import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

public class MainGUI {
	
	private static final String TITLE = "Excel Ping";
	
	private JFrame frame = new JFrame (TITLE);
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu ("File");
	private JMenuItem newFile = new JMenuItem ("New...");
	private JMenuItem openFile = new JMenuItem ("Open...");
	private JMenuItem quit = new JMenuItem ("Quit...");
	private JMenu helpMenu = new JMenu ("Help");
	private JMenuItem manual = new JMenuItem ("Open Manual");
	private JMenuItem about = new JMenuItem ("About");
	
	
	private JTable pingTable = new JTable (new pingTableModel());
	private JScrollPane tableScrollPane = new JScrollPane(pingTable);
	private JPanel panel = new JPanel();
	
	private JButton clearAll = new JButton("Clear All");
	private JButton pingAll = new JButton ("Ping All");
	
	private void adjustPingTable () {
		 TableColumn column = null;
		    for (int i = 0; i < 4; i++) {
		        column = pingTable.getColumnModel().getColumn(i);
		        
		        if (i == 1) {
		            column.setPreferredWidth(200); //address column
		        } else {
		            column.setPreferredWidth(120); 
		        }
		        
		        if (i == 3) {
		        	column.setPreferredWidth(220); //timestamp
		        }
		    }    
		
	}
	
	
	private void setActions() {
		
		pingAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pingTableModel model = (pingTableModel) pingTable.getModel();
				for (int i = 0; i < model.getRowCount(); i++) {
					model.updatePingStatus(i);
					model.fireTableDataChanged();
				}
			}
		});
		
		
		clearAll.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				pingTableModel model = (pingTableModel) pingTable.getModel();				
				model.deleteData();
				model.fireTableDataChanged();
			}
		});
		

		pingTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = pingTable.getSelectedRow();
					pingTableModel model = (pingTableModel) pingTable.getModel();
					model.updatePingStatus(row);
					model.fireTableDataChanged();
				}
			}
		});
		
		
	}
	
	private void setPanel() {
		//panel.setLayout(new SpringLayout());
		adjustPingTable();
		//panel.add(pingTable);
		
		
		
		panel.add(tableScrollPane);
//		panel.add(newAddress, BorderLayout.SOUTH);
		panel.add(clearAll, BorderLayout.SOUTH);
		panel.add(pingAll, BorderLayout.SOUTH);
		
		frame.getContentPane().add(panel);
	}
	
	private void createMenuBar() {
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		fileMenu.add(newFile);
		fileMenu.add(openFile);
		fileMenu.add(quit);
		helpMenu.add(manual);
		helpMenu.add(about);
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		
		
		frame.setJMenuBar(menuBar);
	}
	
	private void setGUIParameters() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(530, 550);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		createMenuBar();
		setPanel();
		setActions();
	}
	
	
	
	public MainGUI () {
		setGUIParameters();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setVisible(true);
		
	}
	public MainGUI (boolean newFile) {
		//updates GUI with information from the user's file. tmemporarily a boolean, please change
		
	}
	
}
