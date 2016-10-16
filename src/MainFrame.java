import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;

public class MainFrame extends JFrame {
	
	private JPanel stockPanel;
	private JPanel contentPane;
	private JTable stockTable;
	private JCheckBox expiryViewCheck;
	private JComboBox<String> sortByCombo;
	private DefaultTableModel stockTableModel;
	private ArrayList<FoodItem> inventoryNoStock;
	private ArrayList<FoodStock> inventoryStock;
	
	private static String[] columnHeadersNoExp = new String[]{"Name", "Stock", "Popularity"};
	private static String[] columnHeadersExp = new String[]{"Name", "Stock", "Popularity", "Expiry"};
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Squirtle Squad Leos Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 692, 515);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenu mnTesting = new JMenu("Testing");
		menuBar.add(mnTesting);
		
		JMenuItem testRefreshStock = new JMenuItem("Refresh Stock");
		testRefreshStock.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				UpdateStock();
				System.out.println("BRAP");
			}
		});
		mnTesting.add(testRefreshStock);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "name_1040126215182");
		
		stockPanel = new JPanel();
		stockPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Stock", null, stockPanel, null);
		stockPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel sotckSidePanel = new JPanel();
		stockPanel.add(sotckSidePanel, BorderLayout.EAST);
		GridBagLayout gbl_sotckSidePanel = new GridBagLayout();
		gbl_sotckSidePanel.columnWidths = new int[]{73, 0};
		gbl_sotckSidePanel.rowHeights = new int[]{23, 0, 0, 0, 0, 0};
		gbl_sotckSidePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_sotckSidePanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		sotckSidePanel.setLayout(gbl_sotckSidePanel);
		
		expiryViewCheck = new JCheckBox("Expiry View");
		expiryViewCheck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				UpdateStock();
			}
		});
		GridBagConstraints gbc_expiryViewCheck = new GridBagConstraints();
		gbc_expiryViewCheck.anchor = GridBagConstraints.NORTHWEST;
		gbc_expiryViewCheck.insets = new Insets(0, 0, 5, 0);
		gbc_expiryViewCheck.gridx = 0;
		gbc_expiryViewCheck.gridy = 0;
		sotckSidePanel.add(expiryViewCheck, gbc_expiryViewCheck);
		
		JButton setLimitButton = new JButton("Set Restock Limit");
		GridBagConstraints gbc_setLimitButton = new GridBagConstraints();
		gbc_setLimitButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_setLimitButton.insets = new Insets(0, 0, 5, 0);
		gbc_setLimitButton.anchor = GridBagConstraints.NORTH;
		gbc_setLimitButton.gridx = 0;
		gbc_setLimitButton.gridy = 1;
		sotckSidePanel.add(setLimitButton, gbc_setLimitButton);
		
		JLabel sortByLabel = new JLabel("Sort By: ");
		GridBagConstraints gbc_sortByLabel = new GridBagConstraints();
		gbc_sortByLabel.anchor = GridBagConstraints.NORTH;
		gbc_sortByLabel.insets = new Insets(0, 0, 5, 0);
		gbc_sortByLabel.gridx = 0;
		gbc_sortByLabel.gridy = 2;
		sotckSidePanel.add(sortByLabel, gbc_sortByLabel);
		
		sortByCombo = new JComboBox<String>();
		sortByCombo.addItem("Test1");
		GridBagConstraints gbc_sortByCombo = new GridBagConstraints();
		gbc_sortByCombo.insets = new Insets(0, 0, 5, 0);
		gbc_sortByCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_sortByCombo.gridx = 0;
		gbc_sortByCombo.gridy = 3;
		sotckSidePanel.add(sortByCombo, gbc_sortByCombo);
		
		inventoryStock = new ArrayList<FoodStock>();
		
		stockTableModel = new DefaultTableModel(columnHeadersNoExp, 0);
		stockTable = new JTable(stockTableModel);
		JScrollPane stockScrollPanel = new JScrollPane(stockTable);
		stockPanel.add(stockScrollPanel, BorderLayout.CENTER);
		
		
		JPanel workerPanel = new JPanel();
		workerPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Worker", null, workerPanel, null);
	}
	
	/**
	 * Updates the stock panel to show new table and combo box.
	 */
	public void UpdateStock()
	{
		//Table
		stockTableModel.setColumnCount(0);
		String[] columns = columnHeadersExp;
		boolean exp = expiryViewCheck.isSelected();
		if(exp) {
			//Activate column
			stockTableModel.setColumnCount(0);
		} 
		else {
			//Deactivate column
			stockTableModel.setColumnCount(0);
			columns = columnHeadersNoExp;
		}
		stockTableModel.setColumnIdentifiers(columns);
		
		stockTableModel.setRowCount(0);
		/*
		for(int i = 0; i < inventoryStock.size(); i++) {
			if(exp)
			{
				stockTableModel.addRow(new String[]{inventoryStock.get(i).name, inventoryExp.get(i).popularity.toString(), inventoryExp.get(i).stock.toString(), inventoryExp.get(i).expiryDate.toString()});
			}
			else
			{
				stockTableModel.addRow(new String[]{inventoryExp.get(i).name, inventoryExp.get(i).popularity.toString(), inventoryExp.get(i).stock.toString()});
			}
		}
		*/
		//Combo Box
		sortByCombo.removeAllItems();
		
		for(int i = 0; i < columns.length; i++) {
			sortByCombo.addItem(columns[i]);
		}
		
		System.out.println("update stock called");
	}
}
